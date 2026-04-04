package handler

import (
	"encoding/json"
	"errors"
	"net/http"

	"github.com/ppp16bit/challs/internal/transaction"
	"github.com/ppp16bit/challs/internal/worker"
)

type Handler struct {
	pool *worker.Pool
}

func New(pool *worker.Pool) *Handler {
	return &Handler{pool: pool}
}

func (h *Handler) Metrics(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(h.pool.Metrics())
}

func respond(w http.ResponseWriter, code int, body any) {
	w.WriteHeader(code)
	json.NewEncoder(w).Encode(body)
}

func (h *Handler) ProcessTransaction(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")

	var request transaction.Request
	if err := json.NewDecoder(r.Body).Decode(&request); err != nil {
		respond(w, http.StatusUnprocessableEntity, transaction.Response{
			Status: "rejected",
			Error:  "invalid payload",
		})
		return
	}

	if err := transaction.Validate(request); err != nil {
		errMsg := err.Error()
		if !errors.Is(err, transaction.ErrTimestampInvalid) && !errors.Is(err, transaction.ErrTimestampFuture) {
			errMsg = "invalid payload"
		}

		respond(w, http.StatusUnprocessableEntity, transaction.Response{
			Status: "rejected",
			Error:  errMsg,
		})
		return
	}

	resultCh := make(chan transaction.Response, 1)
	submitted := h.pool.Submit(worker.Job{
		Request:  request,
		Response: resultCh,
	})

	if !submitted {
		respond(w, http.StatusTooManyRequests, transaction.Response{
			Status: "rejected",
			Error:  "too many requests, try again later",
		})
		return
	}
	result := <-resultCh
	respond(w, http.StatusCreated, result)
}
