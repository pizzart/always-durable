package com.pibbium.alwaysdurable.mixin;

import com.pibbium.alwaysdurable.config.AlwaysDurableConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;

@Mixin(Item.class)
@Environment(EnvType.CLIENT)
public class ItemMixin {
	@Inject(method = "getItemBarColor(Lnet/minecraft/item/ItemStack;)I", at = @At(value = "RETURN"), cancellable = true)
	private void injected(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
		if (AlwaysDurableConfig.changeDurabilityBar) {
			float f = Math.max(0F, ((float) ((Item) (Object) this).getMaxDamage() - (float) stack.getDamage()) / (float) ((Item) (Object) this).getMaxDamage());

			Color startColor = Color.decode(AlwaysDurableConfig.start);
			Color endColor = Color.decode(AlwaysDurableConfig.end);

			float rs = (float) startColor.getRed() / 255;
			float gs = (float) startColor.getGreen() / 255;
			float bs = (float) startColor.getBlue() / 255;

			float re = (float) endColor.getRed() / 255;
			float ge = (float) endColor.getGreen() / 255;
			float be = (float) endColor.getBlue() / 255;

			float r = MathHelper.lerp(f, re, rs);
			float g = MathHelper.lerp(f, ge, gs);
			float b = MathHelper.lerp(f, be, bs);

			cir.setReturnValue(MathHelper.packRgb(r, g, b));
		}
	}
}