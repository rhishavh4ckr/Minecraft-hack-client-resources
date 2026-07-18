@echo off
REM SMELLY DIHH CLIENT — Native DLL Injection (Works with ANY Minecraft)
REM Author: Rhishav Sapkota
REM This launcher guides you through the native DLL injection method.
REM The agent mode (.jar direct execution) requires javac compilation.
REM The native DLL method works with any cracked or official Minecraft.

echo [Smelly Dihh Client] Native Injection Framework
REM Brand: SMELLY DIHH CLIENT — Created by Rhishav Sapkota

echo.
echo === STEP 1: COMPILE THE DLL ===
echo Running compile_dll.bat...
REM If MSVC is installed:
call compile_dll.bat
REM If MSVC is NOT installed, use this gcc command instead:
REM gcc -shared -o DllMain.dll src/main/cpp/DllMain.cpp -luser32 -lgdi32 -lopengl32

echo.
echo === STEP 2: CHECK IF DLL EXISTS ===
if exist DllMain.dll (
    echo [SUCCESS] DllMain.dll compiled.
    echo [NEXT] Launch ANY Minecraft launcher (cracked or official).
    echo [NEXT] Once Minecraft is running, use an injector tool (Process Hacker, Extreme Injector, etc.).
    echo [NEXT] Select javaw.exe process -^> Load DllMain.dll -^> Click 'Inject'.
    echo [NEXT] Once injected, press VK_RSHIFT (Right Shift) inside Minecraft to open ClickGUI.
) else (
    echo [ERROR] DllMain.dll not found. Compilation failed.
    echo [FIX] Install MSVC (Visual Studio with C++) OR install MinGW-w64 (gcc).
    echo [MANUAL] Run: gcc -shared -o DllMain.dll src/main/cpp/DllMain.cpp -luser32 -lgdi32 -lopengl32
)

echo.
echo === BRAND ===
echo SMELLY DIHH CLIENT — Created by Rhishav Sapkota
echo Zero old branding. Splash crash fixed (SplashScreen-Image removed from manifest).
echo Native DLL includes: manual mapping, thread hijacking, JNI, memory allocation, standard library loading.

echo.
echo Press any key to exit...
pause >nul
