# NMAMIT Placement Portal 🎓

<div align="center">

**A beginner-friendly, full-stack campus recruitment management system for NMAM Institute of Technology, Nitte.**

[![Java](https://img.shields.io/badge/Java-21%20LTS-orange?logo=openjdk)](https://adoptium.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.x-6DB33F?logo=springboot)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.x-4479A1?logo=mysql)](https://www.mysql.com/)
[![Flutter](https://img.shields.io/badge/Flutter-Cross--Platform-02569B?logo=flutter)](https://flutter.dev/)

</div>

---

## 👋 Welcome!
If you are new to this project (or new to programming), don't worry! This guide will walk you through exactly what this project is and how to run it step-by-step on your computer.

### What is this?
This is a software application designed to help colleges manage their campus placements. It consists of two main parts:
1. **The Backend (Server & Database)**: Built with **Java (Spring Boot)** and **MySQL**. This is the brain of the app that stores all the data (students, jobs, applications) securely.
2. **The Mobile App (Frontend)**: Built with **Flutter**. This is the app that students and admins install on their phones to view jobs, apply, and see dashboards.

---

## 🚀 How to Run the Project (Step-by-Step)

### Step 1: What you need to install first
Before running the project, you need to have a few tools installed on your computer:
- **Java 21**: The programming language for the backend. ([Download here](https://adoptium.net/))
- **Docker Desktop**: The easiest way to run our database without complex setups. ([Download here](https://www.docker.com/products/docker-desktop/))
- **Flutter SDK**: The tool to run the mobile app. ([Download here](https://docs.flutter.dev/get-started/install))

---

### Step 2: Start the Backend (The Brain)

1. Open a terminal (or Command Prompt / PowerShell) and navigate to the project folder.
2. We have made it extremely easy to start the database using Docker. Just run:
   ```bash
   cd backend/placement-backend
   docker-compose up -d
   ```
   *(This downloads and starts a MySQL database in the background automatically!)*

3. Now, start the Spring Boot server:
   ```bash
   # On Windows:
   mvnw.cmd spring-boot:run

   # On Mac/Linux:
   ./mvnw spring-boot:run
   ```
4. **Success!** Your backend is now running at `http://localhost:8080`. 
   - You can see all the available APIs by visiting the interactive documentation here: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).

---

### Step 3: Start the Mobile App (The UI)

1. Open a new terminal window.
2. Navigate to the flutter app folder:
   ```bash
   cd flutter/placement_app
   ```
3. Download the required packages:
   ```bash
   flutter pub get
   ```
4. Run the app! (Make sure you have an Android Emulator running, or a phone connected to your PC):
   ```bash
   flutter run
   ```

---

## 💡 How to Test and Play Around

Once the app is running on your phone/emulator:
1. **Register a Student Account**: Open the app, go to the login screen, and create an account. *(Note: you must use an `@nmamit.in` email address to register!)*
2. **Login**: Use the email and password you just created.
3. **Explore**:
   - **Dashboard**: See your statistics.
   - **Jobs**: View available job drives.
   - **Profile**: Fill out your academic details (CGPA, Branch, etc.)

---

## 📁 Project Structure (Where is everything?)

If you want to look at the code, here is where to find things:

- `backend/placement-backend/src/main/java/` 👉 This is where all the Java code lives. If you want to change how data is saved or processed, look here!
- `backend/placement-backend/src/main/resources/` 👉 This is where configuration files live (like database passwords and setup scripts).
- `flutter/placement_app/lib/` 👉 This is where all the Flutter (Dart) code lives. If you want to change the colors, buttons, or screens of the mobile app, look here!

---

## ✅ Development Status
**We are 100% complete with the core MVP!**
- ✅ **Phase 1**: Backend Core & Database setup
- ✅ **Phase 2**: Standardized API Responses & Mappers
- ✅ **Phase 3**: JWT Authentication & Security
- ✅ **Phase 4**: Dockerization & Swagger Documentation
- ✅ **Phase 5**: Flutter Mobile App with Material 3 UI

---

## 🤝 Need Help?
If you get stuck or have errors:
1. **Check the logs**: Look at the terminal where you typed `mvnw spring-boot:run` or `flutter run` for red error messages.
2. **Database Issues**: Make sure Docker Desktop is open and running before you type `docker-compose up -d`.
3. **Flutter Issues**: Run `flutter doctor` in your terminal to see if you missed installing any Android Studio components.

> Built with ❤️ for **NMAM Institute of Technology**, Nitte — Karnataka, India.
