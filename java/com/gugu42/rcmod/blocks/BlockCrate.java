package com.gugu42.rcmod.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import com.gugu42.rcmod.RcMod;
import com.gugu42.rcmod.items.RcItems;


public class BlockCrate extends BlockFalling{

    public static boolean fallInstantly;
	
	public BlockCrate(Material par2Material) {
		super(par2Material);
		setCreativeTab(RcMod.rcTab);
	}
	
	@Override
	public int quantityDropped(Random par1Random)
    {
		int quantity = par1Random.nextInt(7);
        return quantity;
    }

	@Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return RcItems.bolt;
    }
    
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3,
			int par4, Entity par5Entity) {
		if (!par1World.isRemote) {
			this.onBlockDestroyedByPlayer(par1World, par2, par3, par4, 0);
		}
	}
	
	public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        par1World.scheduleBlockUpdate(par2, par3, par4, this, this.tickRate(par1World));
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        par1World.scheduleBlockUpdate(par2, par3, par4, this, this.tickRate(par1World));
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!par1World.isRemote)
        {
            this.tryToFall(par1World, par2, par3, par4);
        }
    }

    /**
     * If there is space to fall below will start this block falling
     */
    private void tryToFall(World par1World, int par2, int par3, int par4)
    {
        if (canFallBelow(par1World, par2, par3 - 1, par4) && par3 >= 0)
        {
            byte b0 = 32;

            if (!fallInstantly && par1World.checkChunksExist(par2 - b0, par3 - b0, par4 - b0, par2 + b0, par3 + b0, par4 + b0))
            {
                if (!par1World.isRemote)
                {
                    EntityFallingBlock entityfallingsand = new EntityFallingBlock(par1World, (double)((float)par2 + 0.5F), (double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F), this, par1World.getBlockMetadata(par2, par3, par4));
                    this.func_149829_a(entityfallingsand);
                    par1World.spawnEntityInWorld(entityfallingsand);
                }
            }
            else
            {
                par1World.setBlockToAir(par2, par3, par4);

                while (canFallBelow(par1World, par2, par3 - 1, par4) && par3 > 0)
                {
                    --par3;
                }

                if (par3 > 0)
                {
                    par1World.setBlock(par2, par3, par4, this);
                }
            }
        }
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate(World par1World)
    {
        return 2;
    }

    /**
     * Checks to see if the sand can fall into the block below it
     */
    public static boolean canFallBelow(World par0World, int par1, int par2, int par3)
    {
        Block l = par0World.getBlock(par1, par2, par3);

        if (par0World.isAirBlock(par1, par2, par3))
        {
            return true;
        }
        else if (l == Blocks.fire)
        {
            return true;
        }
        else
        {
            Material material = l.getMaterial();
            return material == Material.water ? true : material == Material.lava;
        }
    }

    /**
     * Called when the falling block entity for this block hits the ground and turns back into a block
     */
    public void onFinishFalling(World par1World, int par2, int par3, int par4, int par5) {}
	
}
