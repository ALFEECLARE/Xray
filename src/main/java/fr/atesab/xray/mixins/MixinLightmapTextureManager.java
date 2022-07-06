package fr.atesab.xray.mixins;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import fr.atesab.xray.XrayMain;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.network.chat.Component;

@Mixin(value = LightTexture.class)
public class MixinLightmapTextureManager {

	private final OptionInstance<Double> gammaBypass = new OptionInstance<>("options.gamma", OptionInstance.noTooltip(), (optionText, value) -> Component.empty(), OptionInstance.UnitDouble.INSTANCE.xmap(
			d -> (double) XrayMain.getMod().getInternalFullbrightState(), d -> 1
	), 0.5, value -> {});

	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Options;gamma()Lnet/minecraft/client/OptionInstance;", opcode = Opcodes.H_INVOKEVIRTUAL), method = "updateLightTexture(F)V")
	private OptionInstance<Double> getFieldValue(Options options) {
		if (XrayMain.getMod().isInternalFullbrightEnable()) {
			gammaBypass.set(1.0);
			return gammaBypass;
		} else {
			return options.gamma();
		}
	}

}
