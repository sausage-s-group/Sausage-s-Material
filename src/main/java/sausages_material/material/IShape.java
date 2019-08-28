package sausages_material.material;

import net.minecraft.util.ResourceLocation;
import sausage_core.api.annotation.LoadClass;

@LoadClass(when = LoadClass.When.PRE_INIT)
public interface IShape {
	String name();
	ResourceLocation location();
	int value();
}
