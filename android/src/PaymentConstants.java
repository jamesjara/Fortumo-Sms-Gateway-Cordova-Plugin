package jamesjara.com.cordova.fortumo;

/**
 * Created by lars on 06.06.13.
 */
public class PaymentConstants {

    // Intent filter for successfull payments
    public static final String SUCCESSFUL_PAYMENT= "ee.larseckart.SUCCESSFUL_PAYMENT";

    //These values are constants used for shared preferences.
    public static final String PREFS = "ee.larseckart.FortumoDemo.PREFS";
    public static final String SP_KEY_GOLD = "SP_KEY_GOLD";
    public static final String SP_KEY_BONUS_LEVEL = "SP_KEY_BONUS_LEVEL";
    public static final String SP_KEY_HEALTH_POTIONS = "SP_KEY_HEALTH_POTIONS";
    public static final String SP_KEY_MANA_POTIONS = "SP_KEY_MANA_POTIONS";

    // Strings for the PaymentRequestBuilder
    public static final String PRODUCT_GOLD = "gold";
    public static final String PRODUCT_BONUS_LEVEL = "bonusLevel";
    public static final String PRODUCT_HEALTH_POTION = "healthPotion";
    public static final String PRODUCT_MANA_POTION = "manaPotion";

    public static final String DISPLAY_STRING_GOLD = "Buy gold now and get 10% extra!";
    public static final String DISPLAY_STRING_BONUS_LEVEL = "Buy the bonus level!";
    public static final String DISPLAY_STRING_HEALTH_POTION = "Buy a health potion!";
    public static final String DISPLAY_STRING_MANA_POTION = "Buy a mana potion!";

    public static final String GOLD_SERVICE_ID = "0fdeacd6a247f7f37553eee4ab0279d6";
    public static final String GOLD_SERVICE_IN_APP_SECRET = "7376ed8a9a5fd28e20e63bd94518f2fd";

    public static final String BONUS_LEVEL_SERVICE_ID = "622cff9bff6a8028d2fb96c757c60780";
    public static final String BONUS_LEVEL_IN_APP_SECRET = "4fe70efc3b7377a667378d3dabbbc164";

    public static final String POTION_SERVICE_ID = "96fb3c6d57af90867606032ed799451d";
    public static final String POTION_IN_APP_SECRET = "0b7e3805997bc2edc4a29efbd9dccdbb";

}