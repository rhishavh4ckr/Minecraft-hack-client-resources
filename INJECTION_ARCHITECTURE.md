**SMELLY DIHH CLIENT — Technical Injection Architecture**
Created by Rhishav Sapkota
========================================

This document maps the technical mechanics from the native framework (`DllMain.cpp`) to the client's module architecture.

**1. PROCESS ATTACHMENT**
- Native DLL (`DllMain.cpp`) attaches to running Minecraft JVM (`javaw.exe`)
- Uses `DllMain` entry point (`DLL_PROCESS_ATTACH`) for silent initialization
- No launcher command required for native injection mode

**2. STANDARD LIBRARY LOADING**
- `LoadLibrary` / `GetModuleHandle` resolves `opengl32.dll`
- `GetProcAddress` retrieves `wglSwapBuffers` pointer for hooking
- Standard OS APIs used for process and module identification

**3. MANUAL MAPPING (Anti-Detection)**
- `InstallManualMapPayload()` copies binary payload directly into target memory
- Bypasses standard OS logging systems (no `LoadLibrary` footprint for payload modules)
- This is the ghost injector mechanism: invisible to standard detection

**4. THREAD HIJACKING**
- `ThreadHijackForHookInstallation()` suspends active game thread temporarily
- Installs hook pointer during suspension window
- Resumes thread immediately — clean installation without race conditions

**5. JNI (Java Native Interface)**
- Native code (`DllMain.cpp`) interacts with Java classes and methods via JNI layer
- Module bridge (`SmellyDihhBridge.java`) connects native hooks to Java modules
- Binary payload modules (`a`, `b`, `c`, `d`, `e`) loaded through custom classloader (`m.java`)

**6. MEMORY ALLOCATION**
- Dedicated memory space (`MEMORY_SIZE = 4096 bytes`) allocated within JVM process
- `VirtualAlloc` creates protected buffer for module state management
- Memory freed cleanly during `UninjectDLL()`

**7. RUNTIME ALTERATION**
- `wglSwapBuffers_hook()` redefines rendering pipeline on the fly
- ClickGUI (`ClickGUIEngine.java`) activates when `VK_RSHIFT` pressed
- Original function pointer preserved for clean uninjection (`wglSwapBuffers_orig`)

**MODULE FLOW:**
Native DLL attaches → Memory allocated → Thread suspended briefly → Hook installed → Listener thread starts → Binary payloads loaded (`a`, `b`, `c`, `d`, `e`) → Java modules initialized (`h`, `i`, `r`, `s`, `t`) → ClickGUI ready (Right Shift toggles dark glass interface)

**CLEAN UNINJECTION (`UninjectDLL`):**
1. Restore original `wglSwapBuffers` pointer
2. Terminate listener thread
3. Free dedicated memory (`VirtualFree`)
4. Save JSON config and log event
