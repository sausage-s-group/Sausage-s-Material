package sausages_material.api;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;
import sausage_core.api.util.registry.IBRegistryManager;
import sausages_material.SausagesMaterial;

public final class DelegatedManager extends IBRegistryManager {
	public DelegatedManager() {
		super(SausagesMaterial.MODID, new CreativeTabs(SausagesMaterial.MODID + ".materials") {
			@Override
			public ItemStack getTabIconItem() {
				return new ItemStack(Items.IRON_INGOT);
			}
		});
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected void loadModel(Item item) {
		Pair<IShape, IMaterial> pair = MaterialRegistry.INSTANCE.get(item);
		render(item, pair.getLeft().location());
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected void loadModel(Block block) {
		Pair<IShape, IMaterial> pair = MaterialRegistry.INSTANCE.get(block);
		render(block, pair.getLeft().location());
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected void loadModel(ItemStack stack) {
	}

	@SideOnly(Side.CLIENT)
	public static void render(Item item, ResourceLocation location) {
		ModelLoader.setCustomMeshDefinition(item, new SimpleItemMeshDefinition(location, "inventory"));
		ModelLoader.registerItemVariants(item, new ModelResourceLocation(location, "inventory"));
	}

	@SideOnly(Side.CLIENT)
	public static void render(Block block, ResourceLocation location) {
		ModelLoader.setCustomStateMapper(block, blockIn -> ImmutableMap.of(blockIn.getDefaultState(), new ModelResourceLocation(location, "normal")));
		ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(block), new SimpleItemMeshDefinition(location, "normal"));
	}

	@SideOnly(Side.CLIENT)
	public void color() {
		items.forEach(item -> color(item, MaterialRegistry.INSTANCE.get(item).getRight().color()));
		blocks.forEach(block -> color(block, MaterialRegistry.INSTANCE.get(block).getRight().color()));
	}

	@SideOnly(Side.CLIENT)
	private void color(Block block, int color) {
		if (block != null) {
			Minecraft.getMinecraft().getItemColors().registerItemColorHandler((stack, tintIndex) -> {
				if (tintIndex == 0 && stack.getItem().equals(Item.getItemFromBlock(block))) {
					return color;
				}
				return 0xFFFFFF;
			}, block);
			Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler((state, worldIn, pos, tintIndex) -> {
				if (tintIndex == 0 && state.getBlock().equals(block)) {
					return color;
				}
				return 0xFFFFFF;
			}, block);
		}
	}

	@SideOnly(Side.CLIENT)
	private void color(Item item, int color) {
		if (item != null) {
			Minecraft.getMinecraft().getItemColors().registerItemColorHandler((stack, tintIndex) -> {
				if (tintIndex == 0 && stack.getItem().equals(item)) {
					return color;
				}
				return 0xFFFFFF;
			}, item);
		}
	}

	@SideOnly(Side.CLIENT)
	static class SimpleItemMeshDefinition implements ItemMeshDefinition {
	    protected ResourceLocation location;
	    protected String variants;

	    public SimpleItemMeshDefinition(ResourceLocation location, String variants) {
	        this.location = location;
	        this.variants = variants;
	    }

	    @Override
	    public ModelResourceLocation getModelLocation(ItemStack stack) {
	        return new ModelResourceLocation(location, variants);
	    }
	}
}
