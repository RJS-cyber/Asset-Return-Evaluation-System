@echo off
REM ARES Full Stack Starter
REM This script starts both backend and frontend servers

echo ========================================
echo  ARES Full Stack Starter
echo ========================================
echo.

cd /d "C:\Users\rschmoltzi\IdeaProjects\ARES"

echo Starting backend server in new window...
start "ARES Backend" cmd /k "call start-backend.bat"

timeout /t 3 /nobreak >nul

echo Starting frontend server in new window...
start "ARES Frontend" cmd /k "call start-frontend.bat"

echo.
echo ========================================
echo  Both servers are starting!
echo ========================================
echo.
echo Backend:  http://localhost:8080
echo Frontend: http://localhost:8000
echo.
echo Close the opened windows to stop the servers.
echo.
pause
