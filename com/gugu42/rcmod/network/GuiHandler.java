package com.gugu42.rcmod.network;

import com.gugu42.rcmod.ContainerVendor;
import com.gugu42.rcmod.gui.GuiVendor;
import com.gugu42.rcmod.tileentity.TileEntityVendor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
    //returns an instance of the Container you made earlier
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world,
                    int x, int y, int z) {
            TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
            if(tileEntity instanceof TileEntityVendor){
                    return new ContainerVendor(player.inventory, (TileEntityVendor) tileEntity);
            }
            return null;
    }

    //returns an instance of the Gui you made earlier
    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world,
                    int x, int y, int z) {
            TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
            if(tileEntity instanceof TileEntityVendor){
                    return new GuiVendor(player.inventory, (TileEntityVendor) tileEntity, player, new ContainerVendor(player.inventory, (TileEntityVendor) tileEntity));
            }
            return null;

    }
}