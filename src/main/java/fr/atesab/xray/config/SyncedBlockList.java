package fr.atesab.xray.config;

import java.util.List;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;

public class SyncedBlockList extends SyncedRegistryList<Block> {

    private SyncedBlockList(SyncedRegistryList<Block> other) {
        super(other);
    }

    public SyncedBlockList() {
        super(BuiltInRegistries.BLOCK);
    }

    public SyncedBlockList(Block... objects) {
        super(objects, BuiltInRegistries.BLOCK);
    }

    public SyncedBlockList(List<Block> objects) {
        super(objects, BuiltInRegistries.BLOCK);
    }

    @Override
    public SyncedBlockList clone() {
        return new SyncedBlockList(this);
    }
}