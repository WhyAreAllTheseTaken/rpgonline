# RPGOnline
A game engine for 2D top-down tile-based games with multiplayer support. The library also provides a format for storing binary data as a series of hierachal tags.

RPGOnline is licensed under the 3-Clause BSD Licence.

In the future, the possibility of a 3D system in addition to the 2D engine may be considered.

[Contributing to RPGOnline](/CONTRIBUTING.md)

## Documentation
Docs: https://tomaso2468.github.io/rpgonline  
Wiki: https://github.com/Tomaso2468/rpgonline/wiki  
Reddit: https://www.reddit.com/r/rpgonline/  
Discord Server: https://discord.gg/cxBZTwb

## Dependencies
All dependencies are included in the release.zip file.
- Apache Commons Lang https://commons.apache.org/proper/commons-lang/ [Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0)
- Apache Commons Math http://commons.apache.org/proper/commons-math/ [Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0)
- SlickUtil (and related depenencies) http://slick.ninjacave.com [Slick2D Licence](http://slick.ninjacave.com/license/)
  - IBXM [BSD 3-Clause](https://opensource.org/licenses/BSD-3-Clause)
  - JInput https://github.com/jinput/jinput [BSD 3-Clause](https://opensource.org/licenses/BSD-3-Clause)
  - LWJGL2 http://legacy.lwjgl.org [BSD 3-Clause](http://legacy.lwjgl.org/license.php.html)
  - tinylinepp
- JNA https://github.com/java-native-access/jna [Apache 2.0 or LGPL 2.1](https://github.com/java-native-access/jna/blob/master/LICENSE)
- Sound System http://www.paulscode.com/forum/index.php?topic=4.0 [Sound System License](http://www.paulscode.com/forum/index.php?topic=4.0)
  - CodecWAV http://www.paulscode.com/forum/index.php?topic=4.0
  - CodecIBXM http://www.paulscode.com/forum/index.php?topic=4.0
    - IBXM [BSD 3-Clause](https://opensource.org/licenses/BSD-3-Clause)
  - CodecJOrbis http://www.paulscode.com/forum/index.php?topic=4.0
    - JOrbis http://www.jcraft.com/jorbis [LGPL 3.0](https://www.gnu.org/licenses/lgpl-3.0.en.html)
- Simplex Noise https://github.com/SRombauts/SimplexNoise/blob/master/references/SimplexNoise.java Public Domain
- ABT https://github.com/Tomaso2468/abt [BSD 3-Clause](https://github.com/Tomaso2468/abt/blob/master/LICENSE)
- JDA https://github.com/DV8FromTheWorld/JDA [Apache 2.0](https://github.com/DV8FromTheWorld/JDA/blob/master/LICENSE)
- SteamWorks4j https://github.com/code-disaster/steamworks4j [MIT License](https://github.com/code-disaster/steamworks4j/blob/master/LICENSE)
    
## Installation
1. Download the rpgonline.zip folder of the latest stable release.
2. Extract the contents of the folder.
3. Add the JAR files to your project classpath.
4. Add the folder as a folder containing native files.

[For a complete guide check the Wiki](https://github.com/Tomaso2468/rpgonline/wiki/Setting-up-RPGOnline-for-Eclipse)

## Notes
This library was created to help with the development of certain games and to avoid having to rewrite code.

## Older Versions
The intial versions of this engine can be found in an archived repository: https://github.com/Tomaso2468/rpgonline-old
