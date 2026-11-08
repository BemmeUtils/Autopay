package de.bemmeutils.autopay.utils;

import de.bemmeutils.autopay.event.EventBus;
import net.minecraft.client.Minecraft;

public final class ThreadUtil {
    private ThreadUtil() {}

    public static void postOnClientThread(final Object event) {
        Minecraft.getMinecraft().addScheduledTask(new Runnable() {
            @Override
            public void run() {
                EventBus.post(event);
            }
        });
    }
}