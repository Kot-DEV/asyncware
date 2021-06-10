package com.nquantum.module.movement;

import cf.nquan.util.MovementUtil;
import cf.nquan.util.PacketUtil;
import cf.nquan.util.Strings;
import com.mojang.realmsclient.gui.ChatFormatting;
import com.nquantum.Asyncware;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventMove;
import com.nquantum.event.impl.EventUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.world.MinecraftException;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.LinkedList;

public class Fly extends Module {
    public boolean bow;
    public Fly() {
        super("Fly", Keyboard.KEY_F, Category.MOVEMENT);
    }

    private double startY;
    public cf.nquan.util.Timer timer = new cf.nquan.util.Timer();

    public static EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Vanilla");
        options.add("Verus-Packet");
        options.add("Verus");
        options.add("Verus-Nigger");
        options.add("Float");
        options.add("VictoryCraft");
        options.add("Gravity");
        options.add("Kokscraft-Old");
        options.add("Kokscraft");
        options.add("Hypixel");
        options.add("Hypixel2");
        options.add("Hypixel3");

        options.add("Motion");
        options.add("Glide");
        options.add("Dev");

        Asyncware.instance.settingsManager.rSetting(new Setting("Fly Mode", this, "Vanilla", options));
        Asyncware.instance.settingsManager.rSetting(new Setting("Fly Speed", this, 3, 0, 10, true));
    }


    @EventTarget
    public void onUpdate(EventUpdate nigger) {
        String mode = Asyncware.instance.settingsManager.getSettingByName("Fly Mode").getValString();
        boolean move = MovementUtil.isMoving();
        double speed = Asyncware.instance.settingsManager.getSettingByName("Fly Speed").getValDouble();
        this.setDisplayName("Fly");
        if (mode.equalsIgnoreCase("VictoryCraft")) {


            MovementUtil.setSpeed(1.0D);


            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY -= 0.01f, mc.thePlayer.posZ, false));
            PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY -= 0.01f, mc.thePlayer.posZ, false));

            if (this.timer.hasTimeElapsed(200L, true)) {
                mc.timer.timerSpeed = 1.0f;
                MovementUtil.setSpeed(2.01D);

            }


        }

        if (mode.equalsIgnoreCase("Glide")) {

            startY = mc.thePlayer.posY;
            final boolean shouldBlock = mc.thePlayer.posY + 0.1 >= startY && mc.gameSettings.keyBindJump.isKeyDown();
            if (mc.thePlayer.isSneaking()) {
                mc.thePlayer.motionY = -0.4f;
            } else if (mc.gameSettings.keyBindJump.isKeyDown() && !shouldBlock) {

                mc.thePlayer.motionY = 0.4f;
            } else {
                mc.thePlayer.motionY = -0.04;
            }

        }
        if (mode.equalsIgnoreCase("Kokscraft")) {
            // this fly is coutrtesy of OlekAleksander, OlekAleksander#5599 on Discord (725619653749243906)

            LinkedList<Packet> queue = new LinkedList<>();

            // spoof the ground
            mc.thePlayer.onGround = true;
            mc.thePlayer.motionY = 0;
            queue.clear();

            PacketUtil.sendPacket(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw + 0.3f, mc.thePlayer.rotationPitch + 0.3f, true));


            for(int i = 0; i < 10; i++){
                // sending 10x C0F to simulate fake transaction
                PacketUtil.sendPacketPlayerNoEvent(new C0FPacketConfirmTransaction());
            }

            // set the fake camera yaw
            PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, true));
            MovementUtil.setSpeed(speed);
            if (mc.thePlayer.ticksExisted % 10 == 0 && mc.thePlayer.onGround) {
                // push into ground
                PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
                //PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 3.725, mc.thePlayer.posZ, false));
                PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
            }
            if (this.timer.hasTimeElapsed(1200L, true)) {
                MovementUtil.setSpeed(2.0f);
                if(mc.thePlayer.onGround){
                    MovementUtil.damagePlayer();
                }
                mc.thePlayer.motionY = 0.005268723;


            }

        }
        if (mode.equalsIgnoreCase("Kokscraft-Old")) {
            mc.thePlayer.onGround = true;
            mc.thePlayer.motionY = 0;
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
            mc.thePlayer.motionX *= 1.8;
            mc.thePlayer.motionZ *= 1.8;
        }


        if (mode.equalsIgnoreCase("Float")) {
            MovementUtil.setSpeed(0.5f);
            mc.thePlayer.motionY = 0;
            mc.thePlayer.onGround = true;
            if (this.timer.hasTimeElapsed(622L, true)) {
                mc.thePlayer.onGround = false;
                MovementUtil.setSpeed(5f);
                mc.thePlayer.motionY = 0.10;
            }
        }

        if (mode.equalsIgnoreCase("Verus-Packet")) {
            /*
            mc.thePlayer.motionY = 0;
            mc.thePlayer.onGround = true;

            MovementUtil.setSpeed(speed);

             */

            // this fly is coutrtesy of OlekAleksander, OlekAleksander#5599 on Discord (725619653749243906)

            mc.timer.timerSpeed = 0.4f;
            mc.thePlayer.onGround = true;
            mc.thePlayer.motionY = 0;
            MovementUtil.setSpeed(speed);
            if (this.timer.hasTimeElapsed(1585L, true)) {
                MovementUtil.setSpeed(speed);
                if(mc.thePlayer.onGround){

                    //PacketUtil.sendPacketPlayer(new C17PacketCustomPayload());
                    MovementUtil.damagePlayer();
                } mc.thePlayer.motionY = 0.25;

            }

        }

        if (mode.equalsIgnoreCase("Hypixel")) {


                double x;
                double z;

                mc.timer.timerSpeed = 1.0f;
                PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.04f, mc.thePlayer.posZ, false));
                PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.04f, mc.thePlayer.posZ, true));


                x = mc.thePlayer.posX - 1.461D;
                z = mc.thePlayer.posZ - 1.261D;

                // double playerX, double playerY, double playerZ, float playerYaw, float playerPitch, boolean playerIsOnGround
                PacketUtil.sendPacket(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw + 0.3f, mc.thePlayer.rotationPitch + 0.3f, true));
                if (this.timer.hasTimeElapsed(120L, true)) {


                    mc.timer.timerSpeed = 0.67f;
                    mc.thePlayer.motionY += 0.000001f;
                    mc.thePlayer.onGround = false;
                }


                mc.thePlayer.motionY = 0.0;
                mc.thePlayer.onGround = true;


                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.05F, mc.thePlayer.posZ, true));
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));


            }


        if(mode.equalsIgnoreCase("Hypixel2")){
            if(mc.thePlayer.onGround){
                MovementUtil.damageVerus();
            }
            boolean flag =  mc.thePlayer.posY + 0.1 >= startY && mc.gameSettings.keyBindJump.isKeyDown();
            boolean flag1 = mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() ||mc.gameSettings.keyBindRight.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown();
            if(flag){
                mc.thePlayer.motionY = 0.0048f;
                PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.lastTickPosY, mc.thePlayer.lastTickPosZ, false));
            }

            if (mc.gameSettings.keyBindJump.isKeyDown() && !flag) {

                mc.thePlayer.motionY = 0.2f;

            } else {
                mc.thePlayer.motionY = -0.00;
            }


            if(this.timer.hasTimeElapsed(40L, true) &&  flag){
                  MovementUtil.setSpeed(1.0005);
            }


        }


            if (mode.equalsIgnoreCase("Verus")) {
                // this fly is coutrtesy of OlekAleksander, OlekAleksander#5599 on Discord (725619653749243906)
                mc.thePlayer.onGround = true;
                mc.thePlayer.motionY = 0;
                MovementUtil.setSpeed(speed);
                if (this.timer.hasTimeElapsed(1585L, true)) {
                    MovementUtil.setSpeed(speed);
                    if(mc.thePlayer.onGround){

                        //PacketUtil.sendPacketPlayer(new C17PacketCustomPayload());
                        MovementUtil.damagePlayer();
                    } mc.thePlayer.motionY = 0.25;

                }

            }

            if(mode.equalsIgnoreCase("Verus-Nigger")){


                if(mc.thePlayer.onGround){
                    MovementUtil.damagePlayer();
                }
                if (!mc.thePlayer.isCollidedVertically) {
                    mc.thePlayer.onGround = true;
                    mc.timer.timerSpeed = 0.941f;
                    if (mc.thePlayer.ticksExisted % 8 == 0 && !mc.gameSettings.keyBindJump.isKeyDown() && MovementUtil.isMoving()) {
                        if (!bow) mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.41999998688697815, mc.thePlayer.posZ);
                        else {
                            mc.thePlayer.motionX = 0; mc.thePlayer.motionZ = 0;
                            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.0784000015258789, mc.thePlayer.posZ, false));
                            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - (0.0784000015258789 * 2), mc.thePlayer.posZ);
                        }
                        bow = !bow;
                        mc.thePlayer.onGround = false;
                    }
                    else mc.thePlayer.motionY = 0;

                    if (!mc.gameSettings.keyBindJump.isKeyDown())
                        MovementUtil.strafe((float) 0.4);
                    mc.thePlayer.isCollidedHorizontally = false;

                    if (mc.gameSettings.keyBindJump.isKeyDown()) {
                        mc.thePlayer.motionY = 0.5;

                    }



                    if (mc.thePlayer.posY != Math.round(mc.thePlayer.posY)
                            && mc.gameSettings.keyBindJump.isKeyDown() || mc.gameSettings.keyBindSneak.isKeyDown()) {
                        mc.thePlayer.setPosition(mc.thePlayer.posX, Math.round(mc.thePlayer.posY),
                                mc.thePlayer.posZ);
                    }
                }
            }


            if (mode.equalsIgnoreCase("Gravity")) {
                if (this.timer.hasTimeElapsed(90L, true) && mc.thePlayer.onGround == false) {
                    mc.thePlayer.motionY += 0.10f;
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.10f, mc.thePlayer.posZ, true));

                    if (this.timer.hasTimeElapsed(200L, true)) {

                        MovementUtil.setSpeed(2.0f);
                    }

                }
            }
        }

        @EventTarget
        public void onMove(EventMove fag){
            String mode = Asyncware.instance.settingsManager.getSettingByName("Fly Mode").getValString();
            boolean move = MovementUtil.isMoving();
            double speed = Asyncware.instance.settingsManager.getSettingByName("Fly Speed").getValDouble();
            if(mode.equalsIgnoreCase("Hypixel3")){
                mc.thePlayer.motionY = 0;

                double y = mc.thePlayer.posY + 1.0E-9;

                if (mc.thePlayer.ticksExisted % 2 == 0) {
                    y += 1.0E-8;
                }

                fag.y = y;
            }
        }



}