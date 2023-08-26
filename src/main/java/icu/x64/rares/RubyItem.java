package icu.x64.rares;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RubyItem extends Item {
    public static final String ITEM_ID = "ruby";
    private static RubyItem itemInstance;
    public RubyItem(Settings settings) {
        super(settings);
    }
    public static void initialize() {
        Registry.register(Registry.ITEM, new Identifier(Rares.MOD_ID, ITEM_ID), getItemInstance());
    }

    public static RubyItem getItemInstance() {
        if (itemInstance == null) {
            itemInstance = new RubyItem(new Settings().group(ItemGroup.MISC));
        }
        return itemInstance;
    }
}
