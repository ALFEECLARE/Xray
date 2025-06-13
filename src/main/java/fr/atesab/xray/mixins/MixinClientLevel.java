package fr.atesab.xray.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import fr.atesab.xray.XrayMain;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.ARGB;
import net.minecraft.world.phys.Vec3;

@Mixin(value = ClientLevel.class)
public class MixinClientLevel {
	@Inject(method = "getSkyColor(Lnet/minecraft/world/phys/Vec3;F)I", at = @At("HEAD"), cancellable = true)
	public void getSkyColorMixin(Vec3 pPos, float pPartialTick, CallbackInfoReturnable<Integer> info) {
		if (XrayMain.getMod().isBlueBlueSkyEnable()) {
			info.setReturnValue(ARGB.color(1,167,121));
		}
	}
	
	private MixinClientLevel() {
	}
}
