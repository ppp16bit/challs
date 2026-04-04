package storage

import (
	"sync"

	"github.com/ppp16bit/challs/internal/transaction"
)

const maxSize = 10_000

type CircularBuffer struct {
	data  [maxSize]transaction.Request
	head  int
	count int
	mu    sync.Mutex
}

func (cb *CircularBuffer) Add(tx transaction.Request) {
	cb.mu.Lock()
	defer cb.mu.Unlock()

	cb.data[cb.head%maxSize] = tx
	cb.head++
	if cb.count < maxSize {
		cb.count++
	}
}

func (cb *CircularBuffer) Len() int {
	cb.mu.Lock()
	defer cb.mu.Unlock()
	return cb.count
}
