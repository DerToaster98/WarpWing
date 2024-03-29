package de.dertoaster.warpwing;

import de.dertoaster.warpwing.init.WWItems;
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
			event.accept(WWItems.ITEM_BURNING_WING.get());
			event.accept(WWItems.ITEM_BURNT_WING.get());
		}
	}
	
}
