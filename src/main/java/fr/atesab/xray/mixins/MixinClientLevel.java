package fr.atesab.xray.mixins;

import org.joml.Matrix4fc;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.framegraph.FrameGraphBuilder;

import fr.atesab.xray.XrayMain;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.util.ARGB;

@Mixin(value = LevelRenderer.class)
public class MixinClientLevel {
	@Final
	@Shadow
	LevelRenderState levelRenderState;
	
    //@Inject(method = "getSkyColor(Lnet/minecraft/world/phys/Vec3;F)I", at = @At("HEAD"), cancellable = true)
	//@Redirect(method = "addSkyPass(Lcom/mojang/blaze3d/framegraph/FrameGraphBuilder;Lnet/minecraft/client/renderer/state/level/CameraRenderState;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lorg/joml/Matrix4fc;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/state/level/SkyRenderState;skyColor:I", opcode = Opcodes.GETFIELD))
	@Inject(method = "addSkyPass(Lcom/mojang/blaze3d/framegraph/FrameGraphBuilder;Lnet/minecraft/client/renderer/state/level/CameraRenderState;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lorg/joml/Matrix4fc;)V", at = @At("HEAD"))
	private void getSkyColorMixin(FrameGraphBuilder frame, CameraRenderState cameraState, GpuBufferSlice skyFog, Matrix4fc modelViewMatrix, CallbackInfo callback) {
		if (XrayMain.getMod().isBlueBlueSkyEnable()) {
			this.levelRenderState.skyRenderState.skyColor = ARGB.color(1,121,167);
		}
		return;
	}
	
	private MixinClientLevel() {
	}
}
