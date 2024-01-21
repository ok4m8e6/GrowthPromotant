package com.okamesvr.growthpromotant.growthProcess

import com.okamesvr.growthpromotant.utils.CooldownHandler
import com.okamesvr.growthpromotant.utils.EffectsLib
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.data.Ageable
import org.bukkit.entity.Player
import kotlin.random.Random

class Sugarcane {
    companion object {
        var PROBABILITY = 0.2

        private val cooldownHandler = CooldownHandler()
        val effectsLib = EffectsLib()
    }

    fun processSugarcane(player: Player, block: Block) {
        // クールタイム処理
        if (cooldownHandler.isOnCooldown(player.uniqueId)) {
            return
        }

        // ブロックデータの取得
        val blockData = block.blockData

        if (blockData is Ageable) {
            val topBlock = block.location.add(0.0, 1.0, 0.0).block
            val topTopBlock = topBlock.location.add(0.0, 1.0, 0.0).block

            when {
                topBlock.type == Material.SUGAR_CANE && topTopBlock.type != Material.AIR -> return
                topBlock.type != Material.AIR && topBlock.type != Material.SUGAR_CANE -> return
            }

            // 成長のリセット処理
            if (blockData.age == 15 && topTopBlock.type == Material.AIR) {
                if (topBlock.type == Material.AIR || topBlock.type == Material.SUGAR_CANE) {
                    blockData.age = 0
                }
            }

            if (blockData.age < blockData.maximumAge && Random.nextDouble() < PROBABILITY) {
                blockData.age += 1
                block.blockData = blockData

                val particleLocation = block.location.add(0.5, 0.1, 0.5)
                val endRodLocation = block.location.add(0.5, 0.0, 0.5)
                effectsLib.boneParticl(player, particleLocation,8, 0.2, 0.0, 0.2, 0.03)
                effectsLib.boneSound(player, player.location, 1.0f, 1.5f)

                if (blockData.age == blockData.maximumAge) {
                    if (topBlock.type == Material.SUGAR_CANE && topTopBlock.type == Material.AIR) {
                        topTopBlock.type = Material.SUGAR_CANE
                        blockData.age = 15
                        block.blockData = blockData
                    } else if (topBlock.type == Material.AIR) {
                        topBlock.type = Material.SUGAR_CANE
                        blockData.age = 0
                        block.blockData = blockData
                    }

                    effectsLib.completeGrowthParticle(player, endRodLocation,30, 0.2, 0.3, 0.2, 0.03)
                    effectsLib.fullGrowthSound(player, player.location, 1.0f, 1.5f)

                }
            }
        }
    }
}