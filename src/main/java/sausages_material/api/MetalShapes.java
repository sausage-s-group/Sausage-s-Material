package sausages_material.api;

import net.minecraft.util.ResourceLocation;
import sausages_material.SausagesMaterial;

public enum MetalShapes implements IShape {
	//ore(288), block(1296),
	ingot(144), nugget(16),
	dust(144), gear(576), plate(144), stick(72),
	wire(72), dustTiny(16), plateDense(1296);

	public final int value;

	MetalShapes(int value) {
		this.value = value;
	}

	@Override
	public ResourceLocation location() {
		return new ResourceLocation(SausagesMaterial.MODID, "metal_" + name());
	}

	@Override
	public int value() {
		return value;
	}
}
