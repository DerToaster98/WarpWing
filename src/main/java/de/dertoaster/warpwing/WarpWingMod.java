package de.dertoaster.warpwing;

import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.dertoaster.warpwing.config.WarpWingModConfigHolder;
import de.dertoaster.warpwing.init.WWItems;
import de.dertoaster.warpwing.init.WWSounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(WarpWingMod.MODID)
@EventBusSubscriber(modid = WarpWingMod.MODID, bus = Bus.MOD)
public class WarpWingMod {

	public static final String MODID = "warpwing";
	// Directly reference a log4j logger.
	private static final Logger LOGGER = LogManager.getLogger();

	public WarpWingMod() {
		//Register config
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, WarpWingModConfigHolder.CONFIG_SPEC, "ww-config.toml");
		WarpWingModConfigHolder.loadConfig(WarpWingModConfigHolder.CONFIG_SPEC,
				FMLPaths.CONFIGDIR.get().resolve("ww-config.toml").toString());
		
		IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();
		
		MinecraftForge.EVENT_BUS.register(this);
		WWItems.ITEMS.register(modbus);
		WWSounds.SOUNDS.register(modbus);
	}

	public static ResourceLocation prefix(String value) {
		return new ResourceLocation(MODID, value.toLowerCase(Locale.ROOT));
	}

}
