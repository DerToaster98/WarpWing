package de.dertoaster.warpwing.item;

import java.util.List;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemLore extends Item implements IRegistryNameProvider {

	public ItemLore(Properties itemProperties) {
		super(itemProperties);
	}
	
	@OnlyIn(Dist.CLIENT)
	public static boolean isLShiftPressed() {
		return GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_PRESS;
	}
	
	@OnlyIn(Dist.CLIENT)
	public static boolean isRShiftPressed() {
		return GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_RIGHT_SHIFT) == GLFW.GLFW_PRESS;
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		if(this.hasLore(stack)) {
			addHoverTextLogic(tooltip, flagIn, this.getRegistryName().getPath(), this.getRegistryName().getNamespace());
		} else {
			super.appendHoverText(stack, worldIn, tooltip, flagIn);
		}
	}
	
	public static void addHoverTextLogic(List<Component> tooltip, TooltipFlag flagIn, String registryNamePath, String registryNameKey) {
		if (isLShiftPressed() || isRShiftPressed()) {
			tooltip.add(Component.translatable("item." + registryNameKey + "." + registryNamePath + ".tooltip"));
		} else {
			tooltip.add(Component.translatable("item." + registryNameKey + ".tooltip.hold_shift"));
		}
	}
	
	public static void addHoverTextLogic(Item item, List<Component> tooltip, TooltipFlag flagIn) {
		addHoverTextLogic(tooltip, flagIn, IRegistryNameProvider.getRegistryNameOf(item).getPath(), IRegistryNameProvider.getRegistryNameOf(item).getNamespace());
	}
	
	public boolean hasLore(ItemStack stack) {
		return true;
	}

}
