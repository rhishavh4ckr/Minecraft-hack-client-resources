package net.smelly.dihh.client;

/**
 * SMELLY DIHH CLIENT — Built-In Injector Reference
 * Author: Rhishav Sapkota
 * Brand: SMELLY DIHH CLIENT — Created by Rhishav Sapkota
 *
 * This class provides the injection mechanism interface.
 * It does NOT replace external injection tools (Process Hacker, Extreme Injector).
 * It serves as a reference showing how the native DLL integrates with the client.
 */
public class BuiltInInjector {

    // INJECTION MECHANISM STATUS
    public static final String BRAND = "SMELLY DIHH CLIENT — Created by Rhishav Sapkota";
    public static final String DLL_FILE = "DllMain.dll";
    public static final String DLL_SOURCE = "src/main/cpp/DllMain.cpp";
    public static final String COMPILE_SCRIPT = "compile_dll.bat";
    public static final String INSTRUCTION_FILE = "HOW_TO_USE.txt";

    // INJECTION METHOD: Native DLL Injection (works with ANY Minecraft launcher)
    public static String getInjectionMethod() {
        return "Native DLL Injection: compile DLL -> inject into running javaw.exe -> press VK_RSHIFT";
    }

    // INJECTION BUTTON REFERENCE (this is what the user sees in injector tools)
    // The external injector tool (like Process Hacker) has the "Inject" button.
    // This client does NOT contain an "Inject" button in its ClickGUI.
    // The injection is performed EXTERNALLY by the injector tool.
    public static String getInjectButtonReference() {
        return "INJECT BUTTON LOCATION: External injector tool (Process Hacker / Extreme Injector). " +
               "Select javaw.exe process -> Click 'Inject' -> Load DllMain.dll. " +
               "NOT located inside ClickGUI (ClickGUI opens AFTER injection with Right Shift).";
    }

    // INJECTION PROCESS STEPS (clear, step-by-step)
    public static void printInjectionSteps() {
        System.out.println("=== SMELLY DIHH CLIENT — INJECTION PROCESS ===");
        System.out.println("Brand: " + BRAND);
        System.out.println("Step 1: Launch any Minecraft launcher (cracked or official)");
        System.out.println("Step 2: Open external injector tool (Process Hacker, Extreme Injector, etc.)");
        System.out.println("Step 3: Select javaw.exe process (Minecraft JVM)");
        System.out.println("Step 4: Click 'Inject' button in injector tool (NOT in ClickGUI)");
        System.out.println("Step 5: Load file: " + DLL_FILE);
        System.out.println("Step 6: DLL attaches silently (manual mapping, thread hijacking, JNI active)");
        System.out.println("Step 7: Once inside Minecraft, press VK_RSHIFT (Right Shift) to open ClickGUI");
        System.out.println("Step 8: ClickGUI shows glass cards: Combat, Movement, Render, Player, World");
        System.out.println("=== END INJECTION PROCESS ===");
    }

    // COMPILATION COMMAND REFERENCE
    public static String getCompileCommand() {
        return "MSVC: cl /LD " + DLL_SOURCE + " /link user32.lib gdi32.lib opengl32.lib" +
               " | GCC: gcc -shared -o " + DLL_FILE + " " + DLL_SOURCE + " -luser32 -lgdi32 -lopengl32";
    }

    // NATIVE INJECTION ARCHITECTURE (all 7 mechanics from DllMain.cpp)
    public static void printNativeMechanics() {
        System.out.println("=== NATIVE INJECTION MECHANICS ===");
        System.out.println("1. Process Attachment: DllMain attaches to javaw.exe (DLL_PROCESS_ATTACH)");
        System.out.println("2. Standard Library Loading: LoadLibrary / GetModuleHandle / GetProcAddress");
        System.out.println("3. Manual Mapping: Binary payload copied directly into target memory (anti-detection)");
        System.out.println("4. Thread Hijacking: Active game thread suspended briefly for clean hook installation");
        System.out.println("5. JNI (Java Native Interface): Native code interacts with Java classes/methods");
        System.out.println("6. Memory Allocation: VirtualAlloc creates dedicated 4KB module space in JVM");
        System.out.println("7. Runtime Alteration: wglSwapBuffers_hook redefines rendering pipeline on the fly");
    }

    // MAIN REFERENCE ENTRY
    public static void main(String[] args) {
        System.out.println("=== BUILT-IN INJECTOR REFERENCE ===");
        System.out.println("This is NOT an injector tool. This is the client's injection architecture reference.");
        System.out.println("Injection is performed EXTERNALLY by an injector tool (Process Hacker, etc.).");
        System.out.println("The ClickGUI (opened with Right Shift) does NOT contain an 'Inject' button.");
        printInjectionSteps();
        System.out.println("Compile command: " + getCompileCommand());
        System.out.println("DLL file: " + DLL_FILE);
        System.out.println("Documentation: " + INSTRUCTION_FILE);
        System.out.println("Brand: " + BRAND);
    }
}
