package com.okamesvr.growthpromotant

import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.block.data.Ageable
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerToggleSneakEvent
import java.util.UUID
import kotlin.random.Random

class SneakListener : Listener {
    companion object {
        var PROBABILITY = 0.2
        var COOLTIME = 0
        var BoneParticle = true
        var CompleteGrowthParticle = true
        var BoneSound = true
        var FullGrowthSound = true
        private val sneakCooldown = HashMap<UUID, Long>()
    }
    @EventHandler
    fun onPlayerToggleSneak(event: PlayerToggleSneakEvent) {
        val player = event.player
        val playerLocation = player.location
        val block = playerLocation.subtract(0.0, 0.9375, 0.0).block


        if (block.type == Material.FARMLAND) {
            // クールタイム処理
            val currentTime = System.currentTimeMillis()

            if (sneakCooldown.containsKey(player.uniqueId)) {
                val lastSneakTime = sneakCooldown[player.uniqueId]
                if (lastSneakTime != null && currentTime - lastSneakTime < COOLTIME) {
                    return
                }
            }
            sneakCooldown[player.uniqueId] = currentTime

            val aboveBlock = block.location.add(0.0, 1.0, 0.0).block
            val blockData = aboveBlock.blockData

            // 確率&成長処理
            if (blockData is Ageable) {
                if (blockData.age < blockData.maximumAge && Random.nextDouble() < PROBABILITY) {
                    blockData.age += 1
                    aboveBlock.blockData = blockData

                    val particleLocation = aboveBlock.location.add(0.5, 0.1, 0.5)
                    val endRodLocation = aboveBlock.location.add(0.5, 0.0, 0.5)

                    if (BoneParticle) {
                        player.world.spawnParticle(Particle.VILLAGER_HAPPY, particleLocation, 8, 0.2, 0.0, 0.2)
                    }
                    if (BoneSound) {
                        player.playSound(playerLocation, Sound.ITEM_BONE_MEAL_USE, 1.0f, 1.5f)
                    }

                    if (blockData.age == blockData.maximumAge) {
                        if (FullGrowthSound) {
                            player.playSound(playerLocation, Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.5f)
                        }
                        if (CompleteGrowthParticle) {
                            player.world.spawnParticle(Particle.END_ROD, endRodLocation, 30, 0.2, 0.3, 0.2, 0.03)
                        }
                    }
                }
            }
        }
    }
}