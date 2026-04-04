package worker

import (
	"sync/atomic"

	"github.com/ppp16bit/challs/internal/fraud"
	"github.com/ppp16bit/challs/internal/storage"
	"github.com/ppp16bit/challs/internal/transaction"
)

type Job struct {
	Request  transaction.Request
	Response chan transaction.Response
}

type Pool struct {
	jobs        chan Job
	workerCount int
	detector    *fraud.Detector
	buffer      *storage.CircularBuffer
	processed   atomic.Int64
	rejected    atomic.Int64
	active      atomic.Int64
}

func NewPool(workerCount, queueSize int, detector *fraud.Detector, buffer *storage.CircularBuffer) *Pool {
	return &Pool{
		jobs:        make(chan Job, queueSize),
		workerCount: workerCount,
		detector:    detector,
		buffer:      buffer,
	}
}

func (p *Pool) Start() {
	for i := 0; i < p.workerCount; i++ {
		p.active.Add(1)
		go p.run()
	}
}

func (p *Pool) Submit(j Job) bool {
	select {
	case p.jobs <- j:
		return true
	default:
		p.rejected.Add(1)
		return false
	}
}

func (p *Pool) Metrics() map[string]int64 {
	return map[string]int64{
		"processed_transactions": p.processed.Load(),
		"rejected_transactions":  p.rejected.Load(),
		"queue_usage":            int64(len(p.jobs)),
		"active_workers":         p.active.Load(),
	}
}

func (p *Pool) process(job Job) {
	warning := p.detector.Check(job.Request)
	if warning != "" {
		p.detector.SaveAlert(job.Request)

		job.Response <- transaction.Response{
			Status:        "approved_with_warning",
			TransactionID: newID(),
			Warning:       warning,
		}
		return
	}
	p.buffer.Add(job.Request)
	job.Response <- transaction.Response{
		Status:      "approved",
		AuthorizeID: newID(),
	}
}

func (p *Pool) run() {
	for job := range p.jobs {
		p.process(job)
		p.processed.Add(1)
	}
}
