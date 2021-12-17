package de.dertoaster.warpwing.init;

import de.dertoaster.warpwing.WarpWingMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.event.RegistryEvent;

public class WWSounds {

	public static final SoundEvent ITEM_WARP_WING_WOOSH = createEvent("item.warpwing.woosh");
	
	private static SoundEvent createEvent(String sound) {
		ResourceLocation name = WarpWingMod.prefix(sound);
		return new SoundEvent(name).setRegistryName(name);
	
	}
	
	public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
		event.getRegistry().registerAll(
				ITEM_WARP_WING_WOOSH
		);
	}
	
}
