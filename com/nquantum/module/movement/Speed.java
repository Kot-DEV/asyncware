package com.nquantum.module.movement;

import cf.nquan.util.BypassUtil;
import cf.nquan.util.MovementUtil;
import cf.nquan.util.PacketUtil;
import com.nquantum.Asyncware;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import de.Hero.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class Speed extends Module {
    public Speed() {
        super("Speed", Keyboard.KEY_X, Category.MOVEMENT);
    }
    public static EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;

    public cf.nquan.util.Timer timer = new cf.nquan.util.Timer();
    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Vanilla");
        options.add("VanillaHop");
        options.add("Kokscraft");
        options.add("HypixelHop");
        options.add("Hypixel-Port");

        options.add("VerusHop");
        options.add("Verus");
        options.add("Verus-Packet");

        options.add("Test");



        Asyncware.instance.settingsManager.rSetting(new Setting("Speed Mode", this, "Vanilla", options));
        Asyncware.instance.settingsManager.rSetting(new Setting("Speed", this, 0.2D, 0.01D, 1.0D, false));
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        String mode = Asyncware.instance.settingsManager.getSettingByName("Speed Mode").getValString();
        double speed = Asyncware.instance.settingsManager.getSettingByName("Speed").getValDouble();
        boolean move = MovementUtil.isMoving();
        this.setDisplayName("Speed");

        if(mode.equalsIgnoreCase("Verus")) {
            if(mc.thePlayer.onGround) {
                if (this.timer.hasTimeElapsed(1000L, true)) {
                    mc.thePlayer.setSpeed(3F);
                }else if (this.timer.hasTimeElapsed(6000L, true)) {
                    mc.thePlayer.jump();
                    mc.thePlayer.setSpeed(1F);
                }

            }
        }




        if(mode.equalsIgnoreCase("Vanilla")){
            mc.thePlayer.moveForward *= 0.05f;
            mc.thePlayer.moveStrafing *= 0.05f;
            if(MovementUtil.isMoving()) {
                mc.timer.timerSpeed = 1.0f;
              //  MovementUtil.setSpeed(1.55);
               // mc.thePlayer.capabilities.setPlayerWalkSpeed(1);

            }

        }

        if(mode.equalsIgnoreCase("VerusHop")){
            if(MovementUtil.isMovingOnGround()){
               mc.thePlayer.isCollidedVertically = 0.05000000074505806D < 0.0D;
               mc.thePlayer.motionY = 0.4234983219357f;

                float h = BypassUtil.getMaxFallDist();
                System.out.println(h);

               MovementUtil.setSpeed(1.02f);
            }
        }


        if(mode.equalsIgnoreCase("Kokscraft")) {

            if(MovementUtil.isMoving() && mc.thePlayer.onGround) {

                mc.thePlayer.motionY = 0.42000001192092879;

                if (this.timer.hasTimeElapsed(410L, true)){
                    PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX - 0.07347797391252f, mc.thePlayer.posY, mc.thePlayer.posZ - 0.07347797391252f, true));
                    MovementUtil.setSpeed(1.4D);
                }
            }


            //mc.thePlayer.posX += .00000000003f;


        }

        if(mode.equalsIgnoreCase("Hypixel-Port")){
            if(MovementUtil.isMovingOnGround()){
                mc.timer.timerSpeed = 1.0f;
                if(this.timer.hasTimeElapsed(200L, true)){
                    player.motionY = 0.05f;
                    mc.timer.timerSpeed = 1.2f;
                }
            }
            /*
            if (isOnLiquid())
                return;
            if (mc.thePlayer.onGround && (mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0)) {
                if (mc.thePlayer.ticksExisted % 2 != 0)
                    mc.thePlayer.motionY += .004;
                mc.thePlayer.setSpeed(mc.thePlayer.ticksExisted % 2 == 0 ? .45F : .2F);
                mc.timer.timerSpeed = 1.105F;
            }

             */
        }

    }

    /*
    @EventTarget
    public void onPre(EventPreMotionUpdate event) {
        String mode = Asyncware.instance.settingsManager.getSettingByName("Speed Mode").getValString();
        if(mode.equalsIgnoreCase("Hypixel-Port")) {
            if (isOnLiquid())
                return;
            if (mc.thePlayer.onGround && (mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0)) {
                if (mc.thePlayer.ticksExisted % 2 != 0)
                    event.y += .4;
                mc.thePlayer.setSpeed(mc.thePlayer.ticksExisted % 2 == 0 ? .45F : .2F);
                mc.timer.timerSpeed = 1.095F;
            }

        }
    }


     */

    private boolean isOnLiquid() {
        boolean onLiquid = false;
        final int y = (int)(mc.thePlayer.boundingBox.minY - .01);
        for(int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); x < MathHelper.floor_double(mc.thePlayer.boundingBox.maxX) + 1; ++x) {
            for(int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); z < MathHelper.floor_double(mc.thePlayer.boundingBox.maxZ) + 1; ++z) {
                Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                if(block != null && !(block instanceof BlockAir)) {
                    if(!(block instanceof BlockLiquid))
                        return false;
                    onLiquid = true;
                }
            }
        }
        return onLiquid;
    }

}
