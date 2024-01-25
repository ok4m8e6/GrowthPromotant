package com.okamesvr.growthpromotant

import com.okamesvr.growthpromotant.growthProcess.Farmland
import com.okamesvr.growthpromotant.growthProcess.Sapling
import com.okamesvr.growthpromotant.growthProcess.Sugarcane
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerToggleSneakEvent

class SneakListener : Listener {
    @EventHandler
    fun onPlayerToggleSneak(event: PlayerToggleSneakEvent) {
        val player = event.player
        val playerLocation = player.location
        val farmlandBlock = playerLocation.subtract(0.0, 0.9375, 0.0).block
        val block = playerLocation.subtract(0.0, -1.0, 0.0).block

        if (farmlandBlock.type == Material.AIR || block.type == Material.AIR) {
            return
        }

        if (farmlandBlock.type == Material.FARMLAND) {
            Farmland().processFarmland(player, farmlandBlock)
        }

        if (block.type == Material.SUGAR_CANE) {
            Sugarcane().processSugarcane(player, block)
        }

        if (
            block.type == Material.OAK_SAPLING ||
            block.type == Material.SPRUCE_SAPLING ||
            block.type == Material.BIRCH_SAPLING ||
            block.type == Material.JUNGLE_SAPLING ||
            block.type == Material.ACACIA_SAPLING ||
            block.type == Material.DARK_OAK_SAPLING ||
            block.type == Material.CHERRY_SAPLING
            ) {
            Sapling().processSapling(player, block)
        }
    }
}