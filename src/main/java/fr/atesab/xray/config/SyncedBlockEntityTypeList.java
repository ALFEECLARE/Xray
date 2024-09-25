package fr.atesab.xray.config;

import java.util.List;
import java.util.stream.Stream;

import fr.atesab.xray.color.BlockEntityTypeIcon;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class SyncedBlockEntityTypeList extends SyncedRegistryList<BlockEntityType<?>> {

    private SyncedBlockEntityTypeList(SyncedRegistryList<BlockEntityType<?>> other) {
        super(other);
    }

    public SyncedBlockEntityTypeList() {
        super(BuiltInRegistries.BLOCK_ENTITY_TYPE);
    }

    public SyncedBlockEntityTypeList(BlockEntityType<?>... objects) {
        super(objects, BuiltInRegistries.BLOCK_ENTITY_TYPE);
    }

    public SyncedBlockEntityTypeList(List<BlockEntityType<?>> objects) {
        super(objects, BuiltInRegistries.BLOCK_ENTITY_TYPE);
    }

    public Stream<ItemStack> getIcons() {
        return getObjects().stream().map(BlockEntityTypeIcon::getIcon);
    }

    @Override
    public SyncedBlockEntityTypeList clone() {
        return new SyncedBlockEntityTypeList(this);
    }
}