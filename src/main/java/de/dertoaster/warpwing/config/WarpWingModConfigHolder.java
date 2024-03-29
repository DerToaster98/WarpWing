package de.dertoaster.warpwing.config;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.google.common.base.Predicates;

import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class WarpWingModConfigHolder {
	
	public static class ItemConfig {
		private static final int defaultDurability = 16;
		private static final int defaultUseDuration = 40;
		private static final int defaultMinRTPDistance = 32;
		private static final int defaultMaxRTPDistance = 128;
		private static final List<String> defaultHotDimensions = List.of(BuiltinDimensionTypes.NETHER_EFFECTS.toString());
		private static final List<String> defaultEnderDimensions = List.of(BuiltinDimensionTypes.END_EFFECTS.toString());
		
		public final ConfigValue<Integer> wwDurability;
		public final ConfigValue<Integer> wwUseDuration;
		public final ConfigValue<Integer> wwRandomTPMinDistance;
		public final ConfigValue<Integer> wwRandomTPMaxDistance;
		public final ConfigValue<List<? extends String>> wwHotDimensions;
		public final ConfigValue<List<? extends String>> wwEnderDimensions;
		
		public ItemConfig(ForgeConfigSpec.Builder builder) {
			builder.push("warpwing-item");
			this.wwUseDuration = builder
					.comment("Time in ticks it takes to charge up the wing before using it")
					.worldRestart()
					.defineInRange("useDuration", defaultUseDuration, 1, Integer.MAX_VALUE);
			
			this.wwDurability = builder
					.comment("Durability of the warp wing item")
					.worldRestart()
					.defineInRange("durability", defaultDurability, 1, Integer.MAX_VALUE);
			
			this.wwRandomTPMinDistance = builder
					.comment("Minimum distance of random teleport")
					.defineInRange("randomTPMinDistance", defaultMinRTPDistance, 0, Integer.MAX_VALUE);
			
			this.wwRandomTPMaxDistance = builder
					.comment("Maximum distance of random teleport")
					.defineInRange("randomTPMaxDistance", defaultMaxRTPDistance, 0, Integer.MAX_VALUE);
			
			this.wwHotDimensions = builder
					.comment("Dimensions in which the wing will burn")
					.defineList("hotDimensions", () -> defaultHotDimensions, Predicates.alwaysTrue());
			
			this.wwEnderDimensions = builder
					.comment("Dimensions in which the wing will teleport to a random place")
					.defineList("enderDimensions", () -> defaultEnderDimensions, Predicates.alwaysTrue());
			
			builder.pop();
		}
	}
	
	public static final ItemConfig CONFIG;
	public static final ForgeConfigSpec CONFIG_SPEC;
	static {
		final Pair<ItemConfig, ForgeConfigSpec> serverSpecPair = new ForgeConfigSpec.Builder().configure(ItemConfig::new);
		CONFIG = serverSpecPair.getLeft();
		CONFIG_SPEC = serverSpecPair.getRight();
	}
	
	public static void loadConfig(ForgeConfigSpec config, String path) {
		final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave()
				.writingMode(WritingMode.REPLACE).build();
		file.load();
		config.setConfig(file);
	}
	
}
