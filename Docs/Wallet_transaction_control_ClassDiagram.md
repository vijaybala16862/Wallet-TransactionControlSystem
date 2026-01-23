# Wallet Transaction Control System – Class Diagram

```mermaid
classDiagram
    direction LR

    class LoginServlet {
        +doPost()
    }

    class RegisterServlet {
        +doPost()
    }

    class WalletServlet {
        +doPost()
        +doGet()
        +doPut()
        +doDelete()
    }

    class UserService {
        +register(String, String)
        +login(String, String)
    }

    class WalletService {
        +create(userId)
        +get(walletId)
        +credit(walletId, amount)
        +debit(walletId, amount)
        +delete(walletId)
    }

    class UserDao {
        +save(user)
        +findByUsername(username)
    }

    class UserDaoImpl {
        +save(user)
        +findByUsername(username)
    }

    class WalletDao {
        +save(wallet)
        +findById(walletId)
        +update(wallet)
        +delete(walletId)
        +saveTransaction()
    }

    class WalletDaoImpl {
        +save(wallet)
        +findById(walletId)
        +update(wallet)
        +delete(walletId)
        +saveTransaction()
    }

    class User {
        +int id
        +String username
        +String password
    }

    class Wallet {
        +String walletId
        +String userId
        +double balance
        +String status
    }

    LoginServlet --> UserService
    RegisterServlet --> UserService
    WalletServlet --> WalletService

    UserService --> UserDao
    WalletService --> WalletDao

    UserDaoImpl ..|> UserDao
    WalletDaoImpl ..|> WalletDao

    UserDao --> User
    WalletDao --> Wallet
