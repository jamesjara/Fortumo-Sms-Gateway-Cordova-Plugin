var exec = require('cordova/exec');

PLUGIN = "FortumoSmsCordovaPlugin";

function Fortumo()
{
	this.VERIFY_MODE =
	{
		EVERYTHING:0,
		SKIP:1,
		ONLY_KNOWN:2
	}

	this.STORE_NAME =
	{
	    GOOGLE:"com.google.play",
	    AMAZON:"com.amazon.apps",
	    SAMSUNG:"com.samsung.apps",
	    YANDEX:"com.yandex.store",
	    NOKIA:"com.nokia.nstore",
	    APPLAND:"Appland",
	    SLIDEME:"SlideME",
	    APTOIDE:"cm.aptoide.pt",
        NAME_FORTUMO:"com.fortumo.billing",
        NAME_SKUBIT:"com.skubit.android",
        NAME_SKUBIT_TEST:"net.skubit.android"
    }

    this.STORE_SEARCH_STRATEGY =
    {
        INSTALLER: 0,
        BEST_FIT: 1,
        INSTALLER_THEN_BEST_FIT: 2
    }
    
	this.options = 
	{
		checkInventory:false,
		discoveryTimeout:5 * 1000,
		checkInventoryTimeout:10 * 1000,
		verifyMode:this.VERIFY_MODE.SKIP,
		storeKeys: [ [this.STORE_NAME.GOOGLE, 'public_key'] ],
		preferredStoreNames: [ this.STORE_NAME.GOOGLE ],
        availableStoreNames: [ this.STORE_NAME.GOOGLE, this.STORE_NAME.YANDEX ],
        storeSearchStrategy: this.STORE_SEARCH_STRATEGY.INSTALLER,
		samsungCertificationRequestCode: 0
	}
    
    this.options.storeKeys

	this.error =
	{
		code:-1,
		message:""
	}

	this.purchase =
	{
        itemType:null,
        orderId:null,
        packageName:null,
        sku:null,
        purchaseTime:null,
        purchaseState:null,
        developerPayload:null,
        token:null,
        originalJson:null,
        signature:null,
        appstoreName:null
	}

	this.skuDetails =
	{
		itemType:null,
        sku:null,
        type:null,
        price:null,
        title:null,
        description:null,
        json:null
	}
}

Fortumo.prototype.mapSku = function(success, error, sku, storeName, storeSku)
{
	exec(success, error, PLUGIN, "mapSku", [sku, storeName, storeSku]);
}

Fortumo.prototype.getSkuDetails = function(success, error, sku)
{
	exec(success, error, PLUGIN, "getSkuDetails", [sku]);
}

Fortumo.prototype.getSkuListDetails = function(success, error, skuList)
{
	exec(success, error, PLUGIN, "getSkuListDetails", [skuList]);
}

Fortumo.prototype.getPurchases = function(success, error)
{
    exec(success, error, PLUGIN, "getPurchases", []);
}

Fortumo.prototype.init = function(success, error, skuList)
{
	exec(success, error, PLUGIN, "init", [this.options, skuList]);
}

Fortumo.prototype.purchaseProduct = function(success, error, sku, payload)
{
	exec(success, error, PLUGIN, "purchaseProduct", [sku, payload]);
}

Fortumo.prototype.purchaseSubscription = function(success, error, sku, payload)
{
	exec(success, error, PLUGIN, "purchaseSubscription", [sku, payload]);
}

Fortumo.prototype.consume = function(success, error, sku)
{
	exec(success, error, PLUGIN, "consume", [sku]);
}

module.exports = new Fortumo();