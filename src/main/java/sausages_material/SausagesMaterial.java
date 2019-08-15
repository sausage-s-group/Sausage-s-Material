package sausages_material;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.Logger;
import sausage_core.api.registry.AutoSyncConfig;
import sausage_core.api.util.client.IBlockCM;
import sausage_core.api.util.client.IItemCM;
import sausage_core.api.util.oredict.OreDicts;
import sausage_core.api.util.registry.IBRegistryManager;
import sausages_material.api.MetalShapes;
import sausages_material.block.BlockMaterial;
import sausages_material.item.ItemMaterial;

import java.util.List;

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
	private static final IBRegistryManager manager = new IBRegistryManager(MODID, new CreativeTabs("Sausage's Material") {
        @Override
        public ItemStack createIcon() {
            return null;
        }
    });
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
		IItemCM colorer = IItemCM.mappingBy(stack -> ItemMaterial.getMaterial(stack.getItemDamage()).color());
		IBlockCM blockCM = (state, worldIn, pos, tintIndex) -> tintIndex==0?0:ItemMaterial.getMaterial(state.getValue(BlockMaterial.TYPE)).color();
		logger = event.getModLog();
		MinecraftForge.EVENT_BUS.register(AutoSyncConfig.class);
		AutoSyncConfig.AUTO_SYNC_CONFIG.register(MODID);

		for(MetalShapes metalShape:MetalShapes.values()){
			if(metalShape==MetalShapes.block||metalShape==MetalShapes.ore)continue;
			ItemMaterial item = new ItemMaterial(metalShape);
			manager.addItem(metalShape.name(), item);
			manager.addItemCM(item, colorer);
		}
		BlockMaterial block = new BlockMaterial(MetalShapes.block);
		manager.addBlock(MetalShapes.block.name(),block);
		manager.addBlockCM(block, blockCM);

		block = new BlockMaterial(MetalShapes.ore);
		manager.addBlock(MetalShapes.ore.name(),block);
		manager.addBlockCM(block,blockCM);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event){

	}

	@EventHandler
	public void onIMCMessageDistributed(FMLInterModComms.IMCEvent event){
		List<FMLInterModComms.IMCMessage> messages = event.getMessages();
		//TODO communicate with other mods.
	}


}
