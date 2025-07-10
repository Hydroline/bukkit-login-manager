package org.nanfans;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import org.bukkit.configuration.file.YamlConfiguration;

public class Util {
    public static long getSec() {
        long current = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.add(5, 1);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        long tomorrowzero = calendar.getTimeInMillis();
        long tomorrowzeroSeconds = (tomorrowzero - current) / 1000;
        return tomorrowzeroSeconds;
    }

    public static String ipHide(String ip) {
        int firstDot = ip.indexOf(".");
        int lastDot = ip.lastIndexOf(".");
        String prefix = ip.substring(0, firstDot + 1);
        String suffix = ip.substring(lastDot + 1);
        return prefix + "**.**" + suffix;
    }

    public static YamlConfiguration loadDataYml(File file, String ip) {
        boolean onCreate = false;
        try {
            if (!file.exists()) {
                file.createNewFile();
                onCreate = true;
            }
            YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
            if (onCreate) {
                yml.set("last_ip", ip);
                yml.set("time", 0);
                yml.set("password", "");
                yml.save(file);
            }
            return yml;
        } catch (IOException e) {
            return null;
        }
    }

    public static boolean saveDataYml(YamlConfiguration yml, File file) {
        try {
            yml.save(file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
    }
}
