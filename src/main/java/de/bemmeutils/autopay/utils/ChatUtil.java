package de.bemmeutils.autopay.utils;

import de.byteandbit.velociraptor.api.chat.ChatMessage;
import de.bemmeutils.autopay.Addon;
import net.labymod.main.LabyMod;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class ChatUtil {
    public static void displayMessage(String message, boolean success) {
        LabyMod.getInstance().displayMessageInChat((success ? "§a" : "§c") + message);
    }

    public static void displayUsageMessage() {
        LabyMod.getInstance().displayMessageInChat("§1");
        LabyMod.getInstance().displayMessageInChat("§e/ggap add <Name> <Summe>");
        LabyMod.getInstance().displayMessageInChat("§e/ggap delete <Name>");
        LabyMod.getInstance().displayMessageInChat("§e/ggap info <Name>");
        LabyMod.getInstance().displayMessageInChat("§2");
    }

    public static void sendPrivateMessage(String playerName, String message) {
        Addon.getVelociraptorAPI().getChatAPI().send(ChatMessage.command().privateMessage(playerName, message));
    }

    public static String formatDouble(double amount) {
        BigDecimal totalAmount = BigDecimal.valueOf(amount);
        DecimalFormat decimalFormat = new DecimalFormat("#");
        decimalFormat.setGroupingUsed(false);
        return decimalFormat.format(totalAmount);
    }

}

