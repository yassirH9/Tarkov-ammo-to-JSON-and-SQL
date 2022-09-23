package tarkovdatasourceupdater;

public class Ammo {
    int armorDamage;
    int damage;
    String caliber;
    float weight;
    String ammoType;
    float fragmentationChance;
    float ricochetChance;
    float heavyBleedModifier;
    float lightBleedModifier;
    boolean tracer;
    String tracerColor;
    int avg24hPrice;
    int lastLowPrice;
    String name;
    String shortName;
    String image512pxLink;

    public Ammo(int armorDamage, int damage, String caliber, float weight, String ammoType, float fragmentationChance, float ricochetChance, float heavyBleedModifier, float lightBleedModifier, boolean tracer, String tracerColor, int avg24hPrice, int lastLowPrice, String name, String shortName, String image512pxLink) {
        this.armorDamage = armorDamage;
        this.damage = damage;
        this.caliber = caliber;
        this.weight = weight;
        this.ammoType = ammoType;
        this.fragmentationChance = fragmentationChance;
        this.ricochetChance = ricochetChance;
        this.heavyBleedModifier = heavyBleedModifier;
        this.lightBleedModifier = lightBleedModifier;
        this.tracer = tracer;
        this.tracerColor = tracerColor;
        this.avg24hPrice = avg24hPrice;
        this.lastLowPrice = lastLowPrice;
        this.name = name;
        this.shortName = shortName;
        this.image512pxLink = image512pxLink;
    }
    
    
}
