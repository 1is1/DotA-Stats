import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Tom on 25-10-2016.
 */
@SuppressWarnings("DefaultFileTemplate")
class Hero {
    private String heroName;
    private double strStart;
    private double agiStart;
    private double intStart;
    private double strGain;
    private double agiGain;
    private double intGain;
    private double baseArmor;
    private double baseHealth;
    private double agiPerArmor;
    private double healthPerStr;
    private ArrayList<String> attributeNames;

    public Hero(String heroName, double baseHealth, double agiPerArmor, double healthPerStr) throws ClassNotFoundException {
        this.heroName = heroName;
        this.baseHealth = baseHealth;
        this.agiPerArmor = agiPerArmor;
        this.healthPerStr = healthPerStr;
        buildAttributeNames();
    }

    public Hero(String heroName, double strStart, double strGain, double agiStart, double agiGain, double intStart, double intGain, double baseArmor, double baseHealth, double agiPerArmor, double healthPerStr) throws ClassNotFoundException {
        this.heroName = heroName;
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
        buildAttributeNames();
    }

    public Hero(Map<String, String> mapOfAttributes) throws NoSuchFieldException, IllegalAccessException {
        buildAttributeNames();
        buildAttributes(mapOfAttributes);
        baseHealth = 200;
        agiPerArmor = 7;
        healthPerStr = 20;
    }

    public void buildAttributes(double strStart, double strGain, double agiStart, double agiGain, double intStart, double intGain, double baseArmor) {
        this.strStart = strStart;
        this.strStart = strStart;
        this.agiStart = agiStart;
        this.intStart = intStart;
        this.strGain = strGain;
        this.agiGain = agiGain;
        this.intGain = intGain;
        this.baseArmor = baseArmor;
    }

    public void buildAttributes(Map<String, String> mapOfAttributes) throws NoSuchFieldException, IllegalAccessException {
        for (String s : attributeNames) {
            if (mapOfAttributes.containsKey(s)) {
                Field attributeField = getClass().getDeclaredField(s);
                if (s.equals("heroName")) {
                    attributeField.set(this, mapOfAttributes.get(s));
                } else {
                    attributeField.set(this, Double.valueOf(mapOfAttributes.get(s)));
                }

            }
        }
    }

    public void buildAttributeNames() {
        attributeNames = new ArrayList<>();
        attributeNames.add("heroName");
        attributeNames.add("strStart");
        attributeNames.add("strGain");
        attributeNames.add("agiStart");
        attributeNames.add("agiGain");
        attributeNames.add("intStart");
        attributeNames.add("intGain");
        attributeNames.add("baseArmor");
        attributeNames.add("baseHealth");
        attributeNames.add("agiPerArmor");
        attributeNames.add("healthPerStr");
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


    public boolean attributeIsDefined(String attributeName) throws ClassNotFoundException {
        Field[] fields = getClass().getFields();
        for (Field f : fields) {
            if (f.getName().equals(attributeName)) {
                return true;
            }
        }
        return false;
    }

    private boolean allAttributesAreDefined() throws ClassNotFoundException {
        Field[] fields = getClass().getFields();
        for (Field f : fields) {
            if (f.getName() == null || f.getName().equals("") || f.getName().equals(" ")) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Hero{" +
                "heroName='" + heroName + '\'' +
                ", strStart=" + strStart +
                ", agiStart=" + agiStart +
                ", intStart=" + intStart +
                ", strGain=" + strGain +
                ", agiGain=" + agiGain +
                ", intGain=" + intGain +
                ", baseArmor=" + baseArmor +
                ", baseHealth=" + baseHealth +
                ", agiPerArmor=" + agiPerArmor +
                ", healthPerStr=" + healthPerStr +
                '}';
    }

    public String getName() {
        return heroName;
    }

    public void setName(String heroName) {
        this.heroName = heroName;
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
