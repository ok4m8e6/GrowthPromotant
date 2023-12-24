package com.okamesvr.growthpromotant.utils

import java.util.UUID

class CooldownHandler {
    companion object {
        var COOLTIME = 0
    }

    private val cooldowns = HashMap<UUID, Long>()

    fun isOnCooldown(id: UUID): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastTime = cooldowns[id]

        return if (lastTime != null && currentTime - lastTime < COOLTIME) {
            true
        } else {
            cooldowns[id] = currentTime
            false
        }
    }
}