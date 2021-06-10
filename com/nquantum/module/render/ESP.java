package com.nquantum.module.render;

import cf.nquan.util.ESPUtil;
import com.nquantum.Asyncware;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.Event3D;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import de.Hero.settings.Setting;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

public class ESP extends Module {


    public ESP() {
        super("ESP",0 , Category.RENDER);
    }


    @Override
    public void setup(){
        ArrayList<String> options = new ArrayList<>();
        options.add("CSGO");
        options.add("Box");
        options.add("Color Ring");




        Asyncware.instance.settingsManager.rSetting(new Setting("ESP Mode", this, "CSGO", options));
    }
    @EventTarget
    public void onRender3D(Event3D event){


        for(Object e : mc.theWorld.loadedEntityList) {
            if(e instanceof EntityPlayer) {
                EntityPlayer player = mc.thePlayer;
                if(!(e == player) && !player.isInvisible()) {
                    double x = (((EntityPlayer) e).lastTickPosX + (((EntityPlayer) e).posX - ((EntityPlayer) e).lastTickPosX) * ((Event3D) event).partialTicks);
                    double y = (((EntityPlayer) e).lastTickPosY + (((EntityPlayer) e).posY - ((EntityPlayer) e).lastTickPosY) * ((Event3D) event).partialTicks);
                    double z = (((EntityPlayer) e).lastTickPosZ + (((EntityPlayer) e).posZ - ((EntityPlayer) e).lastTickPosZ) * ((Event3D) event).partialTicks);
                    drawBox((EntityLivingBase) e,x - RenderManager.renderPosX, y - RenderManager.renderPosY, z - RenderManager.renderPosZ);
                }
            }
        }


         /*
        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity instanceof EntityLivingBase) {
                final EntityLivingBase e = (EntityLivingBase) entity;
                if (e.isDead || e == mc.thePlayer )
                    continue;

                ESPUtil.drawCircleOnEntityFade(e, event.getPartialTicks());
            }
        }

          */
    }

   /*
    * */


    public void drawBox(EntityLivingBase entity, double posX, double posY, double posZ) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(posX, posY, posZ);
        GlStateManager.rotate(-RenderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.scale(-0.1, -0.1, 0.1);
        GL11.glDisable(2896);
        GL11.glDisable(2929);

        Gui.drawRect(-5.75f, -21f, -7.1f, 2.15f, -1);
        Gui.drawRect(5.75f, -19.f, 7.1f, 1.15f,-1);
        Gui.drawRect(-6.1f, 2.2f, 8.1f, 1.875f, -1);
        Gui.drawRect(-5.1f, -21f, 8.1f, -19.65f, -1);


        Gui.drawRect(9.3f, -20.1f, 9.35f, 2.1f, new Color(0, 0,0,255).getRGB());
        Gui.drawRect(10, -entity.getHealth(), 9.25f, 2, new Color(0,255, 55,255).getRGB());
        GlStateManager.scale(0.1, 0.1, 0.1);
        mc.fontRendererObj.drawStringWithShadow(Math.round(entity.getHealth()) + "?", 80 - mc.fontRendererObj.getStringWidth(Math.round(entity.getHealth()) + "HP") / 2 + 15, (int) (-entity.getHealth() * 10), -1);
        mc.fontRendererObj.drawStringWithShadow(entity.getName(), 0 - mc.fontRendererObj.getStringWidth(entity.getName()) / 2, -210, -1);
        GL11.glEnable(2929);
        GL11.glEnable(2896);
        GlStateManager.popMatrix();
    }



}
