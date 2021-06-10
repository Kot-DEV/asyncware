package com.nquantum.module.player;

import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.C03PacketPlayer;

public class InstaBow extends Module {

    public InstaBow(){
        super("InstaBow", 0, Category.PLAYER);
    }

    @EventTarget
    public void onUpdate(EventUpdate nigger){
        if(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow && mc.thePlayer.isUsingItem()){
            for(int i = 0; i < 50; i++){
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
            }
            mc.rightClick();
          //  mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging());
        }
    }
}
