package de.dertoaster.warpwing.init;

import de.dertoaster.warpwing.WarpWingMod;
import de.dertoaster.warpwing.objects.items.ItemWarpWing;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class WWItems {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WarpWingMod.MODID);
	
	public static final RegistryObject<Item> ITEM_WARP_WING = ITEMS.register("warp_wing", () -> new ItemWarpWing(new Properties().tab(ItemGroup.TAB_TRANSPORTATION).stacksTo(1).setNoRepair().durability(16).rarity(Rarity.RARE)));
}
