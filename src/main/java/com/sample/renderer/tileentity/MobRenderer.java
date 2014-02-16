package com.sample.renderer.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MobRenderer extends TileEntitySpecialRenderer
{
	private static final ResourceLocation resource     = new ResourceLocation("textures/entity/creeper/creeper.png");
	private              ModelCreeper     modelCreeper = new ModelCreeper();

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTickTime)
	{
		this.doRender(tileEntity, x, y, z, partialTickTime);
	}

	private void doRender(TileEntity tileEntity, double x, double y, double z, float partialTickTime)
	{
		this.bindTexture(resource);

		/*
		 * 回転用の角度, tick毎に更新される.
		 * なお, インベントリ内部では回転しない.
		 */
		float rot = 0.0F;

		if (tileEntity.getWorldObj() != null)
		{
			rot = tileEntity.getWorldObj().getWorldTime() % 360L;
		}

		GL11.glPushMatrix();
		/*
		 * 位置の調整と色の設定.
		 */
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);

		GL11.glRotatef(-180F, 1.0F, 0.0F, 0.0F);
		GL11.glTranslatef(0.0F, -0.75F, 0.0F);

		/*
		 * Y軸を回転させる処理.
		 */
		GL11.glRotatef(rot, 0.0F, 1.0F, 0.0F);

		modelCreeper.render((Entity) null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
	}
}
