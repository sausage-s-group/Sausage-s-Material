package sausages_material.api;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import sausage_core.api.util.client.Colors;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public enum MetalMaterials implements IMaterial {
	// colors here now are placeholders
	//elements
	iron(Colors.WHITE),
	copper(0Xffd99122),
	tin(0Xffabbec9),
	aluminium(Colors.WHITE_SMOKE),

	chromium(Colors.ORANGE_RED),
	tungsten(Colors.LIGHT_GREEN),
	nickel(Colors.PLUM),
	lead(0Xff6e7ba3),

	gold(0Xfff3fa00),
	silver(Colors.SILVER),
	platinum(0Xffa5e1f5),
	iridium(Colors.SNOW),
	//non-metal alloys
	steel(Colors.GRAY),
	hardened(Colors.DARK_GREEN)
	;


	public final int color;
	private final Map<IShape,Supplier<Item>> items = Maps.newHashMap();
	private final Map<IShape,Supplier<Block>> blocks = Maps.newHashMap();


	MetalMaterials(int color) {
		this.color = color;
	}

	@Override
	public int color() {
		return color;
	}

	@Override
	public Map<IShape, Supplier<Item>> items() {
		return items;
	}

	@Override
	public Map<IShape, Supplier<Block>> blocks() {
		return blocks;
	}
}

/*
征集：20种金属的颜色，他们是
纯金属：Al Cr Fe Ni Cu Ag Sn W Ir Pt Au Pb
合金：Cu-Sn Cu-Ni Fe-C W-C Fe-Cr Fe-Ni Au-Ag Pt-Ir
*/