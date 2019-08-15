package com.mraof.minestuck.world.lands.gen;

import com.mraof.minestuck.world.biome.LandBiomeHolder;
import com.mraof.minestuck.world.biome.ModBiomes;
import com.mraof.minestuck.world.lands.LandAspects;
import com.mraof.minestuck.world.lands.structure.blocks.StructureBlockRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationSettings;

public class LandGenSettings extends GenerationSettings
{
	private LandAspects landAspects;
	private StructureBlockRegistry blockRegistry;
	private LandBiomeHolder biomeHolder;
	public float oceanChance = 1/3F, roughChance = 1/5F;
	
	public LandAspects getLandAspects()
	{
		return landAspects;
	}
	
	public StructureBlockRegistry getBlockRegistry()
	{
		return blockRegistry;
	}
	
	public LandBiomeHolder getBiomeHolder()
	{
		return biomeHolder;
	}
	
	public void setLandAspects(LandAspects landAspects)
	{
		this.landAspects = landAspects;
		
		blockRegistry = new StructureBlockRegistry();
		landAspects.aspectTerrain.registerBlocks(blockRegistry);
		//TODO Also register from title landspect
		setDefaultBlock(blockRegistry.getBlockState("ground"));
		setDefaultFluid(blockRegistry.getBlockState("ocean"));
		
		landAspects.aspectTerrain.setGenSettings(this);
		landAspects.aspectTitle.setGenSettings(this);
	}
	
	public void setBiomeHolder(LandBiomeHolder biomeHolder)
	{
		this.biomeHolder = biomeHolder;
	}
	
	@Override
	public int getBedrockFloorHeight()
	{
		return 0;
	}
}