package sausages_material.event.worldgen.ore;

import net.minecraft.world.biome.Biome;

import java.util.function.IntFunction;
import java.util.function.Predicate;

public class GeneratePredication {
    private final int minY;
    private final int maxY;
    private final Predicate<Biome> biomeCondition;
    private final IntFunction<Integer> transformHeight;

    public GeneratePredication(int minY, int maxY, Predicate<Biome> biomeCondition, IntFunction<Integer> transformHeight) {
        this.minY = minY;
        this.maxY = maxY;
        this.biomeCondition = biomeCondition;
        this.transformHeight = transformHeight;
    }
}