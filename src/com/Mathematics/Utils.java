package com.Mathematics;


import org.bukkit.ChatColor;

public class Utils {
    
    public static int RNG(int min,int max) {
        return (int) Math.round(((max-min)*Math.random())+min);
    }
    
    public static String build(int operators) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Math.abs(RNG(Main.config.getInt("options.number_min",1),Main.config.getInt("options.number_max",
                5000))));
        for (int i = 0; i<operators; i++)
            stringBuilder.append(Main.operatorMap.getOrDefault(RNG(1,4),Operator.ADDITION).symbol).append(Math.abs(RNG(Main.config.getInt("options.number_min",1),Main.config.getInt("options.number_max",
                    5000))));
        return stringBuilder.toString();
    }


    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&',s);
    }


}
