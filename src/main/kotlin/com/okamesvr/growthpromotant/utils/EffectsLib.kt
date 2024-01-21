package com.okamesvr.growthpromotant.utils

import org.bukkit.*
import org.bukkit.entity.Player

class EffectsLib {
    companion object {
        var BoneParticle = true
        var CompleteGrowthParticle = true
        var BoneSound = true
        var FullGrowthSound = true

        val BoneParticleType = Particle.VILLAGER_HAPPY
        val CompleteGrowthParticleType = Particle.END_ROD
        val BoneSoundType = Sound.ITEM_BONE_MEAL_USE
        val FullGrowthSoundType = Sound.ENTITY_PLAYER_LEVELUP
        val warningSoundType = Sound.BLOCK_ANVIL_USE
    }

    fun boneParticl(player: Player, location: Location, count: Int, offsetX: Double, offsetY: Double, offsetZ: Double, speed: Double) {
        if (BoneParticle) {
            player.world.spawnParticle(BoneParticleType, location, count, offsetX, offsetY, offsetZ, speed)
        }
    }

    fun completeGrowthParticle(player: Player, location: Location, count: Int, offsetX: Double, offsetY: Double, offsetZ: Double, speed: Double) {
        if (CompleteGrowthParticle) {
            player.world.spawnParticle(CompleteGrowthParticleType, location, count, offsetX, offsetY, offsetZ, speed)
        }
    }

    fun boneSound(player: Player, location: Location, volume: Float, pitch: Float) {
        if (BoneSound) {
            player.playSound(location, BoneSoundType, volume, pitch)
        }
    }

    fun fullGrowthSound(player: Player, location: Location, volume: Float, pitch: Float) {
        if (FullGrowthSound) {
            player.playSound(location, FullGrowthSoundType, volume, pitch)
        }
    }

    fun warningSound(player: Player, location: Location, volume: Float, pitch: Float) {
        player.playSound(location, warningSoundType, volume, pitch)
    }
}