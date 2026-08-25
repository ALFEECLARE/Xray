package fr.atesab.xray.view;

import fr.atesab.xray.color.EnumElement;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

public enum ViewMode implements EnumElement {
    /**
     * Default mode, like in Xray and Redstone mode
     */
    EXCLUSIVE("x13.mod.mode.view.exclusive", Blocks.DIAMOND_ORE, (il, currentState, neighberState) -> il),
    /**
     * Inclusive mode, like in Cave Mode
     */
    INCLUSIVE("x13.mod.mode.view.inclusive", Blocks.STONE, (il, currentState, neighberState) -> !il
            && neighberState.isAir());

    private final Viewer viewer;
    private final Component title;
    private final ItemLike icon;

    ViewMode(String translation, ItemLike icon, Viewer viewer) {
        this.viewer = viewer;
        this.icon = icon;
        this.title = Component.translatable(translation);
    }

    @Override
    public Component getTitle() {
        return title;
    }

    public Viewer getViewer() {
        return viewer;
    }

    @Override
    public ItemLike getIcon() {
        return icon;
    }
}
