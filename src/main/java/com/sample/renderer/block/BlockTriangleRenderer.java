package com.sample.renderer.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class BlockTriangleRenderer implements ISimpleBlockRenderingHandler
{
	/*
	 * Tessellatorを直接操作するサンプル例.
	 * 三次元の頂点に二次元の頂点を対応させるので, 文字だけだと解説しづらいためコメント少なめ.
	 */
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
		if (getRenderId() == modelId)
		{
			/*
			 * RenderHelper.disable~()とenable~()で囲まないと描画が暗くなる.
			 */
			RenderHelper.disableStandardItemLighting();
			renderBlockTriangleInInv(block);
			RenderHelper.enableStandardItemLighting();
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		if (getRenderId() == modelId)
		{
			renderBlockTriangleInWorld(world, block, x, y, z);
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

	/*
	 * ワールドに設置されたブロックを描画するメソッド.
	 * setColor~とsetBrightness~は明るさの設定.
	 */
	private void renderBlockTriangleInWorld(IBlockAccess world, Block block, int x, int y, int z)
	{
		Tessellator tessellator = Tessellator.instance;

		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));

		doRenderBlockTriangle(tessellator, block, (double)x, (double)y, (double)z);
	}

	/*
	 * インベントリ内のブロックを描画するメソッド.
	 */
	private void renderBlockTriangleInInv(Block block)
	{
		Tessellator tessellator = Tessellator.instance;
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		tessellator.startDrawingQuads();
		doRenderBlockTriangle(tessellator, block, 0.0D, 0.0D, 0.0D);
		tessellator.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

	/*
	 * ブロック自体の描画をするメソッド.
	 * ワールド内描画もインベントリ内描画もこの部分は共通.
	 */
	private void doRenderBlockTriangle(Tessellator tessellator, Block block, double x, double y, double z)
	{
		IIcon icon = block.getIcon(0, 0);

		/*
		 * アイコンの4つの頂点(minU, minV, maxU, maxV)を取得する.
		 */
		double minU = (double)icon.getMinU();
		double maxU = (double)icon.getMaxU();
		double minV = (double)icon.getMinV();
		double maxV = (double)icon.getMaxV();

		/*
		 * 斜めの描画.
		 * 頂点(x, y, z)に(u, v)を対応させる.
		 */
		tessellator.addVertexWithUV(x + 0.0D, y + 0.0D, z + 0.0D, minU, minV);
		tessellator.addVertexWithUV(x + 0.0D, y + 1.0D, z + 0.5D, minU, maxV);
		tessellator.addVertexWithUV(x + 1.0D, y + 1.0D, z + 0.5D, maxU, maxV);
		tessellator.addVertexWithUV(x + 1.0D, y + 0.0D, z + 0.0D, maxU, minV);

		/*
		 * 上記と同様, 反対側の描画.
		 */
		tessellator.addVertexWithUV(x + 1.0D, y + 0.0D, z + 1.0D, maxU, minV);
		tessellator.addVertexWithUV(x + 1.0D, y + 1.0D, z + 0.5D, maxU, maxV);
		tessellator.addVertexWithUV(x + 0.0D, y + 1.0D, z + 0.5D, minU, maxV);
		tessellator.addVertexWithUV(x + 0.0D, y + 0.0D, z + 1.0D, minU, minV);


		/*
		 * 底面の描画.
		 */
		tessellator.addVertexWithUV(x + 1.0D, y + 0.0D, z + 0.0D, maxU, minV);
		tessellator.addVertexWithUV(x + 1.0D, y + 0.0D, z + 1.0D, maxU, maxV);
		tessellator.addVertexWithUV(x + 0.0D, y + 0.0D, z + 1.0D, minU, maxV);
		tessellator.addVertexWithUV(x + 0.0D, y + 0.0D, z + 0.0D, minU, minV);

		/*
		 * △の部分.
		 * アイコンの始点, 終点以外の数値はgetInterpolatedU()/getInterpolatedV()の引数で調整して取得する.
		 * 今回は中央なので8.0Dを指定.
		 */
		double halfU = (double)icon.getInterpolatedU(8.0D);

		tessellator.addVertexWithUV(x + 1.0D, y + 0.0D, z + 0.0D, minU,  maxV);
		tessellator.addVertexWithUV(x + 1.0D, y + 1.0D, z + 0.5D, halfU, maxV);
		tessellator.addVertexWithUV(x + 1.0D, y + 0.0D, z + 1.0D, maxU,  minV);
		tessellator.addVertexWithUV(x + 1.0D, y + 0.0D, z + 0.0D, minU,  minV);

		tessellator.addVertexWithUV(x + 0.0D, y + 0.0D, z + 0.0D, minU,  maxV);
		tessellator.addVertexWithUV(x + 0.0D, y + 0.0D, z + 1.0D, maxU,  minV);
		tessellator.addVertexWithUV(x + 0.0D, y + 1.0D, z + 0.5D, halfU, maxV);
		tessellator.addVertexWithUV(x + 0.0D, y + 0.0D, z + 0.0D, minU,  minV);
	}
}
