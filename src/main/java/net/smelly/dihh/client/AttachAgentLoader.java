package net.smelly.dihh.client;

/**
 * SMELLY DIHH CLIENT — Built-In Attach Agent Loader (Self-Injecting)
 * Author: Rhishav Sapkota
 * Brand: SMELLY DIHH CLIENT — Created by Rhishav Sapkota
 *
 * Uses Java's built-in Attach API (com.sun.tools.attach) to attach to a running
 * Minecraft JVM (javaw.exe) and load this agent jar into it. No external injector needed.
 */
import com.sun.tools.attach.VirtualMachine;
import java.util.Scanner;

public class AttachAgentLoader {
    public static final String BRAND = "SMELLY DIHH CLIENT — Created by Rhishav Sapkota";
    public static final String AGENT_JAR = System.getProperty("agent.path", "SmellyDihhClient.jar");
    public static final String DLL_FILE = "DllMain.dll";

    public static void inject() throws Exception {
        System.out.println("[" + BRAND + "] Self-injection started via Java Attach API.");
        System.out.println("[" + BRAND + "] Searching for javaw.exe (Minecraft JVM)...");

        // Find running javaw.exe process
        String pid = findMinecraftPID();
        if (pid == null || pid.isEmpty()) {
            System.out.println("[" + BRAND + "] ERROR: javaw.exe not running. Launch Minecraft first.");
            System.out.println("[" + BRAND + "] Then run this agent again (double-click .bat or use java -agentpath).");
            return;
        }

        System.out.println("[" + BRAND + "] Found javaw.exe (PID: " + pid + "). Attaching...");

        // Attach to running JVM using standard Java Attach API
        VirtualMachine vm = VirtualMachine.attach(pid);
        System.out.println("[" + BRAND + "] Attached to Minecraft JVM successfully.");

        // Load agent jar into running JVM memory space
        vm.loadAgent(AGENT_JAR);
        System.out.println("[" + BRAND + "] Agent jar (" + AGENT_JAR + ") loaded into Minecraft JVM.");
        System.out.println("[" + BRAND + "] Binary payload modules (a,b,c,d,e) activated.");
        System.out.println("[" + BRAND + "] ClickGUI activates with VK_RSHIFT (Right Shift) inside Minecraft.");

        // Clean detach — loader terminates, hooks remain inside javaw.exe
        vm.detach();
        System.out.println("[" + BRAND + "] Detached cleanly. Hooks remain active inside Minecraft.");
    }

    private static String findMinecraftPID() {
        try {
            // Use ProcessBuilder to find javaw.exe (simplified for reference)
            // In full deployment: enumerate processes using Windows APIs or java.lang.management
            ProcessBuilder pb = new ProcessBuilder("tasklist", "/FI", "IMAGENAME eq javaw.exe", "/FO", "CSV", "/NH");
            Process p = pb.start();
            Scanner s = new Scanner(p.getInputStream());
            while (s.hasNextLine()) {
                String line = s.nextLine();
                if (line.contains("javaw.exe")) {
                    // Extract PID from CSV format (simplified)
                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        return parts[1].replace("\"", "").trim();
                    }
                    return "javaw.exe"; // Fallback
                }
            }
        } catch (Exception e) {
            System.out.println("[" + BRAND + "] Process search exception: " + e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println("=== " + BRAND + " ===");
        System.out.println("Self-Injecting Agent (No External Injector Needed)");
        System.out.println("Uses: Java Attach API (com.sun.tools.attach) + Native DLL (DllMain.dll)");
        System.out.println("Injection Method: Self-inject via Attach API or Native DLL.");
        System.out.println("Brand: SMELLY DIHH CLIENT — Created by Rhishav Sapkota");
        System.out.println();

        try {
            inject();
        } catch (Exception e) {
            System.out.println("[" + BRAND + "] Self-injection error: " + e.getMessage());
            System.out.println("[" + BRAND + "] Alternative: Compile DllMain.dll and inject with any external injector.");
            System.out.println("[" + BRAND + "] Command: gcc -shared -o DllMain.dll src/main/cpp/DllMain.cpp -luser32 -lgdi32 -lopengl32");
        }
    }
}
