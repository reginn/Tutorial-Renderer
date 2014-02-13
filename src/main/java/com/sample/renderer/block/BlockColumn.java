package com.sample.renderer.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockColumn extends Block
{
	public BlockColumn(Material material)
	{
		super(material);
	}

	/*
	 * 追加したRenderingHandlerで描画するためのRenderTypeIDを返す.
	 */
	@Override
	public int getRenderType()
	{
		return SampleBlockRendererCore.renderID;
	}

	/*
	 * 正方形のブロックではないためfalseを返す.
	 */
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	/*
	 * 透過部分を含むのでfalseを返す.
	 */
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
}
