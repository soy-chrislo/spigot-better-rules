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
 * NECESITAMOS PARA PAGINACION:
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
 * */

public class ReglasFinalizadoCommand implements CommandExecutor {
    public static final String GENERAL = "general";
    public static final String VIP = "vip";
    public static final String STAFF = "staff";
    Player player;
    Map<String, List<String>> reglas;
    List<String> reglasGeneral, reglasVip, reglasStaff;
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
        List<String> reglasGeneralConfig = (List<String>) plugin.getConfig().getList("reglas.general");
        if (reglasGeneralConfig != null){
            reglasGeneral.addAll(reglasGeneralConfig);
            plugin.getLogger().info("Reglas general cargadas");
        } else {
            reglasGeneral.add("No hay reglas generales en el archivo de configuracion");
        }
//        reglasGeneral.add("Ser respetuoso");
//        reglasGeneral.add("Ser amable");
//        reglasGeneral.add("Ser amistoso");
//        reglasGeneral.add("Ser buena gente");
//        reglasGeneral.add("Ser chevere");
//        reglasGeneral.add("Ser buena onda");
//        reglasGeneral.add("Ser humilde");

        reglas.put(GENERAL, reglasGeneral);

        reglasVip = new ArrayList<>();
        List<String> reglasVipConfig = (List<String>) plugin.getConfig().getList("reglas.vip");
        if (reglasVipConfig != null){
            reglasVip.addAll(reglasVipConfig);
            plugin.getLogger().info("Reglas vip cargadas");
        } else {
            reglasVip.add("No hay reglas vip en el archivo de configuracion");
        }
//        reglasVip.add("Ser todavia mas respetuoso");
//        reglasVip.add("Ser todavia mas amable");
//        reglasVip.add("Ser todavia mas amistoso");
//        reglasVip.add("Ser todavia mas buena gente");
//        reglasVip.add("Ser todavia mas chevere");
//        reglasVip.add("Ser todavia mas buena onda");
//        reglasVip.add("Ser todavia mas humilde");

        reglas.put(VIP, reglasVip);

        reglasStaff = new ArrayList<>();
        List<String> reglasStaffConfig = (List<String>) plugin.getConfig().getList("reglas.staff");
        if (reglasStaffConfig != null){
            reglasStaff.addAll(reglasStaffConfig);
            plugin.getLogger().info("Reglas staff cargadas");
        } else {
            reglasStaff.add("No hay reglas staff en el archivo de configuracion");
        }
//        reglasStaff.add("Ser muchisimo mas respetuoso");
//        reglasStaff.add("Ser muchisimo mas amable");
//        reglasStaff.add("Ser muchisimo mas amistoso");
//        reglasStaff.add("Ser muchisimo mas buena gente");
//        reglasStaff.add("Ser muchisimo mas chevere");
//        reglasStaff.add("Ser muchisimo mas buena onda");
//        reglasStaff.add("Ser muchisimo mas humilde");

        reglas.put(STAFF, reglasStaff);

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
            // SIEMPRE pasa por este flujo por que numeroPagina siempre tiene un valor.
            int pagina = Integer.parseInt(numeroPagina);
            int indiceInicio = (pagina - 1) * 5;
            int indiceFinal = indiceInicio + 5;

            for (int i = indiceInicio; i < indiceFinal; i++){
                if (i >= reglasFinales.size()) break;
                player.sendMessage(reglas.get(GENERAL).get(i));
            }
        }



        // DECLARATIVA
//        reglas.forEach(regla -> player.sendMessage(regla));

        //reglas.forEach(player::sendMessage);

        // IMPERATIVA
        /*for (int i = 0; i < reglas.size(); i++){
            player.sendMessage(reglas.get(i));
        }*/

        player.sendMessage("");
        player.sendMessage("----------- PAGINA " + numeroPagina + " DE " + contadorPaginas +   " -----------");
        player.sendMessage("");

        return true;
    }
}
