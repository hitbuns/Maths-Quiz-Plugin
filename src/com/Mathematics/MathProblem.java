package com.Mathematics;

import com.sun.istack.internal.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class MathProblem {

    public static int repeattask;
    public BukkitTask endit;
    public int value;
    public String problem;
    public int difficulty;
    public String problem_message;

    public MathProblem() {
        this.problem = Utils.build(this.difficulty = Utils.RNG(1,Math.abs(Main.config.getInt("options.operator_max"))));
        long l = this.difficulty*500L;
        this.endit = Bukkit.getScheduler().runTaskLater(Main.getInstance(), MathProblem::remove,l);
        this.value = (int) Math.round(new Calculator(this.problem).evalExpr());
        System.out.println(this.problem);
        System.out.println(this.value);
        System.out.println(this.difficulty);
        this.problem_message = Utils.color(Main.config.getString("messages.question",
                "%prefix% &eWhat is %problem%? &8(If there is a decimal answer round to the nearest integer)").replace("%prefix%",Main.config.getString("messages.prefix", "&8[&6Math&8]&e"))
        .replace("%problem%",this.problem.replace("*","x")
                .replace("/","÷")));
        Main.getInstance().getServer().broadcastMessage(this.problem_message);
        System.out.println(this.problem_message);
        Bukkit.getScheduler().cancelTask(repeattask);
    }

    public static void remove() {
        if (Main.mathProblem != null) {
            Main.getInstance().getServer().broadcastMessage(Utils.color(Main.config.getString("messages.noonegotitintime",
                    "%prefix% &cNoone answered the question in time! The answer was %value%")
                    .replace("%prefix%", Main.config.getString("messages.prefix", "&8[&6Math&8]&e")
                            ).replace("%value%", String.valueOf(Main.mathProblem.value))));
            Main.mathProblem = null;
            resetTimer(false);
        }
    }

    public static void answered(@NotNull Player p) {
        if (Main.mathProblem != null) {
            Main.getInstance().getServer().broadcastMessage(Utils.color(Main.config.getString("messages.answered",
                    "%prefix% &2%player% &ahas answered the question correctly with the answer &e%value%")
                    .replace("%prefix%", Main.config.getString("messages.prefix", "&8[&6Math&8]&e")).replace("%player%",p.getName()).replace("%value%", String.valueOf(Main.mathProblem.value))));
            double d = 0;
            Main.getEconomy().depositPlayer(p,d = new Calculator(Main.config.getString("options.rewards_formula","50000*%operator_amount%")
            .replace("%operator_amount%",String.valueOf(Main.mathProblem.difficulty))).evalExpr());
            p.sendMessage(Utils.color(Main.config.getString("messages.moneyadd","&6+$%money%")
                    .replace("%money%",String.valueOf(d))));
            Main.mathProblem = null;
            resetTimer(false);
        }
    }

    public static void resetTimer(boolean firsttime) {
        Bukkit.getScheduler().cancelTask(repeattask);
        repeattask = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(),()->{
            if (Bukkit.getOnlinePlayers().size() >= Main.config.getInt("options.players_online_required",1))
            Main.mathProblem = new MathProblem();
        },(firsttime)?300L:12000L,12000L);
    }
}
