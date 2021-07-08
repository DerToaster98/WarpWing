package de.dertoaster.warpwing.objects.items;

import java.util.List;

import org.lwjgl.glfw.GLFW;

import de.dertoaster.warpwing.init.WWSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.network.play.server.SPlaySoundEffectPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ItemWarpWing extends Item {

	public ItemWarpWing(Properties props) {
		super(props);
	}

	@Override
	public UseAction getUseAnimation(ItemStack p_77661_1_) {
		return UseAction.BOW;
	}

	@Override
	public int getUseDuration(ItemStack p_77626_1_) {
		return 40;
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
	public ItemStack finishUsingItem(ItemStack item, World world, LivingEntity user) {
		if (user instanceof ServerPlayerEntity) {
			ServerPlayerEntity player = (ServerPlayerEntity) user;
			BlockPos respawnPosition = player.getRespawnPosition();
			if (respawnPosition == null) {
				if (world instanceof ServerWorld) {
					ServerWorld sw = (ServerWorld) world;
					respawnPosition = new BlockPos(sw.getLevelData().getXSpawn(), sw.getLevelData().getYSpawn(), sw.getLevelData().getZSpawn());
				}
			}
			ServerWorld respawnDimension = world.getServer().getLevel(player.getRespawnDimension());
			if (respawnPosition != null) {
				item.hurtAndBreak(1, user, (p_220038_0_) -> {
					p_220038_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
				});
				player.teleportTo(respawnDimension, respawnPosition.getX(), respawnPosition.getY(), respawnPosition.getZ(), player.getRespawnAngle(), 0);
				player.connection.send(new SPlaySoundEffectPacket(WWSounds.ITEM_WARP_WING_WOOSH, SoundCategory.PLAYERS, (double) respawnPosition.getX(), (double) respawnPosition.getY(), (double) respawnPosition.getZ(), 1.0F, 1.0F));
			}
		}
		
		return item;
	}

	@Override
	public void appendHoverText(ItemStack p_77624_1_, World p_77624_2_, List<ITextComponent> tooltip, ITooltipFlag p_77624_4_) {
		super.appendHoverText(p_77624_1_, p_77624_2_, tooltip, p_77624_4_);
		if (GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT) != GLFW.GLFW_PRESS) {
			tooltip.add(new StringTextComponent(TextFormatting.BLUE + I18n.get("item." + this.getRegistryName().getNamespace() + ".tooltip.hold_shift", '\n', '\n', '\n', '\n', '\n', '\n', '\n', '\n', '\n', '\n')));
		} else {
			tooltip.add(new StringTextComponent(TextFormatting.BLUE + I18n.get("item." + this.getRegistryName().getNamespace() + "." + this.getRegistryName().getPath() + ".tooltip", '\n', '\n', '\n', '\n', '\n', '\n', '\n', '\n', '\n', '\n')));
		}
	}

	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		player.startUsingItem(hand);
		return ActionResult.consume(itemstack);
	}

}
