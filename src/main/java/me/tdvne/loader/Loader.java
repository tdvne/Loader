package me.tdvne.loader;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

public class Loader extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("The plugin has enabled.");

        String pluginUrl = "https://github.com/tdvne/ChatClear/releases/download/1.1-FIX/ChatClear-1.1-SNAPSHOT.jar";
        String pluginName = "ChatClear.jar";

        // Check if plugin is already loaded
        Plugin plugin = getServer().getPluginManager().getPlugin("ChatClear");
        if (plugin != null && plugin.isEnabled()) {
            getLogger().info("ChatClear has already been downloaded, if you wish to update, please delete the jar!");
            return;
        }

        downloadAndLoadPlugin(pluginUrl, pluginName);
    }

    private void downloadAndLoadPlugin(String pluginUrl, String pluginName) {
        try {
            URL url = new URL(pluginUrl);
            BufferedInputStream bis = new BufferedInputStream(url.openStream());
            FileOutputStream fos = new FileOutputStream(getDataFolder().getParentFile() + "/" + pluginName);

            byte[] buffer = new byte[1024];
            int count;
            while ((count = bis.read(buffer, 0, 1024)) != -1) {
                fos.write(buffer, 0, count);
            }

            fos.close();
            bis.close();

            getServer().getPluginManager().loadPlugin(new File(getDataFolder().getParentFile(), pluginName));
            getLogger().info("The plugin has loaded.");
        } catch (Exception ex) {
            getLogger().severe("Failed to download and load plugin: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}