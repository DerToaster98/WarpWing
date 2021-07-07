package de.dertoaster.warpwing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.dertoaster.warpwing.init.WWItems;
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
		
		MinecraftForge.EVENT_BUS.register(this);
		WWItems.ITEMS.register(modbus);
		
	}

}
