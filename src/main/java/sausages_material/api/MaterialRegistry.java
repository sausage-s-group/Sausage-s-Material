package sausages_material.api;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;
import sausage_core.api.common.InternalUse;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MaterialRegistry {

	public static final MaterialRegistry INSTANCE = new MaterialRegistry();

	private Map<String, IMaterial> materials = new HashMap<>();
	Map<IMaterial, Map<IShape, Supplier<Item>>> sItem = new HashMap<>();
	Map<IMaterial, Map<IShape, Supplier<Block>>> sBlock = new HashMap<>();
	private Map<Item, Pair<IShape, IMaterial>> items = new HashMap<>();
	private Map<Block, Pair<IShape, IMaterial>> blocks = new HashMap<>();
	private DelegatedManager manager = new DelegatedManager();

	public IMaterial add(IMaterial material) {
		materials.put(material.name(), material);
		return material;
	}


	Pair<IShape, IMaterial> get(Item item) {
		return items.get(item);
	}

	Pair<IShape, IMaterial> get(Block block) {
		return blocks.get(block);
	}

	public Map<String, IMaterial> getMaterials() {
		return Collections.unmodifiableMap(materials);
	}

	@InternalUse
	public void register() {
		materials.forEach((name, material) -> {
			material.items().forEach((shape, supplier) -> {
				Item item = supplier.get();
				manager.addItem(name + "_" + shape.name(), item);
				items.put(item, Pair.of(shape, material));
			});
			material.blocks().forEach((shape, supplier) -> {
				Block block = supplier.get();
				manager.addBlock(name + "_" + shape.name(), block);
				blocks.put(block, Pair.of(shape, material));
			});
		});
		manager.registerAll();
	}

	@InternalUse
	@SideOnly(Side.CLIENT)
	public void loadModel() {
		manager.loadAllModel();
	}

	@InternalUse
	@SideOnly(Side.CLIENT)
	public void color() {
		manager.color();
	}
}
