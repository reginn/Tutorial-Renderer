package com.sample.renderer.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockTriangle extends Block
{
	public BlockTriangle(Material material)
	{
		super(material);
	}

	@Override
	public int getRenderType()
	{
		return SampleBlockRendererCore.blockTriangleRenderID;
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
