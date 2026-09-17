package com.guntape.api.scheduler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class GuntapeScheduler {

    // CachedThreadPool akan membuat thread baru sesuai kebutuhan,
    // dan menghapus thread yang menganggur selama 60 detik. Sangat ringan.
    private static final ExecutorService ASYNC_POOL = Executors.newCachedThreadPool();

    /**
     * Menjalankan tugas secara asinkron di thread terpisah.
     * Gunakan untuk tugas berat seperti I/O File, koneksi API web, atau komputasi lama.
     *
     * @param task Tugas (Runnable) yang akan dijalankan
     */
    public static void runAsync(Runnable task) {
        ASYNC_POOL.submit(task);
    }

    /**
     * Menjalankan tugas yang mengembalikan nilai secara asinkron.
     *
     * @param task Tugas (Supplier) yang menghasilkan nilai
     * @return CompletableFuture untuk memantau kapan tugas selesai dan mengambil hasilnya
     */
    public static <T> CompletableFuture<T> supplyAsync(Supplier<T> task) {
        return CompletableFuture.supplyAsync(task, ASYNC_POOL);
    }

    /**
     * Mematikan thread pool secara elegan.
     * HANYA dipanggil secara internal oleh GuntapeAPI saat server Minecraft shutdown.
     */
    public static void shutdown() {
        if (!ASYNC_POOL.isShutdown()) {
            ASYNC_POOL.shutdown();
        }
    }
}