package com.Mathematics;

import com.Mathematics.EventListener.ChatListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class Main extends JavaPlugin {

    public static MathProblem mathProblem;
    public static Map<Integer,Operator> operatorMap = new HashMap<>();
    private static Main Instance;
    public static Config config;
    private static Economy econ;

    @Override
    public void onEnable(){
        Instance = this;
        if (!setupEconomy() ) {
            getLogger().info("No Vault Detected! Disabling Plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        for (Operator operator : Operator.values()) {
            operatorMap.put(operator.id,operator);
        }
        MathProblem.resetTimer(true);
        config = new Config(this,getDataFolder(),"config.yml","default.yml");
        getServer().getPluginManager().registerEvents(new ChatListener(),this);
    }

    public static Main getInstance() {
        return Instance;
    }

    @Override
    public void onDisable() {
        if (mathProblem != null) mathProblem.endit.cancel();
        getServer().getScheduler().cancelTask(MathProblem.repeattask);
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }
}
