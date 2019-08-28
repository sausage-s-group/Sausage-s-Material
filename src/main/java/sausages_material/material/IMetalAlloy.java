package sausages_material.material;

import sausage_core.api.annotation.LoadClass;

import java.util.List;

/**
 * The steel which includes iron and carbon does not belongs to this!
 * */
@LoadClass(when = LoadClass.When.PRE_INIT)
public interface IMetalAlloy extends IMaterial {
	@SuppressWarnings("WeakerAccess")
	class WeightedMetal {
		public final IMaterial metal;
		public final int weight;

		public WeightedMetal(IMaterial metal) {this(metal, 1);}

		public WeightedMetal(IMaterial metal, int weight) {
			this.metal = metal;
			this.weight = weight;
		}
	}
	List<WeightedMetal> ingredients();
}
