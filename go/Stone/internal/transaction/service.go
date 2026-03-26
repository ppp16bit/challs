package transaction

import (
	"errors"
	"time"
)

var (
	ErrInvalidPayload   = errors.New("invalid payload")
	ErrTimestampInvalid = errors.New("timestamp not valid")
	ErrTimestampFuture  = errors.New("timestamp on the future")
)

func Validate(request Request) error {
	if request.CardNumber == "" || request.Amount <= 0 || request.Currency == "" || request.Merchant == "" {
		return ErrInvalidPayload
	}

	if request.Timestamp.IsZero() {
		return ErrTimestampInvalid
	}

	if request.Timestamp.After(time.Now()) {
		return ErrTimestampFuture
	}
	return nil
}
