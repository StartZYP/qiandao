package com.github.startzyp.mc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.bukkit.entity.Player;

class NmsJunk
{
    static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle)
    {
        try
        {
            Object enumTimes = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get(null);
            Constructor<?> titleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] { getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent") });
            Constructor<?> titleConstructorWithTimes = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] { getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE });
            Object chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + title + "\"}" });
            if (title != null)
            {
                Object titlePacket = titleConstructorWithTimes.newInstance(new Object[] { enumTimes, chatTitle, fadeIn, stay, fadeOut });
                sendPacket(player, titlePacket);


                Object enumTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
                titlePacket = titleConstructor.newInstance(new Object[] { enumTitle, chatTitle });
                sendPacket(player, titlePacket);
            }
            if (subtitle != null)
            {
                Object titlePacket = titleConstructorWithTimes.newInstance(new Object[] { enumTimes, chatTitle, fadeIn, stay, fadeOut });
                sendPacket(player, titlePacket);

                Object enumSubtitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
                Object chatSubtitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + subtitle + "\"}" });
                titlePacket = titleConstructor.newInstance(new Object[] { enumSubtitle, chatSubtitle });
                sendPacket(player, titlePacket);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static Class<?> getNMSClass(String name)
    {
        String version = org.bukkit.Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try
        {
            return Class.forName("net.minecraft.server." + version + "." + name);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private static void sendPacket(Player player, Object packet)
    {
        try
        {
            Object handle = player.getClass().getMethod("getHandle", new Class[0]).invoke(player, new Object[0]);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", new Class[] { getNMSClass("Packet") }).invoke(playerConnection, new Object[] { packet });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
