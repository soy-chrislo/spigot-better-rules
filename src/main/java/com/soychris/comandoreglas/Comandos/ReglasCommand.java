package com.soychris.comandoreglas.Comandos;

import com.soychris.comandoreglas.ComandoReglas;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Comentarios de una sola linea: Ctrl + /
 * Comentarios de varias lineas: Ctrl + Shift + /
 *
 * NECESITAMOS:
 * 1. Comprobar la pagina pedida por el usuario.
 * 2. A partir de las reglas, paginar 5 reglas por pagina.
 *
 *  SINTAXIS NI API SPIGOT
 * - LOGICA DE PROGRAMACION
 *
 * NECESITAMOS PARA RANGOS:
 * 1. Crear un HashMap con los rangos y sus reglas.
 * 2. Comprobar el rango del usuario.
 * 3. Manipular estructuras de datos (List, Map) Arreglos y Llave - Valor.
 *
 * NECESITAMOS PARA LAS REGLAS DINAMICAS (CONFIG.YML)
 * 1. Setup del config.yml
 * 2. Leer el config.yml
 * 3. Realizar comprobacion y guardar valores.
 *
 * */

public class ReglasCommand implements CommandExecutor {
    public static final String GENERAL = "general";
    public static final String VIP = "VIP";
    public static final String STAFF = "staff";
    Player player;
    List<String> reglasGeneral, reglasVip, reglasStaff;
    Map<String, List<String>> reglas;
    String numeroPagina;
    ComandoReglas plugin;
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return true;
        plugin = (ComandoReglas) Bukkit.getPluginManager().getPlugin("ComandoReglas");
        if (strings.length == 0){
            numeroPagina = "1";
        } else {
            numeroPagina = strings[0];
        }

        // Casting (polimorfismo)
        player = (Player) commandSender;
        reglas = new HashMap<>();

        reglasGeneral = new ArrayList<>();
        List<String> reglasGeneralConfig = plugin.getConfig().getStringList("reglas.general");
        if (reglasGeneralConfig.isEmpty()){
            plugin.getLogger().info("No hay reglas generales que mostrar en el archivo de configuracion");
        } else {
            reglasGeneral.addAll(reglasGeneralConfig);
        }

        reglas.put(GENERAL, reglasGeneral);

        reglasVip = new ArrayList<>();
        List<String> reglasVipConfig = plugin.getConfig().getStringList("reglas.vip");
        if (reglasVipConfig.isEmpty()){
            plugin.getLogger().info("No hay reglas vip que mostrar en el archivo de configuracion");
        } else {
            reglasVip.addAll(reglasVipConfig);
        }

        reglas.put(VIP, reglasVip);

        reglasStaff = new ArrayList<>();
        List<String> reglasStaffConfig = plugin.getConfig().getStringList("reglas.staff");
        if (reglasStaffConfig.isEmpty()){
            plugin.getLogger().info("No hay reglas staff que mostrar en el archivo de configuracion");
        } else {
            reglasStaff.addAll(reglasStaffConfig);
        }

        reglas.put(STAFF, reglasStaff);

        // DECLARATIVA
//        reglas.forEach(regla -> player.sendMessage(regla));

        //reglas.forEach(player::sendMessage);

        // IMPERATIVA
        /*for (int i = 0; i < reglas.size(); i++){
            player.sendMessage(reglas.get(i));
        }*/

        return showRules();
    }

    boolean showRules(){
        player.sendMessage("");
        //player.sendMessage("----------- " + ChatColor.GOLD + "" + ChatColor.BOLD + "REGLAS" + ChatColor.RESET + " -----------");
        String header = ChatColor.translateAlternateColorCodes('&', " ----------- &6&lREGLAS &r -----------");
        player.sendMessage(header);
        player.sendMessage("");

        List<String> reglasFinales = reglas.get(GENERAL);

        if (player.hasPermission("reglas.vip")){
            reglasFinales.addAll(reglas.get(VIP));
        }
        if (player.hasPermission("reglas.staff")){
            reglasFinales.addAll(reglas.get(STAFF));
        }

        for (int i = 0; i < reglasFinales.size(); i++){
            String regla = reglasFinales.get(i);
            String reglaEnumerada = (i + 1) + ". " + regla;
            reglasFinales.set(i, reglaEnumerada);
        }

        int contadorPaginas = (reglasFinales.size() / 5) + 1;
        if (Integer.parseInt(numeroPagina) > contadorPaginas){
            player.sendMessage(ChatColor.RED + " No hay mas paginas");
            player.sendMessage("");
            player.sendMessage("--------- --------");
            return true;
        }

        if (numeroPagina.isEmpty()){
            numeroPagina = "1";
            for (int i = 0; i < 5; i++){
                player.sendMessage(reglasFinales.get(i));
            }
        } else {
            int pagina = Integer.parseInt(numeroPagina);
            int indiceInicio = (pagina - 1) * 5;
            int indiceFinal = indiceInicio + 5;

            for (int i = indiceInicio; i < indiceFinal; i++){
                if (i >= reglasFinales.size()) break;
                player.sendMessage(reglasFinales.get(i));
            }
        }

        player.sendMessage("");
        player.sendMessage("----------- PAGINA " + numeroPagina + " DE " + contadorPaginas +   " -----------");
        player.sendMessage("");

        return true;
    }
}
