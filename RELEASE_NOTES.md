## LionClient Redesigned — VAPE HUD + LionClient ClickGUI

### What's new
- **VAPE v4 HUD ArrayList** — orange→red vertical accent rail, VAPE wordmark, dark translucent rows, drop-shadow text, bundled Proxima Nova + Assetsio TTFs.
- **LionClient-styled ClickGUI** — navy `#1e2d3d` panels, `#4AA0FF` bright blue accent, top tab bar (COMBAT / MOVEMENT / CLIENT / RENDER / PLAYER / MISC), left module list + right settings panel (checkboxes, sliders, dropdowns), confetti particle background.
- All backend / module / cheat logic is preserved untouched — only the presentation layer was replaced, wired in through `net.hackclient.compat.Bridge`.
- Press **RIGHT_SHIFT** in-game to open the new ClickGUI.

### Install (Windows — CMD, JDK 21)
1. Download **`LionClient-Redesigned.zip`** from the Assets below and extract it anywhere (e.g. `C:\LionClient\`).
2. Inside the extracted folder, **double-click `BUILD.BAT`** (or open a Command Prompt in that folder and run `BUILD.BAT`). It auto-finds `javac` on your PATH (JDK 21 works).
3. When the batch finishes, it produces `build\client-patched.jar`.
4. Drop `client-patched.jar` into your Minecraft `mods\` folder (Forge / Fabric / LabyMod) **or** run it standalone:
   ```
   java -jar build\client-patched.jar
   ```

See `INSTALL.txt` and `CHANGES.md` inside the zip for full details.
