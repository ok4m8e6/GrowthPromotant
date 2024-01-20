package com.okamesvr.growthpromotant.growthProcess

import com.okamesvr.growthpromotant.utils.CooldownHandler
import com.okamesvr.growthpromotant.utils.EffectsLib
import org.bukkit.block.Block
import org.bukkit.block.data.Ageable
import org.bukkit.entity.Player
import kotlin.random.Random

class Farmland {
    companion object {
        var PROBABILITY = 0.2
        private val cooldownHandler = CooldownHandler()
        private val effectsLib = EffectsLib()
    }

    fun processFarmland(player: Player, farmlandBlock: Block) {
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

                effectsLib.boneParticl(player, particleLocation,8, 0.2, 0.0, 0.2, 0.03)
                effectsLib.boneSound(player, player.location, 1.0f, 1.5f)

                if (blockData.age == blockData.maximumAge) {

                    effectsLib.completeGrowthParticle(player, endRodLocation,30, 0.2, 0.3, 0.2, 0.03)
                    effectsLib.fullGrowthSound(player, player.location, 1.0f, 1.5f)

                }
            }
        }
    }
}
