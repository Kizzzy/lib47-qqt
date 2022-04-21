package cn.kizzzy.qqt;

public enum GameMode {
    normal(1, "普通"),
    bomb(2, "足球"),
    bun(3, "抢包山"),
    match(4, "比武"),
    treasure(5, "夺宝"),
    sculpture(6, "英雄传说"),
    machine(7, "机械世界"),
    box(8, "推箱子"),
    practice(9, "练习"),
    exploration(10, "探险"),
    common(11, "通用"),
    pve(12, "PVE"),
    tank(13, "糖客战"),
    ;
    
    private final int id;
    
    private final String desc;
    
    GameMode(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }
    
    public int getId() {
        return id;
    }
    
    public String getDesc() {
        return desc;
    }
    
    public static GameMode valueOf(int id) {
        for (GameMode mode : values()) {
            if (id == mode.id) {
                return mode;
            }
        }
        throw new IllegalArgumentException("Unknown Type: " + id);
    }
}
