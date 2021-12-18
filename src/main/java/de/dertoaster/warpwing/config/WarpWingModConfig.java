package de.dertoaster.warpwing.config;

import de.dertoaster.warpwing.WarpWingMod;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = WarpWingMod.MODID)
public class WarpWingModConfig implements ConfigData {

	public int useDuration = 40;
	public int durability = 16;
	
}
