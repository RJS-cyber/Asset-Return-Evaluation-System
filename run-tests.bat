@echo off
REM ARES Backend Test Runner (Windows Batch Script)
REM This script runs all Maven tests for the ARES backend

echo ========================================
echo  ARES Backend Test Runner
echo ========================================
echo.

cd /d "C:\Users\rschmoltzi\IdeaProjects\ARES\backend"

if not exist "pom.xml" (
    echo Error: pom.xml not found. Are you in the correct directory?
    pause
    exit /b 1
)

echo Running Maven clean test...
echo.

call mvn clean test

echo.
echo ========================================
echo  Test Execution Complete
echo ========================================

if %ERRORLEVEL% EQU 0 (
    echo ✓ All tests passed successfully!
) else (
    echo ✗ Some tests failed. Check the output above.
)

echo.
pause
