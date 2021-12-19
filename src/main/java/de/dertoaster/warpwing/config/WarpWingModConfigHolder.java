package de.dertoaster.warpwing.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class WarpWingModConfigHolder {

	public static class ItemConfig {
		private static final int defaultDurability = 16;
		private static final int defaultUseDuration = 40;
		
		public final ConfigValue<Integer> wwDurability;
		public final ConfigValue<Integer> wwUseDuration;
		
		public ItemConfig(ForgeConfigSpec.Builder builder) {
			builder.push("warpwing-item");
			this.wwUseDuration = builder
					.comment("Time in ticks it takes to charge up the wing before using it")
					.worldRestart()
					.defineInRange("Value must be at least 1", defaultUseDuration, 1, Integer.MAX_VALUE);
			
			this.wwDurability = builder
					.comment("Durability of the warp wing item")
					.worldRestart()
					.defineInRange("Uses of the warp wing, muse be at least 0", defaultDurability, 1, Integer.MAX_VALUE);
			builder.pop();
		}
	}
	
	public static final ItemConfig ITEM_CONFIG;
	public static final ForgeConfigSpec ITEM_CONFIG_SPEC;
	
	static {
		Pair<ItemConfig, ForgeConfigSpec> itemConfigSpecPair = new ForgeConfigSpec.Builder().configure(ItemConfig::new);
		
		ITEM_CONFIG = itemConfigSpecPair.getLeft();
		ITEM_CONFIG_SPEC = itemConfigSpecPair.getRight();
	}
	
}
