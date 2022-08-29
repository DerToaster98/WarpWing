package de.dertoaster.warpwing.init;

import de.dertoaster.warpwing.WarpWingMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WWSounds {

	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, WarpWingMod.MODID);

	// public static final SoundEvent ITEM_WARP_WING_WOOSH = createEvent("item.warpwing.woosh");

	// formatter:off
	public static final RegistryObject<SoundEvent> ITEM_WARP_WING_WOOSH = SOUNDS.register("item.warpwing.woosh", () -> new SoundEvent(WarpWingMod.prefix("item.warpwing.woosh")));
	// formatter:on

}
