# LionClient-style UI Framework

This directory contains a complete redesign of the client's user interface
layer, replacing the obfuscated original renderer with a clean, themeable,
component-based architecture that matches the LionClient / VAPE v4 visual
style from the reference screenshots.

## Layout

```
ui_framework/
├── resources/
│   ├── fonts/            # TTF fonts (proxima.ttf, proximabd.ttf, assetsio.ttf)
│   ├── fonts.json        # Font manifest (from files.zip)
│   └── hud.json          # Default HUD layout (from files.zip)
└── src/
    ├── net/hackclient/
    │   ├── compat/       # Bridges from the old entry points into the new UI
    │   └── ui/
    │       ├── UIController.java          # Top-level UI orchestrator
    │       ├── animation/                 # AnimationController + Easing
    │       ├── component/                 # Reusable UI components
    │       ├── font/                      # FontManager (TTF loader, text metrics)
    │       ├── gui/LionClickGui.java      # The new flagship ClickGUI
    │       ├── hud/                       # HUD elements (Watermark, ArrayList, …)
    │       ├── input/InputEvent.java      # GC-free input event struct
    │       ├── layout/LayoutManager.java  # Vertical/horizontal stacking
    │       ├── preview/Preview.java       # Standalone Swing preview (no MC needed)
    │       ├── render/RenderContext.java  # Surface abstraction (AWT / GL pluggable)
    │       ├── theme/                     # Color, Theme, ThemeManager, palettes
    │       └── util/                      # MathUtil, Rectangle
    └── org/json/                          # Minimal JSON parser (no ext. deps)
```

## Design highlights

- **Two palettes**: VAPE orange→red for the HUD/watermark (matches the
  original ArrayList screenshots) and a new **Lion Dark** navy/blue palette
  for the ClickGUI (matches the second reference screenshot).
- **Theme system**: `ThemeManager` swaps palettes at runtime; every
  component reads `ThemeManager.current()` each frame.
- **Component library**: `BaseWindow`, `Panel`, `Button`, `Toggle`,
  `CheckBox`, `Slider`, `SliderRow`, `Dropdown`, `EnumSetting`, `NumberBox`,
  `NumberSetting`, `SearchField`, `CategoryContainer`, `ScrollArea`,
  `TextLabel`, `Notification`, `Tooltip`, `ConfettiBackground`.
- **HUD elements**: `WatermarkElement` (VAPE "VAPE v4" wordmark),
  `ArrayListElement` (the enabled-module list with the vertical accent
  rail), `ScoreboardElement`, `TextElement`, plus `HudConfig` which parses
  the existing `hud.json` schema from `files.zip` so user layouts carry
  over unchanged.
- **Performance**:
  - Zero-allocation input events (`InputEvent` is reused every frame).
  - Cached text measurements in `FontManager.CachedFont`.
  - Bounded LRU behaviour on the width cache.
  - Mutable `Rectangle` bounds to avoid GC churn during layout.
  - No iterator/stream allocations in the hot render loop.
- **Animations**: `AnimationController` with easing functions; hover,
  toggle-slide, window open/close, toast slide, confetti drift.

## Build

```bash
cd ui_framework
mkdir -p out
javac -d out -encoding UTF-8 $(find src -name "*.java")
```

## Run preview (Swing, no Minecraft required)

```bash
java -cp out net.hackclient.ui.preview.Preview
```

Press RIGHT_SHIFT to toggle the ClickGUI. Click tabs on the top bar, click
modules in the left column, drag sliders, toggle checkboxes, open dropdowns.

## Integrating with the mod

The bootstrap stubs in `client_src/net/java/{h,i,r,s,ag}.java` and
`client_src/mod_d.java` now call `net.hackclient.compat.Bridge.bootstrap(...)`
immediately before handing off to the encrypted backend bootstrap. This
means:

1. The original backend module logic (combat, movement, etc.) is untouched.
2. The new UI framework is initialised before the first render so HUD
   elements and the ClickGUI can be displayed.
3. From a Minecraft hook (the RenderGameOverlayEvent / GuiScreen), call:
   ```java
   UIController.instance().renderHud(ctx, width, height, partialTicks);
   UIController.instance().renderScreen(ctx, width, height, partialTicks);
   ```
   and forward mouse/keyboard events via the `onMouseXxx` / `onKeyPress` /
   `onCharType` methods.

## RenderContext is backend-agnostic

The default `RenderContext` targets an AWT `Graphics2D` surface (used by
the preview and by the HUD screenshots). For Minecraft, subclass
`RenderContext` and override the primitive methods (`fillRect`,
`fillGradientV`, `fillRoundedRect`, `drawString`) to emit GL quads using
the client's own tessellator / font renderer; everything above it in the
stack (components, HUD elements, ClickGUI) is agnostic to how pixels are
pushed.
