package net.greenjab.jabsfixedcombat.client.mixin;

import net.greenjab.jabsfixedcombat.hud.HUDOverlayHandler;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {

    @Inject(slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=food")),
             at = @At(value = "net.greenjab.jabsfixedcombat.mixin.BeforeInc", args = "intValue=-10", ordinal = 0),
             method = "extractPlayerHealth")
    private void renderFoodPost(GuiGraphicsExtractor graphics, CallbackInfo ci) {
         HUDOverlayHandler.onRender(graphics);
     }

    @Redirect(method = "extractItemHotbar", at = @At(value = "INVOKE",
                                                     target = "Lnet/minecraft/world/entity/player/Player;getOffhandItem()Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack noNetheriteFix(Player instance) {return instance.equipment.get(EquipmentSlot.OFFHAND); }

}
