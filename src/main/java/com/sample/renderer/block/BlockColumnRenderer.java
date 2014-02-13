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
public class BlockColumnRenderer implements ISimpleBlockRenderingHandler
{
	/*
	 * ブロックの形状を変えて単純な立方体にするハンドラ.
	 */

	/*
	 * インベントリ内と手に持っているときのブロックの描画.
	 * RenderBlocks.renderBlockAsItem()の8471以降を参照のこと.
	 */
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
		if (getRenderId() == modelId)
		{
			/*
			 * Tessellatorのインスタンスを取得.
			 */
			Tessellator tessellator = Tessellator.instance;

			/*
			 * ブロックのアイコンを取得.
			 * 今回は6面全部同じアイコンなので引数は(0, 0)でよい.
			 */
			IIcon icon = block.getIcon(0, 0);

			/*
			 * 描画位置を適切な個所に移す.
			 */
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

			/*
			 * renderWorldBlockで設定した形にする.
			 */
			block.setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 1.0F, 0.7F);

			/*
			 * BlockRendererのインスタンスに形の変更を通知.
			 */
			renderer.setRenderBoundsFromBlock(block);

			/*
			 * 6面の描画を行う.
			 * setNormalの引数と次のrenderFace[XYZ](Neg|Pos)は対応している.
			 * また[XYZ](Neg|Pos)はゲーム内の方向とも対応している.
			 * setNormal( 1.0F,  0.0F,  0.0F) <-> XPos : East
			 * setNormal(-1.0F,  0.0F,  0.0F) <-> XNeg : West
			 * setNormal( 0.0F,  1.0F,  0.0F) <-> YPos : Top
			 * setNormal( 0.0F, -1.0F,  0.0F) <-> YNeg : Bottom
			 * setNormal( 0.0F,  0.0F,  1.0F) <-> ZPos : South
			 * setNormal( 0.0F,  0.0F, -1.0F) <-> ZNeg : North
			 *
			 * このサンプルでは単純に6面を描画しているだけである.
			 *
			 * startDrawingQuads()とdraw()で囲む必要がある点に注意.
			 */
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

			/*
			 * ずらした分を元に戻す.
			 */
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);

		}
	}

	/*
	 * worldにブロックを描画するメソッド.
	 */
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		if (getRenderId() == modelId)
		{
			/*
			 * RenderBlocksのインスタンスに使用するアイコンの参照を渡す.
			 */
			renderer.setOverrideBlockTexture(block.getIcon(0, 0));

			/*
			 * 描画したいブロックの形にする.
			 * 今回は太さ0.4Fの柱の形.
			 */
			block.setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 1.0F, 0.7F);

			/*
			 * rendererにblockの形状を通知.
			 */
			renderer.setRenderBoundsFromBlock(block);

			/*
			 * rendererのメソッドでブロックを描画.
			 */
			renderer.renderStandardBlock(block, x, y, z);

			/*
			 * rendererに渡したアイコンの情報を削除.
			 */
			renderer.clearOverrideBlockTexture();

			/*
			 * ブロックの形状を元に戻し, rendererにそれを通知.
			 */
			block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			renderer.setRenderBoundsFromBlock(block);
			return true;
		}
		return false;
	}

	/*
	 * インベントリで3Dのレンダリングを行うかどうか(renderInventoryBlockを呼び出すかどうか)を返すメソッド.
	 * falseの場合, アイコンがそのまま描画される.
	 */
	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		if (this.getRenderId() == modelId)
		{
			return true;
		}

		return false;
	}

	/*
	 * このRenderingHandlerが対応するRenderTypeIDを返す.
	 */
	@Override
	public int getRenderId()
	{
		return SampleBlockRendererCore.blockColumnRenderID;
	}
}
