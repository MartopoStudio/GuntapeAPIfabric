package com.guntape.api;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Nantinya kita akan meng-import ini setelah class-nya dibuat
// import com.guntape.api.scheduler.GuntapeScheduler;

public class GuntapeAPI implements ModInitializer {
	// Ubah mod_id sesuai dengan yang ada di fabric.mod.json
	public static final String MOD_ID = "guntape_api";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		LOGGER.info("GuntapeAPI sedang diinisialisasi...");

		/*
		 * Catatan: Nanti setelah class GuntapeScheduler kita buat,
		 * hapus komentar (//) pada blok kode di bawah ini
		 * agar Thread Pool dimatikan dengan aman saat server mati.
		 */

		// ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
		//     GuntapeScheduler.shutdown();
		//     LOGGER.info("GuntapeAPI Thread Pool dimatikan dengan aman.");
		// });

		LOGGER.info("GuntapeAPI berhasil dimuat!");
	}

	// Fungsi utilitas yang sangat berguna bagi developer lain
	// untuk membuat Identifier (Namespace Minecraft) dengan mudah.
	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}