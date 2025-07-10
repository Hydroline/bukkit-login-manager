package org.nanfans;

import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class LoginListener implements Listener {
    private final LoginManager loginManager;
    private File tempData;

    public LoginListener(LoginManager loginManager) {
        this.loginManager = loginManager;
        loginManager.getServer().getPluginManager().registerEvents(this, loginManager);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onLogin(PlayerJoinEvent login) {
        final Player player = login.getPlayer();
        final String type = LoginManager.message_type;
        String nowIp = player.getAddress().toString();
        final String ipString = nowIp.substring(1, nowIp.lastIndexOf(":"));
        this.tempData = new File(LoginManager.playerdataFold, String.valueOf(login.getPlayer().getName()) + ".yml");
        final YamlConfiguration playerdata = Util.loadDataYml(this.tempData, ipString);
        if (!playerdata.getString("last_ip").equals(ipString) && LoginManager.useaddress_alert) {
            player.sendMessage(String.valueOf(LoginManager.prefix) + "您的地址与上次在线不同，建议核对以下信息");
            player.sendMessage(String.valueOf(LoginManager.prefix) + "§f本机IP: §a" + Util.ipHide(ipString) + " §f上次IP: §c" + Util.ipHide(playerdata.getString("last_ip")));
            playerdata.set("password", "");
            playerdata.set("last_ip", ipString);
            if (type.equals("message")) {
                player.sendMessage(String.valueOf(LoginManager.prefix) + LoginManager.address_change);
            } else if (type.equals("title")) {
                player.sendTitle(" ", LoginManager.address_change, 20, 70, 20);
            }
            Util.saveDataYml(playerdata, this.tempData);
        }
        if (playerdata.getString("password").equals("")) {
            return;
        }
        Long time = Long.valueOf(Util.getSec());
        Long pastTime = Long.valueOf(playerdata.getLong("time") - time.longValue());
        if ((pastTime.longValue() < LoginManager.max_time.longValue() * 60 && pastTime.longValue() > 0) || LoginManager.max_time.longValue() == 0) {
            new BukkitRunnable() {
                public void run() {
                    if ((!player.isOp() || LoginManager.useon_op) && playerdata.getString("last_ip").equals(ipString)) {
                        String password = playerdata.getString("password");
                        player.performCommand(String.valueOf(LoginManager.login_command) + " " + password);
                        if (type.equals("message")) {
                            player.sendMessage(String.valueOf(LoginManager.prefix) + LoginManager.success_login);
                        } else if (type.equals("title")) {
                            player.sendTitle(" ", LoginManager.success_login, 20, 70, 20);
                        }
                    }
                }
            }.runTaskLater(this.loginManager, LoginManager.lagging_enabled);
            return;
        }
        if (type.equals("message")) {
            player.sendMessage(String.valueOf(LoginManager.prefix) + LoginManager.time_out);
        } else if (type.equals("title")) {
            player.sendTitle(" ", LoginManager.time_out, 20, 70, 20);
        }
        playerdata.set("password", "");
        Util.saveDataYml(playerdata, this.tempData);
    }

    @EventHandler
    public void onSendPassword(PlayerCommandPreprocessEvent sendCommand) {
        String command = sendCommand.getMessage();
        int spaceIndex = command.indexOf(" ");
        if (spaceIndex < 0) {
            return;
        }
        String commandHead = command.substring(1, spaceIndex);
        if (!commandHead.equals(LoginManager.login_command) && !commandHead.equals("l")) {
            return;
        }
        Player player = sendCommand.getPlayer();
        File file = new File(LoginManager.playerdataFold, String.valueOf(player.getName()) + ".yml");
        YamlConfiguration playerdata = YamlConfiguration.loadConfiguration(file);
        String ip = player.getAddress().toString();
        String ipString = ip.substring(1, ip.lastIndexOf(":"));
        String password = command.substring(spaceIndex + 1, command.length());
        playerdata.set("ip", ipString);
        playerdata.set("time", Long.valueOf(Util.getSec()));
        playerdata.set("password", password);
        Util.saveDataYml(playerdata, file);
    }
}
