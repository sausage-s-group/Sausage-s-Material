package sausages_material.event.worldgen.ore;

import java.util.Map;

public class OreGeneratorOverride extends OreGeneratorDefault implements OreGenerator{

    private OreGeneratorOverride(Map<String, String> cfg) {}

    public static OreGenerator override(Map<String, String> oreGenConfiguration) {
        return new OreGeneratorOverride(oreGenConfiguration);
    }

}
