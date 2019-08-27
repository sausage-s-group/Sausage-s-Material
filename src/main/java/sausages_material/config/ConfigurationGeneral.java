package sausages_material.config;

import net.minecraftforge.common.config.Config;
import sausages_material.SausagesMaterial;

@Config(modid = SausagesMaterial.MODID,name = "general")
@Config.LangKey("cfg.sausages_material.general")
public class ConfigurationGeneral {
    @Config.Name("world_generator_singleton")
    @Config.LangKey("cfg.sausages_material.general.singleton_ore_gen")
    @Config.Comment("If world generators(sausages_material.event.worldgen.*) run in singleton mode.")
    @Config.RequiresMcRestart
    private static boolean singletonOreGen = false;

    public static boolean isSingletonOreGen() {
        return singletonOreGen;
    }
}
