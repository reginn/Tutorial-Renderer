package com.sample.renderer.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class BigHoeRenderer implements IItemRenderer
{
	/*
	 * ItemRenderType(Enum)
	 * + ENTITY
	 *   | ドロップ状態のアイテム(EntityItem)時の描画
	 *   | Object[]にRenderBlocks, EntityItemのインスタンスが渡される.
	 *
	 * + EQUIPPED
	 *   | 装備時(三人称, 他人から見たとき)の描画
	 *   | Object[]にRenderBlocksとEntityLivingのインスタンスが渡される.
	 *
	 * + EQUIPPED_FIRST_PERSON
	 *   | 装備時(一人称, 主観)の描画
	 *   | Object[]にRenderBlocksとEntityLivingのインスタンスが渡される.
	 *
	 * + INVENTORY
	 *   | インベントリ内にあるときの描画
	 *   | Object[]にRenderBlocksのインスタンスが渡される.
	 *
	 * + FIRST_PERSON_MAP
	 *   | 主観で地図を持ったときのような描画
	 *   | Object[]にEntityPlayer, RenderEngine, MapDataのインスタンスが渡される.
	 */

	/*
	 * 引数のItemをItemRenderType時に描画する場合はtrue, そうでないならfalseを返すメソッド.
	 * 今回はBig Hoe, EQUIPPEDとEQUIPPED_FIRST_PERSONのときのみ描画をフックする.
	 */
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		if (item.getItem() == SampleItemRendererCore.itemBigHoe &&
			(type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON))
		{
			return true;
		}
		return false;
	}

	/*
	 * ItemRendererHelperを使う場合, trueを返す.
	 * ItemRendererHelperには以下の定数がある.
	 * + ENTITY_ROTATION
	 * + ENTITY_BOBBING
	 * + EQUIPPED_BLOCK
	 * + BLOCK_3D
	 * + INVENTORY_BLOCK
	 *
	 * 使い方不明, 恐らく描画に何か作用するはずだが作用せず
	 */
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return false;
	}

	/*
	 * 描画を差し替えるメソッド.
	 * このメソッドに具体的な描画処理を記述する.
	 * 引数のObject...はItemRenderTypeによって渡されるものが変わるので注意.
	 */
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		if (data[0] == null || data[1] == null)
		{
			return ;
		}

		if (data[1] instanceof EntityLivingBase &&
			(type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON))
		{
			EntityPlayer player = (EntityPlayer)data[1];

			/*
			 * 追加したアイテムの描画のみ変更する.
			 * 今回はTessellatorを直接扱ってるが, Modelなどを描画させることも可能.
			 */
			if (item.getItem() == SampleItemRendererCore.itemBigHoe)
			{
				/*
				 * 行列スタックにpushしておく.
				 * これはOpenGLないし3Dグラフィックの分野なので詳細は省く.
				 */
				GL11.glPushMatrix();

				/*
				 * 平行移動, 単純に拡大しただけではずれるのでずれを補正する.
				 */
				GL11.glTranslatef(-0.5F, -0.5F, 0.0F);

				/*
				 * 拡大, 元のアイコンの倍の大きさにする.
				 */
				GL11.glScalef(2.0F, 2.0F, 2.0F);

				/*
				 * アイコンをそのまま描画する.
				 * ItemRendererのrenderItemのまま.
				 */
				Tessellator tessellator = Tessellator.instance;
				IIcon icon = item.getIconIndex();

				/*
				 * 末尾の0.0625Fは手に持った時のアイコンの厚み.
				 */
				ItemRenderer.renderItemIn2D(
						tessellator,
						icon.getMaxU(),
						icon.getMinV(),
						icon.getMinU(),
						icon.getMaxV(),
						icon.getIconWidth(),
						icon.getIconHeight(),
						0.0625F);

				/*
				 * pushとpopは必ず対にする.
				 */
				GL11.glPopMatrix();
			}
		}
	}
}
