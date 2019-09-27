package com.mraof.minestuck.world.gen;

import com.mojang.datafixers.Dynamic;
import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.block.MSBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = Minestuck.MOD_ID, bus=Mod.EventBusSubscriber.Bus.MOD)
public class SkaiaSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig>
{
	public final SurfaceBuilderConfig WHITE_CHESS_CONFIG = new SurfaceBuilderConfig(MSBlocks.WHITE_CHESS_DIRT.get().getDefaultState(), MSBlocks.WHITE_CHESS_DIRT.get().getDefaultState(), MSBlocks.WHITE_CHESS_DIRT.get().getDefaultState());
	public final SurfaceBuilderConfig LIGHT_GRAY_CHESS_CONFIG = new SurfaceBuilderConfig(MSBlocks.LIGHT_GRAY_CHESS_DIRT.get().getDefaultState(), MSBlocks.LIGHT_GRAY_CHESS_DIRT.get().getDefaultState(), MSBlocks.LIGHT_GRAY_CHESS_DIRT.get().getDefaultState());
	public final SurfaceBuilderConfig DARK_GRAY_CHESS_CONFIG = new SurfaceBuilderConfig(MSBlocks.DARK_GRAY_CHESS_DIRT.get().getDefaultState(), MSBlocks.DARK_GRAY_CHESS_DIRT.get().getDefaultState(), MSBlocks.DARK_GRAY_CHESS_DIRT.get().getDefaultState());
	public final SurfaceBuilderConfig BLACK_CHESS_CONFIG = new SurfaceBuilderConfig(MSBlocks.BLACK_CHESS_DIRT.get().getDefaultState(), MSBlocks.BLACK_CHESS_DIRT.get().getDefaultState(), MSBlocks.BLACK_CHESS_DIRT.get().getDefaultState());
	
	@ObjectHolder(Minestuck.MOD_ID+":skaia")
	public static final SkaiaSurfaceBuilder SKAIA = getNull();
	
	@Nonnull
	@SuppressWarnings("ConstantConditions")
	private static <T> T getNull()
	{
		return null;
	}
	
	@SubscribeEvent
	public static void registerSurfaceBuilders(RegistryEvent.Register<SurfaceBuilder<?>> event)
	{
		event.getRegistry().register(new SkaiaSurfaceBuilder(SurfaceBuilderConfig::deserialize).setRegistryName("skaia"));
	}
	
	public SkaiaSurfaceBuilder(Function<Dynamic<?>, ? extends SurfaceBuilderConfig> deserializer)
	{
		super(deserializer);
	}
	
	@Override
	public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config)
	{
		int chunkX = Math.floorDiv(x, 16), chunkZ = Math.floorDiv(z, 16);
		if((chunkX + chunkZ) % 2 == 0)
		{
			if(noise > 1.0)
				SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, 0, defaultBlock, defaultFluid, seaLevel, seed, LIGHT_GRAY_CHESS_CONFIG);
			else
				SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, 0, defaultBlock, defaultFluid, seaLevel, seed, WHITE_CHESS_CONFIG);
		} else
		{
			if(noise < -1.0)
				SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, 0, defaultBlock, defaultFluid, seaLevel, seed, DARK_GRAY_CHESS_CONFIG);
			else
				SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, 0, defaultBlock, defaultFluid, seaLevel, seed, BLACK_CHESS_CONFIG);
		}
	}
}