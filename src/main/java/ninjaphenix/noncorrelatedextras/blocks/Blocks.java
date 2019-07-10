package ninjaphenix.noncorrelatedextras.blocks;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import ninjaphenix.noncorrelatedextras.Common;

public class Blocks
{
    public static final Blocks INSTANCE = new Blocks();

    public void initialize()
    {
        FreezerBlock freezerBlock = new FreezerBlock();
        BlockItem freezerItem = new BlockItem(freezerBlock, new Item.Settings().group(ItemGroup.DECORATIONS));
        Registry.register(Registry.BLOCK, new Identifier(Common.MOD_ID, "freezer"), freezerBlock);
        Registry.register(Registry.ITEM, new Identifier(Common.MOD_ID, "freezer"), freezerItem);
    }
}
