package sausages_material.material;

import net.minecraft.util.IStringSerializable;
import sausage_core.api.util.client.Colors;
import sausages_material.event.worldgen.ore.GeneratePredication;

import java.util.Arrays;

public enum MetalMaterials implements IMaterial, IStringSerializable {
	// colors here now are placeholders
	//elements
	iron(Colors.WHITE, null),
	copper(0Xffd99122, null),
	tin(0Xffabbec9, null),
	aluminium(Colors.WHITE_SMOKE, null),

	chromium(Colors.ORANGE_RED, null),
	tungsten(Colors.LIGHT_GREEN, null),
	nickel(Colors.PLUM, null),
	lead(0Xff6e7ba3, null),

	gold(0Xfff3fa00, null),
	silver(Colors.SILVER, null),
	platinum(0Xffa5e1f5, null),
	iridium(Colors.SNOW, null),
	;

	public final int color;

	MetalMaterials(int color, GeneratePredication predicate) {
		this.color = color;
	}

	public static String[] names() {
		return Arrays.stream(values()).map(IStringSerializable::getName).toArray(String[]::new);
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