package potionstudios.lushfarmlandfix;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.Util;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import potionstudios.byg.common.block.BYGBlocks;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Predicate;

public class LushFarmlandFixHelper {
    // The mapping is initially null.
    private static Map<Block, Pair<Predicate<UseOnContext>, BlockState>> TILLABLES_FORGE = null;

    // Call this method during common setup to initialize the mapping.
    public static void initialize(FMLCommonSetupEvent event) {        
        if (TILLABLES_FORGE == null) {
            TILLABLES_FORGE = Util.make(new IdentityHashMap<>(), map -> {
                map.put(BYGBlocks.LUSH_GRASS_BLOCK.get(),
                        Pair.of(HoeItem::onlyIfAirAbove, BYGBlocks.LUSH_FARMLAND.defaultBlockState()));
                map.put(BYGBlocks.LUSH_DIRT.get(),
                        Pair.of(HoeItem::onlyIfAirAbove, BYGBlocks.LUSH_FARMLAND.defaultBlockState()));
                map.put(BYGBlocks.PEAT.get(),
                        Pair.of(HoeItem::onlyIfAirAbove, Blocks.FARMLAND.defaultBlockState()));
            });
        }
    }

    // Accessor; if called too early (should not happen normally), it initializes the mapping as a fallback.
    public static Map<Block, Pair<Predicate<UseOnContext>, BlockState>> getTillablesForge() {
        if (TILLABLES_FORGE == null) {
            // As a fallback, initialize now.
            TILLABLES_FORGE = Util.make(new IdentityHashMap<>(), map -> {
                map.put(BYGBlocks.LUSH_GRASS_BLOCK.get(),
                        Pair.of(HoeItem::onlyIfAirAbove, BYGBlocks.LUSH_FARMLAND.defaultBlockState()));
                map.put(BYGBlocks.LUSH_DIRT.get(),
                        Pair.of(HoeItem::onlyIfAirAbove, BYGBlocks.LUSH_FARMLAND.defaultBlockState()));
                map.put(BYGBlocks.PEAT.get(),
                        Pair.of(HoeItem::onlyIfAirAbove, Blocks.FARMLAND.defaultBlockState()));
            });
        }
        return TILLABLES_FORGE;
    }
}