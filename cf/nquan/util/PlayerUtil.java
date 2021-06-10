package cf.nquan.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class PlayerUtil {
    private static Minecraft mc = Minecraft.getMinecraft();

    public static void sendMessage(String message) {
        sendMessage(message, true);
    }

    public static void sendMessage(String message, boolean prefix) {
        mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(message));
    }


}
