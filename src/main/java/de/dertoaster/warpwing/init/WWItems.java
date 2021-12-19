package de.dertoaster.warpwing.init;

import de.dertoaster.warpwing.WarpWingMod;
import de.dertoaster.warpwing.config.WarpWingModConfigHolder;
import de.dertoaster.warpwing.item.ItemWarpWing;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WWItems {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WarpWingMod.MODID);
	
	//formatter:off
	public static final RegistryObject<Item> ITEM_WARP_WING = ITEMS.register(
		"warp_wing", () -> new ItemWarpWing(
			new Properties()
			.tab(CreativeModeTab.TAB_TRANSPORTATION)
			.stacksTo(1)
			.setNoRepair()
			.durability(WarpWingModConfigHolder.ITEM_CONFIG.wwDurability.get())
			.rarity(Rarity.RARE)
		)
	);
	//formatter:on
}
