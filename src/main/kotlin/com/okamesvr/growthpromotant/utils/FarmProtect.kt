package com.okamesvr.growthpromotant.utils

import org.bukkit.Material
import org.bukkit.entity.LivingEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.EntityChangeBlockEvent
import org.bukkit.event.player.PlayerInteractEvent

class FarmProtect : Listener {
    companion object {
        var FarmProtect = true
    }
    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        if (FarmProtect) {
            if (event.action == Action.PHYSICAL && event.clickedBlock?.type == Material.FARMLAND) {
                event.isCancelled = true
            }
        }
    }
    @EventHandler
    fun onAnimalTrampling(event: EntityChangeBlockEvent) {
        val entity = event.entity
        if (entity is LivingEntity && event.block.type == Material.FARMLAND) {
            event.isCancelled = true
        }
    }
}
