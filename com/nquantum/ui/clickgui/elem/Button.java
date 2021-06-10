package com.nquantum.ui.clickgui.elem;

import com.nquantum.Asyncware;
import com.nquantum.module.Module;
import com.nquantum.ui.clickgui.Frame;
import net.minecraft.client.Minecraft;

public class Button
{
    int x, y, width, height;

    Module module;

    Frame parent;


    Minecraft mc = Minecraft.getMinecraft();
    public Button(Module module, int x, int y, Frame parent){
        this.module = module;
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.height = 15;
    }

    public void draw(int MouseX, int MouseY){
        Asyncware.renderer.drawString(module.getName(), x + 2, y + 2, -1, false);
    }

}
