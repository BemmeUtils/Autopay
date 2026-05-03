package de.bemmeutils.autopay.utils;

public enum CitybuildType {
    // Numbered servers 1-22
    CB1(1, "CB1"),
    CB2(2, "CB2"),
    CB3(3, "CB3"),
    CB4(4, "CB4"),
    CB5(5, "CB5"),
    CB6(6, "CB6"),
    CB7(7, "CB7"),
    CB8(8, "CB8"),
    CB9(9, "CB9"),
    CB10(10, "CB10"),
    CB11(11, "CB11"),
    CB12(12, "CB12"),
    CB13(13, "CB13"),
    CB14(14, "CB14"),
    CB15(15, "CB15"),
    CB16(16, "CB16"),
    CB17(17, "CB17"),
    CB18(18, "CB18"),
    CB19(19, "CB19"),
    CB20(20, "CB20"),
    CB21(21, "CB21"),
    CB22(22, "CB22"),

    // Special servers
    EVIL(-1, "CBE"),
    NATURE(-2, "Nature"),
    EXTREM(-3, "Extrem"),
    WASSER(-4, "Wasser"),
    LAVA(-5, "Lava"),
    EVENT(-6, "Event"),

    // Lobby, Portal etc
    NONE(-99, "None");

    private final int id;
    private final String displayName;

    CitybuildType(int id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public int getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isNumbered() {
        return id > 0;
    }

    public boolean isSpecial() {
        return id < 0;
    }

    // Find by ID
    public static CitybuildType fromId(int id) {
        for (CitybuildType cb : values()) {
            if (cb.id == id) {
                return cb;
            }
        }
        return null;
    }

    // Find by display name (case insensitive)
    public static CitybuildType fromDisplayName(String name) {
        for (CitybuildType cb : values()) {
            if (cb.displayName.equalsIgnoreCase(name)) {
                return cb;
            }
        }
        return CitybuildType.NONE;
    }

    // Find by server name pattern (for parsing server names like "CB15", "citybuild15", etc.)
    public static CitybuildType fromServerName(String serverName) {
        if (serverName == null) return null;

        String lower = serverName.toLowerCase();

        // Check special servers first
        if (lower.contains("cbe")) return EVIL;
        if (lower.contains("nature")) return NATURE;
        if (lower.contains("extrem")) return EXTREM;
        if (lower.contains("wasser")) return WASSER;
        if (lower.contains("lava")) return LAVA;
        if (lower.contains("event")) return EVENT;

        // Try to extract number for numbered servers
        for (int i = 1; i <= 22; i++) {
            if (lower.contains("cb" + i) ||
                    lower.contains("citybuild" + i) ||
                    lower.contains("server" + i)) {
                return fromId(i);
            }
        }

        return CitybuildType.NONE;
    }

    @Override
    public String toString() {
        return displayName;
    }
}