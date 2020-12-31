package tf.justdisablevac.afk;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import tf.justdisablevac.afk.listeners.onJoin;
import tf.justdisablevac.afk.listeners.onLeave;

import java.util.HashMap;

public final class main extends JavaPlugin {

    public static main plugin;
    public HashMap<Player, Integer> taskID = new HashMap<>();

    @Override
    public void onEnable() {
        plugin = this;
        getServer().getPluginManager().registerEvents(new onJoin(), this);
        getServer().getPluginManager().registerEvents(new onLeave(), this);
    }
}
