package fr.atesab.xray.mixins;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import fr.atesab.xray.XrayMain;
import net.minecraft.client.renderer.block.ModelBlockRenderer;

@Mixin(value = ModelBlockRenderer.class)
public class MixinMinecraftClient {
	@Final
	@Shadow
    private boolean ambientOcclusion;
	
	//@Inject(at = @At(value = "HEAD"), method = "useAmbientOcclusion()Z", cancellable = true)
	@Redirect(method = "tesselateBlock(Lnet/minecraft/client/renderer/block/BlockQuadOutput;FFFLnet/minecraft/client/renderer/block/BlockAndTintGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/client/renderer/block/dispatch/BlockStateModel;J)V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/block/ModelBlockRenderer;ambientOcclusion:Z", opcode = Opcodes.GETFIELD))
	private boolean isAmbientOcclusionEnabled(ModelBlockRenderer renderer) {
		if (XrayMain.getMod().isInternalFullbrightEnable()) {
			return false;
		}
		return this.ambientOcclusion;
	}

	private MixinMinecraftClient() {
	}
}
