package de.dertoaster.warpwing.init;

import de.dertoaster.warpwing.WarpWingMod;
import de.dertoaster.warpwing.item.ItemWarpWing;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class WWItemProperties {
	
	public static void register() {
		// And finally, register the model predicates for the crossbow...
		ItemProperties.register(WWItems.ITEM_WARP_WING.get(), new ResourceLocation(WarpWingMod.MODID, "wing_state"), (itemStack, clientWorld, itemHolder, intIn) -> {
			return itemStack != null ? ItemWarpWing.getState(itemStack).ordinal() : 0;
		});
		
	}

}
