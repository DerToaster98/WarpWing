package de.dertoaster.warpwing.item;

import de.dertoaster.warpwing.init.WWItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemBurningWing extends AbstractItemWarpWing {

	public ItemBurningWing(Properties props) {
		super(props);
	}

	@Override
	protected boolean canTeleport() {
		return false;
	}
	
	@Override
	public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
		super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
		
		if ((pEntity.isInWater() || (pEntity.wasOnFire && !pEntity.isOnFire())) && pEntity instanceof ServerPlayer p) {
			this.replaceWings(p, p.getInventory());
		} else {
			burnDownWing(pStack, pLevel, pEntity);
		}
	}
	
	@Override
	protected Tuple<BlockPos, ServerLevel> getRespawnPosition(ServerPlayer player, ServerLevel respawnDimension) {
		return null;
	}

	@Override
	protected ItemStack createReplacement(ItemStack original) {
		return WWItems.ITEM_BURNT_WING.get().getDefaultInstance();
	}

	@Override
	protected void onAfterReplacment(Player player) {
		// TODO: Spawn particles
		player.sendSystemMessage(Component.translatable("item." + this.getRegistryName().getNamespace() + "." + this.getRegistryName().getPath() + ".burnt"));
		final Holder<SoundEvent> soundHolder = Holder.direct(SoundEvents.FIRE_EXTINGUISH);
		if (player instanceof ServerPlayer sp) {
			sp.connection.send(new ClientboundSoundPacket(soundHolder, SoundSource.PLAYERS, player.position().x(), player.position().y(), player.position().z(), 1.0F, 1.0F, player.getRandom().nextLong()));
		}
	}
	
	protected void burnDownWing(ItemStack pStack, Level pLevel, Entity pEntity) {
		if (pEntity.tickCount % 5 == 0) {
			if (!(pEntity instanceof Player p && (p.isCreative() || p.isSpectator()))) {
				pEntity.setSecondsOnFire(5);
			}
			pEntity.hurt(pLevel.damageSources().onFire(), 1);
			if (pEntity.tickCount % 20 == 0) {
				if (pEntity instanceof LivingEntity le) {
					pStack.hurtAndBreak(1, le, (arg0) -> {
						// Send message and play sound
						if (le instanceof ServerPlayer player) {
							player.sendSystemMessage(Component.translatable("item." + this.getRegistryName().getNamespace() + "." + this.getRegistryName().getPath() + ".burnt"));
							final Holder<SoundEvent> soundHolder = Holder.direct(SoundEvents.FIRE_EXTINGUISH);
							player.connection.send(new ClientboundSoundPacket(soundHolder, SoundSource.PLAYERS, player.position().x(), player.position().y(), player.position().z(), 1.0F, 1.0F, player.getRandom().nextLong()));
						}
					});
				}
			}
		}
	}

}
