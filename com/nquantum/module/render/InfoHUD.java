package com.nquantum.module.render;

import cf.nquan.util.Colors;
import cf.nquan.util.GuiUtil;
import cf.nquan.util.RenderUtil;
import cf.nquan.util.Time;
import com.nquantum.Asyncware;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventRenderUI;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.world.MinecraftException;
import org.lwjgl.opengl.GL11;

import javax.sound.sampled.Line;
import java.awt.*;
import java.util.ArrayList;

public class InfoHUD extends Module {

    static Minecraft mc = Minecraft.getMinecraft();

    public InfoHUD(){
        super("InfoHUD", 0, Category.RENDER);
    }

    public void setup() {
         ArrayList<String> options = new ArrayList<>();
         options.add("Classic");
         options.add("CatSense");
         options.add("CatSense CSGO");
         options.add("Asyncware");

        Asyncware.instance.settingsManager.rSetting(new Setting("InfoHUD Mode", this, "Classic", options));

    }



    @EventTarget
    public void onRenderUI(EventRenderUI e){
        ScaledResolution sr = new ScaledResolution(mc);
        float bps = Math.round(mc.thePlayer.getDistance(mc.thePlayer.lastTickPosX, mc.thePlayer.posY, mc.thePlayer.lastTickPosZ) * 200) / 10f;

        String mode = Asyncware.instance.settingsManager.getSettingByName("InfoHUD Mode").getValString();


        if(mode.equalsIgnoreCase("Classic")){
            Asyncware.renderer.drawString(Time.getTime(System.currentTimeMillis(), "HH:mm"), sr.getScaledHeight() - 13 * 2, 7 - 3, -1, true);
            Asyncware.renderer.drawString("BPS: " + bps, 2, sr.getScaledHeight() - 13 * 2, -1, true);
            Asyncware.renderer.drawString("FPS: " + mc.getDebugFps(), 2, sr.getScaledHeight() - 8 * 2, -1, true);

        }
        if(mode.equalsIgnoreCase("CatSense")){
            Gui gui = new Gui();
            GL11.glPushMatrix();
            GL11.glTranslatef(5, 35, 0);


            Gui.drawRect(71, 61, 1, 1, Colors.RGB());
            //Gui.drawRect(70, 60, 2, 2, new Color(24, 24, 24, 255).getRGB());
            //RenderUtil.drawRoundedShadow(70, 60, 2, 2);
            gui.drawGradientRect(70, 60, 2, 2, new Color(24, 24, 24, 255).getRGB(), new Color(14, 14, 14, 255).getRGB());
            GL11.glTranslatef(0, -20, 0);
            Asyncware.renderer.drawString("Time: " + Time.getTime(System.currentTimeMillis(), "HH:mm"), 5, 25, -1, false);
            Asyncware.renderer.drawString("BPS: " + bps, 5, 35, -1, false);
            Asyncware.renderer.drawString("FPS: " + mc.getDebugFps(), 5, 45, -1, false);
            GL11.glPopMatrix();
        }

        if(mode.equalsIgnoreCase("CatSense CSGO")){
            Gui gui = new Gui();
            GL11.glPushMatrix();
            GL11.glTranslatef(5, 35, 0);


            Gui.drawRect(71, 61, 1, 1, Colors.RGB());
            //Gui.drawRect(70, 60, 2, 2, new Color(24, 24, 24, 255).getRGB());
            //RenderUtil.drawRoundedShadow(70, 60, 2, 2);
            gui.drawGradientRect(70, 60, 2, 2, new Color(24, 24, 24, 255).getRGB(), new Color(14, 14, 14, 255).getRGB());
            GL11.glTranslatef(0, -20, 0);
            Asyncware.verdana.drawString("Time: " + Time.getTime(System.currentTimeMillis(), "HH:mm"), 5, 25, -1, false);
            Asyncware.verdana.drawString("BPS: " + bps, 5, 35, -1, false);
            Asyncware.verdana.drawString("FPS: " + mc.getDebugFps(), 5, 45, -1, false);
            GL11.glPopMatrix();
        }


        if(mode.equalsIgnoreCase("Classic")){
            Asyncware.renderer.drawString(Time.getTime(System.currentTimeMillis(), "HH:mm"), sr.getScaledHeight() - 13 * 2, 7 - 3, -1, true);
            Asyncware.renderer.drawString("BPS: " + bps, 2, sr.getScaledHeight() - 13 * 2, -1, true);
            Asyncware.renderer.drawString("FPS: " + mc.getDebugFps(), 2, sr.getScaledHeight() - 8 * 2, -1, true);

        }
    }
}
