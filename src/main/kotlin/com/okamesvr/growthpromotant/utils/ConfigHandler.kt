package com.okamesvr.growthpromotant.utils

import com.okamesvr.growthpromotant.SneakListener
import org.bukkit.configuration.file.FileConfiguration

class ConfigHandler(private var config: FileConfiguration) {
    fun loadConfig() {
        SneakListener.PROBABILITY = config.getDouble("PROBABILITY")
        CooldownHandler.COOLTIME = config.getDouble("COOLTIME").toInt()
        SneakListener.BoneParticle = config.getBoolean("BoneParticle")
        SneakListener.CompleteGrowthParticle = config.getBoolean("CompleteGrowthParticle")
        SneakListener.BoneSound = config.getBoolean("BoneSound")
        SneakListener.FullGrowthSound = config.getBoolean("FullGrowthSound")
        FarmProtect.FarmProtect = config.getBoolean("FarmProtect")
    }
}
