package com.pibbium.alwaysdurable.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class AlwaysDurableConfig extends MidnightConfig {
    @Entry
    public static boolean showText = true, changeTextColor = true, changeDurabilityBar = true;
    @Comment
    public static Comment textColorComment;
    @Entry
    public static Colors def = Colors.DARK_GRAY, veryhigh = Colors.GREEN, high = Colors.DARK_GREEN, medium = Colors.YELLOW, low = Colors.GOLD, verylow = Colors.RED;

    public enum Colors {
        BLACK,
        DARK_BLUE,
        DARK_GREEN,
        DARK_AQUA,
        DARK_RED,
        DARK_PURPLE,
        GOLD,
        GRAY,
        DARK_GRAY,
        BLUE,
        GREEN,
        AQUA,
        RED,
        LIGHT_PURPLE,
        YELLOW,
        WHITE,
    }

    @Comment static Comment barColorComment;

    @Entry(width = 7, min = 7, isColor = true)
    public static String start = "#00FF00", end = "#FF0000";
}
