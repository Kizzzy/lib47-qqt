package cn.kizzzy.qqt;

public enum MapCity {
    desert(1, "desert", "沙漠"),
    snow(2, "snow", "雪地"),
    town(3, "town", "中国城"),
    mine(4, "mine", "矿洞"),
    water(5, "water", "水面"),
    field(6, "field", "野外"),
    bomb(7, "bomb", "足球"),
    bun(8, "bun", "抢包山"),
    pig(9, "pig", "功夫"),
    treasure(10, "treasure", "夺宝"),
    match(11, "match", "比武"),
    sculpture(12, "sculpture", "英雄传说"),
    machine(13, "machine", "机械世界"),
    box(14, "box", "推箱子"),
    practice(15, "practice", "练习"),
    exploration(16, "exploration", "探险"),
    common(17, "common", "通用"),
    pve(18, "pve", "PVE"),
    tank(19, "tank", "糖客战"),
    ;
    
    private final int id;
    
    private final String name;
    
    private final String desc;
    
    MapCity(int id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDesc() {
        return desc;
    }
    
    public static MapCity valueOf(int id) {
        for (MapCity city : values()) {
            if (id == city.id) {
                return city;
            }
        }
        throw new IllegalArgumentException("Unknown Type: " + id);
    }
}
