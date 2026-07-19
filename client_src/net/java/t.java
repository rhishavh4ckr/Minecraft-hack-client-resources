package net.java;

import net.labymod.api.addon.AddonConfig;
import net.labymod.api.configuration.loader.property.ConfigProperty;

public class t extends AddonConfig {
   private ConfigProperty a;

   public t() {
      this.a = new ConfigProperty(Boolean.TRUE);
   }

   public ConfigProperty enabled() {
      return this.a;
   }
}
