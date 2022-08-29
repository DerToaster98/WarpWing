package de.dertoaster.warpwing.item;

import de.dertoaster.warpwing.config.WarpWingModConfigHolder;
import de.dertoaster.warpwing.init.WWSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class ItemWarpWing extends ItemLore {

	public ItemWarpWing(Properties props) {
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

	@Override
	public ItemStack finishUsingItem(ItemStack item, Level world, LivingEntity user) {
		if (user instanceof ServerPlayer) {
			ServerPlayer player = (ServerPlayer) user;
			BlockPos respawnPosition = player.getRespawnPosition();
			if (respawnPosition == null) {
				if (world instanceof ServerLevel) {
					ServerLevel sw = (ServerLevel) world;
					respawnPosition = new BlockPos(sw.getLevelData().getXSpawn(), sw.getLevelData().getYSpawn(), sw.getLevelData().getZSpawn());
				}
			}
			ServerLevel respawnDimension = world.getServer().getLevel(player.getRespawnDimension());
			if (respawnPosition != null) {
				item.hurtAndBreak(1, user, (p_220038_0_) -> {
					p_220038_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
				});
				player.teleportTo(respawnDimension, respawnPosition.getX(), respawnPosition.getY(), respawnPosition.getZ(), player.getRespawnAngle(), 0);
				player.sendSystemMessage(Component.translatable("item." + this.getRegistryName().getNamespace() + "." + this.getRegistryName().getPath() + ".teleporting"));
				player.connection.send(new ClientboundSoundPacket(WWSounds.ITEM_WARP_WING_WOOSH.get(), SoundSource.PLAYERS, (double) respawnPosition.getX(), (double) respawnPosition.getY(), (double) respawnPosition.getZ(), 1.0F, 1.0F, player.getRandom().nextLong()));
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

}
