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

        val saplingID = block.type

        val treeType = when (block.type) {
            Material.OAK_SAPLING -> TreeType.TREE
            Material.SPRUCE_SAPLING -> TreeType.REDWOOD
            Material.BIRCH_SAPLING -> TreeType.BIRCH
            Material.JUNGLE_SAPLING -> TreeType.COCOA_TREE
            Material.ACACIA_SAPLING -> TreeType.ACACIA
            Material.CHERRY_SAPLING -> TreeType.CHERRY
            else -> return
        }

        val bigTreeType = when (block.type) {
            Material.OAK_SAPLING -> TreeType.BIG_TREE
            Material.SPRUCE_SAPLING -> TreeType.TALL_REDWOOD
            Material.BIRCH_SAPLING -> TreeType.TALL_BIRCH
            Material.JUNGLE_SAPLING -> TreeType.COCOA_TREE
            Material.ACACIA_SAPLING -> TreeType.ACACIA
            Material.CHERRY_SAPLING -> TreeType.CHERRY
            else -> return
        }

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
            val randomValue = Random.nextDouble()

            // 確率で巨大な木が成長する処理の追加
            if (randomValue <= 0.1) {
                block.world.generateTree(block.location, bigTreeType)
                if (block.type == Material.AIR) {
                    block.world.generateTree(block.location, treeType)
                }
            } else {
                block.world.generateTree(block.location, treeType)
            }

            saplingAges.remove(block.location)

            // 成長スペースがない場合に苗木のみ消える仕様を修正
            if (block.type == Material.AIR) {
                val itemStack = ItemStack(saplingID)
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