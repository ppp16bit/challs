package transaction

import "time"

type Request struct {
	CardNumber string    `json:"card_number"`
	Amount     float64   `json:"amount"`
	Currency   string    `json:"currency"`
	Merchant   string    `json:"merchant"`
	Timestamp  time.Time `json:"timestamp"`
}

type Response struct {
	Status      string `json:"status"`
	AuthorizeID string `json:"authorize_id,omitempty"`
	Erorr       string `json:"error,omitempty"`
}
