@echo off
REM SMELLY DIHH CLIENT — Built-In Injector Launcher
REM Author: Rhishav Sapkota
REM This launches the built-in native injector. No external injector tool needed.

echo [Smelly Dihh Client] Built-In Injector
REM Brand: SMELLY DIHH CLIENT — Created by Rhishav Sapkota

echo.
echo [INSTRUCTION] Launch any Minecraft launcher and wait for game window.
echo [INSTRUCTION] Once Minecraft is running, this script will inject DllMain.dll.
echo [INSTRUCTION] After injection, press VK_RSHIFT (Right Shift) to open ClickGUI.
echo.

REM Check Python availability
python --version >nul 2>nul || python3 --version >nul 2>nul
if %errorlevel% neq 0 (
    echo [ERROR] Python not found. This launcher requires Python (python3 or python) to run the built-in injector.
    echo [ALTERNATIVE] Use native DLL injection manually with an external injector tool (Process Hacker, Extreme Injector).
    echo [ALTERNATIVE] Compile: compile_dll.bat OR gcc -shared -o DllMain.dll src/main/cpp/DllMain.cpp -luser32 -lgdi32 -lopengl32
    pause
    exit /b 1
)

REM Run built-in injector
python built_in_injector.py || python3 built_in_injector.py

REM Once injection completes, remind user
if %errorlevel% equ 0 (
    echo [SUCCESS] Built-in injector executed. Check Minecraft window.
    echo [NEXT] Press VK_RSHIFT (Right Shift) inside Minecraft to open ClickGUI.
)

pause
