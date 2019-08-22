package sausages_material.item;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sausages_material.material.AlloyMaterials;
import sausages_material.material.IMaterial;
import sausages_material.material.IShape;
import sausages_material.material.MetalMaterials;

import javax.annotation.ParametersAreNonnullByDefault;

import static sausage_core.api.util.common.SausageUtils.nonnull;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemMaterial extends Item {
	public ItemMaterial(IShape shape){
	    setNoRepair();
	    setMaxDamage(0);
	    setHasSubtypes(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items){
        if(isInCreativeTab(tab)){
            for(MetalMaterials metal: MetalMaterials.values()){
                items.add(new ItemStack(this,1,metal.ordinal()));
            }
            for(AlloyMaterials alloy:AlloyMaterials.values()){
                items.add(new ItemStack(this,1,alloy.ordinal()+MetalMaterials.values().length));
            }
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack s){
        return I18n.format("sausages_material.shape." + nonnull(getRegistryName()).getPath(),
                I18n.format("sausages_material." + (s.getItemDamage() >= 14 ? "alloy" : "metal") + IMaterial.getMaterial(s.getItemDamage()).name())
        );
    }


}
