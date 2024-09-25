package fr.atesab.xray.screen;

import java.util.Comparator;
import java.util.stream.Stream;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;

public abstract class EntitySelector extends EnumSelector<XrayEntityMenu.EntityUnion> {

    public EntitySelector(Screen parent) {
        super(Component.translatable("x13.mod.esp.selector"), parent,
                Stream.concat(
                		BuiltInRegistries.ENTITY_TYPE.stream().map(XrayEntityMenu.EntityUnion::new),
                		BuiltInRegistries.BLOCK_ENTITY_TYPE.stream().map(XrayEntityMenu.EntityUnion::new)
                ).sorted(Comparator.comparing(XrayEntityMenu.EntityUnion::text))
        );
    }


}
