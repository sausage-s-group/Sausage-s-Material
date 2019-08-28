package sausages_material.material;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import sausage_core.api.annotation.LoadClass;
import sausage_core.api.util.common.SausageUtils;
import sausages_material.block.BlockAlloy;
import sausages_material.block.BlockMetal;

import java.util.Arrays;

import static sausage_core.api.util.common.SausageUtils.nonnull;
import static sausage_core.api.util.common.SausageUtils.rawtype;
@LoadClass(when = LoadClass.When.PRE_INIT)
public interface IMaterial {
    /**
     * *INTERNAL USE*
     */
    static <T extends Enum<T> & IMaterial & IStringSerializable> T getMaterial(int meta) {
        assert meta > 0;
        if (meta >= MetalMaterials.values().length) {
            return rawtype(AlloyMaterials.values()[meta - MetalMaterials.values().length]);
        }
        return rawtype(MetalMaterials.values()[meta]);
    }

    /**
     * *INTERNAL USE*
     */
    static String getLocalizedName(ItemStack stack) {
        Block bk;
        return I18n.format("sausages_material.shape." +
                        (stack.getItem() instanceof ItemBlock ?
                                ( (bk = SausageUtils.<ItemBlock>rawtype(stack.getItem()).getBlock() ) instanceof BlockAlloy||
                                        bk instanceof BlockMetal)?"block":"ore"
                                :nonnull(stack.getItem().getRegistryName()).getPath()),
                I18n.format("sausages_material." + (stack.getItemDamage() >= MetalMaterials.values().length ? "alloy" : "metal") + "." + IMaterial.getMaterial(stack.getItemDamage()).name())
        );
    }

    /**
     * *INTERNAL USE*
     */
    static String[] getAllMaterialName(){
        return Lists.newArrayList(MetalMaterials.names(),AlloyMaterials.names()).stream().flatMap(Arrays::stream).toArray(String[]::new);
    }

    /**
     * Color used to color item/block
     * @return RGB color of the material
     */
    int color();
}
