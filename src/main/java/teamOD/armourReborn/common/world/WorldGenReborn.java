package teamOD.armourReborn.common.world;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import teamOD.armourReborn.common.block.BlockMaterial;
import teamOD.armourReborn.common.block.ModBlocks;

public class WorldGenReborn implements IWorldGenerator {
	
	public static WorldGenReborn INSTANCE = new WorldGenReborn () ;
	
	private WorldGenerator oreAluminium ;
	private WorldGenerator oreCopper ;
	
	public WorldGenReborn () {
		oreAluminium = new WorldGenMinable (ModBlocks.oresMaterials.getStateFromMeta(BlockMaterial.Ores.Aluminium.getMeta()), 5) ;
		oreCopper = new WorldGenMinable (ModBlocks.oresMaterials.getStateFromMeta(BlockMaterial.Ores.Copper.getMeta()), 5) ;
	}
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		
		if (world.provider instanceof WorldProviderSurface) {
			this.runGenerator(this.oreAluminium, world, random, chunkX, chunkZ, 20, 0, 64);
			this.runGenerator(this.oreCopper, world, random, chunkX, chunkZ, 20, 0, 64);
		}
		
	}
	
	private void runGenerator(WorldGenerator generator, World world, Random rand, int chunk_X, int chunk_Z, int chancesToSpawn, int minHeight, int maxHeight) {
	    if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
	        throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");

	    int heightDiff = maxHeight - minHeight + 1;
	    for (int i = 0; i < chancesToSpawn; i ++) {
	        int x = chunk_X * 16 + rand.nextInt(16);
	        int y = minHeight + rand.nextInt(heightDiff);
	        int z = chunk_Z * 16 + rand.nextInt(16);
	        generator.generate(world, rand, new BlockPos(x, y, z));
	    }
	}

}
