package com.okamesvr.growthpromotant.growthProcess

import com.okamesvr.growthpromotant.utils.CooldownHandler
import com.okamesvr.growthpromotant.utils.EffectsLib
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.TreeType
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

class Sapling {
    companion object {
        var PROBABILITY = 0.2

        private val cooldownHandler = CooldownHandler()

        // 現段階でconfigでの編集不許可
        private const val MAX_AGE = 15
        val saplingAges = HashMap<Location, Int>()

        val effectsLib = EffectsLib()
    }

    fun processSapling(player: Player, block: Block) {
        if (cooldownHandler.isOnCooldown(player.uniqueId)) {
            return
        }

        val particleLocation = block.location.add(0.5, 0.1, 0.5)
        val endRodLocation = block.location.add(0.5, 0.0, 0.5)

        val age = saplingAges.getOrDefault(block.location, 0)
        if (age < MAX_AGE && Random.nextDouble() < PROBABILITY) {
            val newAge = age + 1
            saplingAges[block.location] = newAge

            effectsLib.boneParticl(player, particleLocation,8, 0.2, 0.0, 0.2, 0.03)
            effectsLib.boneSound(player, player.location, 1.0f, 1.5f)
        }

        if (age == MAX_AGE) {
            block.type = Material.AIR
            block.world.generateTree(block.location, TreeType.TREE)
            saplingAges.remove(block.location)

            // 成長スペースがない場合に苗木のみ消える仕様を修正
            if (block.type == Material.AIR) {
                val itemStack = ItemStack(Material.OAK_SAPLING)
                block.world.dropItemNaturally(block.location, itemStack)

                // 警告の送信
                effectsLib.warningSound(player, player.location, 1.0f, 1.0f)
                val component = TextComponent("スペースがありません")
                component.color = ChatColor.RED
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, component)

            } else {
                effectsLib.completeGrowthParticle(player, endRodLocation, 30, 0.2, 0.3, 0.2, 0.03)
                effectsLib.fullGrowthSound(player, player.location, 1.0f, 1.5f)
            }
        }
    }
}