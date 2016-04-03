package rtg.world.gen.surface.vanilla;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.ChunkPrimer;
import rtg.util.math.CanyonColour;
import rtg.util.math.CliffCalculator;
import rtg.util.noise.CellNoise;
import rtg.util.noise.OpenSimplexNoise;
import rtg.world.biome.realistic.RealisticBiomeBase;
import rtg.world.gen.surface.SurfaceBase;

import java.util.Random;

public class SurfaceVanillaMesaPlateauF extends SurfaceBase {
    private int grassRaise = 0;

    public SurfaceVanillaMesaPlateauF(RealisticBiomeBase biome, int grassHeight) {
        super(biome);
        grassRaise = grassHeight;
    }

    @Override
    public void paintTerrain(ChunkPrimer primer, int i, int j, int x, int y, int depth, World world, Random rand, OpenSimplexNoise simplex, CellNoise cell, float[] noise, float river, BiomeGenBase[] base) {
        float c = CliffCalculator.calc(x, y, noise);
        boolean cliff = c > 1.3f;

        for (int k = 255; k > -1; k--) {
            Block b = primer.getBlockState(x, k, y).getBlock();
            if (b == Blocks.air) {
                depth = -1;
            } else if (b == Blocks.stone) {
                depth++;

                if (depth > -1 && depth < 12) {
                    if (cliff) {
                        primer.setBlockState(x, k, y, CanyonColour.MESA.getForHeight(i, k, j));
                    } else {
                        if (depth > 4) {
                            primer.setBlockState(x, k, y, CanyonColour.MESA.getForHeight(i, k, j));
                        } else if (k < 62) {
                            primer.setBlockState(x, k, y, Blocks.dirt.getDefaultState());
                        } else if (k < 62 + grassRaise) {
                            if (depth == 0) {
                                primer.setBlockState(x, k, y, Blocks.grass.getDefaultState());
                            } else {
                                primer.setBlockState(x, k, y, Blocks.dirt.getStateFromMeta(1));
                            }
                        } else {
                            if (depth == 0) {
                                int r = 4;
                                if (rand.nextInt(r + 1) == 0) {
                                    primer.setBlockState(x, k, y, Blocks.grass.getDefaultState());
                                } else if (rand.nextInt((int) (r / 2f) + 1) == 0) {
                                    primer.setBlockState(x, k, y, Blocks.dirt.getStateFromMeta(1));
                                } else {
                                    primer.setBlockState(x, k, y, biome.config.TOP_BLOCK.get());
                                }
                            } else {
                                primer.setBlockState(x, k, y, biome.config.FILL_BLOCK.get());
                            }
                        }
                    }
                } else if (k > 63) {
                    primer.setBlockState(x, k, y, CanyonColour.MESA.getForHeight(i, k, j));
                }
            }
        }
    }
}
