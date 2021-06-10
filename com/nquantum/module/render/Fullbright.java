package com.nquantum.module.render;

import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import org.lwjgl.input.Keyboard;

public class Fullbright extends Module {
    private float oldBrightness;

    public Fullbright() {
        super("Fullbright", Keyboard.KEY_C, Category.RENDER);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        oldBrightness = mc.gameSettings.gammaSetting;
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        mc.gameSettings.gammaSetting = 10F;
    }

    @Override
    public void onDisable() {
        super.onDisable();

        mc.gameSettings.gammaSetting = oldBrightness;
    }
}
