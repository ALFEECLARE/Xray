package fr.atesab.xray.view;

import net.minecraft.world.level.block.state.BlockState;

@FunctionalInterface
public interface Viewer {
    public boolean shouldRenderSide(boolean blockInList, BlockState currentState, BlockState neighberState);
}
