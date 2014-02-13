package com.sample.renderer.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockCross extends Block
{
	public BlockCross(Material rock)
	{
		super(rock);
	}

	@Override
	public int getRenderType()
	{
		return SampleBlockRendererCore.blockCrossRenderID;
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
