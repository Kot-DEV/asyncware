package com.nquantum.module.render;

import com.nquantum.Asyncware;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import de.Hero.settings.Setting;

import java.util.ArrayList;

public class SwordAnimation extends Module {
    public SwordAnimation(){
        super("SwordAnimation", 0, Category.RENDER);
    }


    public static int animint;
    @Override
    public void setup(){
        ArrayList<String> anims = new ArrayList<>();
        anims.add("Swang");
        anims.add("Swong");
        anims.add("Asyncware");
        anims.add("Fan");
        anims.add("Idk");


        Asyncware.instance.settingsManager.rSetting(new Setting("Animation", this, "Swang", anims));




    }

    @EventTarget
    public void onUpdate(EventUpdate event){
        String mode = Asyncware.instance.settingsManager.getSettingByName("Animation").getValString();
        if(mode.equalsIgnoreCase("Fan")){
            animint = 1;
        }
        if(mode.equalsIgnoreCase("Swang")){
            animint = 2;
        }
    }



}
