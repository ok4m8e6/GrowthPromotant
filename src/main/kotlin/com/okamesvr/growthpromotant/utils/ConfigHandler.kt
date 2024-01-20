package com.okamesvr.growthpromotant.utils

import com.okamesvr.growthpromotant.SneakListener
import com.okamesvr.growthpromotant.growthProcess.Farmland
import com.okamesvr.growthpromotant.growthProcess.Sapling
import com.okamesvr.growthpromotant.growthProcess.Sugarcane
import org.bukkit.configuration.file.FileConfiguration

class ConfigHandler(private var config: FileConfiguration) {
    fun loadConfig() {
        // 共通のプロパティを読み込む
        val probability = config.getDouble("PROBABILITY")
        val boneParticle = config.getBoolean("BoneParticle")
        val completeGrowthParticle = config.getBoolean("CompleteGrowthParticle")
        val boneSound = config.getBoolean("BoneSound")
        val fullGrowthSound = config.getBoolean("FullGrowthSound")

        // 耕地に適用する
        Farmland.PROBABILITY = probability

        // サトウキビに適用する
        Sugarcane.PROBABILITY = probability

        // 苗木に適応する
        Sapling.PROBABILITY = probability

        // EffectsLibに適応する
        EffectsLib.BoneParticle = boneParticle
        EffectsLib.CompleteGrowthParticle = completeGrowthParticle
        EffectsLib.BoneSound = boneSound
        EffectsLib.FullGrowthSound = fullGrowthSound

        // その他
        CooldownHandler.COOLTIME = config.getDouble("COOLTIME").toInt()
        FarmProtect.FarmProtect = config.getBoolean("FarmProtect")
    }

}
