
# Car Rental Shop Management System

A comprehensive Java-based car rental management system with a command-line interface (CLI) that allows administrators, managers, and customers to manage car rentals, customers, and transactions.

## 🚗 Features

### User Roles
- **Admin**: Car management, customer management, rental transactions
- **Manager**: Full system access including admin management
- **Customer**: View rented cars, make rentals, view profile

### Core Functionality
- **Car Management**: Add, update, delete, and view cars with different statuses (available, rented, sold)
- **Customer Management**: Complete customer lifecycle management
- **Rental System**: Process car rentals with payment card integration
- **User Authentication**: Role-based login system
- **Database Integration**: MongoDB for data persistence

## 🛠️ Technology Stack

- **Language**: Java 17
- **Build Tool**: Maven
- **Database**: MongoDB 5.5.1
- **Testing**: JUnit 4.11

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- MongoDB Atlas account (or local MongoDB instance)
- Git

## 🚀 Installation & Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd car_rental_shop
   ```

2. **Configure Database Connection**
   - Update `src/main/resources/config.properties` with your MongoDB connection details:
   ```properties
   mongodb.uri=your_mongodb_connection_string
   mongodb.database=your_database_name
   ```

3. **Build the project**
   ```bash
   mvn clean compile
   ```

4. **Run the application**
   ```bash
   mvn exec:java -Dexec.mainClass="car_rental_shop.CLI.start_cli"
   ```

## 📁 Project Structure

```
src/
├── main/
│   ├── java/
│   │   ├── car_rental_shop/
│   │   │   └── CLI/
│   │   │       └── start_cli.java          # Main application entry point
│   │   ├── dao/                            # Data Access Objects
│   │   │   ├── AdminDao.java
│   │   │   ├── CarDao.java
│   │   │   ├── CardDao.java
│   │   │   ├── CustomerDao.java
│   │   │   ├── RentDao.java
│   │   │   └── UserDao.java
│   │   ├── db/
│   │   │   └── MongoUtil.java              # Database connection utility
│   │   ├── model/                          # Data models
│   │   │   ├── admin.java
│   │   │   ├── Car.java
│   │   │   ├── Card.java
│   │   │   ├── Customer.java
│   │   │   ├── Manger.java
│   │   │   └── User.java
│   │   └── service/                        # Business logic
│   │       ├── AdminService.java
│   │       ├── cardService.java
│   │       ├── CarService.java
│   │       ├── CustomerService.java
│   │       ├── Login.java
│   │       ├── RentService.java
│   │       └── test.java
│   └── resources/
│       └── config.properties               # Database configuration
└── test/                                   # Test files
```

## 🎯 Usage

### Starting the Application
Run the main class to start the CLI interface:
```bash
mvn exec:java -Dexec.mainClass="car_rental_shop.CLI.start_cli"
```

### Login System
The application starts with a login prompt. You'll need valid credentials for one of the three user roles:
- **Admin**: Full car and customer management
- **Manager**: Complete system access including admin management
- **Customer**: Personal car rental operations

### Main Menu Options

#### Admin Menu
1. **Car Management**: Add, view, update, delete cars
2. **Customer Management**: Manage customer accounts
3. **Rental Transactions**: Process rentals and sales

#### Manager Menu
1. **Car Management**: Full car operations
2. **Customer Management**: Customer lifecycle
3. **Admin Management**: Manage admin accounts
4. **Rental Transactions**: All rental operations

#### Customer Menu
1. **Show My Cars**: View currently rented vehicles
2. **Make a Rental**: Rent available cars
3. **Show My Profile**: View personal information

## 🔧 Development

### Building the Project
```bash
# Clean and compile
mvn clean compile

# Run tests
mvn test

# Package the application
mvn package
```

### Database Schema
The application uses MongoDB collections for:
- **cars**: Car inventory and details
- **customers**: Customer information
- **users**: Authentication and user management
- **admins**: Admin user details
- **rents**: Rental transaction records
- **cards**: Payment card information

## 🧪 Testing

Run the test suite:
```bash
mvn test
```

## 📝 Configuration

### Database Configuration
Edit `src/main/resources/config.properties`:
```properties
mongodb.uri=mongodb://username:password@host:port/database
mongodb.database=your_database_name
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🐛 Known Issues

- Some menu options in rental transactions are not fully implemented
- Customer profile viewing functionality is commented out
- Card payment validation could be enhanced

## 🔮 Future Enhancements

- Web-based user interface
- Email notifications for rentals
- Advanced reporting and analytics
- Mobile application
- Integration with external payment gateways
- Automated car maintenance scheduling

## 📞 Support

For support and questions, please open an issue in the repository or contact the development team.

---

**Note**: Make sure to update the MongoDB connection string in `config.properties` before running the application.
