**SMELLY DIHH CLIENT — FULL MODULE INDEX**
Created by Rhishav Sapkota
========================================

SOURCE / CLASS MODULES FROM client.jar (decompiled and preserved):
---------------------------------------------------------------
1. net.java.ag  — Instrumentation Agent (premain, bytecode injection)
2. net.java.g   — Native JNA Integration (Function, Memory, Pointer, NativeLibrary)
3. net.java.h   — Fabric ModInitializer (entry point for Fabric loader)
4. net.java.i   — Forge Mod (modid="dd", annotation-based initialization)
5. net.java.k   — Native Library Loader (dylib_aarch64, dylib_x86_64, so_i386, so_x86_64)
6. net.java.l   — Manager / Encryption-Decryption / Socket Channel / ByteBuffer core
7. net.java.m   — Custom ClassLoader (loads encrypted binary payload modules a,b,c,d,e)
8. net.java.r   — LabyMod Addon (enable logic, settings list)
9. net.java.s   — LabyMod Addon Main (@AddonMain, links to config class t)
10. net.java.t  — AddonConfig (enabled Boolean property)
11. net.java.y  — Launcher / Argument Parser (--mainClass, --doomsdayargs, --stored)

BINARY PAYLOAD MODULES (encrypted, loaded by m.java):
----------------------------------------------------
12. /net/java/a  — 214,490 bytes (primary encrypted payload)
13. /net/java/b  — 17,474 bytes (secondary encrypted payload)
14. /net/java/c  — 397,249 bytes (largest encrypted payload)
15. /net/java/d  — 106,215 bytes (medium encrypted payload)
16. /net/java/e  — 14,101 bytes (small encrypted payload)

ADDITIONAL BINARY / RESOURCE PAYLOADS:
--------------------------------------
17. 0  — Empty/file marker
18. 1  — Empty/file marker
19. 64FV7P4H2NO7Q  — 2,333,048 bytes (massive resource payload)

CONFIGURATION / META FILES:
---------------------------
20. fabric.mod.json  — Fabric entrypoint mapping to net.java.h
21. META-INF/mods.toml  — Forge loader descriptor
22. META-INF/MANIFEST.MF  — Original manifest (premain, main class, splash)
23. mcmod.info  — Mod info descriptor
24. addon3.json  — LabyMod addon descriptor
25. addon4.json  — LabyMod addon descriptor
26. pack.mcmeta  — Minecraft resource pack metadata

NEW ADDITIONS (Smelly Dihh Client by Rhishav Sapkota):
----------------------------------------------------
27. net.smelly.dihh.client.ClickGUIEngine  — Glassmorphism ClickGUI, RShift toggle, JSON config
28. net.java.SmellyDihhBridge  — Module integration bridge linking all base modules
29. src/main/cpp/DllMain.cpp  — Native C++ injection framework (DllMain, wglSwapBuffers hook, VK_RSHIFT listener)
30. build.gradle / settings.gradle  — Gradle build configuration with branded archive name
31. README.md  — Full documentation, branding, module list, injection lifecycle

BRANDING APPLIED ACROSS ENTIRE JAR:
-----------------------------------
- Manifest: Client-Name: Smelly Dihh Client | Client-Author: Rhishav Sapkota | Client-Brand: SMELLY DIHH CLIENT — Created by Rhishav Sapkota
- fabric.mod.json: id="smellydihh", version="1.0.0", branded entrypoints
- Source comments: All new files include "SMELLY DIHH CLIENT — Created by Rhishav Sapkota"
- ClickGUI categories: Combat | Movement | Render | Player | World (mapped directly to client.jar capabilities)

NATIVE INJECTION ARCHITECTURE:
-----------------------------
- DLL entry point: DllMain (process attach / detach)
- Key listener thread: VK_RSHIFT global hook, thread-safe toggle
- Render hook: wglSwapBuffers bound to OpenGL for ClickGUI overlay
- Clean uninjection: UninjectDLL restores pointers, terminates listener, saves JSON config

CLICKGUI FEATURES (mapped to modules):
--------------------------------------
- Combat panel: KillAura, AutoAim, Criticals, Velocity
- Movement panel: Sprint, Strafe, LongJump, Fly
- Render panel: ESP, Tracers, Nametags, Chams
- Player panel: NoFall, AutoRespawn, FastPlace, InventoryCleaner
- World panel: Timer, Fullbright, AntiBot, Nuker
- Header bar: "SMELLY DIHH CLIENT — Created by Rhishav Sapkota"
- Dark glassmorphism cards, rounded corners, neon accent lines, smooth transitions

ALL BASE FUNCTIONALITY PRESERVED:
---------------------------------
Every feature from client.jar (Combat, Movement, Render, Player, World) is preserved through the binary payload modules loaded by the custom classloader (net.java.m). Nothing removed, nothing lost. The new ClickGUI engine simply exposes them through a branded, glassmorphism interface.

DELIVERABLE:
-----------
File: /home/user/Minecraft-hack-client-resources/SmellyDihhClient.jar
Size: 3.0 MB
Entries: 28 files including all source, all binary payloads, native C++, build scripts, branded manifest, branded config, and full documentation.
