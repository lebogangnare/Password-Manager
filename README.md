# 🔐 Password Manager

A secure, offline password manager built in **Java** using **Maven**. This project is designed to demonstrate object-oriented programming, secure software design principles, and clean software architecture.

---

## 📖 Overview

Managing dozens of online accounts often leads to poor password habits such as password reuse or storing credentials in insecure locations.

This application provides a simple desktop solution for securely storing and managing passwords locally. It is being developed as a personal portfolio project while learning Java and software engineering best practices.

---

## ✨ Features

### Current Features (Version 1)

- Create password entries
- View stored passwords
- Search password entries
- Delete password entries
- Store passwords locally using JSON
- Console-based user interface
- Object-oriented design
- Maven project structure

### Planned Features

- Password encryption
- Master password authentication
- Password strength checker
- Secure password generator
- Categories and tags
- Automatic backups
- Import and export functionality
- JavaFX graphical user interface
- Unit and integration tests
- Secure notes

---

## 🏗️ Project Structure

```
Password-Manager/
│
├── docs/
│   ├── Planning-and-Design.md
│   ├── UML-Class-Diagram.md
│   ├── UML-Use-Case.md
│   └── UML-Sequence-Diagrams.md
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── za/
│   │           └── co/
│   │               └── lebogang/
│   │                   └── passwordmanager/
│   │                       ├── model/
│   │                       ├── service/
│   │                       ├── storage/
│   │                       ├── ui/
│   │                       ├── security/
│   │                       ├── util/
│   │                       ├── exception/
│   │                       └── Main.java
│   │
│   └── test/
│
├── pom.xml
├── README.md
└── .gitignore
```

---

## 🛠️ Technologies

- Java 17
- Maven
- Gson
- JUnit 5
- Git
- GitHub

---

## 🧠 Architecture

The project follows a layered architecture.

```
User Interface
      │
      ▼
Business Logic
      │
      ▼
Storage Layer
      │
      ▼
JSON File
```

Each layer has a single responsibility, making the application easier to maintain and extend.

---

## 🚀 Getting Started

### Prerequisites

- Java 17 or later
- Maven
- Git

### Clone the repository

```bash
git clone https://github.com/YOUR_USERNAME/Password-Manager.git
```

### Navigate into the project

```bash
cd Password-Manager
```

### Compile the project

```bash
mvn compile
```

### Run the application

```bash
mvn exec:java
```

---

## 📂 Documentation

Detailed project documentation can be found in the **docs/** folder and the GitHub Wiki.

Documentation includes:

- Planning & Design
- Functional Requirements
- Non-functional Requirements
- UML Diagrams
- Architecture
- Development Roadmap

---

## 📅 Roadmap

### Version 1

- Console application
- CRUD operations
- JSON storage

### Version 2

- Encryption
- Master password
- Password generator

### Version 3

- JavaFX desktop application
- Improved security
- Search improvements
- Backup system

---

## 🎯 Learning Objectives

This project is helping me develop skills in:

- Object-Oriented Programming
- SOLID Principles
- File Handling
- JSON Serialization
- Software Architecture
- Clean Code
- Version Control with Git
- Maven
- Java Testing

---

## 🤝 Contributing

This is currently a personal learning project. Suggestions, feedback, and improvements are always welcome.

---

## 👨‍💻 Author

**Lebogang Nare**

Software Engineering Student at WeThinkCode_

Interested in:

- Backend Development
- Cyber Security
- Data Engineering