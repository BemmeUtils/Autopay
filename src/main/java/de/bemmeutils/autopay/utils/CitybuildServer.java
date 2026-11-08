package de.bemmeutils.autopay.utils;

public enum CitybuildServer {
    // Numbered servers 1-22
    CB1(1, "Citybuild 1"),
    CB2(2, "Citybuild 2"),
    CB3(3, "Citybuild 3"),
    CB4(4, "Citybuild 4"),
    CB5(5, "Citybuild 5"),
    CB6(6, "Citybuild 6"),
    CB7(7, "Citybuild 7"),
    CB8(8, "Citybuild 8"),
    CB9(9, "Citybuild 9"),
    CB10(10, "Citybuild 10"),
    CB11(11, "Citybuild 11"),
    CB12(12, "Citybuild 12"),
    CB13(13, "Citybuild 13"),
    CB14(14, "Citybuild 14"),
    CB15(15, "Citybuild 15"),
    CB16(16, "Citybuild 16"),
    CB17(17, "Citybuild 17"),
    CB18(18, "Citybuild 18"),
    CB19(19, "Citybuild 19"),
    CB20(20, "Citybuild 20"),
    CB21(21, "Citybuild 21"),
    CB22(22, "Citybuild 22"),

    // Special servers
    EVIL(-1, "Citybuild Evil"),
    NATURE(-2, "Nature"),
    EXTREM(-3, "Extrem"),
    WASSER(-4, "Wasser"),
    LAVA(-5, "Lava"),
    EVENT(-6, "Event"),

    // Lobby, Portal etc
    NONE(-99, "None");

    private final int id;
    private final String displayName;

    CitybuildServer(int id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public static CitybuildServer fromDisplayName(String name) {
        if (name == null) return NONE;

        for (CitybuildServer type : values()) {
            if (type.displayName.equalsIgnoreCase(name.trim())) {
                return type;
            }
        }
        return NONE;
    }

    @Override
    public String toString() {
        return displayName;
    }
}