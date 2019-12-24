package com.lerrific.horsestats;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = "horsestats", name = "Horse Stats", version = "1.0.0")
public class HorseStats {

	public HorseStats() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void getHorseStats(EntityInteract event) {
		if (event.getEntityPlayer() != null && event.getWorld() != null && event.getTarget() instanceof AbstractHorse) {
			EntityPlayer player = event.getEntityPlayer();
			AbstractHorse horseEntity = (AbstractHorse) event.getTarget();
			if (player.isSneaking() && player.getHeldItemMainhand().getItem() == Items.STICK) {
				event.setResult(Result.DENY);
				event.setCanceled(true); // Don't access the horse's inventory
				TextFormatting colourHealth = TextFormatting.WHITE;
				TextFormatting colourSpeed = TextFormatting.WHITE;
				TextFormatting colourJump = TextFormatting.WHITE;
				double health = horseEntity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue();
				double speed = horseEntity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
				double jump = horseEntity.getHorseJumpStrength();

				jump = -0.1817584952 * Math.pow(jump, 3) + 3.689713992 * Math.pow(jump, 2) + 2.128599134 * jump - 0.343930367; // https://minecraft.gamepedia.com/Horse
				speed = speed * 43;

				if (health <= 20) {
					colourHealth = TextFormatting.GRAY;
				} else if (health <= 23) {
					colourHealth = TextFormatting.WHITE;
				} else if (health <= 26) {
					colourHealth = TextFormatting.YELLOW;
				} else if (health <= 29) {
					colourHealth = TextFormatting.AQUA;
				} else if (health <= Double.MAX_VALUE) {
					colourHealth = TextFormatting.LIGHT_PURPLE;
				}

				if (speed <= 7) {
					colourSpeed = TextFormatting.GRAY;
				} else if (speed <= 9) {
					colourSpeed = TextFormatting.WHITE;
				} else if (speed <= 11) {
					colourSpeed = TextFormatting.YELLOW;
				} else if (speed <= 13) {
					colourSpeed = TextFormatting.AQUA;
				} else if (speed <= Double.MAX_VALUE) {
					colourSpeed = TextFormatting.LIGHT_PURPLE;
				}

				if (jump <= 1.50) {
					colourJump = TextFormatting.GRAY;
				} else if (jump <= 2.0) {
					colourJump = TextFormatting.WHITE;
				} else if (jump <= 3.0) {
					colourJump = TextFormatting.YELLOW;
				} else if (jump <= 4.0) {
					colourJump = TextFormatting.AQUA;
				} else if (jump <= Double.MAX_VALUE) {
					colourJump = TextFormatting.LIGHT_PURPLE;
				}

				if (horseEntity instanceof EntityLlama) {
					TextFormatting colourSlots = TextFormatting.WHITE;
					double slots = ((EntityLlama) horseEntity).getStrength();

					slots = slots * 3;

					if (slots <= 3) {
						colourSlots = TextFormatting.GRAY;
					} else if (slots <= 6) {
						colourSlots = TextFormatting.WHITE;
					} else if (slots <= 9) {
						colourSlots = TextFormatting.YELLOW;
					} else if (slots <= 12) {
						colourSlots = TextFormatting.AQUA;
					} else if (slots <= Double.MAX_VALUE) {
						colourSlots = TextFormatting.LIGHT_PURPLE;
					}

					player.sendStatusMessage(new TextComponentString(String.format("%sHealth: %.0f %sSpeed: %.1f %sChest Slots: %.0f", colourHealth, health, colourSpeed, speed, colourSlots, slots)), true);

				} else {
					player.sendStatusMessage(new TextComponentString(String.format("%sHealth: %.0f %sSpeed: %.1f %sJump Height: %.1f", colourHealth, health, colourSpeed, speed, colourJump, jump)), true);
				}
			}
		}
	}
}
