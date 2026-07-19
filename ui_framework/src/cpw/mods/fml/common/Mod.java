package cpw.mods.fml.common;
import java.lang.annotation.*;
/** Stub for compilation only; provided by Forge at runtime. */
@Retention(RetentionPolicy.RUNTIME) @Target(ElementType.TYPE)
public @interface Mod { String modid() default ""; String value() default ""; }
