package com.mraof.minestuck.item.block;

import com.mraof.minestuck.block.AlchemiterBlock;
import com.mraof.minestuck.block.MSBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AlchemiterItem extends BlockItem
{
	
	public AlchemiterItem(Block blockIn, Properties builder)
	{
		super(blockIn, builder);
	}
	
	@Override
	public ActionResultType tryPlace(BlockItemUseContext context)
	{
		World world = context.getWorld();
		Direction sideFace = context.getFace();
		BlockPos pos = context.getPos();
		PlayerEntity player = context.getPlayer();
		if (world.isRemote)
		{
			return ActionResultType.SUCCESS;
		} else if (sideFace != Direction.UP)
		{
			return ActionResultType.FAIL;
		} else
		{
			BlockState block = world.getBlockState(pos);
			boolean flag = block.isReplaceable(context);
			
			if (!flag)
			{
				pos = pos.up();
			}
			
			Direction facing = context.getPlacementHorizontalFacing();
			ItemStack itemstack = context.getItem();
			
			pos = pos.offset(facing.rotateYCCW());
			
			if(facing == Direction.WEST && context.getHitVec().z >= 0.5F || facing == Direction.EAST && context.getHitVec().z < 0.5F
					|| facing == Direction.NORTH && context.getHitVec().x < 0.5F || facing == Direction.SOUTH && context.getHitVec().x >= 0.5F)
				pos = pos.offset(facing.rotateYCCW());
			
			if (!itemstack.isEmpty())
			{
				if(!canPlaceAt(context, pos, facing))
					return ActionResultType.FAIL;
				
				BlockState state = getBlock().getDefaultState().with(AlchemiterBlock.FACING, facing);
				this.placeBlock(context, state);
				return ActionResultType.SUCCESS;
			}
			return ActionResultType.FAIL;
		}
	}
	

	public static boolean canPlaceAt(BlockItemUseContext context, BlockPos pos, Direction facing)
	{
		for(int x = 0; x < 4; x++)
		{
			for(int z = 0; z < 4; z++)
			{
				if(!context.getPlayer().canPlayerEdit(pos.offset(facing.rotateY(), x).offset(facing, z), Direction.UP, context.getItem()))
					return false;
				for(int y = 0; y < 4; y++)
				{
					if(!context.getWorld().getBlockState(pos.offset(facing, z).offset(facing.rotateY(), x).up(y)).isReplaceable(context))
						return false;
				}
			}
		}
		return true;
	}
	
	@Override
	protected boolean placeBlock(BlockItemUseContext context, BlockState newState)
	{
		BlockPos pos = context.getPos();
		World world = context.getWorld();
		PlayerEntity player = context.getPlayer();
		if(!world.isRemote)
		{
			Direction facing = context.getPlacementHorizontalFacing();
			
			pos = pos.offset(facing.rotateYCCW());
			
			if(facing == Direction.WEST && context.getHitVec().z >= 0.5F || facing == Direction.EAST && context.getHitVec().z < 0.5F
					|| facing == Direction.NORTH && context.getHitVec().x < 0.5F || facing == Direction.SOUTH && context.getHitVec().x >= 0.5F)
				pos = pos.offset(facing.rotateYCCW());
			
			BlockState corner = MSBlocks.ALCHEMITER.CORNER.map(Block::getDefaultState).orElseThrow(IllegalStateException::new);
			BlockState left = MSBlocks.ALCHEMITER.LEFT_SIDE.map(Block::getDefaultState).orElseThrow(IllegalStateException::new);
			BlockState right = MSBlocks.ALCHEMITER.RIGHT_SIDE.map(Block::getDefaultState).orElseThrow(IllegalStateException::new);
			BlockState center = MSBlocks.ALCHEMITER.CENTER.map(Block::getDefaultState).orElseThrow(IllegalStateException::new);
			world.setBlockState(pos.offset(facing,3).offset(facing.rotateY(),3).up(0), MSBlocks.ALCHEMITER.TOTEM_CORNER.get().getDefaultState().with(AlchemiterBlock.FACING, facing));
			world.setBlockState(pos.offset(facing,3).offset(facing.rotateY(),3).up(1), MSBlocks.ALCHEMITER.TOTEM_PAD.get().getDefaultState().with(AlchemiterBlock.FACING, facing));
			world.setBlockState(pos.offset(facing,3).offset(facing.rotateY(),3).up(2), MSBlocks.ALCHEMITER.LOWER_ROD.get().getDefaultState().with(AlchemiterBlock.FACING, facing));
			world.setBlockState(pos.offset(facing,3).offset(facing.rotateY(),3).up(3), MSBlocks.ALCHEMITER.UPPER_ROD.get().getDefaultState().with(AlchemiterBlock.FACING, facing));
			
			world.setBlockState(pos.offset(facing,0).offset(facing.rotateY(),0), corner.with(AlchemiterBlock.FACING, facing.getOpposite()));
			world.setBlockState(pos.offset(facing,0).offset(facing.rotateY(),1), left.with(AlchemiterBlock.FACING, facing.getOpposite()));
			world.setBlockState(pos.offset(facing,0).offset(facing.rotateY(),2), right.with(AlchemiterBlock.FACING, facing.getOpposite()));
			world.setBlockState(pos.offset(facing,1).offset(facing.rotateY(),1), center.with(AlchemiterBlock.FACING, facing.getOpposite()));
			world.setBlockState(pos.offset(facing,0).offset(facing.rotateY(),3), corner.with(AlchemiterBlock.FACING, facing.rotateY()));
			world.setBlockState(pos.offset(facing,1).offset(facing.rotateY(),2), center.with(AlchemiterBlock.FACING, facing.rotateY()));
			world.setBlockState(pos.offset(facing,1).offset(facing.rotateY(),3), left.with(AlchemiterBlock.FACING, facing.rotateY()));
			world.setBlockState(pos.offset(facing,2).offset(facing.rotateY(),3), right.with(AlchemiterBlock.FACING, facing.rotateY()));
			world.setBlockState(pos.offset(facing,1).offset(facing.rotateY(),0), right.with(AlchemiterBlock.FACING, facing.rotateYCCW()));
			world.setBlockState(pos.offset(facing,2).offset(facing.rotateY(),0), left.with(AlchemiterBlock.FACING, facing.rotateYCCW()));
			world.setBlockState(pos.offset(facing,2).offset(facing.rotateY(),1), center.with(AlchemiterBlock.FACING, facing.rotateYCCW()));
			world.setBlockState(pos.offset(facing,3).offset(facing.rotateY(),0), corner.with(AlchemiterBlock.FACING, facing.rotateYCCW()));
			world.setBlockState(pos.offset(facing,2).offset(facing.rotateY(),2), center.with(AlchemiterBlock.FACING, facing));
			world.setBlockState(pos.offset(facing,3).offset(facing.rotateY(),1), right.with(AlchemiterBlock.FACING, facing));
			world.setBlockState(pos.offset(facing,3).offset(facing.rotateY(),2), left.with(AlchemiterBlock.FACING, facing));
			
			if(player instanceof ServerPlayerEntity)
				CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) player, pos, context.getItem());
		}
		return true;
	}
}