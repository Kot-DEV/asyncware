package com.nquantum;

import cf.nquan.util.ChatUtil;
import cf.nquan.util.font.GlyphPageFontRenderer;
import com.nquantum.event.EventManager;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventKey;
import com.nquantum.module.ModuleManager;
import de.Hero.clickgui.ClickGUI;
import de.Hero.settings.SettingsManager;
import org.lwjgl.opengl.Display;

import java.awt.*;

public class Asyncware{


    public String name = "Asyncware", version = "1.0", creator = "nquantum";

    public static Asyncware instance = new Asyncware();


    public static int fontSize = 20;
    /*



    public static FontUtil fontxd = new FontUtil("blazing/regular.ttf", Font.PLAIN, 20);
    public static FontUtil fontRegularHeader = new FontUtil("blazing/regular.ttf", Font.PLAIN, 90);
    public static FontUtil fontBold = new FontUtil("blazing/bold.ttf", Font.BOLD, 90);

     */

    public static GlyphPageFontRenderer renderer;
    public static GlyphPageFontRenderer renderer1;
    public static GlyphPageFontRenderer renderer2;
    public static GlyphPageFontRenderer csgoRenderer;
    public static GlyphPageFontRenderer verdana;





    public static String defaultUsername = "Vers4tile";
    public SettingsManager settingsManager;
    public EventManager eventManager;
    public ModuleManager moduleManager;
    public ClickGUI clickGui;

/*
    public static MinecraftFontRenderer getClientFont(String size, boolean bold) {
        MinecraftFontRenderer font = null;
        font = FontUtil.clean;
        return font;
    }


 */
    public void startClient() {
        settingsManager = new SettingsManager();
        eventManager = new EventManager();
        moduleManager = new ModuleManager();
        clickGui = new ClickGUI();




       // moduleManager.getModuleByName("BetterChat").toggle();

       // MinecraftFontRenderer renderer = this.getClientFont("Regular", false);
       // MinecraftFontRenderer theFont = this.getClientFont("Medium", false);


        for(String i : GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()){
            System.out.println(i);
        }

        renderer = GlyphPageFontRenderer.create("SF UI Display Light", 10, false, false, false);
        renderer1 = GlyphPageFontRenderer.create("SF UI Display Light", 20, false, false, false);

        renderer2 = GlyphPageFontRenderer.create("SF UI Display Light", 70, false, false, false);
        verdana = GlyphPageFontRenderer.create("Verdana", 17, false, false, false);

        csgoRenderer = GlyphPageFontRenderer.create("Consolas", 14, false, false, false);

        System.out.println("[" + name + "] Starting client, b" + version + ", created by " + creator);
        Display.setTitle(name + " b" + version);

        ChatUtil.sendMessagePrefix("BetterChat can cause crashing. Please consider turning it off!");

        eventManager.register(this);
       // discordRP.start();

    }

    public void shutdown() {

    }


    public void stopClient() {
        eventManager.unregister(this);
    }

    @EventTarget
    public void onKey(EventKey event) {
        moduleManager.getModules().stream().filter(module -> module.getKey() == event.getKey()).forEach(module -> module.toggle());
    }

    public static String getDefaultUsername() {
        return defaultUsername;
    }


}
