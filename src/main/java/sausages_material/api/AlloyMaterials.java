package sausages_material.api;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import sausage_core.api.util.client.Colors;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static sausages_material.api.MetalMaterials.*;

public enum AlloyMaterials implements IMetalAlloy {
	bronze(Colors.GOLD, new WeightedMetal(copper, 3), new WeightedMetal(tin)),
	constantan(Colors.CORAL, new WeightedMetal(copper), new WeightedMetal(nickel)),
	invar(Colors.IVORY, new WeightedMetal(iron, 2), new WeightedMetal(nickel)),
	
	electrum(Colors.LIGHT_GOLDENROD_YELLOW, new WeightedMetal(gold), new WeightedMetal(silver)),
	stainless(Colors.LIGHT_GRAY, new WeightedMetal(iron, 2), new WeightedMetal(chromium)),
	platiridinum(Colors.LIGHT_STEEL_BLUE, new WeightedMetal(platinum), new WeightedMetal(iridium));


	public final List<WeightedMetal> ingredients;
	public final int color;
	private final Map<IShape, Supplier<Item>> items = Maps.newHashMap();
	private final Map<IShape,Supplier<Block>> blocks = Maps.newHashMap();

	AlloyMaterials(int color, WeightedMetal... ingredients) {
		this.ingredients = Arrays.asList(ingredients);
		this.color = color;
	}

	@Override
	public Map<IShape, Supplier<Item>> items() {
		return items;
	}

	@Override
	public Map<IShape, Supplier<Block>> blocks() {
		return blocks;
	}

	@Override
	public List<WeightedMetal> ingredients() {
		return ingredients;
	}

	@Override
	public int color() {
		return color;
	}
}
