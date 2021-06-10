package com.nquantum.ui.clickgui;

import com.nquantum.Asyncware;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import com.nquantum.ui.clickgui.elem.Button;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;


public class Frame {

    int x;
    int y;
    int width;
    int height;

    Category category;
    Minecraft mc = Minecraft.getMinecraft();
    ArrayList<Button> moduleButtons;

    public Frame(Category category, int x, int y){
        this.x = x;
        this.y = y;
        this.width = 100;
        this.height = 300;
        this.category = category;

        moduleButtons = new ArrayList<>();
        int offsetY = 15;
        for(Module module : Asyncware.instance.moduleManager.getModules(category)){
            moduleButtons.add(
                    new Button(module, x, y + 15 + offsetY, this)
            );
            offsetY += 15;
        }



    }

    public void render(int MouseX, int MouseY){

        Gui.drawRect(x,y, x + width, y + height, new Color(24, 24, 24, 255).getRGB());
        Asyncware.renderer.drawString(category.toString(), x + 2, y + 2, new Color(255, 255, 255).getRGB(), false);
        for(Button button : moduleButtons){
            button.draw(MouseX, MouseY);
        }
    }
}
