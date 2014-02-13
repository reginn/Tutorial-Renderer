package com.sample.renderer.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class BlockCrossRenderer implements ISimpleBlockRenderingHandler
{
	/*
	 * 2つの立方体を使って描画するハンドラ.
	 */
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
		if (getRenderId() == modelId)
		{
			/*
			 * tessellatorの箇所は共通なのでメソッド化して利用する.
			 */
			renderInvCuboid(renderer, block, block.getIcon(0, 0), 0.3F, 0.3F, 0.0F, 0.7F, 0.7F, 1.0F);
			renderInvCuboid(renderer, block, block.getIcon(0, 0), 0.0F, 0.3F, 0.3F, 1.0F, 0.7F, 0.7F);
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		if (getRenderId() == modelId)
		{
			renderer.setOverrideBlockTexture(block.getIcon(0, 0));

			/*
			 * 向きの異なる2つの立方体を組み合わせて十字を作っている.
			 */
			block.setBlockBounds(0.3F, 0.3F, 0.0F, 0.7F, 0.7F, 1.0F);
			renderer.setRenderBoundsFromBlock(block);
			renderer.renderStandardBlock(block, x, y, z);

			block.setBlockBounds(0.0F, 0.3F, 0.3F, 1.0F, 0.7F, 0.7F);
			renderer.setRenderBoundsFromBlock(block);
			renderer.renderStandardBlock(block, x, y, z);

			renderer.clearOverrideBlockTexture();
			block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			renderer.setRenderBoundsFromBlock(block);
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
		return SampleBlockRendererCore.blockCrossRenderID;
	}

	/*
	 * インベントリでブロック描画用の汎用メソッド.
	 */
	private void renderInvCuboid(RenderBlocks renderer, Block block, IIcon icon, float minX, float minY, float minZ, float maxX, float maxY, float maxZ)
	{
		Tessellator tessellator = Tessellator.instance;

		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		block.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
		renderer.setRenderBoundsFromBlock(block);

		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, icon);
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, icon);
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, icon);
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 1.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, icon);
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, icon);
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, icon);
		tessellator.draw();

		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}
}
