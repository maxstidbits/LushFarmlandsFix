package potionstudios.lushfarmlandfix.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.Util;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import potionstudios.byg.BYG;
import potionstudios.byg.common.block.BYGBlocks;
import potionstudios.byg.mixin.access.HoeItemAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Mixin(potionstudios.byg.common.BYGHoeables.class)
public abstract class MixinBYGHoeables {
    @Unique
    private static final Map<Block, Pair<Predicate<UseOnContext>, Consumer<UseOnContext>>> TILLABLES =
        Util.make(new IdentityHashMap<>(), map -> {
            map.put(BYGBlocks.LUSH_GRASS_BLOCK.get(),
                    Pair.of(HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(BYGBlocks.LUSH_FARMLAND.defaultBlockState())));
            map.put(BYGBlocks.LUSH_DIRT.get(),
                    Pair.of(HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(BYGBlocks.LUSH_FARMLAND.defaultBlockState())));
            map.put(BYGBlocks.PEAT.get(),
                    Pair.of(HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(Blocks.FARMLAND.defaultBlockState())));
        });

    @Inject(method = "tillablesBYG", at = @At("HEAD"), cancellable = true, remap = false)
    private static void injectTillablesBYG(CallbackInfo ci) {
        BYG.LOGGER.info("LushFarmlandFix: Adding tillables...");
        Map<Block, Pair<Predicate<UseOnContext>, Consumer<UseOnContext>>> tillables =
            new IdentityHashMap<>(HoeItemAccess.byg_getTILLABLES());
        tillables.putAll(TILLABLES);
        HoeItemAccess.byg_setTILLABLES(tillables);
        BYG.LOGGER.info("LushFarmlandFix: Added tillables!");
        ci.cancel();
    }
}