package icu.x64.rares;

import net.fabricmc.fabric.api.item.v1.EquipmentSlotProvider;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Wearable;


public class PartyHatItem extends Item implements Wearable {
    private static class PartyHatEquipmentSlotProvider implements EquipmentSlotProvider {
        public PartyHatEquipmentSlotProvider() {}
        @Override
        public EquipmentSlot getPreferredEquipmentSlot(ItemStack stack) {
            return EquipmentSlot.HEAD;
        }
    }
    public PartyHatItem() {
        super(new FabricItemSettings().equipmentSlot(new PartyHatEquipmentSlotProvider()));
    }
}