fortumo-sms-cordova-plugin
=======================

Fortumo Sms Gateway plugin for Cordova-based frameworks

Cordova Integration
-------------------

1) Add Android platform to your project using CLI.
```
$ cordova platform add android
```

2) Add Fortumo Sms Gateway plugin, referencing repo.
```
$ cordova plugin add https://github.com/jamesjara/Fortumo-Sms-Gateway-Cordova-Plugin.git
```

3) Build the app.
```
$ cordova build
```

4) Run it.
```
$ cordova run android
```

Plugin Usage
------------

1) add your GAP or NPM.
```
<gap:plugin name="fortumo-sms-cordova-plugin" source="npm" /> 
```

2) Add your scripts under cordova.js tag
```
<script type="text/javascript" src="cordova.js"></script>
<script type="text/javascript" src="fortumo"></script> 
<script src="myWallet.js"></script>
```
2) Simply call ```window.fortumo``` object methods, passing callback functions.

Initialize plugin.
```
fortumo.init(function(){}, function(){});
``` 

Example
------------
Check example folder for a wallet store prototype.
