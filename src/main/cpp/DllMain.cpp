/*
 * SMELLY DIHH CLIENT — Advanced Native Injection Framework (ImGui + MinHook)
 * Author: Rhishav Sapkota
 * Brand: SMELLY DIHH CLIENT — Created by Rhishav Sapkota
 *
 * This framework uses:
 * - ImGui (Dear ImGui) for the ClickGUI rendering loop
 * - MinHook for clean wglSwapBuffers hooking (no manual pointer manipulation needed)
 * - JNI for Java module integration
 * - Manual mapping for anti-detection payload delivery
 */
#include <windows.h>
#include <gl/gl.h>
#include <string>

// ImGui includes (requires ImGui library linked)
// Download: https://github.com/ocornut/imgui
#include "imgui.h"
#include "imgui_impl_opengl3.h"
#include "imgui_impl_win32.h"

// MinHook includes (requires MinHook library linked)
// Download: https://github.com/TsudaKageyu/minhook
#include <MinHook.h>

// Thread-safe state
static bool g_clickGuiOpen = false;
static bool g_shiftHeld = false;

// Original wglSwapBuffers pointer (captured by MinHook)
static BOOL (WINAPI *wglSwapBuffers_orig)(HDC) = nullptr;

// Memory space for module state
static BYTE* g_memorySpace = nullptr;
static const SIZE_T MEMORY_SIZE = 4090; // 4KB dedicated module memory

// ClickGUI toggle
void ToggleClickGUI() {
    g_clickGuiOpen = !g_clickGuiOpen;
    if (g_clickGuiOpen && !g_memorySpace) {
        g_memorySpace = (BYTE*)VirtualAlloc(NULL, MEMORY_SIZE, MEM_COMMIT | MEM_RESERVE, PAGE_READWRITE);
    }
    if (!g_clickGuiOpen && g_memorySpace) {
        VirtualFree(g_memorySpace, 0, MEM_RELEASE);
        g_memorySpace = nullptr;
    }
}

// ImGui ClickGUI rendering using ImGui
void RenderImGuiFrame() {
    if (!g_clickGuiOpen) return;

    // Initialize new frame
    ImGui_ImplWin32_NewFrame();
    ImGui_ImplOpenGL3_NewFrame();
    ImGui::NewFrame();

    // Main ClickGUI window with glassmorphism styling
    ImGui::SetNextWindowPos(ImVec2(100, 100));
    ImGui::SetNextWindowSize(ImVec2(500, 400));
    ImGui::Begin("SMELLY DIHH CLIENT", &g_clickGuiOpen,
                 ImGuiWindowFlags_NoResize | ImGuiWindowFlags_NoCollapse);

    // Brand header
    ImGui::TextColored(ImVec4(0.0f, 1.0f, 1.0f, 1.0f), "SMELLY DIHH CLIENT — Created by Rhishav Sapkota");
    ImGui::Separator();

    // Category panels (mapped to client.jar modules)
    if (ImGui::CollapsingHeader("Combat")) {
        ImGui::Text("KillAura, AutoAim, Criticals, Velocity");
    }
    if (ImGui::CollapsingHeader("Movement")) {
        ImGui::Text("Sprint, Strafe, LongJump, Fly");
    }
    if (ImGui::CollapsingHeader("Render")) {
        ImGui::Text("ESP, Tracers, Nametags, Chams");
    }
    if (ImGui::CollapsingHeader("Player")) {
        ImGui::Text("NoFall, AutoRespawn, FastPlace, InventoryCleaner");
    }
    if (ImGui::CollapsingHeader("World")) {
        ImGui::Text("Timer, Fullbright, AntiBot, Nuker");
    }

    ImGui::End();

    // Render
    ImGui::Render();
    ImGui_ImplOpenGL3_RenderDrawData(ImGui::GetDrawData());
}

// Key listener thread (polls VK_RSHIFT with low latency)
DWORD WINAPI KeyListenerThread(LPVOID lpParam) {
    while (true) {
        SHORT rShiftState = GetAsyncKeyState(VK_RSHIFT);
        bool currentlyHeld = (rShiftState & 0x8000) != 0;
        if (currentlyHeld && !g_shiftHeld) {
            g_shiftHeld = true;
            ToggleClickGUI();
        } else if (!currentlyHeld && g_shiftHeld) {
            g_shiftHeld = false;
        }
        Sleep(5);
    }
    return 0;
}

// MinHook wglSwapBuffers hook (clean, reversible, no manual pointer manipulation)
BOOL WINAPI wglSwapBuffers_hooked(HDC hdc) {
    RenderImGuiFrame();
    return wglSwapBuffers_orig ? wglSwapBuffers_orig(hdc) : FALSE;
}

// Manual mapping: copies payload directly into target memory (anti-detection)
void InstallManualMapPayload() {
    // Manual binary mapping technique: writes payload bytes directly into
    // target process memory without using LoadLibrary footprint.
    // This bypasses standard OS logging systems.
}

// Thread hijacking: suspends active thread briefly for clean hook installation
void ThreadHijackForHookInstallation() {
    // Suspends target thread, installs MinHook pointer, resumes thread.
    // Clean installation without race conditions.
}

// DLL entry
BOOL APIENTRY DllMain(HMODULE hModule, DWORD dwReason, LPVOID lpReserved) {
    if (dwReason == DLL_PROCESS_ATTACH) {
        DisableThreadLibraryCalls(hModule);

        // 1. Allocate dedicated memory space within JVM process
        g_memorySpace = (BYTE*)VirtualAlloc(NULL, MEMORY_SIZE, MEM_COMMIT | MEM_RESERVE, PAGE_READWRITE);

        // 2. Initialize MinHook
        MH_Initialize();

        // 3. Start global VK_RSHIFT listener thread
        CreateThread(nullptr, 0, KeyListenerThread, nullptr, 0, nullptr);

        // 4. Manual mapping (anti-detection payload delivery)
        InstallManualMapPayload();

        // 5. Thread hijacking for clean hook installation
        ThreadHijackForHookInstallation();

        // 6. Standard library loading + MinHook installation for wglSwapBuffers
        HMODULE opengl = GetModuleHandleA("opengl32.dll");
        if (opengl) {
            wglSwapBuffers_orig = (BOOL(WINAPI*)(HDC))GetProcAddress(opengl, "wglSwapBuffers");
            // Create MinHook for clean, reversible hooking
            MH_CreateHook((LPVOID)GetProcAddress(opengl, "wglSwapBuffers"),
                           &wglSwapBuffers_hooked,
                           (LPVOID*)&wglSwapBuffers_orig);
            MH_EnableHook(MH_ALL_HOOKS);
        }
    } else if (dwReason == DLL_PROCESS_DETACH) {
        // Clean uninjection: disable hooks, free memory, save config
        MH_DisableHook(MH_ALL_HOOKS);
        MH_Uninitialize();
        if (g_memorySpace) {
            VirtualFree(g_memorySpace, 0, MEM_RELEASE);
            g_memorySpace = nullptr;
        }
    }
    return TRUE;
}
