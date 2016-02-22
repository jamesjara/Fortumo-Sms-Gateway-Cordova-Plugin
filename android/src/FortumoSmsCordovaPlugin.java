package jamesjara.com.cordova.fortumo;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.Manifest;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import mp.MpUtils;
import mp.PaymentActivity;
import mp.PaymentRequest;
import mp.PaymentResponse;

public class FortumoSmsCordovaPlugin extends CordovaPlugin {
	
    private static final int REQUEST_CODE = 87944598; // if you want to call me :) +506
    public static final String TAG = "forumo-sms-gateway";
    private static final HashMap <String, JSONObject> products = new HashMap <String, JSONObject>();
    private CallbackContext connectionCallbackContext;
    
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        PluginResult.Status status = PluginResult.Status.OK;
        if ("init".equals(action)) {
            //_helper = Fortumo.enablePaymentBroadcast(this, Manifest.permission.PAYMENT_BROADCAST_PERMISSION);
            init(callbackContext);
            return true;
        } else if ("setProduct".equals(action)) {
            String productId = args.getString(0);
            JSONObject productData = args.getJSONObject(1);
            setProduct(productId, productData, callbackContext);
            return true;
        } else if ("getProducts".equals(action)) {
            getProducts(callbackContext);
            return true;
        } else if ("purchaseProduct".equals(action)) {
            this.connectionCallbackContext = callbackContext;
            String productId = args.getString(0);
            String payload = args.length() > 1 ? args.getString(1) : "";
            purchaseProduct(productId, payload, callbackContext);
            return true;
        }
        return false; // Returning false results in a "MethodNotFound" error.
    }
    private void getProducts(final CallbackContext callbackContext) {
        if (!checkInitialized(callbackContext)) return;
        JSONArray JSON = new JSONArray();
        for (Map.Entry < String, JSONObject > entry: products.entrySet()) {
            String key = entry.getKey();
            JSONObject value = entry.getValue();
            try {
                JSONObject childs = new JSONObject();
                childs.put("asd", "tsssss");
                JSONObject parentObj = new JSONObject();
                parentObj.put(key, childs);
                JSON.put(parentObj);
            } catch (JSONException e) {
                Log.e(TAG, "Invalid JSON string: ", e);
            }
        }
        callbackContext.success(JSON);
    }
    private void init(final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {}
        });
    }
    private boolean checkInitialized(final CallbackContext callbackContext) {
        if (false) {
            Log.e(TAG, "Not initialized");
            callbackContext.error("Not initialized");
            return false;
        }
        return true;
    }
    private void setProduct(final String productId, final JSONObject productData, final CallbackContext callbackContext) {
        if (!checkInitialized(callbackContext)) return;
        cordova.setActivityResultCallback(this);
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                products.put(productId, productData);
                callbackContext.success();
            }
        });
    }
    private void purchaseProduct(final String productId, final String developerPayload, final CallbackContext callbackContext) {
        if (!checkInitialized(callbackContext)) return;
        cordova.setActivityResultCallback(this);
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject config = products.get(productId);
                    String productName = config.getString("name");
                    String appSecret = config.getString("appSecret");
                    String display = config.getString("display");
                    String amount = config.getString("amount");
                    String currency = config.getString("currency");
                    PaymentRequest.PaymentRequestBuilder builder = new PaymentRequest.PaymentRequestBuilder();
                    builder.setService(productId, appSecret);
                    builder.setProductName(productName);
                    builder.setDisplayString(display);
                    builder.setPriceAmount(amount);
                    builder.setPriceCurrency(currency);
                    // builder.setType(MpUtils.TYPE_CONSUMABLE);
                    //builder.setCreditsMultiplier(1.1d);
                    //builder.setIcon(R.drawable.ic_launcher);
                    PaymentRequest pr = builder.build();
                    Intent localIntent = pr.toIntent(cordova.getActivity());
                    cordova.getActivity().startActivityForResult(localIntent, REQUEST_CODE);
                } catch (JSONException e) {
                    Log.e(TAG, "Invalid JSON string: ", e);
                }
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (data == null) {
                return;
            }
            // OK
            if (resultCode == Activity.RESULT_OK) { //RESULT_OK) {
                PaymentResponse response = new PaymentResponse(data);
                switch (response.getBillingStatus()) {
                    case MpUtils.MESSAGE_STATUS_BILLED:
                        Bundle extras = data.getExtras();
                        try {
                            JSONObject result = new JSONObject();
                            result.put("credit_amount", extras.getString("credit_amount"));
                            result.put("credit_name", extras.getString("credit_name"));
                            result.put("message_id", extras.getString("message_id"));
                            result.put("payment_code", extras.getString("payment_code"));
                            result.put("price_amount", extras.getString("price_amount"));
                            result.put("price_currency", extras.getString("price_currency"));
                            result.put("product_name", extras.getString("product_name"));
                            result.put("service_id", extras.getString("service_id"));
                            result.put("user_id", extras.getString("user_id"));
                            result.put("billing_status", extras.getString("billing_status"));
                            result.put("credit_amount", extras.getString("credit_amount"));
                            PluginResult resultSuccess = new PluginResult(PluginResult.Status.OK, result);
                            resultSuccess.setKeepCallback(true);
                            connectionCallbackContext.sendPluginResult(resultSuccess);
                        } catch (JSONException e) {
                            Log.e(TAG, "Invalid JSON string: ", e);
                        }
                        break;
                    case MpUtils.MESSAGE_STATUS_FAILED:
                        PluginResult resultFail = new PluginResult(PluginResult.Status.ERROR);
                        resultFail.setKeepCallback(true);
                        connectionCallbackContext.sendPluginResult(resultFail);
                        break;
                    case MpUtils.MESSAGE_STATUS_PENDING:
                        // ...
                        break;
                }
                // Cancel
            } else {
                // ..
            }
        } else {
            //super.onActivityResult(requestCode, resultCode, data);
        }
    }
}