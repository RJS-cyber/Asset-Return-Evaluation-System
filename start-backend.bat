@echo off
REM ARES Backend Server Starter
REM This script starts the Spring Boot backend server

echo ========================================
echo  ARES Backend Server
echo ========================================
echo.

cd /d "C:\Users\rschmoltzi\IdeaProjects\ARES\backend"

if not exist "pom.xml" (
    echo Error: pom.xml not found. Are you in the correct directory?
    pause
    exit /b 1
)

echo Starting Spring Boot application...
echo.
echo The server will be available at: http://localhost:8080
echo Press Ctrl+C to stop the server
echo.

call mvn spring-boot:run
