package com.gugu42.rcmod.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

import com.gugu42.rcmod.CommonProxy;
import com.gugu42.rcmod.RcMod;
import com.gugu42.rcmod.entity.projectiles.EntityVisibombAmmo;
import com.gugu42.rcmod.items.ItemRcWeap;
import com.gugu42.rcmod.items.RcItems;
import com.gugu42.rcmod.testing.EntityVisibombCamera;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RcEventHandler {

	private CommonProxy proxy;

	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {

		if (event.entity instanceof EntityPlayer
				&& ExtendedPlayerBolt.get((EntityPlayer) event.entity) == null)

			ExtendedPlayerBolt.register((EntityPlayer) event.entity);

		if (event.entity instanceof EntityPlayer
				&& event.entity
						.getExtendedProperties(ExtendedPlayerBolt.EXT_PROP_NAME) == null) {
			event.entity.registerExtendedProperties(
					ExtendedPlayerBolt.EXT_PROP_NAME, new ExtendedPlayerBolt(
							(EntityPlayer) event.entity));
		}

		if (event.entity instanceof EntityPlayer
				&& ExtendedEntityLivingBaseTarget
						.get((EntityPlayer) event.entity) == null) {
			ExtendedEntityLivingBaseTarget
					.register((EntityPlayer) event.entity);
		}
		if (event.entity instanceof EntityLivingBase) {
			ExtendedEntityLivingBaseTarget
					.register((EntityLivingBase) event.entity);
		}

		if (event.entity != null) {
			event.entity.getEntityData().setInteger("missilesTargeting", 0);
		}

	}

	@SubscribeEvent
	public void onLivingJumpEvent(LivingJumpEvent event) {
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			if (player.inventory.armorItemInSlot(2) != null
					&& player.inventory.armorItemInSlot(2).getItem() == RcMod.clankBackpack
					&& player.isSneaking()) {
				event.entity.motionY += 0.3D;
				event.entity.getEntityData().setBoolean("clankJumped", true);
				event.entity.getEntityData().setInteger("clankCooldown", 2);
			}

			if (player.inventory.armorItemInSlot(2) != null
					&& player.inventory.armorItemInSlot(2).getItem() == RcMod.clankBackpack
					&& player.isSprinting()) {
				double x = Math.cos(Math
						.toRadians(player.rotationYawHead + 90.0F)) * 0.05d;

				double z = Math.sin(Math
						.toRadians(player.rotationYawHead + 90.0F)) * 0.05d;

				player.motionX += x;
				player.motionZ += z;
				event.entity.getEntityData().setBoolean("clankJumped", true);
				event.entity.getEntityData().setInteger("clankCooldown", 2);
			}
		}
	}

	@SubscribeEvent
	public void onLivingFallEvent(LivingFallEvent event) {
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			if (player.inventory.armorItemInSlot(2) != null
					&& player.inventory.armorItemInSlot(2).getItem()== RcMod.clankBackpack) {
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onLivingUpdateEvent(LivingUpdateEvent event) {
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			if (player.inventory.armorItemInSlot(2) != null
					&& player.inventory.armorItemInSlot(2).getItem() == RcMod.clankBackpack) {
				if (player.fallDistance > 1.6F) {
					event.entity.getEntityData()
							.setBoolean("clankJumped", true);
					event.entity.getEntityData().setInteger("clankCooldown", 2);
				}
			}
		}
	}

	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event) {
		if (!event.entity.worldObj.isRemote
				&& event.entity instanceof EntityPlayer) {
			NBTTagCompound playerData = new NBTTagCompound();
			((ExtendedPlayerBolt) (event.entity
					.getExtendedProperties(ExtendedPlayerBolt.EXT_PROP_NAME)))
					.saveNBTData(playerData);
			proxy.storeEntityData(((EntityPlayer) event.entity).getDisplayName(),
					playerData);
			ExtendedPlayerBolt.saveProxyData((EntityPlayer) event.entity);
		} else {

		}
	}

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if (!event.entity.worldObj.isRemote
				&& event.entity instanceof EntityPlayer) {
			NBTTagCompound playerData = proxy
					.getEntityData(((EntityPlayer) event.entity).getDisplayName());
			if (playerData != null) {
				((ExtendedPlayerBolt) (event.entity
						.getExtendedProperties(ExtendedPlayerBolt.EXT_PROP_NAME)))
						.loadNBTData(playerData);
			}

			((ExtendedPlayerBolt) (event.entity
					.getExtendedProperties(ExtendedPlayerBolt.EXT_PROP_NAME)))
					.sync();
		}

		if (event.entity instanceof EntityLivingBase) {
			ExtendedEntityLivingBaseTarget props2 = ExtendedEntityLivingBaseTarget
					.get((EntityLivingBase) event.entity);
			props2.setTargeted(false);
		}

		// EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;

		if (event.entity instanceof EntityVisibombAmmo) {
			EntityVisibombAmmo arrow = (EntityVisibombAmmo) event.entity;
			EntityPlayer arrowShooter = arrow.worldObj
					.getClosestPlayerToEntity(arrow, 10);
			if (arrowShooter == null)
				return;

			EntityVisibombCamera.getInstance().startCam(arrow, true);

		}

		if (event.entity != null) {
			event.entity.getEntityData().setInteger("missilesTargeting", 0);
		}
	}

	@SubscribeEvent
	public void onItemPickup(EntityItemPickupEvent event) {
		ItemStack item = event.item.getEntityItem();
		ExtendedPlayerBolt props = ExtendedPlayerBolt.get(event.entityPlayer);
		if (item.getItem() != null && item.getItem() == RcItems.bolt) {
			props.addBolt(25);
			event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer,
					"rcmod:BoltCollect", 0.3f, 1.0f);
			event.item.setDead();
			event.setCanceled(true);
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void preRenderPlayer(RenderPlayerEvent.Pre event) {
		EntityPlayer player = event.entityPlayer;
		ItemStack is = player.getCurrentEquippedItem();
		if ((is != null) && ((is.getItem() instanceof ItemRcWeap))) {
			ItemRcWeap itemInHand = (ItemRcWeap) is.getItem();
			if (itemInHand.getHeldType() == 1) {
				ModelBiped modelMain = ObfuscationReflectionHelper
						.getPrivateValue(RenderPlayer.class, event.renderer, 1);
				ModelBiped modelArmorChestplate = ObfuscationReflectionHelper
						.getPrivateValue(RenderPlayer.class, event.renderer, 2);
				ModelBiped modelArmor = ObfuscationReflectionHelper
						.getPrivateValue(RenderPlayer.class, event.renderer, 3);
				modelMain.aimedBow = modelArmorChestplate.aimedBow = modelArmor.aimedBow = true;
			}
		}

	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderGameOverlay(RenderGameOverlayEvent event) {
		if (Minecraft.getMinecraft().thePlayer == null
				|| Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem() == null)
			return;
		if (Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem()
				.getItem() instanceof ItemRcWeap) {
			ItemRcWeap item = (ItemRcWeap) Minecraft.getMinecraft().thePlayer
					.getCurrentEquippedItem().getItem();
			if (item.hasCrosshair || item.hideCrosshair) {
				if (event.type == RenderGameOverlayEvent.ElementType.CROSSHAIRS) {
					event.setCanceled(true);
				}
			}
		}
	}

}