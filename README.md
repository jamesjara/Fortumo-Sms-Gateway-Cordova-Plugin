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

1) Include script to your application.
```
<script type="text/javascript" src="cordova.js"></script>
```

2) Simply call ```fortumo``` object methods, passing callback functions.


Set store keys.
```
fortumo.options.storeKeys = [['your public key']];
```

c) Initialize plugin.
```
fortumo.init(function(){}, function(error){}, [ "SKU1", "SKU2", "SKU3" ]);
```


