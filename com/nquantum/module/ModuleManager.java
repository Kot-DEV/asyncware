package com.nquantum.module;

import com.nquantum.module.combat.Criticals;
import com.nquantum.module.combat.KillAura;
import com.nquantum.module.misc.*;
import com.nquantum.module.combat.AutoArmor;
import com.nquantum.module.movement.*;
import com.nquantum.module.player.InstaBow;
import com.nquantum.module.player.InvMove;
import com.nquantum.module.player.NoFall;
import com.nquantum.module.render.*;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private ArrayList<Module> modules = new ArrayList<Module>();

    public ModuleManager() {
        // COMBAT
        modules.add(new KillAura());

        modules.add(new AutoArmor());
        modules.add(new Criticals());

        // MOVEMENT
        modules.add(new Sprint());
        modules.add(new Step());
        //modules.add(new Flight());
        modules.add(new Fly());
        modules.add(new LongJump());
        modules.add(new Speed());
        modules.add(new Phase());
        modules.add(new JumpAir());
        modules.add(new Scaffold());

        // RENDER
        modules.add(new Fullbright());
        modules.add(new ClickGUI());
        modules.add(new ArreyList());
        modules.add(new BetterChat());
        modules.add(new Jello());
        modules.add(new HUD());
        modules.add(new SwordAnimation());
        modules.add(new ClickUI());
        modules.add(new ESP());
        modules.add(new Tracers());
        modules.add(new TargetHUD());
        modules.add(new InfoHUD());
        modules.add(new TargetStrafe());


        // PLAYER
        modules.add(new NoFall());
        modules.add(new Jesus());
        modules.add(new InstaBow());
        modules.add(new InvMove());

        // MISC
        modules.add(new PacketLogger());
        modules.add(new Timer());
        modules.add(new Spammer());
        modules.add(new BedBreaker());
        modules.add(new NoArmorStandBB());
        // NONE
    }

    public ArrayList<Module> getModules() {
        return modules;
    }
    public Module getModuleByName(String name) {
        return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public List<Module> getModules(Category category) {
        List<Module> modules = new ArrayList<Module>();
        for (int i = 0; i < this.modules.size(); i++) {
            Module module = this.modules.get(i);
            if (module.getCategory().equals(category))
                modules.add(module);
        }

        return modules;
    }
}


