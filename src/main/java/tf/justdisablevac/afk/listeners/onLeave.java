package tf.justdisablevac.afk.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import tf.justdisablevac.afk.main;

public class onLeave implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {

        Player player = event.getPlayer();
        Bukkit.getScheduler().cancelTask(main.plugin.taskID.get(player)); //end the task because its unnecessary to run after the player has left

    }

}
