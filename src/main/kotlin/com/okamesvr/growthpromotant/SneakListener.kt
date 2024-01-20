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
        //TODO blockなどがAIRの時処理を終了してパフォーマンスを最適化
        if (farmlandBlock.type == Material.FARMLAND) {
            Farmland().processFarmland(player, farmlandBlock)
        }

        if (block.type == Material.SUGAR_CANE) {
            Sugarcane().processSugarcane(player, block)
        }

        if (block.type == Material.OAK_SAPLING) {
            Sapling().processSapling(player, block)
        }
    }
}