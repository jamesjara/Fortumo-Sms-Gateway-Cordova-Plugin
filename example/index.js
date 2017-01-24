/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
var app = {
    // Application Constructor
    initialize: function() {
        this.bindEvents();
    },
    // Bind Event Listeners
    //
    // Bind any events that are required on startup. Common events are:
    // 'load', 'deviceready', 'offline', and 'online'.
    bindEvents: function() {
        document.addEventListener('deviceready', this.onDeviceReady, false);
    },
    // deviceready Event Handler
    //
    // The scope of 'this' is the event. In order to call the 'receivedEvent'
    // function, we must explicitly call 'app.receivedEvent(...);'
    onDeviceReady: function() {
        app.receivedEvent('deviceready'); 
        
    	myWallet.initialize(function(){}, function(){});
    	
		// Set Productos
		myWallet.registerProduct("759d862f18c1f140b1fe3331ac0581d7", {
	        "name" : "MANUAL PDF 2017",
	        "appSecret" : "1deff734c0a3b5d8ad9309049108b4f1",
	        "display" : "MANUAL PDF 2017",
	        "amount" : "4.99",
	        "currency" : "USD",
	    }, function(){
	    	//alert('registerProduct success ');
	    	
	    }, function(){
	    	//alert('registerProduct fail ');
	    	
	    }, function(){
	    	alert('La descarga iniciara en unos segundos, muchos exitos en tu examen ');
	    	window.open('https://goo.gl/imXVEc', '_system');
	    	
	    }, function(){
	    	alert('Ups, error, intentalo mas tarde');
	    	
	    	alert('La descarga iniciara en unos segundos, muchos exitos en tu examen ');
	    	window.open('https://goo.gl/imXVEc', '_system');
	    }); 
		 
    },
    // Update DOM on a Received Event
    receivedEvent: function(id) {  
        console.log('Received Event: ' + id); 
        
    },

    getPurchasesSuccess: function(purchaseList)
    {
    	alert("GetPurchases.SUCCESS: " + JSON.stringify(purchaseList));
    },
    getPurchasesFail: function(error)
    {
    	alert("GetPurchases.FAIL: " + error.message);
    },    
    initSuccess: function()
    {
    	alert("Init.SUCCESS");      
    },
    initFail: function(error)
    { 
    	alert("Init.FAIL: " + error.message);
    },
    purchaseSuccess: function(purchase)
    {
    	alert("Purchase.SUCCESS: " + JSON.stringify(purchase));
    },
    purchaseFail: function(error)
    {
    	alert("Purchase.FAIL: " + error.message);
    }, 
};



app.initialize(); 

function buy() { 
	myWallet.purchaseProduct("759d862f18c1f140b1fe3331ac0581d7");	
}