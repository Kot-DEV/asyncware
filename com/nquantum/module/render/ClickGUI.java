package com.nquantum.module.render;

import com.nquantum.Asyncware;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import de.Hero.settings.Setting;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class ClickGUI extends Module {
    public ClickGUI() {
        super("ClickGUI", Keyboard.KEY_RSHIFT, Category.RENDER);
    }

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("New");
        options.add("JellyLike");
        Asyncware.instance.settingsManager.rSetting(new Setting("Design", this, "New", options));
        Asyncware.instance.settingsManager.rSetting(new Setting("Sound", this, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("GuiRed", this, 25, 0, 255, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("GuiGreen", this, 255, 0, 255, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("GuiBlue", this, 25, 0, 255, true));
    }

    @Override
    public void onEnable() {
        super.onEnable();

        mc.displayGuiScreen(Asyncware.instance.clickGui);
        toggle();
    }
}
