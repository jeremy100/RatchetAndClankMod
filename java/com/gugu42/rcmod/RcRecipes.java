package com.gugu42.rcmod;

import com.gugu42.rcmod.blocks.RcBlocks;
import com.gugu42.rcmod.items.RcItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RcRecipes 
{
	public static void addRecipes()
	{
		GameRegistry.addRecipe(new ItemStack(RcBlocks.VENDOR, 1), new Object[]{
			"XZX",
			"ZYZ",
			"XZX",
			'X', Blocks.IRON_BLOCK, 'Y', RcItems.VENDOR_CORE, 'Z', Blocks.OBSIDIAN
		});
		GameRegistry.addRecipe(new ItemStack(RcItems.OMNIWRENCH_3000, 1), new Object[]{
			"X X",
			" Y ",
			" X ",
			'X', Items.IRON_INGOT, 'Y', Blocks.IRON_BLOCK
		});
		GameRegistry.addRecipe(new ItemStack(RcBlocks.CRATE, 1), new Object[]{
			"XXX",
			"XZX",
			"XXX",
			'X', Blocks.PLANKS, 'Z', Items.IRON_INGOT
		});
		GameRegistry.addRecipe(new ItemStack(RcBlocks.AMMO_CRATE, 1), new Object[]{
			"XXX",
			"XZX",
			"XXX",
			'X', Blocks.COBBLESTONE, 'Z', Items.IRON_INGOT
		});
		GameRegistry.addRecipe(new ItemStack(RcItems.VENDOR_CORE, 1), new Object[]{
			"XXX",
			"XZX",
			"XXX",
			'X', new ItemStack(Items.DYE, 1, 14), 'Z', Items.DIAMOND
		});
		
		GameRegistry.addRecipe(new ItemStack(RcItems.CLANK_CORE, 1), new Object[]{
			"GGG",
			"GDG",
			"GGG",
			'G', new ItemStack(Items.DYE, 1, 10), 'D', Items.DIAMOND
		});
		
		GameRegistry.addRecipe(new ItemStack(RcItems.CLANK, 1), new Object[]{
			"IRI",
			"ICI",
			"IBI",
			'I', Items.IRON_INGOT, 'R', Items.REDSTONE, 'B', Blocks.REDSTONE_BLOCK, 'C', RcItems.CLANK_CORE
		});
		
		GameRegistry.addRecipe(new ItemStack(RcItems.HELIPACK_HELICE, 1), new Object[]{
			" I ",
			"IYI",
			" I ",
			'I', Items.IRON_INGOT, 'Y', new ItemStack(Items.DYE, 1, 11)
		});
		
		GameRegistry.addRecipe(new ItemStack(RcItems.HELIPACK, 1), new Object[]{
			" H ",
			" I ",
			"HCH",
			'I', Blocks.IRON_BARS, 'C', RcItems.CLANK, 'H', RcItems.HELIPACK_HELICE
		});
		GameRegistry.addRecipe(new ItemStack(RcBlocks.TNT_CRATE, 4), new Object[]{
			" T ",
			"TYT",
			" T ",
			'T', Blocks.TNT, 'Y', new ItemStack(Items.DYE, 1, 11)
		});
		
		GameRegistry.addRecipe(new ItemStack(RcItems.RATCHET_EARS, 1), new Object[]{
			"Y Y",
			"YTY",
			'T', Items.STRING, 'Y', new ItemStack(Blocks.WOOL, 1, 4)
		});
		
		GameRegistry.addRecipe(new ItemStack(RcItems.THRUSTER_PACK, 1), new Object[]{
			"ICI",
			"H H",
			'I', Blocks.IRON_BARS, 'C', RcItems.CLANK, 'H', Items.FLINT_AND_STEEL
		});
		
		GameRegistry.addRecipe(new ItemStack(RcItems.SWINGSHOT, 1), new Object[]{
			"ISI",
			"  I",
			'I', Items.IRON_INGOT, 'S', Items.STRING
		});
		
		GameRegistry.addRecipe(new ItemStack(RcBlocks.VERSA_TARGET_GREEN, 1), new Object[]{
			"III",
			"IGI",
			"III",
			'I', Items.IRON_INGOT, 'G', new ItemStack(Items.DYE, 1, 10)
		});
		
		GameRegistry.addRecipe(new ItemStack(RcBlocks.SHIP_PLATFORM, 1), new Object[]{
			"YPY",
			"III",
			"YIY",
			'I', Items.IRON_INGOT, 'Y', new ItemStack(Items.DYE, 1, 11), 'P', new ItemStack(Blocks.STONE_PRESSURE_PLATE)
		});
	}
}
