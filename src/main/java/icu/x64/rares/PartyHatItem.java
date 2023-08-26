package icu.x64.rares;

import net.fabricmc.fabric.api.item.v1.EquipmentSlotProvider;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Equipment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Wearable;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;


public class PartyHatItem extends Item implements Equipment {
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

    @Override
    public EquipmentSlot getSlotType(){
        return EquipmentSlot.HEAD;
    }
}