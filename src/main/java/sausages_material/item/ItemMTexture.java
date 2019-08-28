package sausages_material.item;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import sausages_material.material.IMaterial;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemMTexture extends ItemMultiTexture {

    public ItemMTexture(Block block, Block block2, String[] namesByMeta) {
        super(block, block2, namesByMeta);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack){
        return IMaterial.getLocalizedName(stack);
    }

}
