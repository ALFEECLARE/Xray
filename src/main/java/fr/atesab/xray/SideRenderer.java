package fr.atesab.xray;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.level.block.state.BlockState;

@FunctionalInterface
public interface SideRenderer {
	public void shouldSideBeRendered(BlockState currentState,BlockState neighberState, CallbackInfoReturnable<Boolean> ci);
}
