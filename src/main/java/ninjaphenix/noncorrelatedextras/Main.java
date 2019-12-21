package ninjaphenix.noncorrelatedextras;

import com.google.common.collect.Sets;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import ninjaphenix.noncorrelatedextras.blocks.BreakerBlock;
import ninjaphenix.noncorrelatedextras.blocks.entities.BreakerBlockEntity;
import ninjaphenix.noncorrelatedextras.items.MagnetItem;

public class Main implements ModInitializer
{
    public static final Main INSTANCE = new Main();
    public static final BlockEntityType<?> BREAKER_BE_TYPE;

    private Main() {}

    static
    {
        BreakerBlock breaker = new BreakerBlock();
        BREAKER_BE_TYPE = new BlockEntityType<>(BreakerBlockEntity::new, Sets.newHashSet(breaker), null);
        Registry.register(Registry.BLOCK_ENTITY, new Identifier("noncorrelatedextras", "block_breaker"), BREAKER_BE_TYPE);
        Item breakerItem = new BlockItem(breaker, new Item.Settings().group(ItemGroup.REDSTONE));
        Registry.register(Registry.BLOCK, new Identifier("noncorrelatedextras", "block_breaker"), breaker);
        Registry.register(Registry.ITEM, new Identifier("noncorrelatedextras", "block_breaker"), breakerItem);
        MagnetItem magnetItem = new MagnetItem(new Item.Settings().group(ItemGroup.TOOLS).maxCount(1));
        Registry.register(Registry.ITEM, new Identifier("noncorrelatedextras", "magnet"), magnetItem);
    }

    @Override
    public void onInitialize()
    {

    }
}
