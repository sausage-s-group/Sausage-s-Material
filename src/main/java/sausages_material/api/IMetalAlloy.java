package sausages_material.api;

import java.util.List;

/**
 * The steel which includes iron and carbon does not belongs to this!
 * */
public interface IMetalAlloy extends IMaterial {
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
