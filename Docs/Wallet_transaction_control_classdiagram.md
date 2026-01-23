classDiagram
direction LR

    %% ===== Servlets =====
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

    %% ===== Services =====
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

    %% ===== DAO Layer =====
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

    %% ===== Models =====
    class User {
        +int id
        +String username
        +String password
        +getId()
        +getUsername()
        +getPassword()
        +setId()
        +setUsername()
        +setPassword()
    }

    class Wallet {
        +String walletId
        +String userId
        +double balance
        +String status
        +getWalletId()
        +getUserId()
        +getBalance()
        +getStatus()
    }

    %% ===== Relationships (Exact Flow) =====
    LoginServlet --> UserService
    RegisterServlet --> UserService

    UserService --> UserDao
    UserDaoImpl ..|> UserDao
    UserDao --> User

    WalletServlet --> WalletService
    WalletService --> WalletDao
    WalletDaoImpl ..|> WalletDao
    WalletDao --> Wallet
