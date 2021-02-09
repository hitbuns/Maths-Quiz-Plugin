package com.Mathematics.EventListener;

import com.Mathematics.Main;
import com.Mathematics.MathProblem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e) {
        if (Main.mathProblem != null) {
            try {
                int attempt = Integer.parseInt(e.getMessage());
                if (attempt == Main.mathProblem.value) {
                    e.setCancelled(true);
                    MathProblem.answered(e.getPlayer());
                }
            } catch (Exception exception) {}
        }
    }

}
