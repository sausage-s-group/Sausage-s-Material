package sausages_material.block;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sausages_material.api.IMaterial;
import sausages_material.api.MetalShapes;

@MethodsReturnNonnullByDefault
public class BlockMaterial<T extends Enum<T>& IMaterial& IStringSerializable> extends Block
{
    public static final PropertyInteger TYPE = PropertyInteger.create("material",0,19);
    private final Class<T> clz;
    private final PropertyEnum<T> MATERIAL;

    public BlockMaterial(Class<T> clz,PropertyEnum<T> material) {
        super(Material.IRON,MapColor.IRON);
        this.clz = clz;
        this.MATERIAL = material;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
        state.withProperty(TYPE,stack.getItemDamage());
    }
    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this,MATERIAL);
    }

    @Override
    public int getMetaFromState(IBlockState state){
        return state.getValue(MATERIAL).ordinal();
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return  getDefaultState().withProperty(MATERIAL,clz.getEnumConstants()[meta]);
    }
}
