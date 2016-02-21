package jamesjara.com.cordova.fortumo;

//import com.squareup.okhttpxxxxxxx3.internal.StrictLineReader;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

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
    
    //private PaymentActivity  mClass;
    public static final String READ = "xxx";//Manifest.permission.PAYMENT_BROADCAST_PERMISSION;

    public String ServiceId = "";
    public String AppSecret = "";
    
    /*
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(PaymentConstants.SUCCESSFUL_PAYMENT);
        registerReceiver(updateReceiver, filter);
        Log.i(TAG, "updateReceiver registered");
    }

    @Override
    protected void onStop() {
        unregisterReceiver(updateReceiver);
        Log.i(TAG, "updateReceiver unregistered");
        super.onPause();
    }
    */

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException
    {
        if ("init".equals(action))
        {
        	/*
            JSONObject config = args.getJSONObject(0);
            String serviceId = config.getString("serviceId");
            String appSecret = config.getString("appSecret");
            ServiceId = serviceId;
            AppSecret = appSecret;            
            */
            
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
            String productId = args.getString(0);
            String payload = args.length() > 1 ? args.getString(1) : "";
            purchaseProduct(productId, payload, callbackContext);
            return true;
        }
        else if ("purchaseSubscription".equals(action))
        {
        	/*
            String sku = args.getString(0);
            String payload = args.length() > 1 ? args.getString(1) : "";
            purchaseProduct(sku, payload, callbackContext);
            return true;
            */
        }
        else if ("consume".equals(action))
        {
        	/*
            String sku = args.getString(0);
            consume(sku, callbackContext);
            return true;
            */
        }
        else if ("getSkuDetails".equals(action))
        {
        	/*
            String sku = args.getString(0);
            getSkuDetails(sku, callbackContext);
            return true;
            */
        }
        else if ("getSkuListDetails".equals(action))
        {
        	/*
            List<String> skuList = new ArrayList<String>();
            if (args.length() > 0) {
                JSONArray jSkuList = args.getJSONArray(0);
                int count = jSkuList.length();
                for (int i = 0; i < count; ++i) {
                    skuList.add(jSkuList.getString(i));
                }
            }       
            getSkuListDetails(skuList, callbackContext);
            return true;
            */
        }
        else if ("getPurchases".equals(action))
        {
        	/*
            getPurchases(callbackContext);
            return true;
            */
        }
        else if ("mapSku".equals(action))
        {
        	/*
            String sku = args.getString(0);
            String storeName = args.getString(1);
            String storeSku = args.getString(2);
            mapSku(sku, storeName, storeSku);
            return true;
            */
        }
        return false;  // Returning false results in a "MethodNotFound" error.
    }

    private void getProducts(final CallbackContext callbackContext) {
        if (!checkInitialized(callbackContext)) return;

      //  try {
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
                    Log.e(TAG, "Invalid JSON string: " + childs , e);
                }
        	    
        	}
        	
        	 
        	

            
        	callbackContext.success(JSON);
       /* } catch (JSONException e) {
            callbackContext.error( e.getMessage());
            return;
        }*/
    }
    
    private void mapSku(String sku, String storeName, String storeSku) {
        //SkuManager.getInstance().mapSku(sku, storeName, storeSku);
    }

    private void getPurchases(final CallbackContext callbackContext) {
        if (!checkInitialized(callbackContext)) return;
        
        /*
        List<Purchase> purchaseList = _inventory.getAllPurchases();
        
        JSONArray jsonPurchaseList = new JSONArray();
        for (Purchase p : purchaseList) {
            JSONObject jsonPurchase;
            try {
                jsonPurchase = Serialization.purchaseToJson(p);
                jsonPurchaseList.put(jsonPurchase);
            } catch (JSONException e) {
                callbackContext.error(Serialization.errorToJson(-1, "Couldn't serialize Purchase: " + p.getSku()));
                return;
            }
        }
        callbackContext.success(jsonPurchaseList);
        */
    }
    
    private void getSkuDetails(String sku, final CallbackContext callbackContext) {
        if (!checkInitialized(callbackContext)) return;

        /*
        if (!_inventory.hasDetails(sku)) {
            callbackContext.error(Serialization.errorToJson(-1, "SkuDetails not found: " + sku));
            return;
        }

        JSONObject jsonSkuDetails;
        try {
            jsonSkuDetails = Serialization.skuDetailsToJson(_inventory.getSkuDetails(sku));
        } catch (JSONException e) {
            callbackContext.error(Serialization.errorToJson(-1, "Couldn't serialize SkuDetails: " + sku));
            return;
        }
        callbackContext.success(jsonSkuDetails);
        */
    }
    
    private void getSkuListDetails(List<String> skuList, final CallbackContext callbackContext) {
        if (!checkInitialized(callbackContext)) return;
/*
        JSONArray jsonSkuDetailsList = new JSONArray();
        for (String sku : skuList) {
            if (_inventory.hasDetails(sku)) {
                JSONObject jsonSkuDetails;
                try {
                    jsonSkuDetails = Serialization.skuDetailsToJson(_inventory.getSkuDetails(sku));    
                    jsonSkuDetailsList.put(jsonSkuDetails);
                } catch (JSONException e) {
                    callbackContext.error(Serialization.errorToJson(-1, "Couldn't serialize SkuDetails: " + sku));
                    return;
                }
            }
            else {
                Log.d(TAG, "SKU NOT FOUND: " + sku);
            }
        }
        callbackContext.success(jsonSkuDetailsList);
        */
    }

    //private void init(final JSONArray  options, final List<String> skuList, final CallbackContext callbackContext) {
    private void init( final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
            	
            //	MpUtils.enablePaymentBroadcast(this, READ); //Manifest.permission.PAYMENT_BROADCAST_PERMISSION);
            	
            	
                // _helper = new OpenIabHelper(cordova.getActivity(), options);
            	createBroadcasts();
            	
            	new UpdateDataTask().execute();

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


    private static final HashMap<String, JSONObject> products = new HashMap<String, JSONObject>();
    
    private void setProduct(final String productId, final JSONObject productData, final CallbackContext callbackContext) {
        if (!checkInitialized(callbackContext)) return;

        cordova.setActivityResultCallback(this);
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
            	try {
            		
                    //Map<String, Object> producDataAsMap = new HashMap<String, Object>();
                    //producDataAsMap = toMap(productData);
                    products.put(productId, productData);
                    callbackContext.success();
                } catch (JSONException e) {
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private void purchaseProduct(final String productId, final String developerPayload, final CallbackContext callbackContext) {
        if (!checkInitialized(callbackContext)) return;

        //Log.d(TAG, "SKU: " + SkuManager.getInstance().getStoreSku(OpenIabHelper.NAME_GOOGLE, sku));

        cordova.setActivityResultCallback(this);
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
            	//mClass.PaymentRequest.PaymentRequestBuilder builder = new mClass.PaymentRequest.PaymentRequestBuilder();
            	PaymentRequest.PaymentRequestBuilder builder = new PaymentRequest.PaymentRequestBuilder();
                builder.setService("aaaa", "ffffffff");
                
                try {            	

                    JSONObject config = products.get(productId);
                    String testtttt = config.getString("productName");
                    
                } catch (JSONException e) {
                    Log.e(TAG, "Invalid JSON string: " + config, e);
                }
                
                
                // get data form map                
                builder.setProductName(testtttt);
                builder.setConsumable(true);
                builder.setDisplayString(PaymentConstants.DISPLAY_STRING_GOLD);
                builder.setCreditsMultiplier(1.1d);
                //builder.setIcon(R.drawable.ic_launcher);
                
                
                
                PaymentRequest pr = builder.build();  
                
                // execute
                Intent localIntent = pr.toIntent(cordova.getActivity());
                cordova.getActivity().startActivityForResult(localIntent, REQUEST_CODE);
                
                //makePayment(pr);
                //_helper.launchPurchaseFlow(cordova.getActivity(), sku, RC_REQUEST, new BillingCallback(callbackContext), developerPayload);
            }
        });
    }

    public void purchaseSubscription(final String sku, final String developerPayload, final CallbackContext callbackContext) {
        /*
    	if (!checkInitialized(callbackContext)) return;

        cordova.setActivityResultCallback(this);
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _helper.launchSubscriptionPurchaseFlow(cordova.getActivity(), sku, RC_REQUEST, new BillingCallback(callbackContext), developerPayload);
            }
        });
        */
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

    

    private class UpdateDataTask extends AsyncTask<Void, Void, String[]> {
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
           // goldTextView.setText(data[0]);
           // bonusLevelUnlockedTextView.setText(data[1]);
           // healthPotionTextView.setText(data[2]);
           // manaPotionTextView.setText(data[3]);
        }
    }
    
    
    /**
     * Callback class for when a purchase or consumption process is finished
     */
/*
    public class BillingCallback implements
            IabHelper.QueryInventoryFinishedListener,
            IabHelper.OnIabPurchaseFinishedListener,
            IabHelper.OnConsumeFinishedListener {

        final CallbackContext _callbackContext;

        public BillingCallback(final CallbackContext callbackContext) {
            _callbackContext = callbackContext;
        }

        @Override
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d(TAG, "Query inventory process finished.");
            if (result.isFailure()) {
                _callbackContext.error(Serialization.errorToJson(result));
                return;
            }

            Log.d(TAG, "Query inventory was successful. Init finished.");
            _inventory = inventory;
            _callbackContext.success();
        }

        @Override
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Purchase process finished: " + result + ", purchase: " + purchase);
            if (result.isFailure()) {
                Log.e(TAG, "Error purchasing: " + result);
                _callbackContext.error(Serialization.errorToJson(result));
                return;
            }
            _inventory.addPurchase(purchase);
            Log.d(TAG, "Purchase successful.");
            JSONObject jsonPurchase;
            try {
                jsonPurchase = Serialization.purchaseToJson(purchase);
            } catch (JSONException e) {
                _callbackContext.error(Serialization.errorToJson(-1, "Couldn't serialize the purchase"));
                return;
            }
            _callbackContext.success(jsonPurchase);
        }

        @Override
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            Log.d(TAG, "Consumption process finished. Purchase: " + purchase + ", result: " + result);

            if (result.isFailure()) {
                Log.e(TAG, "Error while consuming: " + result);
                _callbackContext.error(Serialization.errorToJson(result));
                return;
            }
            _inventory.erasePurchase(purchase.getSku());
            Log.d(TAG, "Consumption successful. Provisioning.");
            JSONObject jsonPurchase;
            try {
                jsonPurchase = Serialization.purchaseToJson(purchase);
            } catch (JSONException e) {
                _callbackContext.error(Serialization.errorToJson(-1, "Couldn't serialize the purchase"));
                return;
            }
            _callbackContext.success(jsonPurchase);
        }
    }
*/

    private void createBroadcasts() {
        Log.d(TAG, "createBroadcasts");
        /*
        IntentFilter filter = new IntentFilter(YANDEX_STORE_ACTION_PURCHASE_STATE_CHANGED);
        cordova.getActivity().registerReceiver(_billingReceiver, filter);
        */
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
        private static final String TAG = "YandexBillingReceiver";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Bundle extras = intent.getExtras();   
            Log.d(TAG, "onReceive intent: " + intent);

            //if (YANDEX_STORE_ACTION_PURCHASE_STATE_CHANGED.equals(action)) {
                purchaseStateChanged(extras, intent);
            //}
        }

        private void purchaseStateChanged(Bundle extras, Intent intent) {
            Log.d(TAG, "purchaseStateChanged intent: " + extras);
            //_helper.handleActivityResult(RC_REQUEST, Activity.RESULT_OK, data);
            
           // Log.d(TAG, "- billing_status:  " + getStatusString(extras.getInt("billing_status")));
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
              new UpdateDataTask().execute();
            }
                        
        }
    };

    
/*
	protected final void makePayment(PaymentRequest payment) {		
		

        Intent localIntent = paymentRequest.toIntent(act);
        act.startActivityForResult(localIntent, requestCode);
        
		Context context =  cordova.getActivity().getApplicationContext();
		Intent intent = new Intent(context,payment.toIntent(this));
		startActivityForResult(this, intent, 0);
	}
	*/
    
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
					// ...
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