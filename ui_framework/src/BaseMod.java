/** Stub base class for mod_d; the legacy loader API at runtime will provide the real BaseMod. */
public class BaseMod {
    public String getVersion() { return "1.0"; }
    public void load() {}
}
