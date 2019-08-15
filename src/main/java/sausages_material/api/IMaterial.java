package sausages_material.api;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public interface IMaterial {
	String name();
	int color();

	Map<IShape, Supplier<Item>> items();
	Map<IShape, Supplier<Block>> blocks();

	default IMaterial addItem(IShape shape) {
		return addItem(shape, Item::new);
	}

	default IMaterial addItem(IShape shape, Supplier<Item> supplier) {
		items().put(shape, supplier);
		return this;
	}

	default IMaterial addBlock(IShape shape, Supplier<Block> supplier) {
		blocks().put(shape, supplier);
		return this;
	}

}
