
var myWallet = myWallet || {};
 
myWallet = {
	cordovaPlugin: null,

	_customEvents: {},
	
    // Application Constructor
    initialize: function(onInit, onError) {  
    	
        this.cordovaPlugin = window.fortumo ? window.fortumo : false;
        if (!this.cordovaPlugin) {
        	alert("not fotrutmo");
            console.log("cordovaPlugin fortumo not found!");
            return;
        } 
        
        this.cordovaPlugin.init(onInit, onError);
        
    },
 	registerProduct: function(productId, configuration, success, fail, purchaseDone, purchaseFail) {   

        var customEvents = this._customEvents;        
        customEvents[productId+'_purchaseDone'] = purchaseDone;
        customEvents[productId+'_purchaseFail'] = purchaseFail;
        
         this.cordovaPlugin.setProduct(success, fail, productId, configuration); 
    },
 
    purchaseProduct: function(productId) {
        var customEvents = this._customEvents;   
        this.cordovaPlugin.purchaseProduct(customEvents[productId+'_purchaseDone'], customEvents[productId+'_purchaseFail'], productId);
    }
    
}