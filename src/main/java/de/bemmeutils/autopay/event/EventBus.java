package de.bemmeutils.autopay.event;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class EventBus {
    private static final Map<Class<?>, List<Consumer<?>>> listeners = new ConcurrentHashMap<>();

    public static <T> void register(Class<T> eventClass, Consumer<T> listener) {
        listeners.computeIfAbsent(eventClass, k -> new ArrayList<>()).add(listener);
    }

    public static void register(EventHandler handler) {
        for (Method method : handler.getClass().getDeclaredMethods()) {
            if (method.getParameterCount() == 1) {
                Class<?> eventType = method.getParameterTypes()[0];
                if (!method.getName().startsWith("on")) continue;

                method.setAccessible(true);

                listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(event -> {
                    try {
                        method.invoke(handler, event);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    public static <T> void post(T event) {
        List<Consumer<?>> eventListeners = listeners.get(event.getClass());
        if (eventListeners != null) {
            for (Consumer<?> listener : eventListeners) {
                @SuppressWarnings("unchecked")
                Consumer<T> typedListener = (Consumer<T>) listener;
                typedListener.accept(event);
            }
        }
    }
}
