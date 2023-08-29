package de.dertoaster.warpwing.item;

import org.apache.commons.lang3.RandomUtils;

import de.dertoaster.warpwing.config.WarpWingModConfigHolder;
import de.dertoaster.warpwing.init.WWSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public abstract class AbstractItemWarpWing extends ItemLore {

	public AbstractItemWarpWing(Properties props) {
		super(props);
	}
	
	@Override
	public int getMaxDamage(ItemStack stack) {
		return WarpWingModConfigHolder.CONFIG.wwDurability.get();
	}

	@Override
	public UseAnim getUseAnimation(ItemStack p_77661_1_) {
		return UseAnim.BOW;
	}
	
	@Override
	public int getUseDuration(ItemStack p_77626_1_) {
		return WarpWingModConfigHolder.CONFIG.wwUseDuration.get();
	}

	@Override
	public boolean isEnchantable(ItemStack p_77616_1_) {
		return false;
	}

	@Override
	public boolean isRepairable(ItemStack stack) {
		return false;
	}
	
	protected void performTeleport(ServerPlayer player, ServerLevel respawnDimension, BlockPos respawnPosition) {
		player.teleportTo(respawnDimension, respawnPosition.getX(), respawnPosition.getY(), respawnPosition.getZ(), player.getRespawnAngle(), 0);
		player.sendSystemMessage(Component.translatable("item." + this.getRegistryName().getNamespace() + "." + this.getRegistryName().getPath() + ".teleporting"));
		final Holder<SoundEvent> soundHolder = Holder.direct(WWSounds.ITEM_WARP_WING_WOOSH.get());
		player.connection.send(new ClientboundSoundPacket(soundHolder, SoundSource.PLAYERS, (double) respawnPosition.getX(), (double) respawnPosition.getY(), (double) respawnPosition.getZ(), 1.0F, 1.0F, player.getRandom().nextLong()));
	}
	
	protected Tuple<BlockPos, ServerLevel> getRespawnPosition(ServerPlayer player, ServerLevel respawnDimension) {
		if (WarpWingModConfigHolder.CONFIG.wwEnderDimensions.get().contains(player.level().dimensionType().effectsLocation().toString())) {
			int dMin = WarpWingModConfigHolder.CONFIG.wwRandomTPMinDistance.get();
			int dMax = WarpWingModConfigHolder.CONFIG.wwRandomTPMaxDistance.get();
			
			dMin = Math.min(dMin, dMax);
			dMax = Math.max(dMin, dMax);
			
			int distX = RandomUtils.nextInt(dMin, dMax);
			int distZ = RandomUtils.nextInt(dMin, dMax);
			
			respawnDimension = player.serverLevel();
			int y = respawnDimension.getLogicalHeight();
			BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos(player.getBlockX() + distX, y, player.getBlockZ() + distZ);
			while (mutable.getY() >= respawnDimension.getMinBuildHeight()) {
				if (!respawnDimension.isEmptyBlock(mutable.below())) {
					break;
				}
				mutable = mutable.move(Direction.DOWN);
			}
			return new Tuple<>(mutable.above(), respawnDimension);
		}
		BlockPos respawnPosition = player.getRespawnPosition();
		if (respawnPosition == null) {
				respawnPosition = new BlockPos(respawnDimension.getLevelData().getXSpawn(), respawnDimension.getLevelData().getYSpawn(), respawnDimension.getLevelData().getZSpawn());
		}
		return new Tuple<>(respawnPosition, respawnDimension);
	}

	protected boolean canTeleport() {
		return true;
	}
	
	@Override
	public ItemStack finishUsingItem(ItemStack item, Level world, LivingEntity user) {
		if (!this.canTeleport()) {
			return item;
		}
		if (user instanceof ServerPlayer) {
			ServerPlayer player = (ServerPlayer) user;
			ServerLevel respawnDimension = world.getServer().getLevel(player.getRespawnDimension());
			Tuple<BlockPos, ServerLevel> respawnPosition = this.getRespawnPosition(player, respawnDimension);
			if (respawnPosition != null) {
				item.hurtAndBreak(1, user, (p_220038_0_) -> {
					p_220038_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
				});
				this.performTeleport(player, respawnPosition.getB(), respawnPosition.getA());
			}
		} /*else if(world.isClientSide) { 
			world.playLocalSound(user.getX(), user.getY(), user.getZ(), WWSounds.ITEM_WARP_WING_WOOSH, SoundCategory.AMBIENT, 10.0F, 1.0F, false);
		}*/
		
		return item;
	}
	
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		player.startUsingItem(hand);
		return InteractionResultHolder.consume(itemstack);
	}
	
	protected abstract ItemStack createReplacement(final ItemStack original);
	protected abstract void onAfterReplacment(final Player holder);
	
	protected void replaceWings(Player holder, Inventory inventory) {
		boolean replaced = false;
		for (int i = 0; i < inventory.getContainerSize(); i++) {
			ItemStack stack = inventory.getItem(i);
			if (stack == null || !(stack.getItem() instanceof AbstractItemWarpWing)) {
				continue;
			}
			AbstractItemWarpWing aiww = (AbstractItemWarpWing)stack.getItem();
			ItemStack replacement = aiww.createReplacement(stack);
			
			inventory.setItem(i, replacement);
			replaced = true;
		}
		if (replaced) {
			this.onAfterReplacment(holder);
		}
	}
	
}
