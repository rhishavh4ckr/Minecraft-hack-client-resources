package net.labymod.api.addon;
/** Stub for compilation; provided by LabyMod at runtime. */
public abstract class LabyAddon<C> {
    protected abstract void enable();
    protected abstract Class<C> configurationClass();
}
