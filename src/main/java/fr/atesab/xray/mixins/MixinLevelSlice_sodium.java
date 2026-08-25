package fr.atesab.xray.mixins;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import fr.atesab.xray.XrayMain;
import net.caffeinemc.mods.sodium.client.world.LevelSlice;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LightLayer;

@Mixin(value = LevelSlice.class)
public class MixinLevelSlice_sodium {
	@Final
	@Shadow
	boolean ambientOcclusion;

	@ModifyReturnValue(method = "useAmbientOcclusion()Z", at = @At("RETURN"))
	private boolean overwriteAmbientAcclusion(boolean original) {
		if (XrayMain.getMod().isInternalFullbrightEnable()) {
			return false;
		}
		return this.ambientOcclusion;
	}

	@Inject(method = "getBrightness", at = @At("HEAD"), cancellable = true)
	private void overwriteBrightness(LightLayer type, BlockPos pos, CallbackInfoReturnable<Integer> ci) {
		if (type.equals(LightLayer.SKY)) {
			if (XrayMain.getMod().isInternalFullbrightEnable()) {
				ci.setReturnValue(15);
			}
		}
	}
}
