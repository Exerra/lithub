package me.jez.lithub;

import java.io.File;
import java.io.IOException;
import java.util.List;

import jdk.nashorn.internal.runtime.regexp.joni.Config;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ListenerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public final class Lithub extends Command {

    public Lithub() {
        super("lithub");
    }

    @SuppressWarnings("deprecation")
    @Override
    public void execute(CommandSender sender, String[] args) {

        if(!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new TextComponent("You must be a player to do this ENZOOOOOOOOOOO using console grrrrrrrr"));
            return;
        }

        ProxiedPlayer p = (ProxiedPlayer) sender;

        if(getDisabledServers().contains(p.getServer().getInfo().getName())) {
            p.sendMessage(new ComponentBuilder(getDisabledError()).color(ChatColor.RED).create());
            return;
        }

        String s = getHub();
        if (s.equalsIgnoreCase("default")) {
            ListenerInfo listener = p.getPendingConnection().getListener();

            s = listener.getFallbackServer();
        }

        if (p.getServer().getInfo().getName().equals(s)) {
            p.sendMessage(new ComponentBuilder(getError()).color(ChatColor.RED).create());
            return;
        }

        p.connect(ProxyServer.getInstance().getServerInfo(s));

    }

    private String getHub() {
        Configuration c = null;
        try {
            c = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Main.getInstance().getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return c.getString("lithub");
    }
    private String getError() {
        Configuration c = null;
        try {
            c = ConfigurationProvider.getProvider(YamlConfiguration.class)
                    .load(new File(Main.getInstance().getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return c.getString("alreadyInHub");

    }

    private String getDisabledError() {
        Configuration c = null;
        try {
            c = ConfigurationProvider.getProvider(YamlConfiguration.class)
                    .load(new File(Main.getInstance().getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return c.getString("disabledServersError");

    }

    private List<String> getDisabledServers() {
        Configuration c = null;
        try {
            c = ConfigurationProvider.getProvider(YamlConfiguration.class)
                    .load(new File(Main.getInstance().getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return c.getStringList("disabled-servers");

    }
}
