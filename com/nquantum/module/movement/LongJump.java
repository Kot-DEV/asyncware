package com.nquantum.module.movement;

import com.nquantum.Asyncware;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventPostMotionUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import de.Hero.settings.Setting;
import net.minecraft.potion.Potion;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class LongJump extends Module {
    public LongJump() {
        super("LongJump", Keyboard.KEY_L, Category.MOVEMENT);
    }


    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Y-Port");
        options.add("Bhop");
       // Asyncware.instance.settingsManager.rSetting(new Setting("Speed", this, "Y-Port", options));
        Asyncware.instance.settingsManager.rSetting(new Setting("LongJump Speed", this, 1.500D, 0.001D, 10.000D, false));
        // Asyncware.instance.settingsManager.rSetting(new Setting("Speed Mode", this, "Bhop", options));


    }

    @EventTarget
    public void onPost(EventPostMotionUpdate event) {

        double speed = Asyncware.instance.settingsManager.getSettingByName("LongJump Speed").getValDouble();
        if((mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown()) && mc.gameSettings.keyBindJump.isKeyDown()) {
            float dir = mc.thePlayer.rotationYaw + ((mc.thePlayer.moveForward < 0) ? 180 : 0) + ((mc.thePlayer.moveStrafing > 0) ? (-90F * ((mc.thePlayer.moveForward < 0) ? -.5F : ((mc.thePlayer.moveForward > 0) ? .4F : 1F))) : 0);
            float xDir = (float)Math.cos((dir + 90F) * Math.PI / 180);
            float zDir = (float)Math.sin((dir + 90F) * Math.PI / 180);
            if(mc.thePlayer.isCollidedVertically && (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown()) && mc.gameSettings.keyBindJump.isKeyDown()) {
                mc.thePlayer.motionX = xDir * .79F;
                mc.thePlayer.motionZ = zDir * .79F;
            }
            if(mc.thePlayer.motionY == .33319999363422365 && (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown())) {
                if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                    mc.thePlayer.motionX = xDir * (float) speed;
                    mc.thePlayer.motionZ = zDir * (float) speed;
                } else {
                    mc.thePlayer.motionX = xDir * (float) speed;
                    mc.thePlayer.motionZ = zDir * (float) speed;
                }
            }
        }
    }
}
