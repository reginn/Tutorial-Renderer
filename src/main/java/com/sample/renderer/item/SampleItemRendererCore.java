package com.sample.renderer.item;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraftforge.client.MinecraftForgeClient;

@Mod(modid = SampleItemRendererCore.MODID, version = SampleItemRendererCore.VERSION)
public class SampleItemRendererCore
{
	public static final String MODID = "ItemRenderer";
	public static final String VERSION = "0.0.0";

	public static Item itemBigHoe;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		/*
		 * 通常のクワより見た目が大きいクワのサンプル.
		 * アイテムの追加方法はいままでと同じ.
		 */
		itemBigHoe = (new ItemHoe(Item.ToolMaterial.IRON))
				.setUnlocalizedName("itemBigHoe")
				.setTextureName("iron_hoe")
				.setCreativeTab(CreativeTabs.tabTools);

		GameRegistry.registerItem(itemBigHoe, "itemBigHoe");
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		/*
		 * アイテムの描画を登録する.
		 * クライアントのみであることに注意.
		 */
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
		{
			MinecraftForgeClient.registerItemRenderer(itemBigHoe, new BigHoeRenderer());
		}
	}
}
