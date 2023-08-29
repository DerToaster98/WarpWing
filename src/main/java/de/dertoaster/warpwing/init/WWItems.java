package de.dertoaster.warpwing.init;

import de.dertoaster.warpwing.WarpWingMod;
import de.dertoaster.warpwing.config.WarpWingModConfigHolder;
import de.dertoaster.warpwing.item.ItemBurningWing;
import de.dertoaster.warpwing.item.ItemBurntWing;
import de.dertoaster.warpwing.item.ItemWarpWing;
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
			.stacksTo(1)
			.setNoRepair()
			.durability(WarpWingModConfigHolder.CONFIG.wwDurability.get())
			.rarity(Rarity.RARE)
		)
	);
	
	public static final RegistryObject<Item> ITEM_BURNING_WING = ITEMS.register(
		"burning_wing", () -> new ItemBurningWing(
			new Properties()
			.stacksTo(1)
			.setNoRepair()
			.durability(WarpWingModConfigHolder.CONFIG.wwDurability.get())
			.rarity(Rarity.EPIC)
		)
	);
	
	public static final RegistryObject<Item> ITEM_BURNT_WING = ITEMS.register(
		"burnt_wing", () -> new ItemBurntWing(
			new Properties()
			.stacksTo(1)
			.setNoRepair()
			.durability(1)
			.rarity(Rarity.COMMON)
		)
	);
	//formatter:on
	
	
}
