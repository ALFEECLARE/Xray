package fr.atesab.xray.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import fr.atesab.xray.XrayMain;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(value = Block.class)
public class MixinBlock {
	@Inject(at = @At("RETURN"), method = "shouldRenderFace(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;)Z",cancellable = true)
	private static void shouldRenderFace(BlockGetter reader, BlockPos pos, BlockState currentState, BlockState neighberState, Direction face, CallbackInfoReturnable<Boolean> ci) {
		XrayMain.getMod().shouldSideBeRendered(currentState,neighberState, ci);
	}

	private MixinBlock() {
	}
}
