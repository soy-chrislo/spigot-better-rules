package com.soychris.comandoreglas;

import com.soychris.comandoreglas.Comandos.ReglasCommand;
import com.soychris.comandoreglas.Comandos.ReglasFinalizadoCommand;
import com.soychris.comandoreglas.Comandos.ReglasReloadCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class ComandoReglas extends JavaPlugin {

    @Override
    public void onEnable() {
        setupConfig();
        getCommand("reglas").setExecutor(new ReglasCommand());
        getCommand("reglasfinalizado").setExecutor(new ReglasFinalizadoCommand());
        getCommand("reglasreload").setExecutor(new ReglasReloadCommand());
    }

    public void setupConfig(){
        this.saveDefaultConfig();
        this.getConfig().options().copyDefaults(true);
        if (!(this.getConfig().contains("reglas"))){
            this.getConfig().set("reglas", null);
        }
        this.saveConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
