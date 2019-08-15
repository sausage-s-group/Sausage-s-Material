package sausages_material.item;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sausages_material.SausagesMaterial;
import sausages_material.api.AlloyMaterials;
import sausages_material.api.IMaterial;
import sausages_material.api.IShape;
import sausages_material.api.MetalMaterials;

@MethodsReturnNonnullByDefault
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
	    return I18n.format("sausages_material.shape."+getRegistryName().getPath(),
                I18n.format( ItemMaterial.getMaterial(s.getItemDamage()).name() )
        );
    }

    public static IMaterial getMaterial(int itemDamage) {
	    assert itemDamage>0;
        if(itemDamage>=MetalMaterials.values().length){
            return AlloyMaterials.values()[itemDamage-14];
        }
        return MetalMaterials.values()[itemDamage];
    }
}
