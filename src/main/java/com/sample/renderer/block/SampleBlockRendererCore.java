package com.sample.renderer.block;

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

@Mod(modid = SampleBlockRendererCore.MODID, version = SampleBlockRendererCore.VERSION)
public class SampleBlockRendererCore
{
	public static final String MODID = "BlockRenderer";
	public static final String VERSION = "0.0.0";

	public static Block blockColumn;
	public static Block blockCross;
	public static Block blockTriangle;

	/*
	 * Blockの新しいRenderTypeIDを取得する.
	 * BlockColumnクラス,
	 */
	@SideOnly(Side.CLIENT)
	public static int blockColumnRenderID = RenderingRegistry.getNextAvailableRenderId();

	@SideOnly(Side.CLIENT)
	public static int blockCrossRenderID = RenderingRegistry.getNextAvailableRenderId();

	@SideOnly(Side.CLIENT)
	public static int blockTriangleRenderID = RenderingRegistry.getNextAvailableRenderId();

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		blockColumn = (new BlockColumn(Material.rock))
				.setBlockName("blockColumn")
				.setBlockTextureName("stone")
				.setCreativeTab(CreativeTabs.tabBlock);

		blockCross =(new BlockCross(Material.rock))
				.setBlockName("blockCross")
				.setBlockTextureName("stone")
				.setCreativeTab(CreativeTabs.tabBlock);

		blockTriangle = (new BlockTriangle(Material.rock))
				.setBlockName("blockTriangle")
				.setBlockTextureName("gold_block")
				.setCreativeTab(CreativeTabs.tabBlock);

		GameRegistry.registerBlock(blockColumn, "blockColumn");
		GameRegistry.registerBlock(blockCross, "blockCross");
		GameRegistry.registerBlock(blockTriangle, "blockTriangle");
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		/*
		 * BlockのRendererを登録する.
		 */
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
		{
			RenderingRegistry.registerBlockHandler(new BlockColumnRenderer());
			RenderingRegistry.registerBlockHandler(new BlockCrossRenderer());
			RenderingRegistry.registerBlockHandler(new BlockTriangleRenderer());
		}
	}
}
