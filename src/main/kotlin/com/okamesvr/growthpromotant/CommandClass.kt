package com.okamesvr.growthpromotant

import com.okamesvr.growthpromotant.utils.ConfigHandler
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class CommandClass(private val growthPromotant: GrowthPromotant) : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isNotEmpty()) {
            when (args[0]) {
                "reload" -> {
                    growthPromotant.reloadConfig()
                    ConfigHandler(growthPromotant.config).loadConfig()
                    sender.sendMessage("§a設定の再読み込みが完了しました")
                    return true
                }

                /*
                "setProbability" -> {
                    if (args.size > 1) {
                        SneakListener.PROBABILITY = args[1].toDouble()
                        sender.sendMessage("Probability の値を変更しました。 ${args[1]}")
                        return true
                    }
                }
                */
            }
        }
        return false
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String>? {
        if (args.size == 1) {
            return mutableListOf(
                "reload"
            )
        }
        return null
    }

}
