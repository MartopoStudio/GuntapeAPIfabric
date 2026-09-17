package com.guntape.api.storage;

import org.yaml.snakeyaml.Yaml;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class YamlStorage {
    private final Yaml yaml;
    private final Path filePath;
    private Map<String, Object> data;

    /**
     * Mempersiapkan file YAML.
     * @param configDir Folder tempat konfigurasi disimpan (biasanya FabricLoader.getInstance().getConfigDir())
     * @param fileName Nama file tanpa ekstensi .yml
     */
    public YamlStorage(Path configDir, String fileName) {
        this.yaml = new Yaml();
        this.filePath = configDir.resolve(fileName + ".yml");
        this.data = new HashMap<>();
        load();
    }

    /**
     * Memuat data dari file ke dalam memory (Map).
     */
    public void load() {
        try {
            if (!Files.exists(filePath)) {
                Files.createDirectories(filePath.getParent());
                Files.createFile(filePath);
            }
            try (InputStream inputStream = Files.newInputStream(filePath)) {
                Map<String, Object> loadedData = yaml.load(inputStream);
                if (loadedData != null) {
                    this.data = loadedData;
                }
            }
        } catch (IOException e) {
            System.err.println("[GuntapeAPI] Gagal memuat file konfigurasi: " + filePath.toString());
            e.printStackTrace();
        }
    }

    /**
     * Mengambil nilai berdasarkan key.
     */
    public Object get(String key) {
        return data.get(key);
    }

    /**
     * Mengambil nilai dengan tipe data spesifik (Generic).
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key, T defaultValue) {
        Object value = data.get(key);
        return value != null ? (T) value : defaultValue;
    }

    /**
     * Menyimpan nilai ke dalam memory (belum tersimpan di file).
     */
    public void set(String key, Object value) {
        data.put(key, value);
    }

    /**
     * Menyimpan data dari memory secara asinkron agar tidak membebani main thread.
     */
    public void save() {
        // Kita menggunakan GuntapeScheduler agar I/O file berjalan di background
        com.guntape.api.scheduler.GuntapeScheduler.runAsync(() -> {
            try (FileWriter writer = new FileWriter(filePath.toFile())) {
                yaml.dump(data, writer);
            } catch (IOException e) {
                System.err.println("[GuntapeAPI] Gagal menyimpan file konfigurasi: " + filePath.toString());
                e.printStackTrace();
            }
        });
    }
}