package de.dertoaster.warpwing.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemBurntWing extends AbstractItemWarpWing {

	public ItemBurntWing(Properties props) {
		super(props.setNoRepair().durability(0).stacksTo(1));
	}
	
	@Override
	public ItemStack finishUsingItem(ItemStack item, Level world, LivingEntity user) {
		super.finishUsingItem(item, world, user);
		
		user.broadcastBreakEvent(EquipmentSlot.MAINHAND);
		
		return ItemStack.EMPTY;
	}
	
	@Override
	protected ItemStack createReplacement(ItemStack original) {
		return original;
	}

	@Override
	protected void onAfterReplacment(Player holder) {
		
	}

}
