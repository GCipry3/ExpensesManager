# 🏡 Family Budget Application

This application is designed to manage a family budget. It has a MariaDB database that stores and encrypts family members' passwords through an API call. The backend is built with **Kotlin Spring Boot**, and the frontend is built with **Python Flask**.

## 🌟 Features

- Add and manage family members
- Securely store and match family members' passwords
- View, add, and manage expenses
- Add funds to the family budget
- Spend money from the family budget

## 🔧 Prerequisites

- JDK 1.8 or later
- MariaDB
- Python 3.10

## 📦 Installation

1. Clone the repository
```bash
git clone https://github.com/GCipry3/ExpensesManager.git
```

2. Navigate to the project directory
```bash
cd ExpensesManager
```

3. Install the required Python packages
```bash
pip install -r src/main/python/requirements.txt
```

🚀 Setup and Run the Application

### Spring Boot Backend

1. Set up your MariaDB database with the appropriate tables and data.
2. Update `src/main/resources/application.properties` with your database connection details.
3. Build the Spring Boot application:
```bash
./mvnw clean install
```
4. Run the Spring Boot application:
```bash
./mvnw spring-boot:run
```


### Python Flask Frontend

1. Run the Flask application:
./run.sh 


🎯 Usage

Access the Family Budget web application at [http://localhost:5000](http://localhost:5000).

