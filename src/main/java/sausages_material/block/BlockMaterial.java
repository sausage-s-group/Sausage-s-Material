package sausages_material.block;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import sausages_material.api.MetalShapes;

@MethodsReturnNonnullByDefault
public class BlockMaterial extends Block
{
    public static final PropertyInteger TYPE = PropertyInteger.create("material",0,19);

    public BlockMaterial(MetalShapes material) {
        super(Material.IRON,MapColor.IRON);
    }

    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this,TYPE);
    }
}
