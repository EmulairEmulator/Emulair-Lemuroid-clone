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

## Details
Emulair is an Android exclusive open-source front-end for Libretro cores, based on Lemuroid. For more information about the project (such as how the code functions, why the project exists, who worked on it, etc.), check the [Wiki page](https://github.com/Emulair/Emulair-Android/wiki). To see our backlog, check the [Projects page](https://github.com/orgs/Emulair/projects/1). Our old backlog (before transferring Emulair to this organization) can be found here: https://github.com/users/RaduBratan/projects/2/. Note that you should access the first link for the most up-to-date backlog.

## Philosophy
The primary goal of Emulair is to combine the ease of use and simple but accessible interface of Lemuroid with RetroArch's extensive customizability and features, while also adding support for standalone emulators.

## Origin
It originates from [Lemuroid](https://github.com/Swordfish90/Lemuroid), which, in turn, is a rib of [Retrograde](https://github.com/retrograde/retrograde-android). It also uses [LibretroDroid](https://github.com/Swordfish90/LibretroDroid) under the hood to run [Libretro](https://github.com/libretro) cores. The 2D sprites for the systems are taken from [hakchi2](https://github.com/ClusterM/hakchi2) and [Faustbear](https://imgur.com/gallery/8RQ1QkA).

## Systems - Libretro Cores
- Arcade by [FinalBurn Neo](https://docs.libretro.com/library/fbneo/) and [MAME 2003-Plus](https://docs.libretro.com/library/mame2003_plus/)
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

**Note: "Emulator1 _and_ Emulator2" means you can't choose between them because they are combined, "Emulator1 _or_ Emulator2" means you can switch the cores.**

## Systems - Standalone Apps
**Note: This feature hasn't been implemented yet.**

## Existing Features (from Lemuroid)
- Easily search for any installed video game
- Manually save/load states
- Automatically save/load states when correctly closing a game (by clicking "Quit" in the pause menu)
- Add games to a "Favourites" list
- Simulate various screens, such as LCD or CRT
- Remap some touch screen controls (such as joysticks or D-pads) to gyroscopic input, by double/triple tapping the buttons
- Customizable touch controls (size and position)
- ROMs scanning and indexing
- BIOSes scanning and indexing
- Gamepad support (with button remapping included)
- .zip ROMs support
- Local, offline multiplayer support (up to 4 controllers, only in games where you can select more than one player)

## Added Features
- [x] Complete visual redesign with consistent sizing, spacing and corner radius
- [x] Complete conversion to Material Design 3 Components
- [x] An easier to navigate app structure
- [x] An info screen containing frequently asked questions and other useful stuff
- [x] A profile screen used for easily connecting to RetroAchievements (RetroAchievements support not yet implemented)
- [x] A "Close menu" button accessible from the pause menu, so users don't have to click their device's back button every time they want to unpause a game
- [x] A themed app icon for Android 13+ devices
- [x] A "Jump back in" section on the "Games" page, along with "Recents", "Favorites" and "All Games"
- [x] Separate screens with vertical lists for "All Games" and "Favorites" (i.e. they're not just horizontal carousels anymore)
- [x] Button prompts before (almost) every important action
- [x] New dark theme, light theme and system default theme
- [x] New in-app icons
- [x] New sprites for systems
- [x] Bypass screen lock to rotate the screen whenever you want
- [x] Automatically rescan internal directories on app restart and resume
- [x] Fixes for bugs that were carried over from Lemuroid (e.g. multitasking splitscreen error, emulation resetting when screen auto rotates while system display scaling is non-default)
- [x] Plenty QOL features, updates and fixes (e.g. show content under notch/punch hole, add more supported word dividers for file names)

## Upcoming Features
- [ ] **_Complete rewrite in Jetpack Compose_** (my priority until I finish it)
- [ ] **_Dynamic light/dark theme_** (tried implementing with XML, should actually work with Compose)
- [ ] More emulation cores (almost all cores found on RetroArch)
- [ ] More exposed settings for all the cores
- [ ] More state slots
- [ ] More save file formats (possibly related to adding more cores)
- [ ] More animations and effects
- [ ] More controls customization (e.g. hide any button, move buttons anywhere on the screen, etc.)
- [ ] Better ROM scraping and name detection (right now games that have even the slightest variation in their names are not detected, e.g. "God of War - Chains of Olympus (USA)" is detected, but "God of War - Chains of Olympus (Asia)" isn't)
- [ ] Better NDS and 3DS touchscreen controls (i.e. will be placed above the screens)
- [ ] Cheat codes support
- [ ] RetroAchievements support
- [ ] .7z ROMs support
- [ ] Save/load state undoing/redoing support (using hidden copies)
- [ ] Search filters support (i.e. sort by system, genre, year; order by ascending or descending)
- [ ] Full phone sensors support (i.e. emulators will take advantage of tilt sensors, cameras, microphones, etc.)
- [ ] Hacked ROMs, homebrews, ROMs with unusual file formats support (possibly related to adding more cores)
- [ ] File saving to storage/emulated/0
- [ ] Automatic state saving every x seconds
- [ ] Custom touchscreen gamepad transparency
- [ ] Custom volume, fast forward speed and vibration strength
- [ ] Change volume and fast forward speed directly from the pause menu
- [ ] Different app layout for tablets and foldables
- [ ] Different app layout for phones in landscape orientation
- [ ] An updated Libretro games database with more fields (e.g. release year, release month, etc.)
- [ ] A user-friendly intro screen concisely explaining how to use the app
- [ ] A "Starred Systems" list
- [ ] A details screen accessible by long-pressing a game or a system
- [ ] A splash screen for Android 12+ devices
- [ ] Choose if you want to automatically fall back on other emulators or not, in case a game doesn't work (must unlock the option to switch between FinalBurn Neo and MAME 2003-Plus first)
- [ ] Set core on a per game basis (not just global)
- [ ] Change filter on a per game or per system basis (not just global)
- [ ] Rebind controls on a per game or per system basis (not just global)
- [ ] Save Libretro thumbnails in cache so they don't have to be reinstalled every time the user reopens the app
- [ ] Toggle between showing the content under notch/punch hole or not
- [ ] Allow BIOs scanner to also accept .bin files, not just .rom files
- [ ] Pause/Resume emulation without having to open the pause menu
- [ ] Configure overscanning amount for PS1 and N64 games (i.e. remove top and bottom borders)
- [ ] Move game screen(s) freely
- [ ] Create a debug log whenever an error occurs so developers can better understand what happened
- [ ] Allow different buttons to trigger the same action
- [ ] Make the title of the Systm Games page reflect the system the user is currently viewing
- [ ] Slide down to rescan games folder instead of automatically rescanning every time
- [ ] (If possible) Automatically convert saves when switching cores
- [ ] (If possible) Tell wether or not a 3DS ROM is encrypted
- [ ] (If possible) Run Ahead from RetroArch support
- [ ] (If possible) ROM patching support

**Note: The above features won't be released in this exact order. Some features may be changed or even removed from this list.**

## Upcoming Bug Fixes
- [ ] Some non PS1 (mostly PS2) games are incorrectly detected as PS1 games
- [ ] Regular back icon doesn't align with the searchView back icon (should be automatically fixed when reworking the search screen using Compose)
- [ ] MaterialSlider doesn't work in SeekBarPreference (should be automatically fixed when reworking the whole app using Compose)
- [ ] Saves from disk 1 aren't kept when switching to disk 2 (more testing needed)
- [ ] After disabling "Auto save state on correct quit", the last save state before the disabling doesn't load
- [ ] (If possible) After closing a game (using the phone's home button) without saving the game to a state slot, reopening the app from the app tray resets it to the home screen; but when clicking the notification or opening the app from the recents tray, the game is corectly resumed and it no longer resets (in other words, closing the app then opening it in a certain way resets it)

## Removed Features (from Lemuroid)
- Android TV support (was difficult to maintain) 
- Turn games into shortcuts for your launcher (didn't understand its purpose initially, I'll bring it back) 
- Save files to Google Drive (worked only if downloaded from Google Play; Drive folder was inaccessible; split the codebase in two versions; only acted as bandaid for the lack of keeping save files after uninstalling the app; better to remove it than keep it)

**Note: Some features will be brought back later.**

## Dropped Features (from Emulair)
- Sync save files with Google Firebase (added in separate branch for the purpose of a university course, will not be implemented)

**Note: Some features will be brought back later.**

## Name Meaning
"Emulair" is a triple entendre and the name is inspired by the Lawnchair launcher (not associated with Emulair). Firstly, "Emulair" kind of sounds like the word "Emulator" (if you try hard enough). Secondly, "Emulair" means "lair of the emu bird", an animal whose name is similar to the abbreviation of the word "emulator". Lastly, "Emulair" also means "lair for all your emulators", which is similar in name to "Vimm's Lair", a place for preserving video games.

## Legal
Copyright (C) 2023-2024 Radu-George Bratan

This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.

Before downloading the source code of this software to use it in a project (no matter if it's private or public), please make sure that you understand what the GNU General Public License v3.0 allows you, forbids you as well as requires you to do.
