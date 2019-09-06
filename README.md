# RPGOnline
A game engine for 2D top-down tile-based games with multiplayer support.

RPGOnline is licensed under the 3-Clause BSD Licence.

This library is written on top of slick2d to provide more specific features. Eventually, the project is planned to move to LWJGL2 and later LWJGL3. Currently using Slick2d are: the rendering engine, collision system, some of the input system, atmosphere system, cutscene system, some of the entity system and some of the world system.

# Dependencies
All dependencies are included in the release.zip file.
- Apache Commons Lang https://commons.apache.org/proper/commons-lang/ [Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0)
- Apache Commons Math http://commons.apache.org/proper/commons-math/ [Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0)
- Slick2D (and related depenencies) http://slick.ninjacave.com [Slick2D Licence](http://slick.ninjacave.com/license/)
  - IBXM [BSD 3-Clause](https://opensource.org/licenses/BSD-3-Clause)
  - JInput https://github.com/jinput/jinput [BSD 3-Clause](https://opensource.org/licenses/BSD-3-Clause)
  - LWJGL2 http://legacy.lwjgl.org [BSD 3-Clause](http://legacy.lwjgl.org/license.php.html)
  - tinylinepp
- JNA https://github.com/java-native-access/jna [Apache 2.0 or LGPL 2.1](https://github.com/java-native-access/jna/blob/master/LICENSE)
- JSR-305 [BSD 2-Clause](https://github.com/findbugsproject/findbugs/blob/master/findbugs/licenses/LICENSE-jsr305.txt)
- Sound System http://www.paulscode.com/forum/index.php?topic=4.0 [Sound System License](http://www.paulscode.com/forum/index.php?topic=4.0)
  - CodecWAV http://www.paulscode.com/forum/index.php?topic=4.0
  - CodecIBXM http://www.paulscode.com/forum/index.php?topic=4.0
    - IBXM [BSD 3-Clause](https://opensource.org/licenses/BSD-3-Clause)
  - CodecJOrbis http://www.paulscode.com/forum/index.php?topic=4.0
    - JOrbis http://www.jcraft.com/jorbis [LGPL 3.0](https://www.gnu.org/licenses/lgpl-3.0.en.html)
    
# Installation
1. Download the rpgonline.zip folder of the latest stable release.
2. Extract the contents of the folder.
3. Add the JAR files to your project classpath.
4. Add the folder as a folder containing native files.

# Notes
This library was created to help with the development of certain games and to avoid having to rewrite code.
