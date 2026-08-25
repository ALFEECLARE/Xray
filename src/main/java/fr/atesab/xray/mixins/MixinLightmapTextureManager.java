package fr.atesab.xray.mixins;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import fr.atesab.xray.XrayMain;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.renderer.LightmapRenderStateExtractor;

@Mixin(value = LightmapRenderStateExtractor.class)
public class MixinLightmapTextureManager {

	//@Redirect(method = "render(Lnet/minecraft/client/renderer/state/LightmapRenderState;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/state/LightmapRenderState;brightness:F", opcode = Opcodes.GETFIELD))
	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Options;gamma()Lnet/minecraft/client/OptionInstance;", opcode = Opcodes.INVOKEVIRTUAL), method = "extract(Lnet/minecraft/client/renderer/state/LightmapRenderState;F)V")
	private OptionInstance<Double> getFieldValue(Options options) {
		if (XrayMain.getMod().isInternalFullbrightEnable()) {
			return XrayMain.getMod().getGammaBypass();
		} else {
			return options.gamma();
		}
	}
}
