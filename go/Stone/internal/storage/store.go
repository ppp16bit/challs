package storage

import (
	"sync"

	"github.com/ppp16bit/challs/internal/transaction"
)

type Store struct {
	mu           sync.RWMutex
	transactions []transaction.Request
}

func NewStore() *Store {
	return &Store{}
}

func (s *Store) Save(tx transaction.Request) {
	s.mu.Lock()
	defer s.mu.Unlock()
	s.transactions = append(s.transactions, tx)
}

func (s *Store) All() []transaction.Request {
	s.mu.RLock()
	defer s.mu.RUnlock()
	return s.transactions
}
