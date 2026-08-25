package fr.atesab.xray.color;

import net.minecraft.network.chat.Component;
import net.minecraft.world.level.ItemLike;

public abstract class AbstractEnumElement implements EnumElement {
    private ItemLike icon;
    private Component title;

    public AbstractEnumElement(ItemLike icon, Component title) {
        this.icon = icon;
        this.title = title;
    }

    @Override
    public ItemLike getIcon() {
        return icon;
    }

    @Override
    public Component getTitle() {
        return title;
    }

}
