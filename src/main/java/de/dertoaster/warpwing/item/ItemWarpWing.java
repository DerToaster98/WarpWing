package de.dertoaster.warpwing.item;

import java.util.List;

import org.lwjgl.glfw.GLFW;

import de.dertoaster.warpwing.WarpWingMod;
import de.dertoaster.warpwing.init.WWSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class ItemWarpWing extends Item {

	public ItemWarpWing(Properties props) {
		super(props);
	}

	@Override
	public UseAnim getUseAnimation(ItemStack p_77661_1_) {
		return UseAnim.BOW;
	}

	@Override
	public int getUseDuration(ItemStack p_77626_1_) {
		return WarpWingMod.getConfig().useDuration;
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
				player.sendMessage(new TextComponent(ChatFormatting.GRAY + I18n.get("item." + this.getRegistryName().getNamespace() + "." + this.getRegistryName().getPath() + ".teleporting", '\n')), ChatType.CHAT, player.getUUID());
				player.connection.send(new ClientboundSoundPacket(WWSounds.ITEM_WARP_WING_WOOSH, SoundSource.PLAYERS, (double) respawnPosition.getX(), (double) respawnPosition.getY(), (double) respawnPosition.getZ(), 1.0F, 1.0F));
			}
		} /*else if(world.isClientSide) { 
			world.playLocalSound(user.getX(), user.getY(), user.getZ(), WWSounds.ITEM_WARP_WING_WOOSH, SoundCategory.AMBIENT, 10.0F, 1.0F, false);
		}*/
		
		return item;
	}

	@Override
	public void appendHoverText(ItemStack p_77624_1_, Level p_77624_2_, List<Component> tooltip, TooltipFlag p_77624_4_) {
		super.appendHoverText(p_77624_1_, p_77624_2_, tooltip, p_77624_4_);
		if (GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT) != GLFW.GLFW_PRESS) {
			tooltip.add(new TextComponent(ChatFormatting.BLUE + I18n.get("item." + this.getRegistryName().getNamespace() + ".tooltip.hold_shift", '\n', '\n', '\n', '\n', '\n', '\n', '\n', '\n', '\n', '\n')));
		} else {
			tooltip.add(new TextComponent(ChatFormatting.BLUE + I18n.get("item." + this.getRegistryName().getNamespace() + "." + this.getRegistryName().getPath() + ".tooltip", '\n', '\n', '\n', '\n', '\n', '\n', '\n', '\n', '\n', '\n')));
		}
	}

	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		player.startUsingItem(hand);
		return InteractionResultHolder.consume(itemstack);
	}

}
