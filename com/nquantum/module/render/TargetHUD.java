package com.nquantum.module.render;

import cf.nquan.util.Colors;
import cf.nquan.util.GuiUtil;
import com.nquantum.Asyncware;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.Event3D;
import com.nquantum.event.impl.EventRenderLiving;
import com.nquantum.event.impl.EventRenderUI;
import com.nquantum.event.impl.EventUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import com.nquantum.module.combat.KillAura;
import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TargetHUD extends Module {

    private double animHealth = 1;
    private double width;
    private int colorPrimary;
    private int colorSecondary;

    public TargetHUD(){
        super("TargetHUD", 0, Category.RENDER);
    }

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Asyncware");
        options.add("Gamesense");

        Asyncware.instance.settingsManager.rSetting(new Setting("TargetHUD Mode", this, "Asyncware", options));

    }

    public static ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
    @EventTarget
    public void onRenderUI(EventRenderUI event){

        String mode = Asyncware.instance.settingsManager.getSettingByName("TargetHUD Mode").getValString();
        if(mode.equalsIgnoreCase("Asyncware")){
            EntityLivingBase target = (EntityLivingBase) mc.pointedEntity;

            if (!(target == null)) {
                if(target.getHealth() < 20) {

                    colorPrimary = new Color(57, 255, 51,120).getRGB();
                    colorSecondary = new Color(93, 255, 85,120).getRGB();

                }
                if(target.getHealth() < 10) {
                    colorPrimary = new Color(255, 91, 5,120).getRGB();
                    colorSecondary = new Color(255, 168, 86,120).getRGB();
                }
                if(target.getHealth() < 5) {
                    colorPrimary = new Color(255, 0, 7,120).getRGB();
                    colorSecondary = new Color(255, 73, 87,120).getRGB();
                }
                GL11.glPushMatrix();
                width = 140 - 32.5;
                GL11.glTranslated(GuiScreen.width / 2 - 40,  GuiScreen.height / 2 + 20, GuiScreen.width / 2);
                GuiUtil.drawRoundedRect(-22.5f, 0, 148 - 3.5f, 50, 2,  new Color(9, 19, 34, 167).getRGB());
                //     Gui.drawRect(-22.5f, 0, 145 - 32.5f, 50, new Color(9, 19, 34, 167).getRGB());



                GL11.glTranslatef(-22.0f, -2.2f, 0);
                Asyncware.renderer1.drawString(target.getName(), 3, 3, Colors.Astolfo(100, 1.0f, 0.5f), true);



                Asyncware.renderer.drawString("Health: " + Math.round(target.getHealth()), 3, 25, -1, true);


                GuiInventory.drawEntityOnScreen(143, 47, 20, 2, 2, target);

                animHealth += ((target.getHealth() - animHealth) / 32) * 0.7;
                if (animHealth < 0 || animHealth > target.getMaxHealth()) {
                    animHealth = target.getHealth();
                }
                else {
                    Gui.drawRect(0f, 47.5f, (int) ((animHealth / target.getMaxHealth()) * width + 6), 45.5f, colorPrimary);
                    Gui.drawRect(0f, 47.5f, (int) ((animHealth / target.getMaxHealth()) * width), 45.5f,  colorSecondary);
                }
                GL11.glScalef(2f,2f,2f);
                //Asyncware.renderer1.drawString(target.getHealth() + "\u2764", 2, 2,Colors.Astolfo(100, 1.0f, 0.5f), true);
                GL11.glPopMatrix();
                //  Gui.drawRect(350.0D, 10.0D, 120.0D, 170.0D, new Color(9, 19, 34, 167).getRGB());

            }
        } else{
            KillAura ka = new KillAura();
            EntityLivingBase target = (EntityLivingBase) mc.pointedEntity;

            if (!(target == null)) {
                if(target.getHealth() < 20) {

                    colorPrimary = new Color(57, 255, 51,120).getRGB();
                    colorSecondary = new Color(93, 255, 85,120).getRGB();

                }
                if(target.getHealth() < 10) {
                    colorPrimary = new Color(255, 91, 5,120).getRGB();
                    colorSecondary = new Color(255, 168, 86,120).getRGB();
                }
                if(target.getHealth() < 5) {
                    colorPrimary = new Color(255, 0, 7,120).getRGB();
                    colorSecondary = new Color(255, 73, 87,120).getRGB();
                }
                GL11.glPushMatrix();
                width = 140 - 32.5;
                GL11.glTranslated(GuiScreen.width / 2 - 40,  GuiScreen.height / 2 + 20, GuiScreen.width / 2);
                GuiUtil.drawRoundedRect(-24.5f, 1, 147 - 3.5f, 49, 2,  Colors.RGB());

                GuiUtil.drawRoundedRect(-22.5f, 0, 148 - 3.5f, 50, 2,  new Color(24, 24, 24, 255).getRGB());
                //     Gui.drawRect(-22.5f, 0, 145 - 32.5f, 50, new Color(9, 19, 34, 167).getRGB());



                GL11.glTranslatef(-22.0f, -2.2f, 0);
                Asyncware.renderer1.drawString(target.getName(), 3, 3, Colors.Astolfo(100, 1.0f, 0.5f), true);



                Asyncware.verdana.drawString("Health: " + Math.round(target.getHealth()), 3, 25, -1, false);


                GuiInventory.drawEntityOnScreen(143, 47, 20, 2, 2, target);

                animHealth += ((target.getHealth() - animHealth) / 32) * 0.7;
                if (animHealth < 0 || animHealth > target.getMaxHealth()) {
                    animHealth = target.getHealth();
                }
                else {
                    Gui.drawRect(0f, 47.5f, (int) ((animHealth / target.getMaxHealth()) * width + 6), 45.5f, colorPrimary);
                    Gui.drawRect(0f, 47.5f, (int) ((animHealth / target.getMaxHealth()) * width), 45.5f,  colorSecondary);
                }
                GL11.glScalef(2f,2f,2f);
                //Asyncware.renderer1.drawString(target.getHealth() + "\u2764", 2, 2,Colors.Astolfo(100, 1.0f, 0.5f), true);
                GL11.glPopMatrix();
                //  Gui.drawRect(350.0D, 10.0D, 120.0D, 170.0D, new Color(9, 19, 34, 167).getRGB());

            }
        }



    }

}
