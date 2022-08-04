package red.jad.randomspawn;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public final class RandomSpawn extends JavaPlugin implements Listener {

    private final Random rand = new Random();
    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    private Location getRandomLocation(World w){
        int radius = this.getConfig().getInt("radius");
        int x = rand.nextInt(radius*2) - radius;
        int z = rand.nextInt(radius*2) - radius;
        return new Location(w, x, w.getHighestBlockYAt(x, z), z);
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        Player p = event.getPlayer();
        if(!p.hasPlayedBefore()){
            Location randLoc = getRandomLocation(p.getWorld());
            if(!randLoc.getChunk().isLoaded()) randLoc.getChunk().load(true);
            p.teleport(randLoc);
        }
    }

    @EventHandler
    public void onRespawn(final PlayerRespawnEvent event) {
        if(!event.isBedSpawn()){
            Location randLoc = getRandomLocation(event.getPlayer().getWorld());
            if(!randLoc.getChunk().isLoaded()) randLoc.getChunk().load(true);
            event.setRespawnLocation(randLoc);
        }
    }
}
