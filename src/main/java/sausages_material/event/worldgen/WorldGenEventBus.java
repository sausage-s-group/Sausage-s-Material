package sausages_material.event.worldgen;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import sausage_core.api.annotation.LoadClass;
import sausages_material.SausagesMaterial;
import sausages_material.event.worldgen.ore.OreGenerator;
import sausages_material.util.EventBusBindings;
import sausages_material.util.EventExceptionHandler;

@LoadClass(when = LoadClass.When.PRE_INIT)
public class WorldGenEventBus {
    public static final EventBus ORE_GEN_BUS = new EventBus(EventExceptionHandler.getDefault());
    public static final EventBus TERRAIN_GEN_BUS = new EventBus(EventExceptionHandler.getDefault());
    static {
        EventBusBindings.destination(ORE_GEN_BUS).listen(MinecraftForge.ORE_GEN_BUS);
        EventBusBindings.destination(TERRAIN_GEN_BUS).listen(MinecraftForge.TERRAIN_GEN_BUS);
        ORE_GEN_BUS.register(OreGenerator.getGenerator());
    }
}
