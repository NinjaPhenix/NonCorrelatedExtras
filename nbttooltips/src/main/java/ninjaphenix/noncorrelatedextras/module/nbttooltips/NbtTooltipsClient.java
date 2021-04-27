package ninjaphenix.noncorrelatedextras.module.nbttooltips;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class NbtTooltipsClient implements ClientModInitializer {
    public static void modifyToolTip(@NotNull final ItemStack stack, @NotNull final List<Component> tooltip) {
        if (stack.hasTag()) {
            final CompoundTag tag = stack.getTag();
            if (Screen.hasControlDown()) {
                tooltip.add(new TranslatableComponent(getTranslationId("nbt"), "").withStyle(ChatFormatting.GRAY));
                @SuppressWarnings("ConstantConditions") final String[] lines = tag.getPrettyDisplay().getString().split("\\n");
                for (String line : lines) {
                    tooltip.add(new TextComponent(line));
                }
            } else {
                final String hold = getTranslationId(Minecraft.ON_OSX ? "hold_cmd" : "hold_ctrl");
                tooltip.add(new TranslatableComponent(getTranslationId("nbt"), new TranslatableComponent(hold).withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY));
            }
        }

        final Collection<ResourceLocation> tags = ItemTags.getAllTags().getMatchingTags(stack.getItem());
        if (tags.size() == 0) {
            return;
        }
        if (Screen.hasAltDown()) {
            tooltip.add(new TranslatableComponent(getTranslationId("data"), "").withStyle(ChatFormatting.GRAY));
            tags.forEach((identifier) -> tooltip.add(new TextComponent(" #" + identifier.toString()).withStyle(ChatFormatting.DARK_GRAY)));
        } else {
            tooltip.add(new TranslatableComponent(getTranslationId("data"),
                                                  new TranslatableComponent(getTranslationId("hold_alt")).withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY));
        }
    }

    @NotNull
    private static String getTranslationId(@NotNull String string) {
        return "noncorrelatedextras.text." + string;
    }

    @Override
    public void onInitializeClient() {
        ItemTooltipCallback.EVENT.register((stack, context, list) -> {
            list.removeIf(text -> text instanceof TranslatableComponent && ((TranslatableComponent) text).getKey().equals("item.nbt_tags"));
            modifyToolTip(stack, list);
        });
    }

}
