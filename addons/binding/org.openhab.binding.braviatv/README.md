# Sony Bravia TV binding

The binding allows sending remote commands to Sony Bravia TV's (Only tested for Android TVs).  


## Supported Things

### BraviaTV

The Sony Bravia Android TV allow remote control commands


## Thing Configuration

### BraviaTV

* ipAddress (mandatory)
* psk (optional), a pre-shared-key set in your TVs settings
* auth (optional), when you do not use the PSK mode you have to authenticate yourself to the TV, you can to this by executing the auth-sh
command from https://github.com/breunigs/bravia-auth-and-remote.git


## Channels

| Channel Type ID | Item Type* | Description  |
|-------------|--------|-----------------------------|
| command | String | The command send to the TV |

## Available Commands
* POWER_OFF
* MODE_ANALOG
* MODE_DIGITAL

* INPUT_TOGGLE
* INPUT_HDMI_1
* INPUT_HDMI_2
* INPUT_HDMI_3
* INPUT_HDMI_4

* GGUIDE
* EPISODE_GUIDE
* FAVORITES
* DISPLAY
* HOME
* OPTIONS
* RETURN
* CONFIRM
* SUBTITLE
* EXIT
* MODE3D
* SCENE
* IMANUAL

* PLAY
* PAUSE
* STOP
* NEXT
* FORWARD
* PREV
* REWIND

* RED
* GREEN
* YELLOW
* BLUE

* NUM_0
* NUM_1
* NUM_2
* NUM_3
* NUM_4
* NUM_5
* NUM_6
* NUM_7
* NUM_8
* NUM_9

* VOLUME_UP
* VOLUME_DOWN
* VOLUME_MUTE

* ARROW_UP
* ARROW_DOWN
* ARROW_LEFT
* ARROW_RIGHT

* CHANNEL_UP
* CHANNEL_DOWN

* APP_NETLFIX

