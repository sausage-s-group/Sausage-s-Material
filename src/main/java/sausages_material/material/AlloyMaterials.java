package sausages_material.material;

import net.minecraft.util.IStringSerializable;
import sausage_core.api.util.client.Colors;

import java.util.Arrays;
import java.util.List;

import static sausages_material.material.MetalMaterials.*;

public enum AlloyMaterials implements IMetalAlloy, IStringSerializable {
	bronze(Colors.GOLD, new WeightedMetal(copper, 3), new WeightedMetal(tin)),
	constantan(Colors.CORAL, new WeightedMetal(copper), new WeightedMetal(nickel)),
	invar(Colors.IVORY, new WeightedMetal(iron, 2), new WeightedMetal(nickel)),
	
	electrum(Colors.LIGHT_GOLDENROD_YELLOW, new WeightedMetal(gold), new WeightedMetal(silver)),
	stainless(Colors.LIGHT_GRAY, new WeightedMetal(iron, 2), new WeightedMetal(chromium)),
	platiridinum(Colors.LIGHT_STEEL_BLUE, new WeightedMetal(platinum), new WeightedMetal(iridium)),
	steel(Colors.GRAY),
	hardened(Colors.DARK_GREEN);


	public final List<WeightedMetal> ingredients;
	public final int color;

	AlloyMaterials(int color, WeightedMetal... ingredients) {
		this.ingredients = Arrays.asList(ingredients);
		this.color = color;
	}

	@Override
	public List<WeightedMetal> ingredients() {
		return ingredients;
	}

	@Override
	public int color() {
		return color;
	}

	@Override
	public String getName() {
		return name();
	}
}
