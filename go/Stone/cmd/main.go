package main

import (
	"log"
	"net/http"
	"os"
	"strconv"

	"github.com/ppp16bit/challs/handler"
	"github.com/ppp16bit/challs/internal/fraud"
	"github.com/ppp16bit/challs/internal/storage"
	"github.com/ppp16bit/challs/internal/worker"
)

func main() {
	workerCount, err := strconv.Atoi(os.Getenv("WORKER_COUNT"))
	if err != nil || workerCount <= 0 {
		workerCount = 4
	}

	buffer := &storage.CircularBuffer{}
	detector := fraud.NewDetector()
	pool := worker.NewPool(workerCount, 1000, detector, buffer)
	pool.Start()
	h := handler.New(pool)

	http.HandleFunc("POST /transactions", h.ProcessTransaction)
	http.HandleFunc("GET /metrics", h.Metrics)

	log.Println("server started on port :8080 :>")
	log.Fatal(http.ListenAndServe(":8080", nil))
}
