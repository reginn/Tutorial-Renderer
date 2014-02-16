package com.sample.renderer.item;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelCow;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ItemModelRenderer implements IItemRenderer
{
	/*
	 * Modelに使うリソースの設定.
	 */
	private static final ResourceLocation resource = new ResourceLocation("textures/entity/cow/cow.png");

	/*
	 * Modelのインスタンスを生成.
	 * 今回は牛のModelを利用する.
	 */
	private ModelCow model = new ModelCow();

	/*
	 * 描画する際の条件を返す. 今回はcanRenderingで条件を設定.
	 */
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return canRendering(item, type);
	}

	/*
	 * 今回は以下の4つのパターン
	 *                ENTITY : Drop状態の描画
	 *              EQUIPPED : 装備してるとき(三人称)の描画
	 * EQUIPPED_FIRST_PERSON : 装備しているとき(一人称)の描画
	 *             INVENTORY : インベントリ内の描画
	 */
	private boolean canRendering(ItemStack item, ItemRenderType type)
	{
		switch(type)
		{
			case ENTITY:
			case EQUIPPED:
			case EQUIPPED_FIRST_PERSON:
			case INVENTORY:
				return true;
			default:
				return false;
		}
	}

	/*
	 * EQUIPPED, EQUIPPED_FIRST_PERSON以外の描画をする場合, ItemRenderHelperを利用するので適切な場合にtrueを返す.
	 * INVENTORYの場合はINVENTORY_BLOCKを, ENTITYの場合はENTITY_BOBBINGとENTITY_ROTATIONを返す.
	 * ENTITY_BOBBINGはEntityItemが上下する描画設定, ENTITY_ROTATIONがEntityItemが回転する描画設定である.
	 */
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		switch (helper)
		{
			case INVENTORY_BLOCK:
			case ENTITY_BOBBING:
			case ENTITY_ROTATION:
				return true;
			default:
				return false;
		}
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		if (canRendering(item, type))
		{
			GL11.glPushMatrix();

			/*
			 * 描画する種類によって回転, 平行移動を行う.
			 */
			switch(type)
			{
				case INVENTORY:
					glMatrixForRenderInInventory(); break;
				case EQUIPPED:
				case EQUIPPED_FIRST_PERSON:
					glMatrixForRenderInEquipped();
					break;
				case ENTITY:
					glMatrixForRenderInEntity();
			}

			/*
			 * リソースをTextureMangerにbindし, modelのrenderを呼んで描画する.
			 */
			FMLClientHandler.instance().getClient().getTextureManager().bindTexture(resource);
			this.model.render((Entity) null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

			GL11.glPopMatrix();
		}
	}

	/*
	 * インベントリ内での描画位置の調整.
	 */
	private void glMatrixForRenderInInventory()
	{
		GL11.glRotatef(-180F, 1.0F, 0.0F, 0.0F);
		GL11.glTranslatef(0.0F, -1.0F, 0.0F);
	}

	/*
	 * 装備状態での描画位置の調整.
	 */
	private void glMatrixForRenderInEquipped()
	{
		GL11.glRotatef(-245F, 1.0F, 0.0F, 0.0F);
		GL11.glTranslatef(0.25F, -1.0F, 0.0F);
	}

	/*
	 * ドロップ状態での描画位置の調整.
	 */
	private void glMatrixForRenderInEntity()
	{
		GL11.glRotatef(-180F, 1.0F, 0.0F, 0.0F);
		GL11.glTranslatef(0.0F, -1.5F, 0.0F);
	}
}
