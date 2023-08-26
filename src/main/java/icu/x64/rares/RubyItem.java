package icu.x64.rares;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class RubyItem extends Item {
    public static final String ITEM_ID = "ruby";
    private static RubyItem itemInstance;
    public RubyItem(Settings settings) {
        super(settings);
    }
    public static void initialize() {
        Registry.register(Registries.ITEM, new Identifier(Rares.MOD_ID, ITEM_ID), getItemInstance());
    }

    public static RubyItem getItemInstance() {
        if (itemInstance == null) {
            itemInstance = new RubyItem(new FabricItemSettings());
        }
        return itemInstance;
    }
}
