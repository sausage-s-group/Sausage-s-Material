package sausages_material.event.worldgen.ore;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.Random;

@SuppressWarnings({"unused", "StatementWithEmptyBody"})
public class OreGeneratorDefault implements OreGenerator{
    @Override
    public void onGenerateMinable(OreGenEvent.GenerateMinable event) {
        World worldIn = event.getWorld();
        WorldGenerator generator = event.getGenerator();
        BlockPos position = event.getPos();
        Random rand = event.getRand();
        if (TerrainGen.generateOre(worldIn, rand, generator, position, OreGenEvent.GenerateMinable.EventType.CUSTOM)){

        }
    }
}