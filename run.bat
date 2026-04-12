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

:: Step 3 — Compile all Java files from all folders
echo Compiling all Java files...

for /r . %%f in (*.java) do (
    echo %%f | find "bin" >nul || javac -d bin "%%f"
)

if %errorlevel% neq 0 (
    echo Compilation Failed! Check errors above.
    pause
    exit /b 1
)

echo Compilation Successful!

:: Step 4 — Run the program
if "%1"=="" (
    echo No file given. Running test1.zara by default...
    if exist samples\test1.zara (
        java -cp bin Main samples\test1.zara
    ) else if exist test1.zara (
        java -cp bin Main test1.zara
    ) else (
        echo test1.zara not found!
    )
) else (
    echo Running: %1
    java -cp bin Main "%1"
)

pause
