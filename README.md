Stud.IP Android App
===================

A mobile Stud.IP client application for the Android plattform which utilizes the studip-rest.ip Rest-API Plugin [studip/studip-rest.ip][2]
for communication with a Stud.IP backend.

NOTE: This app is in beta stadium. Use at own risk.

If you want to contribute to this project, feel free to do so. For more information on how to do this read the Contributing section below.

Setup
-----
1. Git stuff
  * Clone the project.
  * Run ```git submodule init``` to initialize the submodules
  * ```git submodule update``` to clone the submodules

2. Gradle (Android Studio)
  * Under Quick Start click ```Import Project..``` and choose the cloned project root
  * Choose ```Use gradle wrapper (recommended)```
  * Press Ok and let Gradle do the rest
  * If you want to learn more about Gradle and Android Studio, you can find further information [here][9]

3. Server setup
  * The app now expects a static servers String variable in ```de.elanev.studip.android.app.util.ServerData.java```.
  Formatted as followed:
  
  ```json
  {
    "servers": [
      {
        "name": "Installation name",
        "consumer_key": "consumer key generated by the restip plugin",
        "consumer_secret": "consumer secret generated by the restip plugin",
        "base_url": "https://example.com/plugins.php/restipplugin",
        "contact_email": "contact@example.com"
      },
      {
        "name": "Optional second installation",
        "consumer_key": "XXX",
        "consumer_secret": "XXX",
        "base_url": "XXX",
        "contact_email": "XX@XXX.de"
      }
    ]
  }
  ```
  
  * Since the OAuth credentials are stored in a 256-bit AES encrypted database you need to set the PRIVATE_KEY String    constant in ```de.elanev.studip.android.app.util.Config.java``` adequately.

Contributing
------------
* Improve the code
	* Fork it, make your changes, commit them and open a Pull Request. We will take care of the rest.
* Create issues for bugs, feature requests and other ideas
* Contribute to the wiki

Libs
---------
* [oauth-signpost][4]
* [Jackson JSON Processor][5]
* [ActionBarSherlock][6]
* [Google Volley HTTP][7]
* [Square Picasso][10]
* [SQLCipher][11]

Developed By
------------
* [ELAN e.V][8]

License
-------
    Copyright (c) 2014 ELAN e.V.

	All rights reserved. This program and the accompanying materials
    are made available under the terms of the GNU Public License v3.0
    which accompanies this distribution, and is available at
    http://www.gnu.org/licenses/gpl.html

[1]: https://github.com/uol-studip/StudIPAndroidApp
[2]: https://github.com/studip/studip-rest.ip
[3]: http://code.google.com/p/maven-android-plugin/wiki/GettingStarted
[4]: http://code.google.com/p/oauth-signpost/
[5]: http://wiki.fasterxml.com/JacksonHome
[6]: http://actionbarsherlock.com/
[7]: https://android.googlesource.com/platform/frameworks/volley/
[8]: http://www.elan-ev.de/
[9]: http://developer.android.com/sdk/installing/studio.html
[10]: http://square.github.io/picasso/
[11]: http://sqlcipher.net/
