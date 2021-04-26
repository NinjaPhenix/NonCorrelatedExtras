package ninjaphenix.noncorrelatedextras;

import net.minecraft.resources.ResourceLocation;

public class Main {
    public static final String MOD_ID = "noncorrelatedextras";

    public static ResourceLocation resloc(String path) {
        return new ResourceLocation(Main.MOD_ID, path);
    }
}
