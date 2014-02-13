package com.sample.renderer.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

@SideOnly(Side.CLIENT)
public class BlockTriangleRenderer implements ISimpleBlockRenderingHandler
{
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
		if (getRenderId() == modelId)
		{
			
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		if (getRenderId() == modelId)
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		if (getRenderId() == modelId)
		{
			return true;
		}
		return false;
	}

	@Override
	public int getRenderId()
	{
		return SampleBlockRendererCore.blockTriangleRenderID;
	}
}
