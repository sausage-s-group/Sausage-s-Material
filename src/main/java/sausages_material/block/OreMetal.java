package sausages_material.block;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockOre;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sausages_material.material.IMaterial;
import sausages_material.material.MetalMaterials;

@MethodsReturnNonnullByDefault
@SuppressWarnings("deprecation")
public class OreMetal extends BlockOre {

    public static final PropertyEnum<MetalMaterials> MATERIAL = PropertyEnum.create("metal", MetalMaterials.class);

    public OreMetal() {
        super(MapColor.IRON);
        this.setHardness(2.5F);
        this.setSoundType(SoundType.METAL);
    }

    @Override
    public void getSubBlocks(CreativeTabs tabs, NonNullList<ItemStack> list) {
        for (int i = 1; i < MetalMaterials.values().length; i++) {
            list.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        state.withProperty(MATERIAL, IMaterial.getMaterial(stack.getItemDamage()));
    }

    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, MATERIAL);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(MATERIAL).ordinal();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return super.getStateFromMeta(meta).withProperty(MATERIAL, MATERIAL.getValueClass().getEnumConstants()[meta]);
    }
}
