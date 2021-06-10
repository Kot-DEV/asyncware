package com.nquantum.module.misc;

import com.nquantum.Asyncware;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import de.Hero.settings.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C01PacketChatMessage;

import java.util.Random;

public class Spammer extends Module {
    public Spammer(){
        super("Spammer", 0, Category.MISC);
    }

    private EntityLivingBase target;

    @Override
    public void setup() {
        Asyncware.instance.settingsManager.rSetting(new Setting("Delay", this, 2000D, 10D, 6900D, true));

    }

    public cf.nquan.util.Timer timer = new cf.nquan.util.Timer();

    public String arr[] = {


            "Get Asyncware!! https://asyncware.wtf",
            "Asyncware best client",
            "imagine using skidma",
            "omikron is gay",
            "skid skid skid skid",
            "hey im C03PacketPlayer",
            "deez nutt",
            "lel",
            "sigma hatar??????",
           "hey im on drugs ",
 //           "hey im on " + Math.round(mc.thePlayer.posX) + " " + Math.round(mc.thePlayer.posY) +  " " + Math.round(mc.thePlayer.posZ) + ", catch me LOOLLLL",
            "joe mama",
            "Visit https://vibesense.xyz",
            "me when hypixel, verus and kokscraft bypass",
            "here's your tickets to the juice wrld concert", "hey look! it's a fortnite player", "i bet you probably shop at Costco", "i don't cheat, you just need to click faster", "i hope you fall off a cliff", "i speak english not your gibberish", "i understand why your parents abused you", "i'd tell you to uninstall, but your aim is so bad you wouldn't hit the button", "im not saying you're worthless, but i'd unplug ur life support to charge my phone", "need some pvp advice?", "you do be lookin' kinda bad at the game", "you look like you were drawn with my left hand", "you pressed the wrong button when you installed Minecraft", "you should look into buying a client", "you're so white that you don't play on vanilla, you play on clear", "your difficulty settings must be stuck on easy"

    };

    @EventTarget
    public void onUpdate(EventUpdate e){

        long delay = (long) Asyncware.instance.settingsManager.getSettingByName("Delay").getValDouble();
        Random random = new Random();
        int index = random.nextInt(arr.length);
        String msg = arr[index];




        if(this.timer.hasTimeElapsed(delay, true)){
            mc.thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(msg));
        }
    }

    private EntityLivingBase getClosest(double range) {
        double dist = range;
        EntityLivingBase target = null;
        for (Object object : mc.theWorld.loadedEntityList) {
            Entity entity = (Entity) object;
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase player = (EntityLivingBase) entity;

                    double currentDist = mc.thePlayer.getDistanceToEntity(player);
                    if (currentDist <= dist) {
                        dist = currentDist;
                        target = player;
                    }

            }
        }
        return target;
    }


}
