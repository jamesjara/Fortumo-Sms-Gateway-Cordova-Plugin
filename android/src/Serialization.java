package jamesjara.com.cordova.fortumo;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;


public class Serialization {

    /**
     * Serialize purchase data to json
     * @param purchase purchase data
     * @return json string
     * @throws org.json.JSONException
     */
	/*
    public static JSONObject purchaseToJson(Purchase purchase) throws JSONException {
        JSONObject j = new JSONObject();
        j.put("itemType", purchase.getItemType());
        j.put("orderId", purchase.getOrderId());
        j.put("packageName", purchase.getPackageName());
        j.put("sku", purchase.getSku());
        j.put("purchaseTime", purchase.getPurchaseTime());
        j.put("purchaseState", purchase.getPurchaseState());
        j.put("developerPayload", purchase.getDeveloperPayload());
        j.put("token", purchase.getToken());
        j.put("originalJson", purchase.getOriginalJson());
        j.put("signature", purchase.getSignature());
        j.put("appstoreName", purchase.getAppstoreName());
        return j;
    }

    public static JSONObject errorToJson(IabResult result) {
        return errorToJson(result.getResponse(), result.getMessage());
    }

    public static JSONObject errorToJson(int responseCode, String errorMessage) {
        JSONObject j = new JSONObject();
        try {
            j.put("code", responseCode);
            j.put("message", errorMessage);
        } catch (JSONException e) {
            Log.e(OpenIabCordovaPlugin.TAG, e.getMessage());
        }
        return j;
    }
*/
    /**
     * Serialize sku details data to json
     * @param skuDetails sku details data
     * @return json string
     * @throws JSONException
     *//*
    public static JSONObject skuDetailsToJson(SkuDetails skuDetails) throws JSONException {
        JSONObject j = new JSONObject();
        j.put("itemType", skuDetails.getItemType());
        j.put("sku", skuDetails.getSku());
        j.put("type", skuDetails.getType());
        j.put("price", skuDetails.getPrice());
        j.put("title", skuDetails.getTitle());
        j.put("description", skuDetails.getDescription());
        j.put("json", skuDetails.getJson());
        return j;
    }*/
}
