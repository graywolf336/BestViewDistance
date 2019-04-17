# BestViewDistance [![Build Status](https://ci.craftyn.com/job/BestViewDistance/badge/icon)](https://ci.craftyn.com/job/BestViewDistance/)
A view distance plugin for Paper. Created to boost ping/tps and view distance. [Development Builds](https://ci.craftyn.com/job/BestViewDistance/)

## How it works ?
This plugin chooses the **best view distance for your players**.
The **view distance** is calculated based on your ping and the **TPS of the server**.
If the TPS are low, the view distance is reduced by a percentage called "*reduction indice*".
The reduction indice changes according to the TPS of the server.

This plugin **reduces players lag**, reduces players **ping** and **optimizes** your server a **better view distance**.

## Dependencies
Please download Paper !
https://papermc.io
It's better than spigot and bukkit.
Also provide you a lot of optimizations and options.
[WARNING] ProtocolLib is needed ONLY for 1.12.X !

## Commands / Permissions
```
/view server => Get reduction indice.
/view tps => Get server's tps.
/view ping <player> => Get player ping.
/view <player> => Get player actual view distance and his max view distance.
/view => Get help

Permission : "view.check"

/view reload => Reload plugin config. Need "view.reload"
/vdist => Allows your players to see their own view distance. Need "view.info"
/vping => Allows your players to see their own ping. Need "view.info"

Bypass algorithm : "view.set.x" (3 to 32)
(Example : view.set.15 set a view dist of 15 chunks)
Does not work with "*"/"*.*" permissions nodes !
```

## Config
```
#   ╔╗ ┌─┐┌─┐┌┬┐  ╦  ╦┬┌─┐┬ ┬  ╔╦╗┬┌─┐┌┬┐┌─┐┌┐┌┌─┐┌─┐
#   ╠╩╗├┤ └─┐ │   ╚╗╔╝│├┤ │││   ║║│└─┐ │ ├─┤││││  ├┤
#   ╚═╝└─┘└─┘ ┴    ╚╝ ┴└─┘└┴┘  ═╩╝┴└─┘ ┴ ┴ ┴┘└┘└─┘└─┘
#       - Get a Better View Distance, By LXCT. -
#
# Donate: https://paypal.me/lxct

Version: 2.6 # Version of the config file. Don't change this value.

Features: # UseTeleportView can increase lags.
  UseTPS: true # Increase/Decrease view in function of server's TPS (Reduction Indice = 0)
  UsePing: true # The plugin will give a custom view distance for each players
  UseLoginView: true # Use a custom view on login
  UseAFKView: true # Use a custom view if the player is AFK.
  UseTasks: true # Use tasks. Turn this off will reduce lags, but view distance will change slower.
  UseFlyingView: false # Use a custom view if the player is flying.
  UseTeleportView: false # Use a custom view on teleport. Can reduce freeze on teleport.
  UsePermissions: false # Enable permissions (view.set.x) to bypass algorithm.

ViewDistance: # Adjust that according to your needs
  Min: 4 # Default minimum view distance (Minimum: 3)
  Max: 16 # Default maximum view distance (Maximum: 32)
  OnLogin: 4 # View distance on login
  OnAFK: 3 # AFK view distance (If UseAFKView is on true)
  OnTeleport: 4 # View distance on teleport (If UseTeleportView is on true)
  OnFlying: 12 # View distance if flying (If UseFlyingView is on true)
  MoreThanSettings: 0 # Add x chunks more than player's settings.

Delay: # Warning: A low value for SetViewDelay can increase lags and create ghost chunks!
  CalculationsDelay: 1 # Delay in seconds to actualize calculations
  SetViewDelay: 20 # Delay in seconds to actualize global view distance (Impact performances)
  UnsetTeleportViewDelay: 3 # Delay in seconds to unset the OnTeleport custom view (Impact performances)
  IncrementalViewIncreaseDelay: 1 # The amount of seconds between view distance increments. Higher amounts will "appear" as lag to players.
  CheckFlyingDelay: 5 # Delay in seconds before set the OnFlying view distance
  AFKDelay: 90 # Delay in seconds before set the OnAFK view distance

Settings: # Calculations settings
  TpsLimit: 19.5 # Below: Decrease Reduction Indice || Over: Increase Reduction Indice.
  TpsChangeIndice: 0.01 # How much we had to increase/decrease the reduction indice. 0.01 = 1%
  MaxReductionIndice: 0.75 # Maximum Reduction Indice (0.75 = 75%)
  SafePing: 1 # Set this value to 0 for local hosting.
  PingForReduction: 550 # Ping required to decrease view distance
  PingForAugmentation: 90 # Ping required to increase view distance

Permissions: # Bypass settings
  BypassAFKView: true # Player with permissions (view.set.x) can bypass the "OnAFK" view.
  BypassTeleportView: true # Player with permissions (view.set.x) can bypass the "OnTeleport" view.
  BypassFlyingView: true # Player with permissions (view.set.x) can bypass the "OnFlying" view.

Misc:
  DecimalsTPS: 2 # How many decimals for the %VDIST_DECIMAL_TPS% placeholder
  DecimalsIndice: 2 # How many decimals for the %VDIST_REDUCTION_INDICE_DECIMAL% placeholder
  HideVdistLine4: false # Hide the 4th line of the /vdist command
  Metrics: true # Send anonymous stats

Worlds: # Custom Min and Max per worlds
  Example: # Name of your world
    Max: 32 # Max value of world
    Min: 16 # Min value of world
  Example2: # Another Example.
    Max: 8 # Max for Example2 World
    Min: 3 # Min for Example2 World

Regions: # Custom Min and Max per regions
  Example: # Name of your region
    Max: 32 # Max value of region
    Min: 16 # Min value of region
  Example2: # Another Example.
    Max: 8 # Max for Example2 region
    Min: 3 # Min for Example2 region
```

+ Messages.yml File.

## PlaceholderAPI :
```
%VDIST_REDUCTION_INDICE% => Get the reduction indice in percentage
%VDIST_REDUCTION_INDICE_DECIMAL% => Get the reduction indice with decimals
%VDIST_TPS% => Get TPS
%VDIST_DECIMAL_TPS% => Get TPS with decimals
%VDIST_PLAYER_SETTINGS_VIEW% => Get the render distance in player's settings
%VDIST_PLAYER_SUPPORTED_VIEW% => Get the supported view distance of a player
%VDIST_PLAYER_CURRENT_VIEW% => Get the current view distance of a player
```

## Compilation
Bash/Zsh linux terminal :

```
git clone https://github.com/graywolf336/BestViewDistance.git
cd BestViewDistance
mvn clean install
```
The jar is in the `target/` folder.
