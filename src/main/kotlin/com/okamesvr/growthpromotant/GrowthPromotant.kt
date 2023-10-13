package com.okamesvr.growthpromotant

import org.bukkit.plugin.java.JavaPlugin

class GrowthPromotant : JavaPlugin() {
    override fun onEnable() {
        saveDefaultConfig()
        SneakListener.PROBABILITY = config.getDouble("PROBABILITY")
        SneakListener.COOLTIME = config.getDouble("COOLTIME").toInt()
        SneakListener.BoneParticle = config.getBoolean("BoneParticle")
        SneakListener.CompleteGrowthParticle = config.getBoolean("CompleteGrowthParticle")
        SneakListener.BoneSound = config.getBoolean("BoneSound")
        SneakListener.FullGrowthSound = config.getBoolean("FullGrowthSound")
        server.pluginManager.registerEvents(SneakListener(), this)
        // Plugin startup logic
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
