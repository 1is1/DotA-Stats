/**
 * Created by Tom on 25-10-2016.
 */
@SuppressWarnings("DefaultFileTemplate")
class Hero {
    private String name;
    private double strStart;
    private double agiStart;
    private double intStart;
    private double strGain;
    private double agiGain;
    private double intGain;
    private double baseArmor;
    private final double baseHealth;
    private final double agiPerArmor;
    private final double healthPerStr;

    public Hero(String name, double strStart, double strGain, double agiStart, double agiGain, double intStart, double intGain, double baseArmor, double baseHealth, double agiPerArmor, double healthPerStr) {
        this.name = name;
        this.strStart = strStart;
        this.agiStart = agiStart;
        this.intStart = intStart;
        this.strGain = strGain;
        this.agiGain = agiGain;
        this.intGain = intGain;
        this.baseArmor = baseArmor;
        this.baseHealth = baseHealth;
        this.agiPerArmor = agiPerArmor;
        this.healthPerStr = healthPerStr;
    }
    public Hero(String name, double strStart, double strGain, double agiStart, double agiGain, double intStart, double intGain, double baseArmor) {
        this.name = name;
        this.strStart = strStart;
        this.agiStart = agiStart;
        this.intStart = intStart;
        this.strGain = strGain;
        this.agiGain = agiGain;
        this.intGain = intGain;
        this.baseArmor = baseArmor;
        this.baseHealth = 300;
        this.agiPerArmor = 7;
        this.healthPerStr = 20;
    }


    public double effectiveHealth(){
        return effectiveHealth(1);
    }

    public double effectiveHealth(int level){
        double effectiveHealth = displayedHealth(level) / damageMultiplier(mainArmor(level));
        return effectiveHealth;
    }

    public double mainArmor() {
        return mainArmor(1);
    }

    public double mainArmor(int level) {
        double agility = agiStart + agiGained(level);
        double armorFromAgi = agility / agiPerArmor;
        double mainArmor = baseArmor + armorFromAgi;
        return mainArmor;
    }

    public double displayedHealth() {
        return displayedHealth(1);
    }

    public double displayedHealth(int level) {
        double strength = Math.floor(strStart + strGained(level));
        double healthFromStr = strength * healthPerStr;
        double displayedHealth = baseHealth + healthFromStr;
        return displayedHealth;
    }

    public double damageMultiplier(double armor) {
        // taken from the expression "Damage Multiplier = 1 - 0.06 × armor ÷ (1 + (0.06 × |armor|))"
        double damageMultiplier = (1 - ((0.06 * armor) / (1 + (0.06 * Math.abs(armor)))));
        return damageMultiplier;
    }

    private double statGained(int level, double statStart, double statGain) {
        double statGained = statGain * (level - 1);
        return statGained;
    }

    public double agiGained(int level) {
        return statGained(level, agiStart, agiGain);
    }
    public double strGained(int level) {
        return statGained(level, strStart, strGain);
    }
    public double intGained(int level) {
        return statGained(level, intStart, intGain);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getStrStart() {
        return strStart;
    }

    public void setStrStart(double strStart) {
        this.strStart = strStart;
    }

    public double getAgiStart() {
        return agiStart;
    }

    public void setAgiStart(double agiStart) {
        this.agiStart = agiStart;
    }

    public double getIntStart() {
        return intStart;
    }

    public void setIntStart(double intStart) {
        this.intStart = intStart;
    }

    public double getStrGain() {
        return strGain;
    }

    public void setStrGain(double strGain) {
        this.strGain = strGain;
    }

    public double getAgiGain() {
        return agiGain;
    }

    public void setAgiGain(double agiGain) {
        this.agiGain = agiGain;
    }

    public double getIntGain() {
        return intGain;
    }

    public void setIntGain(double intGain) {
        this.intGain = intGain;
    }

    public double getArmorStart() {
        return baseArmor;
    }

    public void setArmorStart(double baseArmor) {
        this.baseArmor = baseArmor;
    }
}
