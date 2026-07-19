# UI Redesign – Change Summary

## What this deliverable is

The existing `client.jar_Decompiler.com.zip` ships an obfuscated loader (`m.class`
custom ClassLoader, `l.class` crypto/unpacker, `k.class` native-lib extractor)
that decrypts the actual client at runtime from the bundled `64FV7P4H2NO7Q` blob.
Because the render/GUI classes live inside that encrypted payload (not as
decompiled Java source), I preserved them **as-is** and added a brand-new
presentation layer (`net.hackclient.ui`) alongside them, modelled after the
VAPE-style ArrayList screenshots AND the LionClient ClickGUI screenshot you
sent at the end.

The new UI is wired in through a single `net.hackclient.compat.Bridge` class
that every existing bootstrap entry point calls before delegating to the
original encrypted bootstrap. Backend behaviour (modules, combat, movement,
networking, …) is completely untouched – only the presentation layer is new.

## Modified files (in `client_src/`)

All bootstrap stubs were updated to initialise the new UI framework while
still invoking the original payload:

- `net/java/h.java` (Fabric `ModInitializer`)
- `net/java/i.java` (Forge `@Mod("dd")`)
- `net/java/r.java` (Legacy LabyMod addon)
- `net/java/s.java` (Modern LabyMod `@AddonMain`)
- `net/java/ag.java` (Java Agent `premain`)
- `mod_d.java` (BaseMod entry)

Each change is a two-line insertion: `m.a(); Bridge.bootstrap(type, path);`
immediately before the original `l.a(new Object[]{...})` call. No other logic
was altered.

## New files (in `ui_framework/`)

### Rendering architecture
- `render/RenderContext.java` – backend-agnostic drawing surface (default
  targets AWT `Graphics2D`; subclass for Minecraft GL). Provides
  `fillRect`, `fillGradientV`, `fillRoundedRect`, `drawRect`, `drawRoundedRect`,
  `drawString`, `drawShadow`, scissor stacks.
- `util/MathUtil.java`, `util/Rectangle.java` – allocation-free maths and
  mutable bounds rectangles used by layout/hit-testing.

### Theme system
- `theme/Color.java` – immutable RGBA, ARGB pack/unpack, HSB rainbow,
  linear interpolation, VAPE orange→red gradient helper.
- `theme/ColorPalette.java` – VAPE-style orange/red HUD palette.
- `theme/LionPalette.java` – LionClient-style navy/blue ClickGUI palette.
- `theme/Theme.java` – bundle of metrics (corner radius, padding, shadow)
  and colors with a `Builder`.
- `theme/ThemeManager.java` – registry + currently-active theme, hot-swappable.

### Font management
- `font/FontManager.java` – TTF loader (registers the bundled Proxima Nova
  regular/bold and Assetsio fonts from `files.zip`), with per-size derived
  fonts and cached string-width/line-height measurements.
- `resources/fonts/{proxima.ttf,proximabd.ttf,assetsio.ttf}` – the reference
  TTFs copied from `files.zip/fonts/`.
- `resources/fonts/fonts.json` – the reference font manifest.

### Animation
- `animation/Easing.java` – `LINEAR`, `EASE_IN_OUT_CUBIC`, `EASE_OUT_QUAD`,
  `EASE_OUT_BACK`.
- `animation/AnimationController.java` – GC-free animation state used for
  hover, press, fade, slide, toggle.

### Layout
- `layout/LayoutManager.java` – simple vertical/horizontal stack layout
  with stretch-aligned children and configurable gaps/padding.

### Input
- `input/InputEvent.java` – mutable, reusable mouse/key event structure.

### Reusable component library
- `component/UIComponent.java` – abstract base (bounds, hover/press state,
  opacity/hover animations, child list, hit-testing, input dispatch).
- `component/TextLabel.java`
- `component/Panel.java` (rounded container with padding + border + layout)
- `component/BaseWindow.java` (draggable titled window with shadow)
- `component/Button.java` (accent-colored pressable)
- `component/Toggle.java` (pill on/off switch)
- `component/CheckBox.java` (square checkbox – used in Lion settings rows)
- `component/Slider.java` (compact horizontal slider)
- `component/SliderRow.java` (full-width Lion-style setting row with label,
  number-box readout and slider track)
- `component/NumberBox.java` (right-aligned sunken value readout)
- `component/Dropdown.java` (select control with animated list)
- `component/SearchField.java` (search box with icon + caret + focus underline)
- `component/CategoryContainer.java` (collapsible titled category group)
- `component/ScrollArea.java` (vertical scroll viewport + scrollbar)
- `component/Notification.java` (toast for ClickGUI screens)
- `component/Tooltip.java` (small floating label)
- `component/ConfettiBackground.java` – the drifting square particles
  behind LionClient's UI.

### HUD layer (in-game overlay)
- `hud/HudElement.java` – anchor/facing/scale abstract element.
- `hud/WatermarkElement.java` – VAPE "VAPE v4" wordmark with gradient,
  shadow, outlined badge, breathing animation.
- `hud/ArrayListElement.java` – VAPE ArrayList with per-row dark
  translucent backgrounds, vertical orange→red accent rail, optional
  rainbow, right-aligned tags – matches both reference screenshots.
- `hud/TextElement.java` – single rainbow/solid shadowed text.
- `hud/ScoreboardElement.java` – left/right label + value scoreboard.
- `hud/NotificationToast.java` – HUD-level slide-in toast.
- `hud/HudRenderer.java` – orchestrator that renders every HUD element
  plus toast stack.
- `hud/HudConfig.java` – parses the existing `files.zip/LiquidBounce-1.8.9/hud.json`
  schema so your saved HUD layout is preserved.
- `resources/hud.json` – the reference HUD layout shipped with `files.zip`.

### ClickGUI
- `gui/LionClickGui.java` – the new flagship ClickGUI modelled 1:1 on the
  LionClient screenshot: top brand header ("LionClient v1.0.5"), horizontal
  tab bar (COMBAT/MOVEMENT/CLIENT/RENDER/PLAYER/MISC) with the active tab
  filled in blue, a two-column layout (left = module list with selected
  item highlighted blue, right = module description + an "Enabled"
  checkbox + a scrollable list of Bool/Number/Enum setting rows styled
  exactly like the reference), and a drifting confetti background.

### Top-level controller
- `UIController.java` – owns the `LionClickGui` and `HudRenderer`, exposes
  `renderHud`/`renderScreen`/`onMouse*`/`onKey*`/`onCharType` for the host
  mod to call from its RenderGameOverlayEvent / GuiScreen hooks.

### Compatibility bridge
- `compat/Bridge.java` – one-liner used by every bootstrap entry point to
  initialise the UI before the encrypted payload runs.

### Standalone preview
- `preview/Preview.java` – launches the entire UI inside a Swing JFrame
  (no Minecraft needed) so you can iterate on look-and-feel.

### Minimal JSON (no external deps)
- `org/json/JSONArray.java`, `JSONObject.java`, `Parser.java` – tiny
  parser sufficient for `hud.json`, avoiding a dependency on the real
  org.json jar.

## How to build / preview

```bash
cd ui_framework
bash build.sh          # compiles to ui_framework/out
java -cp out net.hackclient.ui.preview.Preview
```

Press RIGHT_SHIFT in the preview window to toggle the ClickGUI. Click
tabs, modules, sliders, checkboxes and dropdowns – everything is wired up.

## How to integrate with Minecraft

From your mod's overlay hook (the one that currently calls into the
obfuscated HUD renderer) do:

```java
import net.hackclient.compat.Bridge;
import net.hackclient.ui.UIController;

// once at startup:
Bridge.bootstrap(5, "/64FV7P4H2NO7Q");

// every frame in RenderGameOverlayEvent:
UIController.ui().renderHud(ctx, width, height, partialTicks);

// when the ClickGUI is open (GuiScreen):
UIController.ui().renderScreen(ctx, width, height, partialTicks);

// in mouse/key handlers:
UIController.ui().onMouseMove(x, y);
UIController.ui().onMousePress(x, y, button);
UIController.ui().onMouseRelease(x, y, button);
UIController.ui().onMouseScroll(x, y, delta);
UIController.ui().onKeyPress(keyCode);
UIController.ui().onCharType(codepoint);
```

For a GL backend, subclass `RenderContext` and override the primitive
`fill*`/`drawString` methods to use Minecraft's `Tessellator`/`BufferBuilder`
and the client's `FontRenderer` – every component/HUD element above that
layer works without modification.

## Original files preserved

The obfuscated classes (`g.java`, `l.java`, `k.java`, `y.java`, `m.java`,
`t.java`) and the encrypted payload blobs (`a/b/c/d/e`, `64FV7P4H2NO7Q`)
are left untouched – they remain responsible for the cheat's backend
behaviour; the new `ui_framework` is additive.
