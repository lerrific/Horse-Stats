package com.lerrific.horsestats;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("horsestats")
public class HorseStats {

	public HorseStats() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void getHorseStats(EntityInteract event) {
		if (event.getPlayer() != null && event.getWorld() != null && event.getTarget() instanceof AbstractHorseEntity) {
			PlayerEntity player = event.getPlayer();
			AbstractHorseEntity horseEntity = (AbstractHorseEntity) event.getTarget();
			if (player.isSneaking() && player.getHeldItemMainhand().getItem() == Items.STICK) {
				TextFormatting colourHealth = TextFormatting.WHITE;
				TextFormatting colourSpeed = TextFormatting.WHITE;
				TextFormatting colourJump = TextFormatting.WHITE;
				double health = horseEntity.getAttribute(SharedMonsterAttributes.MAX_HEALTH).getValue();
				double speed = horseEntity.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue();
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

				if (horseEntity instanceof LlamaEntity) {
					TextFormatting colourSlots = TextFormatting.WHITE;
					double slots = ((LlamaEntity) horseEntity).getStrength();

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

					player.sendStatusMessage(new TranslationTextComponent(String.format("%sHealth: %.0f %sSpeed: %.1f %sChest Slots: %.0f", colourHealth, health, colourSpeed, speed, colourSlots, slots)), true);

				} else {
					player.sendStatusMessage(new TranslationTextComponent(String.format("%sHealth: %.0f %sSpeed: %.1f %sJump Height: %.1f", colourHealth, health, colourSpeed, speed, colourJump, jump)), true);
				}
			}
		}
	}
}
