package sausages_material.material;

import net.minecraft.util.IStringSerializable;

public interface IMaterial {
    /**
     * *INTERNAL USE*
     */
    @SuppressWarnings("unchecked")
    static <T extends Enum<T> & IMaterial & IStringSerializable> T getMaterial(int meta) {
        assert meta > 0;
        if (meta >= MetalMaterials.values().length) {
            return (T) AlloyMaterials.values()[meta - MetalMaterials.values().length];
        }
        return (T) MetalMaterials.values()[meta];
    }

    int color();
}
