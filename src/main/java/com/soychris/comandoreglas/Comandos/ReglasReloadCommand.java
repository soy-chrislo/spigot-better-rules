package com.soychris.comandoreglas.Comandos;

import com.soychris.comandoreglas.ComandoReglas;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReglasReloadCommand implements CommandExecutor {
    ComandoReglas plugin;
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        plugin = (ComandoReglas) Bukkit.getPluginManager().getPlugin("ComandoReglas");
        plugin.reloadConfig();
        commandSender.sendMessage("Configuracion recargada");
        return true;
    }
}
