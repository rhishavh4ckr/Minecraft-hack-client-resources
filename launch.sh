#!/bin/bash
# SMELLY DIHH CLIENT — Native DLL Injection (Works with ANY Minecraft)
# Author: Rhishav Sapkota
# Brand: SMELLY DIHH CLIENT — Created by Rhishav Sapkota

echo "[Smelly Dihh Client] Native Injection Framework"
echo "=== STEP 1: COMPILE THE DLL ==="
if command -v gcc &> /dev/null; then
    gcc -shared -o DllMain.dll src/main/cpp/DllMain.cpp -luser32 -lgdi32 -lopengl32
    echo "[SUCCESS] DllMain.dll compiled with gcc."
else
    echo "[ERROR] gcc not found. Install MinGW-w64 or use MSVC (compile_dll.bat)."
    echo "[MANUAL] gcc -shared -o DllMain.dll src/main/cpp/DllMain.cpp -luser32 -lgdi32 -lopengl32"
fi

echo "=== STEP 2: USE ANY MINECRAFT LAUNCHER ==="
echo "Launch ANY cracked or official Minecraft launcher."
echo "Once Minecraft is running, use an injector tool to inject DllMain.dll into javaw.exe."
echo "Press VK_RSHIFT (Right Shift) inside Minecraft to open ClickGUI."
echo "Brand: SMELLY DIHH CLIENT — Created by Rhishav Sapkota"
