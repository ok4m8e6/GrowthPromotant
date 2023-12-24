package com.okamesvr.growthpromotant

import com.okamesvr.growthpromotant.utils.CooldownHandler
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.block.data.Ageable
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerToggleSneakEvent
import kotlin.random.Random

class SneakListener : Listener {
    companion object {
        var PROBABILITY = 0.2
        var BoneParticle = true
        var CompleteGrowthParticle = true
        var BoneSound = true
        var FullGrowthSound = true
        private val cooldownHandler = CooldownHandler()
    }
    @EventHandler
    fun onPlayerToggleSneak(event: PlayerToggleSneakEvent) {
        val player = event.player
        val playerLocation = player.location
        val farmlandBlock = playerLocation.subtract(0.0, 0.9375, 0.0).block
        val block = playerLocation.subtract(0.0, -1.0, 0.0).block

        // 農地作物の処理
        if (farmlandBlock.type == Material.FARMLAND) {
            // クールタイム処理
            if (cooldownHandler.isOnCooldown(player.uniqueId)) {
                return
            }

            val aboveBlock = farmlandBlock.location.add(0.0, 1.0, 0.0).block
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
        // サトウキビの処理
        if (block.type == Material.SUGAR_CANE) {

            // クールタイム処理
            if (cooldownHandler.isOnCooldown(player.uniqueId)) {
                return
            }

            // ブロックデータの取得
            val aboveBlock = block.location.add(0.0, 0.0, 0.0).block
            val blockData = aboveBlock.blockData

            if (blockData is Ageable) {

                val topBlock = aboveBlock.location.add(0.0, 1.0, 0.0).block
                val topTopBlock = topBlock.location.add(0.0, 1.0, 0.0).block

                // 成長のリセット処理
                if (blockData.age == 15 && topTopBlock.type == Material.AIR) {
                    if (topBlock.type == Material.AIR || topBlock.type == Material.SUGAR_CANE) {
                        blockData.age = 0
                    }
                }

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
                        // 修正必須
                        if (topBlock.type == Material.SUGAR_CANE && topTopBlock.type != Material.SUGAR_CANE) {
                            topTopBlock.type = Material.SUGAR_CANE
                            blockData.age = 15
                            aboveBlock.blockData = blockData
                        } else if (topBlock.type != Material.SUGAR_CANE && topTopBlock.type != Material.SUGAR_CANE) {
                            topBlock.type = Material.SUGAR_CANE
                            blockData.age = 0
                            aboveBlock.blockData = blockData
                        }

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