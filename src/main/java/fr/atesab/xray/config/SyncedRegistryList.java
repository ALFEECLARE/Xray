package fr.atesab.xray.config;

import fr.atesab.xray.utils.TagOnWriteList;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SyncedRegistryList<R> extends TagOnWriteList<String> implements Cloneable {

    private List<R> objects = new ArrayList<>();
    private final IForgeRegistry<R> registry;
    private boolean synced = false;

    public SyncedRegistryList(IForgeRegistry<R> registry) {
        super(new ArrayList<>());
        this.registry = registry;
    }

    protected SyncedRegistryList(SyncedRegistryList<R> other) {
        super(new ArrayList<>(other));
        registry = other.registry;
        if (other.synced) {
            this.objects.addAll(other.objects);
            synced = true;
        }
    }

    public SyncedRegistryList(R[] objects, IForgeRegistry<R> registry) {
        this(Arrays.asList(objects), registry);
    }

    public SyncedRegistryList(List<R> objects, IForgeRegistry<R> registry) {
        super(new ArrayList<>());
        this.registry = registry;
        setObjects(objects);
    }

    public List<R> getObjects() {
        if (!synced)
            sync();
        return objects;
    }

    public void setObjects(List<? extends R> objects) {
        setTagEnabled(false);
        this.objects = new ArrayList<>(objects);
        clear();
        objects.stream().filter(Objects::nonNull).map(registry::getKey).map(Object::toString).forEach(this::add);
        setTagEnabled(true);
        synced = true;
    }

    public SyncedRegistryList<R> sync() {
        objects.clear();
        stream().map(ResourceLocation::new).map(registry::getValue).filter(Objects::nonNull).forEach(objects::add);
        removeUpdated();
        synced = true;
        return this;
    }

    @Override
    protected void onUpdate() {
        if (synced)
            sync();
    }

    @Override
    public SyncedRegistryList<R> clone() {
        return new SyncedRegistryList<R>(this);
    }
}