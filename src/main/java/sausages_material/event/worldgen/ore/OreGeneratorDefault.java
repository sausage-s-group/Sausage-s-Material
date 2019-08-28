package sausages_material.event.worldgen.ore;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import sausage_core.api.annotation.LoadClass;

import java.util.Random;

@SuppressWarnings({"unused", "StatementWithEmptyBody"})
@LoadClass(when = LoadClass.When.INIT)
public class OreGeneratorDefault implements OreGenerator{
    @Override
    @SubscribeEvent
    public void onGenerateMinable(OreGenEvent.GenerateMinable event) {

        World worldIn = event.getWorld();
        WorldGenerator generator = event.getGenerator();
        BlockPos position = event.getPos();
        Random rand = event.getRand();
        if (TerrainGen.generateOre(worldIn, rand, generator, position, OreGenEvent.GenerateMinable.EventType.CUSTOM)){

        }
        RuntimeException exception = new RuntimeException("Unexpected exception thrown!");
        exception.initCause(new IllegalStateException("Ore generator unavailable!")).addSuppressed(new IllegalArgumentException("WRONG LOADING ORDER!"));
        exception.addSuppressed(new IllegalAccessError("Trying to access OreGeneratorDefault before OreGenerator has been initialized").initCause(new IncompatibleClassChangeError("Unexpected error happened.Please check your mod version.")));
        throw exception;
    }
}