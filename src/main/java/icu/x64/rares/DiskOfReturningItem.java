package icu.x64.rares;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class DiskOfReturningItem extends Item {
    public static final String ITEM_ID = "disk_of_returning";
    private static DiskOfReturningItem itemInstance;
    public DiskOfReturningItem(Settings settings) {
        super(settings);
    }
    public static void initialize() {
        Registry.register(Registry.ITEM, new Identifier(Rares.MOD_ID, ITEM_ID), getItemInstance());
    }

    public static DiskOfReturningItem getItemInstance() {
        if (itemInstance == null) {
            itemInstance = new DiskOfReturningItem(new Settings().group(ItemGroup.MISC).rarity(Rarity.EPIC));
        }
        return itemInstance;
    }
}
