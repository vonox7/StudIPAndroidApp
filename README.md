# Stud.IP Android App
[![CircleCI](https://circleci.com/gh/elan-ev/StudIPAndroidApp.svg?style=svg)](https://circleci.com/gh/elan-ev/StudIPAndroidApp)

A mobile Stud.IP client application for the Android platform which utilizes the studip-rest.ip Rest-API Plugin [studip/studip-rest.ip][2]
for communication with a Stud.IP backend.

## Setup
If you want to contribute to this project, feel free to do so. At first you need to fork the repository into your own account. This will change the clone URL listed in Step 1. For more information on contributions, have a look at the [contributing](#Contributing) section below.

1. Clone the repo with ```git clone https://github.com/elan-ev/StudIPAndroidApp.git```.

2. Android Studio
  * Under Quick Start click ```Import Project..``` and choose the cloned project root
  * Choose ```Use gradle wrapper (recommended)```
  * Press Ok and let Gradle do the rest
  * If you want to learn more about Gradle and Android Studio, you can find further information [here][9]

3. Server setup
  * The app now expects a static servers String variable in ```de.elanev.studip.android.app.util.ServerData.java```.
  Formatted as followed:
  
  ```json
  [
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
  ```
  
  * Since the OAuth credentials are stored in a 256-bit AES encrypted database you need to set the PRIVATE_KEY String    constant in ```de.elanev.studip.android.app.util.Config.java``` adequately.

## Contributing
* Improve the code
	* Fork it, make your changes, commit them and open a Pull Request. We will take care of the rest. You can find more information on the general workflow of this project [here][13].
* Create issues for bugs, feature requests and other ideas
* Contribute to the wiki
* Help translating the app into other languages [here][12].

## Developed By
* [ELAN e.V][8]

## License
    Copyright (c) 2017 ELAN e.V.

	All rights reserved. This program and the accompanying materials
    are made available under the terms of the GNU Public License v3.0
    which accompanies this distribution, and is available at
    http://www.gnu.org/licenses/gpl.html

[1]: https://github.com/uol-studip/StudIPAndroidApp
[2]: https://github.com/studip/studip-rest.ip
[3]: http://code.google.com/p/maven-android-plugin/wiki/GettingStarted
[8]: http://www.elan-ev.de/
[9]: http://developer.android.com/sdk/installing/studio.html
[12]: https://www.transifex.com/organization/elan-ev/dashboard/studip-mobil
[13]: https://guides.github.com/introduction/flow/
