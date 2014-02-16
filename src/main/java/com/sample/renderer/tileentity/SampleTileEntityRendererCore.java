package com.sample.renderer.tileentity;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

@Mod(modid = SampleTileEntityRendererCore.MODID, version = SampleTileEntityRendererCore.VERSION)
public class SampleTileEntityRendererCore
{
	public static final String MODID   = "TileEntityRenderer";
	public static final String VERSION = "0.0.0";

	public static Block blockMob;

	@SideOnly(Side.CLIENT)
	public static int blockMobRenderType = RenderingRegistry.getNextAvailableRenderId();

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		blockMob = (new BlockMob(Material.rock))
				.setBlockName("blockMob")
				.setBlockTextureName("stone")
				.setCreativeTab(CreativeTabs.tabBlock);

		GameRegistry.registerBlock(blockMob, "blockMob");
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		GameRegistry.registerTileEntity(TileEntityDummy.class, "TileEntityDummy");

		if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
		{
			/*
			 * TileEntitySpecialRendererの登録.
			 */
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDummy.class, new MobRenderer());
			RenderingRegistry.registerBlockHandler(new BlockMobRenderer());
		}

	}
}
