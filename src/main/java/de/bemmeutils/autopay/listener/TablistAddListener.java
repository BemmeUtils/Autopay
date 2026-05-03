package de.bemmeutils.autopay.listener;

import de.bemmeutils.autopay.Addon;
import de.bemmeutils.autopay.event.EventHandler;
import de.bemmeutils.autopay.event.events.TablistAddEvent;
import de.bemmeutils.autopay.messages.PaymentMessage;
import de.bemmeutils.autopay.utils.ChatUtil;
import de.bemmeutils.autopay.utils.Citybuild;
import de.bemmeutils.autopay.utils.Payment;
import de.byteandbit.velociraptor.api.chat.ChatMessage;
import net.minecraft.client.Minecraft;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TablistAddListener implements EventHandler {
    @SuppressWarnings("unused")
    public void onTablistAdd(TablistAddEvent event) {
        if (Minecraft.getMinecraft().thePlayer == null) return;
        if (Minecraft.getMinecraft().thePlayer.getGameProfile().equals(event.getGameProfile()) || Minecraft.getMinecraft().thePlayer.getGameProfile().getId().equals(event.getGameProfile().getId()))
            return;
        if (!Citybuild.getInstance().isOnCityBuild()) return;
        if (Addon.getCitybuildJoinTime() + TimeUnit.SECONDS.toMillis(20) > System.currentTimeMillis()) return;
        Payment payment = Addon.getJsonUtil().getPayment(event.getGameProfile().getId().toString());
        if (payment != null && payment.getSum() > 0) {
            Addon.getJsonUtil().deletePayment(event.getGameProfile().getId().toString());
            Addon.getScheduler().schedule(() -> {
                Addon.getVelociraptorAPI().getChatAPI().send(ChatMessage.command().text("pay " + event.getGameProfile().getName() + " " + ChatUtil.formatDouble(payment.getSum())));
                if (!Addon.getPaymentWebhookUrl().equalsIgnoreCase("")) {
                    try {
                        new PaymentMessage(event.getGameProfile().getName(), payment.getSum()).getWebhook().execute();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
            }, 5, TimeUnit.SECONDS);
        }
    }
}