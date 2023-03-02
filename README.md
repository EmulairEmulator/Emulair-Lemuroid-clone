# Emulair Emulator
<img src="https://play.google.com/intl/en_us/badges/images/generic/en-play-badge.png"
     alt="Get it on Google Play"
     height="80">
<img src="https://fdroid.gitlab.io/artwork/badge/get-it-on.png"
     alt="Get it on F-Droid"
     height="80">
<img src="https://raw.githubusercontent.com/flocke/andOTP/master/assets/badges/get-it-on-github.svg"
     alt="Get it on GitHub"
     height="80">

## Description
Emulair is an Android exclusive open-source front-end for Libretro cores, based on Lemuroid.

## Philosophy
The primary goal of Emulair is to combine the ease of use and simple but accessible interface of Lemuroid with Retroarch's extensive customisability and features.

## Origin
It originated from [Lemuroid](https://github.com/Swordfish90/Lemuroid), which, in turn, is a rib of [Retrograde](https://github.com/retrograde/retrograde-android). It also uses [LibretroDroid](https://github.com/Swordfish90/LibretroDroid) under the hood to run [Libretro](https://github.com/libretro) cores.

## Systems
- Arcade by [FB Neo](https://docs.libretro.com/library/fbneo/) and [MAME 2003-Plus](https://docs.libretro.com/library/mame2003_plus/)
- Atari 2600 by [Stella](https://docs.libretro.com/library/stella/)
- Atari 7800 by [ProSystem](https://docs.libretro.com/library/prosystem/)
- Atari Lynx by [Handy](https://docs.libretro.com/library/handy/)
- Bandai WonderSwan by [Beetle Cygne](https://docs.libretro.com/library/beetle_cygne/)
- Bandai WonderSwan Color by [Beetle Cygne](https://docs.libretro.com/library/beetle_cygne/)
- NEC PC Engine by [Beetle PCE FAST](https://docs.libretro.com/library/beetle_pce_fast/)
- Nintendo 64 by [Mupen64Plus](https://docs.libretro.com/library/mupen64plus/)
- Nintendo DS by [DeSmuME](https://docs.libretro.com/library/desmume/) or [melonDS](https://docs.libretro.com/library/melonds/)
- Nintendo 3DS by [Citra](https://docs.libretro.com/library/citra/)
- Nintendo GB by [Gambatte](https://docs.libretro.com/library/gambatte/)
- Nintendo GBC by [Gambatte](https://docs.libretro.com/library/gambatte/)
- Nintendo GBA by [mGBA](https://docs.libretro.com/library/mgba/)
- Nintendo NES by [FCEUmm](https://docs.libretro.com/library/fceumm/)
- Nintendo SNES by [Snes9x](https://docs.libretro.com/library/snes9x/)
- Sega Genesis by [Genesis Plus GX](https://docs.libretro.com/library/genesis_plus_gx/)
- Sega CD by [Genesis Plus GX](https://docs.libretro.com/library/genesis_plus_gx/)
- Sega Game Gear by [Genesis Plus GX](https://docs.libretro.com/library/genesis_plus_gx/)
- Sega Master System by [Genesis Plus GX](https://docs.libretro.com/library/genesis_plus_gx/)
- SNK NG Pocket by [Beetle NeoPop](https://docs.libretro.com/library/beetle_neopop/)
- SNK NG Pocket Color by [Beetle NeoPop](https://docs.libretro.com/library/beetle_neopop/)
- Sony PS1 by [PCSX ReARMed](https://docs.libretro.com/library/pcsx_rearmed/)
- Sony PSP by [PPSSPP](https://docs.libretro.com/library/ppsspp/)

## Imported Features (from Lemuroid)
- Manually save/load states
- Automatically save/load states when correctly closing a game (by clicking "Quit" in the pause menu)
- Save files to Google Drive (only if downloaded from Google Play)
- Add games to a "Favourites" list
- Simulate various screens, such as LCD or CRT
- Remap some touch screen controls (such as joysticks or d-pads) to gyroscopic input, by double tapping / triple tapping the buttons
- Customizable touch controls (size and position)
- ROMs scanning and indexing
- Gamepad support
- .zip ROMs support
- Local, offline multiplayer (up to 4 controllers, only in games that offer the option for multiple players)

## Added Features
- [x] A "Jump back in" section on the "Games" page, along with "Recents", "Favorites" and "All Games"
- [x] Complete visual app redesign with consistent sizing, spacing and corner radius
- [x] Complete Material3 design conversion

## To-Be-Added Features
- [ ] More emulation cores (almost all cores found on Retroarch)
- [ ] Exposed all settings for all cores
- [ ] Cheat codes support
- [ ] A user friendly intro screen concisely explaining how to use the app
- [ ] An info screen further explaining how the app works
- [ ] A profile screen
- [ ] Button prompts before every important action (such as saving/loading states)
- [ ] Light Theme, AMOLED Theme, Material You Theme (previously there was only a Dark Theme)
- [ ] Slide down in the "Games" page to rescan your directories and refresh your games list
- [ ] Automatically rescan internal directories on app restart
- [ ] Set what screen orientation games should be opened in (follow device / landscape / portrait)
- [ ] Set on-screen buttons' transparency
- [ ] Pause/resume emulation directly from the pause menu
- [ ] Thumbnail cache
- [ ] Different app layout for tablets and foldables
- [ ] .7z ROMs support
- [ ] Automatically save state every x seconds
- [ ] More save file formats support
- [ ] Updated Libretro games database
- [ ] Add systems to a "Starred" list
- [ ] Long press a game to access a details screen
- [ ] Beautiful animations
- [ ] A "Close menu" button accessible from the pause menu, so users don't have to click their back button every time they want to close it

## Name Meaning
"Emulair" is a triple entendre and the name is inspired by the Lawnchair launcher (not associated with Emulair). Firstly, "Emulair" sounds similar to the word "Emulator" (if you try hard enough). Secondly, "Emulair" means "lair of the emu bird", an animal commonly associated with emulators, given their similar names. Lastly, "Emulair" also means "lair for all your emulators", which is similar to "Vimm's Lair", a place for preserving video games.
