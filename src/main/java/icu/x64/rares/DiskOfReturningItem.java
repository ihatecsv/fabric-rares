package icu.x64.rares;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class DiskOfReturningItem extends Item {
    public static final String ITEM_ID = "disk_of_returning";
    private static DiskOfReturningItem itemInstance;
    public DiskOfReturningItem(Settings settings) {
        super(settings);
    }
    public static void initialize() {
        Registry.register(Registries.ITEM, new Identifier(Rares.MOD_ID, ITEM_ID), getItemInstance());
    }

    public static DiskOfReturningItem getItemInstance() {
        if (itemInstance == null) {
            itemInstance = new DiskOfReturningItem(new Settings().rarity(Rarity.EPIC));
        }
        return itemInstance;
    }
}
