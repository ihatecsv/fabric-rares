package icu.x64.rares;

import net.fabricmc.fabric.api.item.v1.EquipmentSlotProvider;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Wearable;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;


public class PartyHatItem extends Item implements Wearable {
    private static final String COLOR_NBT_KEY = "Color";
    private boolean temporary;
    private static class PartyHatEquipmentSlotProvider implements EquipmentSlotProvider {
        public PartyHatEquipmentSlotProvider() {}
        @Override
        public EquipmentSlot getPreferredEquipmentSlot(ItemStack stack) {
            return EquipmentSlot.HEAD;
        }
    }
    public PartyHatItem(boolean temporary) {
        super(new FabricItemSettings().equipmentSlot(new PartyHatEquipmentSlotProvider()));
        this.temporary = temporary;
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        if (temporary) {
            tooltip.add(Text.translatable("desc.rares.temporary").formatted(Formatting.GRAY));
        }
    }
    public static void setColor(ItemStack stack, Formatting color) {
        stack.getOrCreateNbt().putString(COLOR_NBT_KEY, color.getName());
    }
    public static Formatting getColor(ItemStack stack) {
        return Formatting.byName(stack.getOrCreateNbt().getString(COLOR_NBT_KEY));
    }
}