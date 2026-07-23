REM SMELLY DIHH CLIENT — Native DLL Compilator (Windows)
REM Author: Rhishav Sapkota
REM Compiles DllMain.cpp into injection DLL

@echo off
echo [Smelly Dihh] Compiling native injection DLL...
echo Brand: SMELLY DIHH CLIENT — Created by Rhishav Sapkota

REM Check for MSVC compiler
where cl >nul 2>nul
if %errorlevel% neq 0 (
    echo [ERROR] MSVC compiler (cl.exe) not found.
    echo Please install Visual Studio with C++ tools, or add cl.exe to PATH.
    echo Alternative: use gcc/minGW: gcc -shared -o injection.dll DllMain.cpp -luser32 -lgdi32 -lopengl32
    pause
    exit /b 1
)

REM Compile DLL with OpenGL and Windows hooks
cl /LD DllMain.cpp /link user32.lib gdi32.lib opengl32.lib

if exist DllMain.dll (
    echo [SUCCESS] Native DLL compiled: DllMain.dll
    echo [USE] Inject DllMain.dll into running Minecraft process using any injector (e.g., Process Hacker, Extreme Injector)
    echo [HOOK] VK_RSHIFT toggles ClickGUI once injected
) else (
    echo [FAIL] DLL compilation failed. Check compiler errors above.
)

pause
