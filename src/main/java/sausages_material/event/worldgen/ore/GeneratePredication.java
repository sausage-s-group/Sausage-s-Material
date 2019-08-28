package sausages_material.event.worldgen.ore;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.terraingen.OreGenEvent;

import java.util.function.BiFunction;
import java.util.function.IntFunction;
import java.util.function.Predicate;

public class GeneratePredication implements BiFunction<World, BlockPos, Boolean> {

    private final Predicate<BlockPos> locationPredicate;
    private final Predicate<Biome> biomeCondition;


    public GeneratePredication(Predicate<BlockPos> locationPredicate, Predicate<Biome> biomeCondition, IntFunction<Integer> heightTransformer) {
        this.locationPredicate = locationPredicate;
        this.biomeCondition = biomeCondition;
    }

    @Override
    public Boolean apply(World world, BlockPos blockPos) {
        return biomeCondition.test(world.getBiome(blockPos))&&locationPredicate.test(blockPos);
    }
}