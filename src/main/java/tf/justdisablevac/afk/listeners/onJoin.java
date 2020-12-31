package tf.justdisablevac.afk.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.omg.CORBA.TIMEOUT;
import tf.justdisablevac.afk.main;

import java.util.HashMap;

public class onJoin implements Listener {

    HashMap<Player, Location> oldLocation = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        int TIMEOUT = 4; //minutes (+1) that the player has to stand afk for
        String kickReason = "You have been kicked for idling more than " + (TIMEOUT - 1) + " minutes."; //kick reason

        int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(main.plugin, new Runnable() {

            int count = 0;

            @Override
            public void run() {

                count++; //make the counter 1 minute already (=> count = 1)

                if(count == TIMEOUT) { //if counter is equal to TIMEOUT (in minutes)

                    if(player.getLocation().equals(oldLocation.get(player))) { //check if the player's current location is equal to the original location

                        player.kickPlayer(kickReason); //if so, kick the player
                        //Bukkit.broadcastMessage(player.getName() + " was kicked for idling"); //and announce it to the server
                        count = 1; //and reset their counter

                        for(Player opPlayers : Bukkit.getOnlinePlayers()) { //announce to OPs that a player was kicked
                            if(opPlayers.isOp()) {
                                opPlayers.sendMessage("§7§o[Server: Kicked " + player.getName() + ": " + kickReason + "]");
                            }
                        }

                    } else { //otherwise, the player has moved since the original location was captured

                        oldLocation.put(player, player.getLocation()); //reset the original location
                        count = 1; //and reset their counter

                    }

                } else if(count == 1) { //if the count is 1

                    oldLocation.put(player, player.getLocation()); //reset the original location

                } else { //if the count is anything inbetween

                    if(!player.getLocation().equals(oldLocation.get(player))) { //check if the player has moved since the original location was captured

                        oldLocation.put(player, player.getLocation()); //if they have, reset the original location
                        count = 1; //and reset their counter

                    }

                }

            }
        }, 0L, 1200L); //every 1 minute

        main.plugin.taskID.put(player, taskID); //capture the taskID so we can kill it when the player leaves

    }

}
