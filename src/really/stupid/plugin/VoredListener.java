package really.stupid.plugin;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.logging.Logger;

public class VoredListener implements Listener {
    HashMap<Player, Integer> voreCount = new HashMap<>();


    private final Logger logger;
    private final boolean Friendly;
    private final boolean joinLeaveMessage;

    public VoredListener(Logger logger, boolean Friendly, boolean joinLeaveMessage) {
        this.logger = logger;
        this.Friendly = Friendly;
        this.joinLeaveMessage = joinLeaveMessage;
        logger.info("Family friendly mode is set to " + Friendly);
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        logger.info(event.getEntity().getDisplayName()+ " just died lol");

        Player entity = event.getEntity();
        Player killer = entity.getKiller();
        String playerName = entity.getName();
        EntityDamageEvent damageEvent = entity.getLastDamageCause();
        DamageCause reason = damageEvent.getCause();

        if (killer != null) {
            voreCount.putIfAbsent(killer, 0);
            voreCount.putIfAbsent(entity, 0);
            voreCount.merge(killer, 1, Integer::sum);
            int count = voreCount.get(killer);

            String killerName = killer.getName();
            event.setDeathMessage(playerName + " got " + (this.Friendly ? "killed" : "vored") + " by " + killerName);

            switch (count) {
                case 3:
                    Bukkit.broadcastMessage(ChatColor.RED + killerName + "'s hunger grows..");
                    killer.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 90, 1));
                    break;
                case 10:
                    Bukkit.broadcastMessage(ChatColor.RED + killerName + " is getting stronger.");
                    killer.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 60, 1));
                    killer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 1));
                    killer.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 2400, 2));
                    break;
                case 15:
                    Bukkit.broadcastMessage(ChatColor.RED + killerName + "'s hunger is insatiable.");
                    killer.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 120, 1));
                    killer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 1));
                    killer.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 2550000, 4));
                    killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 2400, 2));
                    killer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2400, 1));
                    break;
                case 20:
                    Bukkit.broadcastMessage(ChatColor.DARK_RED + killerName + " is truly insane.");
                    killer.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 240, 1));
                    killer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 240, 2));
                    killer.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 2550, 8));
                    killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 2400, 4));
                    killer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2400, 1));
                    killer.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 120, 1));
                    killer.playSound(killer.getLocation(), Sound.ENTITY_ENDERMAN_STARE, 1, 1);

            }

            if (voreCount.get(entity) > 3) {
                entity.sendMessage(ChatColor.RED + "We all make mistakes.");
                voreCount.put(entity, 0);
            }
        } else if(reason == DamageCause.FALL || reason == DamageCause.SUICIDE) {
            event.setDeathMessage(ChatColor.RED + playerName + (this.Friendly ? " didn't want to live." : " committed suicide."));
        }
    }

    @EventHandler
    public void onPlayerLeaveEvent(PlayerQuitEvent event) {
        voreCount.put(event.getPlayer(), 0);
        if(this.joinLeaveMessage) {
            event.setQuitMessage(ChatColor.YELLOW + event.getPlayer().getDisplayName() + " no longer exists.");
        }
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        voreCount.put(event.getPlayer(), 0);
        if(this.joinLeaveMessage) {
            event.setJoinMessage(ChatColor.YELLOW + event.getPlayer().getDisplayName() + " exists.");
        }
    }
}
