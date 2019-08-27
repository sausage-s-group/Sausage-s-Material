package sausages_material;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.Logger;
import sausage_core.api.annotation.AutoCall;
import sausage_core.api.registry.AutoSyncConfig;
import sausage_core.api.util.client.IItemCM;
import sausage_core.api.util.client.IItemML;
import sausage_core.api.util.oredict.OreDicts;
import sausage_core.api.util.registry.IBRegistryManager;
import sausages_material.block.BlockAlloy;
import sausages_material.block.BlockMetal;
import sausages_material.block.OreMetal;
import sausages_material.event.worldgen.WorldGenEventBus;
import sausages_material.event.worldgen.ore.OreGenerator;
import sausages_material.item.ItemMaterial;
import sausages_material.material.AlloyMaterials;
import sausages_material.material.IMaterial;
import sausages_material.material.MetalMaterials;
import sausages_material.material.MetalShapes;
import scala.tools.nsc.Global;

import java.util.*;
import java.util.function.Function;

import static sausage_core.api.util.common.SausageUtils.nonnull;

/**
 * @author Yaossg
 */
@Mod(modid = SausagesMaterial.MODID,
		name = SausagesMaterial.NAME,
		version = SausagesMaterial.VERSION,
		acceptedMinecraftVersions = "1.12.2",
		dependencies = "required-after:sausage_core@[1.6,)")
@Mod.EventBusSubscriber
@MethodsReturnNonnullByDefault
@SuppressWarnings("unused")
public enum SausagesMaterial {
	INSTANCE;

	public static final String MODID = "sausages_material";
	public static final String NAME = "Sausage's Material";
	public static final String VERSION = "@version@";
	public static Logger logger;
    private static final IBRegistryManager manager = new IBRegistryManager(MODID, new CreativeTabs(MODID) {
		@Override
		public ItemStack createIcon() {
			try {
				return new ItemStack((Item) Class.forName("sausage_core.SausageCore").getField("sausage").get(null));
			} catch (IllegalAccessException | NoSuchFieldException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			return new ItemStack(Items.IRON_INGOT);
		}
	});

    @AutoCall(when = AutoCall.When.INIT)
	static final Set<Runnable> initCall = Sets.newHashSet(() -> MinecraftForge.ORE_GEN_BUS.register(OreGenerator.getGenerator()), WorldGenEventBus::new);
	@SuppressWarnings("SameReturnValue")
	@Mod.InstanceFactory
	public static SausagesMaterial getInstance() {
		return INSTANCE;
	}
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
		IItemCM colorer = IItemCM.mappingBy(stack -> IMaterial.getMaterial(stack.getItemDamage()).color());
		logger = event.getModLog();
		MinecraftForge.EVENT_BUS.register(AutoSyncConfig.class);
		AutoSyncConfig.AUTO_SYNC_CONFIG.register(MODID);
		for(MetalShapes metalShape:MetalShapes.values()){
			if(metalShape==MetalShapes.block||metalShape==MetalShapes.ore)continue;
			ItemMaterial item = new ItemMaterial(metalShape);
			manager.addItem(metalShape.location().getPath(), item);
			manager.addItemCM(item, colorer);
		}

		IItemML ml = $-> ModelLoader.setCustomStateMapper(Block.getBlockFromItem($), block->{
            Map<IBlockState, ModelResourceLocation> model = Maps.newHashMap();
            ModelResourceLocation modelLocation = new ModelResourceLocation(new ResourceLocation("sausages_material",nonnull(block.getRegistryName()).getPath()),"default");
		    for(IBlockState state:block.getBlockState().getValidStates()){
		        model.put(state,modelLocation);
            }
		    return model;
        });
		BlockAlloy block = new BlockAlloy();
		manager.addBlock("block_alloy", block, ml);
		manager.addBlockCM(block, this::colorMultiplier);

		BlockMetal block2 = new BlockMetal();
		manager.addBlock("block_metal", block2, ml);
		manager.addBlockCM(block2, this::colorMultiplier);

		OreMetal block3 = new OreMetal();
		manager.addBlock("ore_metal", block3, ml);
		manager.addBlockCM(block3, this::colorMultiplier);
	}

	private int colorMultiplier(IBlockState state, IBlockAccess world, BlockPos position, int tintIndex) {
		Block block = state.getBlock();
		if (block instanceof BlockAlloy) {
			return state.getValue(BlockAlloy.MATERIAL).color();
		} else if (block instanceof BlockMetal) {
			return state.getValue(BlockMetal.MATERIAL).color();
		} else if (block instanceof OreMetal) {
			return state.getValue(OreMetal.MATERIAL).color();
		}
		return 0;
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
