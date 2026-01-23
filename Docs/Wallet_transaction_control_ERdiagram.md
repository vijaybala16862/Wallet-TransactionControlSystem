# Wallet Transaction Control System – ER Diagram

```mermaid
erDiagram
    USERS {
        INT id PK
        STRING username
        STRING password
        DATETIME created_at
    }

    WALLETS {
        STRING wallet_id PK
        INT user_id FK
        DOUBLE balance
        STRING status
    }

    WALLET_TRANSACTIONS {
        INT txn_id PK
        STRING wallet_id FK
        STRING txn_type
        DOUBLE amount
        DOUBLE balance_after
        DATETIME txn_time
    }

    USERS ||--o{ WALLETS : owns
    WALLETS ||--o{ WALLET_TRANSACTIONS : has
