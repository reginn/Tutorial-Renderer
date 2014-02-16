package com.sample.renderer.tileentity;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMob extends BlockContainer
{
	public BlockMob(Material material)
	{
		super(material);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int par2)
	{
		return new TileEntityDummy();
	}

	@Override
	public int getRenderType()
	{
		return SampleTileEntityRendererCore.blockMobRenderType;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
}
