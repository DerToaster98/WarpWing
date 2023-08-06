package de.dertoaster.warpwing;

import de.dertoaster.warpwing.init.WWItems;
import de.dertoaster.warpwing.item.ItemWarpWing;
import de.dertoaster.warpwing.item.ItemWarpWing.EWingState;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = WarpWingMod.MODID, bus = Bus.MOD)
public class EventHandlerMod {
	
	@SubscribeEvent
	public static void assignItemsToTabs(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey().equals(CreativeModeTabs.TOOLS_AND_UTILITIES)) {
			event.accept(WWItems.ITEM_WARP_WING.get());
			event.accept(ItemWarpWing.setState(WWItems.ITEM_WARP_WING.get().getDefaultInstance(), EWingState.BURNING));
			event.accept(ItemWarpWing.setState(WWItems.ITEM_WARP_WING.get().getDefaultInstance(), EWingState.BURNT));
		}
	}
	
}
