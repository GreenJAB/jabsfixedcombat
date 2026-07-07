package net.greenjab.jabsfixedcombat.mixin;

import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Items.class)
public abstract class ItemsMixin {

    @ModifyArgs(method="<clinit>", at = @At( value = "INVOKE", target = "Lnet/minecraft/world/item/Item$Properties;spear(Lnet/minecraft/world/item/ToolMaterial;FFFFFFFFF)Lnet/minecraft/world/item/Item$Properties;"))
    private static void holdSpearsOutForever(Args args) {
        args.set(4, (float)args.get(4)+32000);
        args.set(6, (float)args.get(6)+32000);
        args.set(8, (float)args.get(8)+32000);
    }
}
