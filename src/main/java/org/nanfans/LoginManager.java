package org.nanfans;

import java.io.File;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class LoginManager extends JavaPlugin {
    public static String prefix;
    public static Long max_time;
    public static boolean useon_op;
    public static boolean useaddress_alert;
    public static int lagging_enabled;
    public static String success_login;
    public static String address_change;
    public static String address_alert;
    public static String time_out;
    public static String message_type;
    public static String login_command;
    public static File playerdataFold;

    public void onEnable() {
        saveDefaultConfig();
        loadConfig();
        checkPlayerData();
        new LoginListener(this);
        getLogger().info("§6LoginManager初始化完成");
    }

    public void loadConfig() {
        prefix = getConfig().getString("prefix").replace("&", "§");
        max_time = Long.valueOf(getConfig().getLong("max_time"));
        useon_op = getConfig().getBoolean("useon_op");
        lagging_enabled = getConfig().getInt("lagging_enabled");
        success_login = getConfig().getString("success_login").replace("&", "§");
        address_change = getConfig().getString("address_change").replace("&", "§");
        time_out = getConfig().getString("time_out").replace("&", "§");
        message_type = getConfig().getString("message_type");
        login_command = getConfig().getString("login_command");
        useaddress_alert = getConfig().getBoolean("useaddress_alert");
        getLogger().info("§6配置文件已载入");
    }

    public void checkPlayerData() {
        playerdataFold = new File(getDataFolder(), "playerdata");
        if (!playerdataFold.exists()) {
            playerdataFold.mkdir();
        }
    }

    public boolean help(CommandSender sender) {
        sender.sendMessage("§6§lLoginiManager帮助");
        sender.sendMessage("§a/loginmanager §6reload §f重载配置文件");
        sender.sendMessage("§a/loginmanager §6lookup  §c<player> §f查看玩家的登录信息");
        sender.sendMessage("§a/loginmanager §6remove §c<player> §f删除玩家的登录信息");
        return true;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            return help(sender);
        }
        if (args[0].equalsIgnoreCase("reload")) {
            reloadConfig();
            loadConfig();
            sender.sendMessage(String.valueOf(prefix) + "LoginManager by NanFans v1.34");
            sender.sendMessage(String.valueOf(prefix) + "配置文件已重载");
            return true;
        } else if (args.length < 2) {
            return help(sender);
        } else {
            if (args[0].equalsIgnoreCase("lookup")) {
                String player = args[1].toString();
                File file = new File(getDataFolder(), "playerdata" + File.separator + player + ".yml");
                if (!file.exists()) {
                    sender.sendMessage(String.valueOf(prefix) + "没有找到此玩家的登录信息");
                    return true;
                }
                YamlConfiguration playerInfo = YamlConfiguration.loadConfiguration(file);
                String isTimeOut = "§c已失效";
                Long pastTime = Long.valueOf(playerInfo.getLong("time") - Util.getSec());
                isTimeOut = (pastTime.longValue() < max_time.longValue() * 60 || max_time.longValue() == 0) ? "§a生效" : "§a生效";
                sender.sendMessage(String.valueOf(prefix) + "玩家§a " + player + " 的登录信息");
                sender.sendMessage(String.valueOf(prefix) + "最后一次上线IP: §a" + playerInfo.getString("last_ip"));
                sender.sendMessage(String.valueOf(prefix) + "已保存的登录密码: §a" + playerInfo.getString("password"));
                sender.sendMessage(String.valueOf(prefix) + "自动登录是否生效: §a" + isTimeOut);
                return true;
            } else if (args[0].equalsIgnoreCase("remove")) {
                if (args.length < 2) {
                    sender.sendMessage(String.valueOf(prefix) + "请提供玩家ID");
                    return true;
                }
                String player2 = args[1].toString();
                File file2 = new File(getDataFolder(), "playerdata" + File.separator + player2 + ".yml");
                if (file2.exists()) {
                    file2.delete();
                    sender.sendMessage(String.valueOf(prefix) + "玩家 §a" + player2 + "§f 的记录已删除");
                    return true;
                }
                sender.sendMessage(String.valueOf(prefix) + "未找到该玩家");
                return true;
            } else {
                return help(sender);
            }
        }
    }
}
