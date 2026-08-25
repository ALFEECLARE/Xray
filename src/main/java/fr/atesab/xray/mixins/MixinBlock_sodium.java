package fr.atesab.xray.mixins;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import fr.atesab.xray.XrayMain;
import net.caffeinemc.mods.sodium.client.render.model.AbstractBlockRenderContext;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(value = AbstractBlockRenderContext.class)
public class MixinBlock_sodium {
    @Final
	@Shadow
    protected BlockAndTintGetter level;

    @Final
	@Shadow
    protected BlockState state;

	@Final
	@Shadow
    protected BlockPos pos;
	
	private boolean isIgnoreCache;

	
	@Inject(at = @At("HEAD"), method = "shouldDrawSide(Lnet/minecraft/core/Direction;)Z",cancellable = true,remap=false)
	public void shouldDrawSideMixin(Direction facing, CallbackInfoReturnable<Boolean> ci) {
		BlockPos.MutableBlockPos neigberPos = new BlockPos(pos).mutable();
		isIgnoreCache = (XrayMain.getMod().shouldSideBeRendered(state, level.getBlockState(neigberPos.setWithOffset(pos, facing).immutable()), ci) != 0);
	}
	
	/*
	//@Redirect(method = "shouldDrawSide(Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Z", at = @At(value = "INVOKE", target = "Lnet/caffeinemc/mods/sodium/client/render/chunk/compile/pipeline/BlockOcclusionCache;lookup(Lnet/minecraft/world/phys/shapes/VoxelShape;Lnet/minecraft/world/phys/shapes/VoxelShape;)Z"))
	@Inject(method = "shouldDrawSide(Lnet/minecraft/core/Direction;)Z", at = @At(value = "INVOKE", target = "Lnet/caffeinemc/mods/sodium/client/render/chunk/compile/pipeline/ShapeComparisonCache;isEmptyShape(Lnet/minecraft/world/phys/shapes/VoxelShape;)Z"), cancellable = true)
	private void ignoreCache(Direction facing, CallbackInfoReturnable<Boolean> ci) {
		if (XrayMain.getMod().isInternalFullbrightEnable()) {
			ci.setReturnValue(true);
		}
	}
	*/
	
	/*
	@Redirect(method = "bufferDefaultModel()", at = @At(value = "INVOKE", target = "Lnet/caffeinemc/mods/sodium/client/render/model/MutableQuadViewImpl;setAmbientOcclusion(Lnet/minecraft/util/TriState;)V"))
	private void overwriteBrightness(MutableQuadViewImpl view, TriState tristate) {
			view.setAmbientOcclusion(TriState.TRUE);
	}
	*/
	
	private MixinBlock_sodium() {
	}
}
