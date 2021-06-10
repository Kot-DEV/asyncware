package com.nquantum.module.combat;

import com.nquantum.Asyncware;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventPostMotionUpdate;
import com.nquantum.event.impl.EventPreMotionUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import de.Hero.settings.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;

import java.util.Random;

public class KillAura extends Module {

    public EntityLivingBase target;
    public long current, last;
    public int delay = 4;
    public float yaw, pitch;
    public boolean others;

    public Random random = new Random();

    public KillAura() {
            super("KillAura", Keyboard.KEY_R, Category.COMBAT);
        }

        @Override
        public void setup() {

            Asyncware.instance.settingsManager.rSetting(new Setting("Crack Size", this, 5, 0, 65, true));
            Asyncware.instance.settingsManager.rSetting(new Setting("Range", this, 3, 1, 6, true));
            Asyncware.instance.settingsManager.rSetting(new Setting("ClicksPerSecond", this, 9, 1, 20, true));


            Asyncware.instance.settingsManager.rSetting(new Setting("Existed", this, 30, 0, 500, true));
            Asyncware.instance.settingsManager.rSetting(new Setting("FOV", this, 360, 0, 360, true));
            Asyncware.instance.settingsManager.rSetting(new Setting("AutoBlock", this, true));
            Asyncware.instance.settingsManager.rSetting(new Setting("Invisibles", this, false));
            Asyncware.instance.settingsManager.rSetting(new Setting("Players", this, true));
            Asyncware.instance.settingsManager.rSetting(new Setting("Animals", this, false));
            Asyncware.instance.settingsManager.rSetting(new Setting("Monsters", this, false));
            Asyncware.instance.settingsManager.rSetting(new Setting("Villagers", this, false));
            Asyncware.instance.settingsManager.rSetting(new Setting("Teams", this, false));
            Asyncware.instance.settingsManager.rSetting(new Setting("LockView", this, false));
        }




    @EventTarget
        public void onPre(EventPreMotionUpdate event) {
            target = getClosest(mc.playerController.getBlockReachDistance());
            if(target == null)
            return;
        updateTime();
        yaw = mc.thePlayer.rotationYaw;
        pitch = mc.thePlayer.rotationPitch;

        mc.thePlayer.rotationYaw = (getRotations((Entity)target)[0] + this.random.nextInt(20) - 10.0F);
        mc.thePlayer.rotationPitch = (getRotations((Entity)target)[1] + this.random.nextInt(20) - 10.0F);

        int APS = (int)Asyncware.instance.settingsManager.getSettingByName("ClicksPerSecond").getValDouble();
        int range = (int)Asyncware.instance.settingsManager.getSettingByName("Range").getValDouble();



        event.setYaw(40f);
        boolean block = target != null && Asyncware.instance.settingsManager.getSettingByName("AutoBlock").getValBoolean() && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword;
        if(block && target.getDistanceToEntity(mc.thePlayer) < (float) range)
            mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
        if(current - last > 1000 / APS) {
            mc.thePlayer.rotationYaw = (getRotations((Entity)target)[0] + this.random.nextInt(20) - 10.0F);
            mc.thePlayer.rotationPitch = (getRotations((Entity)target)[1] + this.random.nextInt(20) - 10.0F);
            attack(target);
            resetTime();
        }
    }

    @EventTarget
    public void onPost(EventPostMotionUpdate event) {
        if(target == null)
            return;
        mc.thePlayer.rotationYaw = yaw;
        mc.thePlayer.rotationPitch = pitch;
    }

    private void attack(Entity entity) {
        for(int i = 0; i < Asyncware.instance.settingsManager.getSettingByName("Crack Size").getValDouble(); i++)
            mc.thePlayer.onCriticalHit(entity);

        mc.thePlayer.swingItem();
        mc.playerController.attackEntity(mc.thePlayer, entity);
    }


    private void updateTime() {
        current = (System.nanoTime() / 1000000L);
    }

    private void resetTime() {
        last = (System.nanoTime() / 1000000L);
    }

    private EntityLivingBase getClosest(double range) {
        double dist = range;
        EntityLivingBase target = null;
        for (Object object : mc.theWorld.loadedEntityList) {
            Entity entity = (Entity) object;
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase player = (EntityLivingBase) entity;
                if (canAttack(player)) {
                    double currentDist = mc.thePlayer.getDistanceToEntity(player);
                    if (currentDist <= dist) {
                        dist = currentDist;
                        target = player;
                    }
                }
            }
        }
        return target;
    }

    private boolean canAttack(EntityLivingBase player) {
        if(player instanceof EntityPlayer || player instanceof EntityAnimal || player instanceof EntityMob || player instanceof EntityVillager) {
            if (player instanceof EntityPlayer && !Asyncware.instance.settingsManager.getSettingByName("Players").getValBoolean())
                return false;
            if (player instanceof EntityAnimal && !Asyncware.instance.settingsManager.getSettingByName("Animals").getValBoolean())
                return false;
            if (player instanceof EntityMob && !Asyncware.instance.settingsManager.getSettingByName("Monsters").getValBoolean())
                return false;
            if (player instanceof EntityVillager && !Asyncware.instance.settingsManager.getSettingByName("Villagers").getValBoolean())
                return false;
        }
        if(player.isOnSameTeam(mc.thePlayer) && Asyncware.instance.settingsManager.getSettingByName("Teams").getValBoolean())
            return false;
        if(player.isInvisible() && !Asyncware.instance.settingsManager.getSettingByName("Invisibles").getValBoolean())
            return false;
        if(!isInFOV(player, Asyncware.instance.settingsManager.getSettingByName("FOV").getValDouble()))
            return false;
        return player != mc.thePlayer && player.isEntityAlive() && mc.thePlayer.getDistanceToEntity(player) <= mc.playerController.getBlockReachDistance() && player.ticksExisted > Asyncware.instance.settingsManager.getSettingByName("Existed").getValDouble();
    }

    private boolean isInFOV(EntityLivingBase entity, double angle) {
        angle *= .5D;
        double angleDiff = getAngleDifference(mc.thePlayer.rotationYaw, getRotations(entity.posX, entity.posY, entity.posZ)[0]);
        return (angleDiff > 0 && angleDiff < angle) || (-angle < angleDiff && angleDiff < 0);
    }

    private float getAngleDifference(float dir, float yaw) {
        float f = Math.abs(yaw - dir) % 360F;
        float dist = f > 180F ? 360F - f : f;
        return dist;
    }

    private float[] getRotations(double x, double y, double z) {
        double diffX = x + .5D - mc.thePlayer.posX;
        double diffY = (y + .5D) / 2D - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        double diffZ = z + .5D - mc.thePlayer.posZ;

        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180D / Math.PI) - 90F;
        float pitch = (float)-(Math.atan2(diffY, dist) * 180D / Math.PI);

        return new float[] { yaw, pitch };
    }

    public float[] getRotations(Entity e){
        double deltaX = e.posX + (e.posX - e.lastTickPosX) - mc.thePlayer.posX,
                deltaY = e.posY - 3.5 + e.getEyeHeight() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight(),
                deltaZ = e.posZ + (e.posZ - e.lastTickPosZ) - mc.thePlayer.posZ,
                distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));

        float yaw = (float) Math.toDegrees(-Math.atan(deltaX / deltaZ)),
                pitch = (float) -Math.toDegrees(Math.atan(deltaY / distance));

        if(deltaX < 0 && deltaZ < 0){
            yaw = (float) (90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        }else if(deltaX > 0 && deltaZ < 0){
            yaw = (float) (-90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        }

        return new float[] { yaw, pitch };
    }

     /*
    private EntityLivingBase target;
    private long current, last;
    private int delay = 8;
    private float yaw, pitch;
    private boolean others;

    public Timer timer = new Timer();

    public KillAura() {
        super("KillAura", Keyboard.KEY_R, Category.COMBAT);
    }

    @Override
    public void setup() {

        Asyncware.instance.settingsManager.rSetting(new Setting("Crack Size", this, 5, 0, 15, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("APS", this, 12, 1, 20, true));

        Asyncware.instance.settingsManager.rSetting(new Setting("Existed", this, 30, 0, 500, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("FOV", this, 360, 0, 360, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("AutoBlock", this, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("Invisibles", this, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("Players", this, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("Animals", this, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("Monsters", this, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("Villagers", this, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("Teams", this, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("LockView", this, false));
    }



    public boolean blocking = false;


    @EventTarget
    public void onUpdate(EventUpdate e) {

        double APS = Asyncware.instance.settingsManager.getSettingByName("APS").getValDouble();

        int aps = (int) APS;
        List<EntityLivingBase> targets = (List<EntityLivingBase>) mc.theWorld.entityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());

        targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < 4 && entity != mc.thePlayer).collect(Collectors.toList());
        targets.sort(Comparator.comparingDouble(entity ->((EntityLivingBase)entity).getDistanceToEntity(mc.thePlayer)));

        if(!targets.isEmpty()) {
            EntityLivingBase target = targets.get(0);


            if(this.timer.hasTimeElapsed(1000 / aps, true)) {


               block();

                //mc.thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage("xd"));
                //mc.rightClick();
                mc.thePlayer.swingItem();
                mc.playerController.attackEntity(mc.thePlayer, target);
                mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));

            }

        }


    }

    private void block() {
        if (!mc.gameSettings.keyBindUseItem.isPressed() && !mc.thePlayer.isBlocking()) {
            mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.getCurrentEquippedItem(), 0.0f, 0.0f, 0.0f));
           mc.getNetHandler().getNetworkManager().sendPacket(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.getCurrentEquippedItem(), 0.0f, 0.0f, 0.0f));
            this.blocking = true;
        }
    }

    public float[] getRots(Entity e) {
        double deltaX = e.posX + (e.posX - e.lastTickPosX) - mc.thePlayer.posX,
                deltaY = e.posY - 7.5 + e.getEyeHeight() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight(),
                deltaZ= e.posX + (e.posZ - e.lastTickPosZ) - mc.thePlayer.posZ,
                dist = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));

        float yaw = (float) Math.toDegrees(-Math.atan(deltaX / deltaZ)),
                pitch = (float) -Math.toDegrees(Math.atan(deltaY / dist));

        if(deltaX < 0 && deltaZ < 0) {
            yaw = (float) (90 + Math.toDegrees(-Math.atan(deltaZ / deltaX)));
        } else if(deltaX > 0 && deltaZ < 0) {
            yaw = (float) (-90 + Math.toDegrees(-Math.atan(deltaZ / deltaX)));
        }

        return new float[] {yaw, pitch};
    }


     */
}
