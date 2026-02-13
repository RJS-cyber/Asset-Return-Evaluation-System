# Run Tests Script
# Execute this script from PowerShell to run the Maven tests

Write-Host "========================================" -ForegroundColor Cyan
Write-Host " ARES Backend Test Runner" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

$backendPath = "C:\Users\rschmoltzi\IdeaProjects\ARES\backend"

if (Test-Path $backendPath) {
    Write-Host "Changing directory to: $backendPath" -ForegroundColor Yellow
    Set-Location $backendPath

    Write-Host "Running Maven clean test..." -ForegroundColor Yellow
    Write-Host ""

    mvn clean test

    Write-Host ""
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host " Test Execution Complete" -ForegroundColor Cyan
    Write-Host "========================================" -ForegroundColor Cyan

    if ($LASTEXITCODE -eq 0) {
        Write-Host "✓ All tests passed successfully!" -ForegroundColor Green
    } else {
        Write-Host "✗ Some tests failed. Check the output above." -ForegroundColor Red
    }
} else {
    Write-Host "Error: Backend directory not found at $backendPath" -ForegroundColor Red
}

Write-Host ""
Write-Host "Press any key to continue..."
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
