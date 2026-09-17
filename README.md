Here is the complete and professional English documentation for your GuntapeAPI GitHub repository. You can copy and paste this directly into your `README.md` file.

```markdown
# 📦 GuntapeAPI

GuntapeAPI is a lightweight API library for Fabric Mods designed to empower developers to implement complex features easily and efficiently, without compromising the server's Main Thread performance (TPS).

This API is built upon the philosophy of **"Simple Design, Easy Integration."**

## ✨ Core Features

1. **YAML Data Storage System**: Effortlessly create, read, and save `.yml` files using the robust *SnakeYAML* library.
2. **Event Communication System**: A seamless cross-plugin communication system via an *Event Bus* (Publisher-Subscriber pattern), eliminating the need for hard-coded mod dependencies.
3. **Background Processing (Scheduler)**: A safe and efficient *Thread Pool* executor to handle heavy tasks (I/O, Web Requests, intensive computations) asynchronously, preventing server lag.

---

## 🚀 Installation (For Developers)

Add the Modrinth repository and the GuntapeAPI dependency to your project's `build.gradle` file:

```gradle
repositories {
    maven {
        name = "Modrinth"
        url = "[https://api.modrinth.com/mven](https://api.modrinth.com/mven)"
    }
}

dependencies {
    // Replace 1.0.0 with the latest version of GuntapeAPI
    modImplementation "maven.modrinth:guntape-api:1.0.0"
}

```

---

## 📚 Usage Examples

### 1. Using YAML Storage

GuntapeAPI automatically handles file creation, directory setup, and background saving processes.

```java
import com.guntape.api.storage.YamlStorage;
import net.fabricmc.loader.api.FabricLoader;

// 1. Initialize configuration (automatically creates "my_mod_config.yml" in the server's config folder)
YamlStorage config = new YamlStorage(FabricLoader.getInstance().getConfigDir(), "my_mod_config");

// 2. Save data (automatically executed asynchronously in the background)
config.set("player_health", 20);
config.set("welcome_message", "Welcome to the server!");
config.save();

// 3. Retrieve data with a fallback default value if the key is not found
int health = config.get("player_health", 20);
String message = config.get("welcome_message", "Hello!");

```

### 2. Using the Event Bus

Utilize this system to allow other mods to react to events within your mod without requiring your mod to be a hard dependency (Soft Dependency).

```java
import com.guntape.api.event.GuntapeEventBus;

// Mod A: Registering a Listener (Subscribing to an Event)
GuntapeEventBus.subscribe(MyCustomEvent.class, event -> {
    System.out.println("Event caught: " + event.getMessage());
});

// Mod B: Triggering/Publishing an Event
GuntapeEventBus.publish(new MyCustomEvent("A player has defeated the boss!"));

```

### 3. Using the Background Scheduler

Employ this scheduler for heavy workloads to ensure the Minecraft server's TPS (Ticks Per Second) remains stable at 20.

```java
import com.guntape.api.scheduler.GuntapeScheduler;

// Executing a pure background task
GuntapeScheduler.runAsync(() -> {
    // Perform heavy tasks here, such as fetching data from a REST API
    String data = fetchFromExternalAPI();
    System.out.println("Data successfully retrieved: " + data);
});

// Executing an asynchronous task and retrieving the result (CompletableFuture)
GuntapeScheduler.supplyAsync(() -> {
    return performComplexCalculation();
}).thenAccept(result -> {
    System.out.println("Calculation completed with result: " + result);
});

```

---

🛠️ Compatibility

* **Mod Loader:** Fabric
* **Minecraft Version:** 1.20.x - 1.21.x (Please refer to the *Versions* tab on Modrinth for specifics)
* **Java:** Java 21+

## 📄 License

GuntapeAPI is released under the **MIT License**. You are free to use, modify, and redistribute this code in both open-source and commercial projects.

```

```
