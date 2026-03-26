package handler

import (
	"encoding/json"
	"errors"
	"net/http"

	"github.com/ppp16bit/challs/internal/storage"
	"github.com/ppp16bit/challs/internal/transaction"

	"github.com/google/uuid"
)

type Handler struct {
	store *storage.Store
}

func New(store *storage.Store) *Handler {
	return &Handler{store: store}
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
			Status: "reject",
			Erorr:  "invalid payload",
		})
		return
	}

	if err := transaction.Validate(request); err != nil {
		status := "rejected"
		errMsg := err.Error()

		switch {
		case errors.Is(err, transaction.ErrTimestampInvalid):
			respond(w, http.StatusUnprocessableEntity, transaction.Response{Status: status, Erorr: errMsg})
		case errors.Is(err, transaction.ErrTimestampFuture):
			respond(w, http.StatusUnprocessableEntity, transaction.Response{Status: status, Erorr: errMsg})
		default:
			respond(w, http.StatusUnprocessableEntity, transaction.Response{Status: status, Erorr: errMsg})
		}
		return
	}

	h.store.Save(request)
	respond(w, http.StatusCreated, transaction.Response{
		Status:      "approved",
		AuthorizeID: uuid.NewString(),
	})
}
