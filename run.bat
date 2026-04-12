@echo off
:: ================================
:: ZARA Interpreter — Windows Runner
:: ================================

echo ==============================
echo    ZARA Interpreter Runner
echo ==============================

:: Step 1 — Check Java
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed!
    echo Please install JDK from https://jdk.java.net
    pause
    exit /b 1
)

echo Java found!

:: Step 2 — Create bin folder
if not exist bin mkdir bin

:: Step 3 — Compile
echo Compiling...
javac -d bin src\*.java

if %errorlevel% neq 0 (
    echo Compilation Failed! Check errors above.
    pause
    exit /b 1
)

echo Compilation Successful!

:: Step 4 — Run
if "%1"=="" (
    echo No file given. Running test1.zara by default...
    java -cp bin Main samples\test1.zara
) else (
    echo Running: %1
    java -cp bin Main "%1"
)

pause
