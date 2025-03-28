package potionstudios.lushfarmlandfix;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import potionstudios.lushfarmlandfix.LushFarmlandFixHelper;

@Mod("lushfarmlandfix")
public class LushFarmlandFixMod {
    public LushFarmlandFixMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onCommonSetup);
    }

    @SubscribeEvent
    public void onCommonSetup(FMLCommonSetupEvent event) {
        // Initialize the mapping during common setup.
        LushFarmlandFixHelper.initialize(event);
    }
}
