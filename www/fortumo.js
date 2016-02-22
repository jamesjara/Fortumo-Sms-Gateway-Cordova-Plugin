var exec = require('cordova/exec');
PLUGIN = "FortumoSmsCordovaPlugin";

function Fortumo() {
    this.options = {}
    this.error = {
        code: -1,
        message: ""
    }
}
Fortumo.prototype.init = function(success, error) {
    exec(success, error, PLUGIN, "init", [this.options]);
}
Fortumo.prototype.setProduct = function(success, error, productId, payload) {
    exec(success, error, PLUGIN, "setProduct", [productId, payload]);
}
Fortumo.prototype.purchaseProduct = function(success, error, productId, payload) {
    exec(success, error, PLUGIN, "purchaseProduct", [productId, payload]);
}
Fortumo.prototype.getProducts = function(success, error) {
    exec(success, error, PLUGIN, "getProducts");
}
Fortumo.prototype.consume = function(success, error, sku) {
    exec(success, error, PLUGIN, "consume", [sku]);
}
module.exports = new Fortumo();