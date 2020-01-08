package com.mraof.minestuck.world.gen;

import com.google.common.base.Predicate;
import com.mraof.minestuck.block.CustomOreBlock;
import com.mraof.minestuck.world.LandDimension;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

import static com.mraof.minestuck.MinestuckConfig.*;
import static com.mraof.minestuck.block.MSBlocks.STONE_CRUXITE_ORE;
import static com.mraof.minestuck.block.MSBlocks.STONE_URANIUM_ORE;

public class OreHandler implements IWorldGenerator
{
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, ChunkGenerator chunkGenerator, AbstractChunkProvider chunkProvider)
	{
		if(world.getDimension().isSurfaceWorld() && (generateCruxiteOre.get() || chunkGenerator instanceof LandChunkGenerator) && !disableCruxite)
		{
			this.addOreSpawn(STONE_CRUXITE_ORE.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 16, 16,
					baseCruxiteVeinSize + random.nextInt(bonusCruxiteVeinSize), cruxiteVeinsPerChunk, cruxiteStratumMin, cruxiteStratumMax);
		}
		
		if(world.getDimension().isSurfaceWorld() && (generateUraniumOre.get() || chunkGenerator instanceof LandChunkGenerator) && !disableUranium)
		{
			this.addOreSpawn(STONE_URANIUM_ORE.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 16, 16,
					baseUraniumVeinSize + random.nextInt(bonusUraniumVeinSize), uraniumVeinsPerChunk, uraniumStratumMin, uraniumStratumMax);
		}
	}
	
	public void addOreSpawn(BlockState block, World world, Random random, int blockXPos, int blockZPos, int maxX, int maxZ, int maxVeinSize, int chancesToSpawn, int minY, int maxY)
	{
		//int maxPossY = minY + (maxY - 1);
		int diffBtwnMinMaxY = maxY - minY;
		BlockState groundType = Blocks.STONE.getDefaultState();
		if(world.getDimension() instanceof LandDimension)
			groundType = world.getChunkProvider().getChunkGenerator().getSettings().getDefaultBlock();
		if(block.getBlock() == STONE_CRUXITE_ORE)
			block = CustomOreBlock.getCruxiteState(groundType);
		if(block.getBlock() == STONE_URANIUM_ORE)
			block = CustomOreBlock.getUraniumState(groundType);
		for(int x = 0; x < chancesToSpawn; x++)
		{
			int posX = blockXPos + random.nextInt(maxX);
			int posY = minY + random.nextInt(diffBtwnMinMaxY);
			int posZ = blockZPos + random.nextInt(maxZ);//TODO
			//(new WorldGenMinable(block, maxVeinSize, new BlockStatePredicate(groundType))).generate(world, random, new BlockPos(posX, posY, posZ));
		}
	}
	
	public static class BlockStatePredicate implements Predicate
	{
		BlockState[] states;
		public BlockStatePredicate(BlockState... blockStates)
		{
			states = blockStates;
		}
		@Override
		public boolean apply(Object input)
		{
			for(BlockState state : states)
				if(state.equals(input))
					return true;
			return false;
		}
	}
}