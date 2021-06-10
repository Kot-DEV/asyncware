package com.nquantum.module.render;

import com.nquantum.Asyncware;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import de.Hero.settings.Setting;
import org.lwjgl.input.Keyboard;

public class ArreyList extends Module {
    public ArreyList() {
        super("ArrayList", Keyboard.KEY_NONE, Category.RENDER);
    }

    @Override
    public void setup() {
        java.util.ArrayList<String> options = new java.util.ArrayList<>();
        java.util.ArrayList<String> designs = new java.util.ArrayList<>();

        designs.add("Weird");
        designs.add("Gamesense");
        options.add("Rect");
        options.add("YOffset");
        options.add("Custom Font");
        Asyncware.instance.settingsManager.rSetting(new Setting("Rect", this, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("YOffset", this, 10, 2, 40, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("Custom Font", this, true));


        Asyncware.instance.settingsManager.rSetting(new Setting("Array Design", this, "New", designs));




    }

}
