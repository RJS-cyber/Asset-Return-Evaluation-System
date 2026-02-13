@echo off
REM ARES Frontend Server Starter
REM This script starts a simple HTTP server for the frontend

echo ========================================
echo  ARES Frontend Server
echo ========================================
echo.

cd /d "C:\Users\rschmoltzi\IdeaProjects\ARES\frontend"

if not exist "index.html" (
    echo Error: index.html not found. Are you in the correct directory?
    pause
    exit /b 1
)

echo Starting frontend server...
echo.

REM Check if Python is available
python --version >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    echo Using Python HTTP server
    echo Frontend will be available at: http://localhost:8000
    echo Press Ctrl+C to stop the server
    echo.
    python -m http.server 8000
    goto :end
)

REM Check if Node.js/npx is available
npx --version >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    echo Using npx http-server
    echo Frontend will be available at: http://localhost:8000
    echo Press Ctrl+C to stop the server
    echo.
    npx http-server -p 8000
    goto :end
)

REM No server available
echo.
echo ERROR: No HTTP server available!
echo.
echo Please install one of the following:
echo   1. Python: https://www.python.org/downloads/
echo   2. Node.js: https://nodejs.org/
echo.
echo Alternatively, open index.html directly in your browser.
echo.
pause
exit /b 1

:end
