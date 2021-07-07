package de.dertoaster.warpwing.objects.items;

import de.dertoaster.warpwing.init.WWSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.network.play.server.SPlaySoundEffectPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
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
		return 720000; //Use duration of bow
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
	public void releaseUsing(ItemStack item, World world, LivingEntity user, int useDuration) {
		if(user instanceof ServerPlayerEntity && useDuration >= 60) {
			ServerPlayerEntity player = (ServerPlayerEntity) user;
			BlockPos respawnPosition = player.getRespawnPosition();
			if(respawnPosition == null) {
			}
			ServerWorld respawnDimension = world.getServer().getLevel(player.getRespawnDimension());
			
			if(respawnDimension != null && respawnPosition != null) {
				player.teleportTo(respawnDimension, respawnPosition.getX(), respawnPosition.getY(), respawnPosition.getZ(), player.getRespawnAngle(), 0);
				player.connection.send(new SPlaySoundEffectPacket(WWSounds.ITEM_WARP_WING_WOOSH, SoundCategory.PLAYERS, (double)respawnPosition.getX(), (double)respawnPosition.getY(), (double)respawnPosition.getZ(), 1.0F, 1.0F));
			}
		}
	}
	
	public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
	      ItemStack itemstack = p_77659_2_.getItemInHand(p_77659_3_);
	      boolean flag = !p_77659_2_.getProjectile(itemstack).isEmpty();

	      ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, p_77659_1_, p_77659_2_, p_77659_3_, flag);
	      if (ret != null) return ret;

	      if (!p_77659_2_.abilities.instabuild && !flag) {
	         return ActionResult.fail(itemstack);
	      } else {
	         p_77659_2_.startUsingItem(p_77659_3_);
	         return ActionResult.consume(itemstack);
	      }
	   }
	

}
