package de.dertoaster.warpwing;

import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.dertoaster.warpwing.config.WarpWingModConfig;
import de.dertoaster.warpwing.init.WWItems;
import de.dertoaster.warpwing.init.WWSounds;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("warpwing")
public class WarpWingMod {

	public static final String MODID = "warpwing";
	// Directly reference a log4j logger.
	private static final Logger LOGGER = LogManager.getLogger();

	public WarpWingMod() {
		IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();
		
		AutoConfig.register(WarpWingModConfig.class, Toml4jConfigSerializer::new);
		
		MinecraftForge.EVENT_BUS.register(this);
		WWItems.ITEMS.register(modbus);
		modbus.addGenericListener(SoundEvent.class, WWSounds::registerSounds);
	}

	public static ResourceLocation prefix(String value) {
		return new ResourceLocation(MODID, value.toLowerCase(Locale.ROOT));
	}
	
	public static WarpWingModConfig getConfig() {
		return AutoConfig.getConfigHolder(WarpWingModConfig.class).getConfig();
	}

}
