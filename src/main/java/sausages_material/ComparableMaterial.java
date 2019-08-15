package sausages_material;

import sausages_material.api.IMaterial;

public class ComparableMaterial implements Comparable<IMaterial> {
    public final IMaterial material;

    public ComparableMaterial(IMaterial material){
        this.material = material;
    }

    @Override
    public int compareTo(IMaterial material) {
        return this.material.color()-material.color();
    }
}
