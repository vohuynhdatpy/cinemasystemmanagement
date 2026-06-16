# 🎬 Cinema Management System

A desktop-based cinema management application built with Java, featuring full ticketing, scheduling, and invoice workflows backed by SQL Server.

---

## 📸 Overview

This system handles the complete operational flow of a cinema — from movie scheduling and seat booking to invoice generation and staff management — designed with a clean DAO architecture for maintainability and scalability.

---

## ✨ Features

| Module | Description |
|---|---|
| 🎥 Movie Management | Add, edit, delete, and list movies with metadata |
| 🗓️ Showtime Scheduling | Manage screening schedules and room assignments |
| 🎟️ Ticket Booking | Book seats and process customer purchases |
| 🧾 Invoice Generation | Auto-generate printed invoices as `.txt` files |
| 👤 Staff Management | Basic employee and user account management |
| 🗄️ Database Integration | Full CRUD via JDBC connected to SQL Server |

---

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java (Swing) | Desktop UI and application logic |
| SQL Server (T-SQL) | Relational database backend |
| JDBC | Java-to-database connectivity |
| DAO Pattern | Separation of data access from business logic |
| IntelliJ IDEA / Eclipse | Development environment |

---

## 🗂️ Project Structure

```
cinemasystemmanagement/
├── src/                        # Java source code
│   ├── dao/                    # Data Access Object layer
│   ├── model/                  # Entity classes (Movie, Ticket, Invoice...)
│   ├── ui/                     # Java Swing UI panels
│   └── Main.java               # Application entry point
├── data/                       # Supporting data files
├── Libraries/                  # External JAR dependencies
├── out/production/             # Compiled class files
├── hoa_don_*.txt               # Sample generated invoices
└── README.md
```

---

## ⚙️ Installation & Setup

### Prerequisites
- Java JDK 8+
- SQL Server (or SQL Server Express)
- IntelliJ IDEA or Eclipse

### Steps

**1. Clone the repository**
```bash
git clone https://github.com/vohuynhdatpy/cinemasystemmanagement.git
cd cinemasystemmanagement
```

**2. Set up the database**
- Open SQL Server Management Studio
- Run the T-SQL schema script located in `data/` to create tables and seed data

**3. Configure the connection**
- Update JDBC connection string in the DAO configuration file with your SQL Server credentials:
```java
String url = "jdbc:sqlserver://localhost:1433;databaseName=CinemaDB";
String user = "your_username";
String password = "your_password";
```

**4. Add external libraries**
- In your IDE, add all `.jar` files from the `Libraries/` folder to the project classpath

**5. Run the application**
- Locate and run `Main.java` (or the class containing `main()`)

---

## 🏗️ Architecture

The system follows a **3-layer DAO architecture**:

```
UI Layer (Java Swing)
       ↓
Business Logic Layer
       ↓
DAO Layer (JDBC → SQL Server)
```

- **Model classes** represent entities: `Movie`, `Showtime`, `Ticket`, `Invoice`, `Customer`
- **DAO classes** handle all database operations with parameterized queries
- **UI panels** are decoupled from data access logic

---

## 🗄️ Database Design

Normalized to **3rd Normal Form (3NF)** with referential integrity constraints:

```
Movie ──< Showtime ──< Ticket >── Customer
                          ↓
                       Invoice
```

Key constraints:
- Foreign keys enforced across all related tables
- Transaction handling for booking and invoice operations
- Validation rules to prevent duplicate bookings

---

## 📄 Sample Invoice Output

Invoices are automatically exported as `.txt` files upon successful booking:

```
===== HÓA ĐƠN VÉ XEM PHIM =====
Mã vé   : VE1762688279106
Phim    : [Movie Name]
Suất    : [Showtime]
Ghế     : [Seat]
Giá     : [Price] VNĐ
================================
```

---

## 👤 Author

**Võ Huỳnh Đạt**
- GitHub: [@vohuynhdatpy](https://github.com/vohuynhdatpy)
- LinkedIn: [linkedin.com/in/nimbid](https://www.linkedin.com/in/nimbid/)

---

## 📄 License

MIT License — free to use and modify.
