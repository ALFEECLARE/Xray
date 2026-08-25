package fr.atesab.xray.color;

import net.minecraft.network.chat.Component;
import net.minecraft.world.level.ItemLike;

public interface EnumElement {
	ItemLike getIcon();

    Component getTitle();
}
