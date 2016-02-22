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

import jamesjara.com.cordova.fortumo.PaymentConstants;

import mp.MpUtils;
import mp.PaymentActivity;
import mp.PaymentRequest;
import mp.PaymentResponse;

public class FortumoSmsCordovaPlugin extends CordovaPlugin
{
	
	private static final int REQUEST_CODE = 87944598; // if you want to call me :) +506
    public static final String TAG = "forumo-sms-gateway";
    private static final HashMap<String, JSONObject> products = new HashMap<String, JSONObject>();

    // we need this callback when Task will finish
    private CallbackContext connectionCallbackContext;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException
    {
        if ("init".equals(action))
        {
			//_helper = Fortumo.enablePaymentBroadcast(this, Manifest.permission.PAYMENT_BROADCAST_PERMISSION);
			init(callbackContext);
            return true;
        }
        else if ("setProduct".equals(action))
        {
            String productId = args.getString(0);
            JSONObject productData = args.getJSONObject(1) ;  
            setProduct(productId, productData , callbackContext);
            return true;
        }
        else if ("getProducts".equals(action))
        {   
            getProducts(callbackContext);
            return true;
        }
        else if ("purchaseProduct".equals(action))
        {
            this.connectionCallbackContext = callbackContext;
            
            String productId = args.getString(0);
            String payload = args.length() > 1 ? args.getString(1) : "";
            purchaseProduct(productId, payload, callbackContext);

            PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, productId);
            pluginResult.setKeepCallback(true);
            callbackContext.sendPluginResult(pluginResult);
            return true;
        }
        else if ("consume".equals(action))
        {
        	/*
            String sku = args.getString(0);
            consume(sku, callbackContext);
            return true;
            */
        }
        return false;  // Returning false results in a "MethodNotFound" error.
    }

    private void getProducts(final CallbackContext callbackContext) {
        if (!checkInitialized(callbackContext)) return;
        	JSONArray JSON = new JSONArray();
        	
        	for(Map.Entry<String, JSONObject> entry : products.entrySet()) {
        		
        	    String key = entry.getKey();
        	    JSONObject value = entry.getValue();


              	try {            	
              		JSONObject childs = new JSONObject();
              		childs.put( "asd" , "tsssss");
              		
                	JSONObject parentObj = new JSONObject();
                	parentObj.put( key , childs);
                	
                	JSON.put(parentObj);
                } catch (JSONException e) {
                    Log.e(TAG, "Invalid JSON string: " , e);
                }
        	    
        	}
        	callbackContext.success(JSON);
    }

    private void init( final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
            	
            	createBroadcasts();
            	//new onPurchaseFinished().execute();

                // Start setup. This is asynchronous and the specified listener
                // will be called once setup completes.
                /*
            	Log.d(TAG, "Starting setup.");
                _helper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
                    public void onIabSetupFinished(IabResult result) {
                        Log.d(TAG, "Setup finished.");

                        if (result.isFailure()) {
                            // Oh noes, there was a problem.
                            Log.e(TAG, "Problem setting up in-app billing: " + result);
                            callbackContext.error(Serialization.errorToJson(result));
                            return;
                        }

                        Log.d(TAG, "Querying inventory.");
                        // TODO: this is SHIT! product and subs skus shouldn't be sent two times
                        //_helper.queryInventoryAsync(true, skuList, skuList, new BillingCallback(callbackContext));
                    }
                });
                */
            }
        });
    }

    private boolean checkInitialized(final CallbackContext callbackContext) {
        if (false)
        {
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
                    Log.e(TAG, "Invalid JSON string: " , e);
                }
            }
        });
    }

    private void consume(final String sku, final CallbackContext callbackContext) {
        if (!checkInitialized(callbackContext)) return;
/*
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!_inventory.hasPurchase(sku))
                {
                    callbackContext.error(Serialization.errorToJson(-1, "Product haven't been purchased: " + sku));
                    return;
                }

                Purchase purchase = _inventory.getPurchase(sku);
                _helper.consumeAsync(purchase, new BillingCallback(callbackContext));
            }
        });
        */
    }

    

    private class onPurchaseFinished extends AsyncTask<Void, Void, String[]> {
    	
        public onPurchaseFinished(boolean showLoading) {
            super();
            // do stuff
        }
        
        @Override
        protected String[] doInBackground(Void... voids) {
            String[] result = new String[1];
            result[0] = String.valueOf("asd");
            //result[0] = String.valueOf(Wallet.getColdAmount(MainActivity.this));
            //result[1] = String.valueOf(BonusLevel.isBonusUnlocked(MainActivity.this));
            //result[2] = String.valueOf(PotionStack.getPotionAmount(MainActivity.this, PaymentConstants.PRODUCT_HEALTH_POTION));
            //result[3] = String.valueOf(PotionStack.getPotionAmount(MainActivity.this, PaymentConstants.PRODUCT_MANA_POTION));
            return result;
        }

        @Override
        protected void onPostExecute(String[] data) {
        	
/*
            PurchaseFinished.status = billingStatus;
            PurchaseFinished.extras = extras;
            
            */
            
           // goldTextView.setText(data[0]);
           // bonusLevelUnlockedTextView.setText(data[1]);
           // healthPotionTextView.setText(data[2]);
           // manaPotionTextView.setText(data[3]);
        }
    }
    
    private void createBroadcasts() {
        Log.d(TAG, "createBroadcasts");
        IntentFilter filter = new IntentFilter(PaymentConstants.SUCCESSFUL_PAYMENT);
        cordova.getActivity().registerReceiver(_billingReceiver, filter);
        Log.i(TAG, "updateReceiver registered");
    }

    private void destroyBroadcasts() {
        Log.d(TAG, "destroyBroadcasts");
        try {
            cordova.getActivity().unregisterReceiver(_billingReceiver);
        } catch (Exception ex) {
            Log.d(TAG, "destroyBroadcasts exception:\n" + ex.getMessage());
        }
    }

    private BroadcastReceiver _billingReceiver = new BroadcastReceiver() {
        private static final String TAG = "jamesjarabillings";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();  
            Bundle extras = intent.getExtras(); 
            Log.d(TAG, "onReceive intent: " + intent);            
           // purchaseStateChanged(intent);

            int billingStatus = extras.getInt("billing_status");
            if(billingStatus == MpUtils.MESSAGE_STATUS_BILLED) {
              int coins = Integer.parseInt(intent.getStringExtra("credit_amount"));
              //Wallet.addCoins(context, coins);
              //onPurchaseFinished PurchaseFinished = new onPurchaseFinished();
              //PurchaseFinished.execute(billingStatus, extras);              
            } 
            
        }

        private void purchaseStateChanged(Intent intent) {
            Bundle extras = intent.getExtras(); 
            Log.d(TAG, "purchaseStateChanged intent: " + extras);

            Log.d(TAG, "- credit_amount:   " + extras.getString("credit_amount"));
            Log.d(TAG, "- credit_name:     " + extras.getString("credit_name"));
            Log.d(TAG, "- message_id:      " + extras.getString("message_id") );
            Log.d(TAG, "- payment_code:    " + extras.getString("payment_code"));
            Log.d(TAG, "- price_amount:    " + extras.getString("price_amount"));
            Log.d(TAG, "- price_currency:  " + extras.getString("price_currency"));
            Log.d(TAG, "- product_name:    " + extras.getString("product_name"));
            Log.d(TAG, "- service_id:      " + extras.getString("service_id"));
            Log.d(TAG, "- user_id:         " + extras.getString("user_id"));

            int billingStatus = extras.getInt("billing_status");
            if(billingStatus == MpUtils.MESSAGE_STATUS_BILLED) {
              int coins = Integer.parseInt(intent.getStringExtra("credit_amount"));
              
              
              //Wallet.addCoins(context, coins);
              //onPurchaseFinished PurchaseFinished = new onPurchaseFinished();
              //PurchaseFinished.execute(billingStatus, extras);              
            }          
        }
    };
    
    @Override
    public void  onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE) {
			if(data == null) {
				return;
			}			
			// OK
			if (resultCode == Activity.RESULT_OK){//RESULT_OK) {
				PaymentResponse response = new PaymentResponse(data);
				
				switch (response.getBillingStatus()) {
					case MpUtils.MESSAGE_STATUS_BILLED:
					
			              PluginResult result = new PluginResult(PluginResult.Status.OK, "test");
			              result.setKeepCallback(true);
			              connectionCallbackContext.sendPluginResult(result);
						
					break;
				case MpUtils.MESSAGE_STATUS_FAILED:
					// ...
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
    

    private static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }
    

    private static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for(int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }
    
}