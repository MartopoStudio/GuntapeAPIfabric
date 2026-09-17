package com.guntape.api.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class GuntapeEventBus {

    // ConcurrentHashMap agar aman jika event didaftarkan/dipanggil dari Thread berbeda
    private static final Map<Class<?>, List<Consumer<Object>>> listeners = new ConcurrentHashMap<>();

    /**
     * Daftarkan listener (subscriber) untuk mendengarkan suatu event.
     *
     * @param eventClass Class dari Event yang ingin didengarkan
     * @param listener Aksi yang dilakukan saat event terjadi
     */
    @SuppressWarnings("unchecked")
    public static <T> void subscribe(Class<T> eventClass, Consumer<T> listener) {
        listeners.computeIfAbsent(eventClass, k -> new ArrayList<>())
                .add((Consumer<Object>) listener);
    }

    /**
     * Trigger (publish) sebuah event agar semua listener mengetahuinya.
     *
     * @param event Objek (data) event yang akan dilempar
     */
    public static void publish(Object event) {
        if (event == null) return;

        List<Consumer<Object>> eventListeners = listeners.get(event.getClass());
        if (eventListeners != null) {
            for (Consumer<Object> listener : eventListeners) {
                // Eksekusi listener
                listener.accept(event);
            }
        }
    }
}