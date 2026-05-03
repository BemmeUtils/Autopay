package de.bemmeutils.autopay.listener;

import de.bemmeutils.autopay.Addon;
import de.bemmeutils.autopay.utils.ChatUtil;
import de.bemmeutils.autopay.utils.Payment;
import net.labymod.api.events.MessageSendEvent;

import java.util.UUID;

public class MessageSendListener implements MessageSendEvent {
    @Override
    public boolean onSend(String message) {
        String[] args = message.split(" ");
        if (!args[0].equalsIgnoreCase("/ggap")) return false;
        if (args.length == 3 && args[1].equalsIgnoreCase("info")) {
            UUID uuid;
            try {
                uuid = Addon.getVelociraptorAPI().getPlayerAPI().getUUIDByPlayerName(args[2]);
                if (uuid == null) {
                    ChatUtil.displayMessage("Der angegebene Spieler existiert nicht.", false);
                    return true;
                }
            } catch (NullPointerException exception) {
                exception.printStackTrace();
                ChatUtil.displayMessage("Der angegebene Spieler existiert nicht.", false);
                return true;
            }
            Payment payment = Addon.getJsonUtil().getPayment(uuid.toString());
            if (payment == null) {
                ChatUtil.displayMessage("Für diesen Spieler ist keine einmalige Zahlung hinterlegt.", false);
                return true;
            }
            ChatUtil.displayMessage("§e" + args[2] + " §7hat aktuell §a" + Addon.getNUMBER_FORMAT().format(payment.getSum()) + "$ §7an offenen Zahlungen hinterlegt.", true);
            return true;
        }
        if (args.length == 3 && args[1].equalsIgnoreCase("delete")) {
            UUID uuid;
            try {
                uuid = Addon.getVelociraptorAPI().getPlayerAPI().getUUIDByPlayerName(args[2]);
                if (uuid == null) {
                    ChatUtil.displayMessage("Der angegebene Spieler existiert nicht.", false);
                    return true;
                }
            } catch (NullPointerException exception) {
                exception.printStackTrace();
                ChatUtil.displayMessage("Der angegebene Spieler existiert nicht.", false);
                return true;
            }
            Payment payment = Addon.getJsonUtil().getPayment(uuid.toString());
            if (payment == null) {
                ChatUtil.displayMessage("Für diesen Spieler ist keine einmalige Zahlung hinterlegt.", false);
                return true;
            }
            Addon.getJsonUtil().deletePayment(uuid.toString());
            ChatUtil.displayMessage("Du hast alle einmaligen Zahlungen für diesen Spieler gelöscht.", true);
            return true;
        }
        if (args.length == 4 && args[1].equalsIgnoreCase("add")) {
            UUID uuid;
            double sum;
            try {
                uuid = Addon.getVelociraptorAPI().getPlayerAPI().getUUIDByPlayerName(args[2]);
                sum = Double.parseDouble(args[3]);
                if (uuid == null) {
                    ChatUtil.displayMessage("Der angegebene Spieler existiert nicht.", false);
                    return true;
                }
                if (sum < 1) {
                    ChatUtil.displayMessage("Die Summe muss mindestens 1$ betragen.", false);
                }
            } catch (NumberFormatException exception) {
                exception.printStackTrace();
                ChatUtil.displayMessage("Die angegebene Zahl ist ungültig.", false);
                return true;
            } catch (NullPointerException exception) {
                exception.printStackTrace();
                ChatUtil.displayMessage("Der angegebene Spieler existiert nicht.", false);
                return true;
            }
            Payment payment = Addon.getJsonUtil().getPayment(uuid.toString());
            if (payment == null) {
                Addon.getJsonUtil().addPayment(uuid.toString(), args[2], sum);
            } else {
                Addon.getJsonUtil().editPayment(uuid.toString(), payment.getSum() + sum);
            }
            ChatUtil.displayMessage("Der Betrag wurde als einmalige Zahlung hinterlegt.", true);
            return true;
        }
        if (args.length == 6 && args[1].equalsIgnoreCase("add")) {
            return true;
        }
        ChatUtil.displayUsageMessage();
        return true;
    }
}