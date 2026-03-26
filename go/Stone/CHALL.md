## Phase 1: Credit Card Transaction Processor

### Context
You've been hired by Stone Pagamento to paint the world green. Stone offers a credit card service with exclusive benefits for its customers, such as cashback on purchases and reduced fees. The company's success depends on an efficient and reliable transaction processing system.

Your role as a software engineer is to develop a central transaction processing module. This module will be responsible for validating and recording transactions made by customers in real time. The reliability and accuracy of this system are essential to prevent fraud, ensure a smooth user experience, and meet financial industry standards.

The Stone team is facing the following challenge: processing large volumes of transactions, ensuring that only valid operations are recorded and that customers are notified immediately if something goes wrong.

#### Requirements

1. **Transaction Input:**

- The transaction will be represented by a struct containing the data below:

- Example of JSON:

```json
{
"card_number": "4111111111111111",
"amount": 100.50,
"currency": "USD",
"merchant": "Amazon",
"timestamp": "2025-01-17T10:00:00Z"
}

```

2. **Validations:**

- The timestamp must be valid and cannot be in the future and must be in the RFC-3339 format.

- Error message if not RFC-3339: `timestamp not valid`

- Error message if after today: `timestamp on the future`

- For any other error:

- Error message: `invalid payload`

3. **Transaction Log:**

- Use a memory structure such as a slice or map to simulate the database.

- Store only valid transactions.

4. **Answer:**

- For valid transactions, a struct with the following information must be provided:

```json
{
"status": "approved",
"authorize_id": "123e4567-e89b-12d3-a456-426614174000"
}
```
- For rejected transactions:
```json
{
"status": "rejected",
"error": "invalid payload"

}
```

## Phase 2: Detection of Fraudulent Transactions

### Context
Congratulations! The authorization system you developed was a success. Customers were satisfied, and Stone managed to register thousands of transactions without problems. However, the increase in the volume of operations also attracted the attention of fraudsters, who try to exploit the system to carry out illicit transactions.

Now, your next challenge is to enhance the transaction processing module by adding a mechanism to detect suspicious activity. This mechanism should identify potential fraud based on simple rules and log alerts for the monitoring team.

### New Requirements

1. **Suspicious Transaction Detection**
- Implement an additional validation that marks transactions as "suspicious" based on the following conditions:

- Very high transactions: Any transaction above **$10,000** should be marked as suspicious: *high amount*

- Non-standard purchases: If the same card number makes more than 5 transactions in less than 1 minute, the additional transactions should be marked as suspicious. *not standard*

2. **Response Change**

- For suspicious transactions:

- Transactions should still be approved, but with a warning.

- The response should include a field indicating the "suspicious" status.

- Example of a response:
```json
{
"status": "approved_with_warning",
"transaction_id": "123e4567-e89b-12d3-a456-426614174000",
"warning": "transaction marked as suspicious: high amount"
}
```

3. **Alert Log**
- Transactions that contain risk should be stored in a database (slice or map).

## Phase 3: High-Scale Processing with Concurrency and Memory Optimization

### Context
Stone's success is expanding globally, and the volume of transactions has increased exponentially. Now, the system needs to process thousands of transactions per second efficiently, without consuming unnecessary resources.

The team identified two major challenges:

1. **Low performance**: Current processing does not scale well with the increase in the number of simultaneous transactions.

2. **Excessive Memory Consumption**: The use of in-memory data structures has grown significantly, impacting operational costs.

Your mission is to improve the system to handle high-scale transactions, using the principles of concurrency and memory optimization.

### New Requirements

1. **Optimized Persistence**
- It has been identified that it is not necessary to store all transactions, only the last 10,000 transactions.

- Replace the storage of valid transactions in memory with a cyclic buffer (circular buffer):

- The buffer should store the
