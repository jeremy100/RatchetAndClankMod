package com.gugu42.rcmod.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotVendor extends Slot{

	public SlotVendor(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public boolean canTakeStack(EntityPlayer par1EntityPlayer)
    {
        return false;
    }
	
	@Override
	public boolean isItemValid(ItemStack par1ItemStack){
		return false;
	}

}
