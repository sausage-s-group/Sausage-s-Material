package sausages_material;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.Logger;
import sausage_core.api.registry.AutoSyncConfig;
import sausage_core.api.util.common.SausageUtils;
import sausage_core.api.util.oredict.OreDicts;
import sausages_material.api.*;

import java.util.ArrayList;

/**
 * @author Yaossg
 */
@Mod(modid = SausagesMaterial.MODID,
		name = SausagesMaterial.NAME,
		version = SausagesMaterial.VERSION,
		acceptedMinecraftVersions = "1.12.2",
		dependencies = "required-after:sausage_core@[1.5,)")
@Mod.EventBusSubscriber
public class SausagesMaterial {
	public static final String MODID = "sausages_material";
	public static final String NAME = "Sausage's Material";
	public static final String VERSION = "@version@";
	public static Logger logger;

	/**
	 * Fix problem of different ore names for Al
	 */
	@SubscribeEvent
	public static void onRegisterOre(OreDictionary.OreRegisterEvent event) {
		String ore = event.getName();
		String material = OreDicts.materialOf(ore);
		switch(material) {
			case "Aluminum":
				OreDicts.shapeOf(ore).ifPresent(shape -> {
					String name = shape + "Aluminium";
					if(OreDicts.names(event.getOre()).noneMatch(name::equals))
						OreDictionary.registerOre(name, event.getOre());
				});
				break;
			case "Aluminium":
				OreDicts.shapeOf(ore).ifPresent(shape -> {
					String name = shape + "Aluminum";
					if(OreDicts.names(event.getOre()).noneMatch(name::equals))
						OreDictionary.registerOre(name, event.getOre());
				});
		}
	}


	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		SausageUtils.loadingInformation(NAME, VERSION, MODID);
		MinecraftForge.EVENT_BUS.register(AutoSyncConfig.class);
		AutoSyncConfig.AUTO_SYNC_CONFIG.register(MODID);

		for(IMaterial material : MetalMaterials.values()) {
			MaterialRegistry.INSTANCE.add(material);
			for(MetalShapes shapes : MetalShapes.values()) {
				material.addItem(shapes);
			}

		}

		for(IMaterial material : AlloyMaterials.values()) {
			MaterialRegistry.INSTANCE.add(material);
			for(MetalShapes shapes : MetalShapes.values()) {
				material.addItem(shapes);
			}

		}



		MaterialRegistry.INSTANCE.register();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		if(event.getSide().isClient()) MaterialRegistry.INSTANCE.color();

	}


	@SubscribeEvent
	public static void loadModels(ModelRegistryEvent event) {
		MaterialRegistry.INSTANCE.loadModel();
	}

}
