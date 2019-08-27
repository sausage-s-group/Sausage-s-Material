package sausages_material.event.worldgen;

import com.google.common.eventbus.EventBus;
import net.minecraftforge.common.MinecraftForge;
import sausages_material.event.worldgen.ore.OreGeneratorDefault;
import sausages_material.util.EventBusBindings;

@SuppressWarnings("UnstableApiUsage")
public class WorldGenEventBus {
    public static final EventBus ORE_GEN_BUS = new EventBus("sausages_material.ORE_GEN");
    public static final EventBus TERRAIN_GEN_BUS = new EventBus("sausages_material.TERRAIN_GEN");

    static {
        EventBusBindings.destination(ORE_GEN_BUS).listen(MinecraftForge.ORE_GEN_BUS);
        EventBusBindings.destination(TERRAIN_GEN_BUS).listen(MinecraftForge.TERRAIN_GEN_BUS);
        ORE_GEN_BUS.register(OreGeneratorDefault.class);
    }
}
