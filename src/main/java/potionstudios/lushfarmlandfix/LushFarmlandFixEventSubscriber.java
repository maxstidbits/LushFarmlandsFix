package potionstudios.lushfarmlandfix;

import net.minecraft.world.item.HoeItem;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import potionstudios.lushfarmlandfix.LushFarmlandFixHelper;

@Mod.EventBusSubscriber(modid = "lushfarmlandfix", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LushFarmlandFixEventSubscriber {
    @SubscribeEvent
    public static void onBlockToolModification(BlockEvent.BlockToolModificationEvent event) {
        if (event.getToolAction() == ToolActions.HOE_TILL) {
            if (event.getHeldItemStack().getItem() instanceof HoeItem) {
                var pair = LushFarmlandFixHelper.getTillablesForge().get(event.getFinalState().getBlock());
                if (pair != null && pair.getFirst().test(event.getContext())) {
                    event.setFinalState(pair.getSecond());
                }
            }
        }
    }
}
