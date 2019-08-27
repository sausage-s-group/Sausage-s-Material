package sausages_material.config;

import net.minecraftforge.common.config.Config;
import sausages_material.SausagesMaterial;

@Config(modid = SausagesMaterial.MODID,name = "customization")
@Config.LangKey("cfg.sausages_material.customization")
public class ConfigurationCustomization {
    @Config.Name("ore_gen_overridable")
    @Config.LangKey("cfg.sausages_material.customization.overridable_ore_gen")
    @Config.Comment("If OreGenerator is able to be wrapped by API.")
    private static boolean useOverridableOreGen = false;

    public static boolean useOverridableOreGen(){
        return useOverridableOreGen;
    }
}
