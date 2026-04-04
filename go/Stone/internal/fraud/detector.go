package fraud

import (
	"sync"
	"time"

	"github.com/ppp16bit/challs/internal/transaction"
)

const (
	maxAmount  = 10_000.0
	maxPerMin  = 5
	windowSecs = 60 * time.Second
)

type Detector struct {
	mu      sync.Mutex
	history map[string][]time.Time
	alerts  []transaction.Request
}

func NewDetector() *Detector {
	return &Detector{
		history: make(map[string][]time.Time),
	}
}

func (d *Detector) record(request transaction.Request) {
	d.mu.Lock()
	defer d.mu.Unlock()
	d.history[request.CardNumber] = append(d.history[request.CardNumber], time.Now())
}

func (d *Detector) Check(request transaction.Request) string {
	if request.Amount > maxAmount {
		d.record(request)
		return "transaction marked as suspicious: high amount"
	}

	if d.exceedsFrequency(request.CardNumber) {
		d.record(request)
		return "transaction marked as suspicious: not standard"
	}

	d.record(request)
	return ""
}

func (d *Detector) Alerts() []transaction.Request {
	d.mu.Lock()
	defer d.mu.Unlock()
	return d.alerts
}

func (d *Detector) SaveAlert(request transaction.Request) {
	d.mu.Lock()
	defer d.mu.Unlock()
	d.alerts = append(d.alerts, request)
}

func (d *Detector) exceedsFrequency(card string) bool {
	d.mu.Lock()
	defer d.mu.Unlock()

	cutoff := time.Now().Add(-windowSecs)
	recent := d.history[card][:0]

	for _, t := range d.history[card] {
		if t.After(cutoff) {
			recent = append(recent, t)
		}
	}
	d.history[card] = recent
	return len(recent) >= maxPerMin
}
