var exec = require('cordova/exec');

PLUGIN = "FortumoSmsCordovaPlugin";

function Fortumo()
{
	this.options = 
	{
                serviceId: '',
                appSecret: '',  

	}
                
      /*          products : [] [
                              productName : 'productName1',
                              icon : 'productName1',
                              text : 'text1',
                              price: '4.99',
                              currency: 'USD',
                              type : 'TYPE_CONSUMABLE'
                              ],[
                                 productName : 'productName2',
                                 icon : 'productName2',
                                 text2 : 'text2',
                                 price: '1.99',
                                 currency: 'USD',
                                 type : 'productName'
                              ] 
                ]
	*/
	}    
	
	this.error =
	{
		code:-1,
		message:""
	}
}
 
Fortumo.prototype.init = function(success, error)
{
	exec(success, error, PLUGIN, "init", [this.options]);
}

Fortumo.prototype.setProduct = function(success, error, productId , payload )
{
        exec(success, error, PLUGIN, "setProduct", [productId, payload]);
}

Fortumo.prototype.purchaseProduct = function(success, error, productId, payload)
{
        exec(success, error, PLUGIN, "purchaseProduct", [productId, payload]);
}


Fortumo.prototype.consume = function(success, error, sku)
{
	exec(success, error, PLUGIN, "consume", [sku]);
}

module.exports = new Fortumo();