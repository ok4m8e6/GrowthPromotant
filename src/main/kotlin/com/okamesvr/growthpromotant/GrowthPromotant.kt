package com.okamesvr.growthpromotant

import com.okamesvr.growthpromotant.utils.ConfigHandler
import com.okamesvr.growthpromotant.utils.FarmProtect
import org.bukkit.plugin.java.JavaPlugin

class GrowthPromotant : JavaPlugin() {
    override fun onEnable() {
        saveDefaultConfig()
        ConfigHandler(config).loadConfig()
        server.pluginManager.registerEvents(SneakListener(), this)
        server.pluginManager.registerEvents(FarmProtect(), this)
        getCommand("growpromo")?.setExecutor(CommandClass(this))
        
        // Plugin startup logic
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
