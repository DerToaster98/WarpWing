package de.dertoaster.warpwing.item;

import de.dertoaster.warpwing.config.WarpWingModConfigHolder;
import de.dertoaster.warpwing.init.WWItems;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemWarpWing extends AbstractItemWarpWing {
	
	public ItemWarpWing(Properties props) {
		super(props);
	}
	
	@Override
	public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
		super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
		
		this.igniteWing(pStack, pLevel, pEntity);
	}
	
	protected void igniteWing(ItemStack pStack, Level pLevel, Entity pEntity) {
		if (pEntity instanceof Player p && (p.isCreative() || p.isSpectator())) {
			return;
		}
		// If in a hot dimension, BURN!!!
		if (pEntity instanceof Player p && WarpWingModConfigHolder.CONFIG.wwHotDimensions.get().contains(pLevel.dimensionType().effectsLocation().toString())) {
			this.replaceWings(p, p.getInventory());
		}
	}

	@Override
	protected ItemStack createReplacement(ItemStack original) {
		return WWItems.ITEM_BURNING_WING.get().getDefaultInstance();
	}

	@Override
	protected void onAfterReplacment(Player holder) {
		if (holder instanceof ServerPlayer player) {
			player.sendSystemMessage(Component.translatable("item." + this.getRegistryName().getNamespace() + "." + this.getRegistryName().getPath() + ".igniting"));
			final Holder<SoundEvent> soundHolder = Holder.direct(SoundEvents.FIRECHARGE_USE);
			player.connection.send(new ClientboundSoundPacket(soundHolder, SoundSource.PLAYERS, player.position().x(), player.position().y(), player.position().z(), 1.0F, 1.0F, player.getRandom().nextLong()));
		}
	}

}
