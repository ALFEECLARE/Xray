package fr.atesab.xray.config;

import java.util.List;
import java.util.stream.Stream;

import fr.atesab.xray.color.EntityTypeIcon;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class SyncedEntityTypeList extends SyncedRegistryList<EntityType<?>> {

    private SyncedEntityTypeList(SyncedRegistryList<EntityType<?>> other) {
        super(other);
    }

    public SyncedEntityTypeList() {
        super(BuiltInRegistries.ENTITY_TYPE);
    }

    public SyncedEntityTypeList(EntityType<?>... objects) {
        super(objects, BuiltInRegistries.ENTITY_TYPE);
    }

    public SyncedEntityTypeList(List<EntityType<?>> objects) {
        super(objects, BuiltInRegistries.ENTITY_TYPE);
    }

    public Stream<ItemLike> getIcons() {
        return getObjects().stream().map(EntityTypeIcon::getIcon);
    }

    public Stream<ItemStack> getIconsStack() {
        return getObjects().stream().map(EntityTypeIcon::getIcon).map(item -> new ItemStack(item));
    }

    @Override
    public SyncedEntityTypeList clone() {
        return new SyncedEntityTypeList(this);
    }
}