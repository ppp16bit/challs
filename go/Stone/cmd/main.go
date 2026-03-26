package main

import (
	"log"
	"net/http"

	"github.com/ppp16bit/challs/handler"
	"github.com/ppp16bit/challs/internal/storage"
)

func main() {
	store := storage.NewStore()
	h := handler.New(store)
	http.HandleFunc("POST /transactions", h.ProcessTransaction)
	log.Println("server started on port :8080 :>")
	log.Fatal(http.ListenAndServe(":8080", nil))
}
