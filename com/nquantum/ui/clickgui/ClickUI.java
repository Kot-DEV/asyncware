package com.nquantum.ui.clickgui;

import com.nquantum.module.Category;
import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;

public class ClickUI extends GuiScreen {



    ArrayList<Frame> frames;
    public ClickUI(){
        frames = new ArrayList<>();
        int offset = 0;
        for(Category category : Category.values()){
            frames.add(new Frame(category, 10 + offset, 20));
            offset += 110;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        for(Frame frame : frames){
            frame.render(mouseX, mouseY);
        }
    }
}
