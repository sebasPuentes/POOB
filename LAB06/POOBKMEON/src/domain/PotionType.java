package domain;
public enum PotionType {
    /**
     * Represents a Hyper Potion with a high healing value of 200 points.
     */
    HYPER(200),
    
    /**
     * Represents a Super Potion with a medium healing value of 50 points.
     */
    SUPER(50),
    
    /**
     * Represents a Normal Potion with a basic healing value of 20 points.
     */
    NORMAL(20);

    private final int value;

    /**
     * Constructor for the PotionType enum.
     * 
     * @param value The effectiveness value of the potion type
     */
    PotionType(int value) {
        this.value = value;
    }

    /**
     * Gets the numerical value associated with this potion type.
     * This value represents the amount of healing or stat boost provided by potions of this type.
     * 
     * @return The effectiveness value of the potion type
     */
    public int getValue() {
        return value;
    }
}