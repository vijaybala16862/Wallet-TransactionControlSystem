# Wallet Transaction Control Flow – Class Diagram

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
        +register(username, password)
        +login(username, password)
    }

    class WalletService {
        +create(userId)
        +get(walletId)
        +credit(walletId, amount)
        +debit(walletId, amount)
        +delete(walletId)
    }

    class UserDao
    class WalletDao
    class UserDaoImpl
    class WalletDaoImpl

    class User {
        id
        username
        password
    }

    class Wallet {
        walletId
        userId
        balance
        status
    }

    LoginServlet --> UserService
    RegisterServlet --> UserService
    WalletServlet --> WalletService

    UserService --> UserDao
    WalletService --> WalletDao

    UserDaoImpl ..|> UserDao
    WalletDaoImpl ..|> WalletDao
