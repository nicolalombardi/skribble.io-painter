# Skribble.io Painter
This is a small program written in Java that allows you to paint perfect pictures in Skribble.io

This project can be easily imported into IDEs as a maven project

## Dependencies
The only external dependency for this project is Unirest. You can learn more and get instructions to install it [here](http://unirest.io/java.html)
## Configuration
The configuration is managed by the file "config.cfg".</br>A template file is provided to simplify the configuration process.

Key|Value
---|-----
apiKey|Google Cloud api key, necessary to use the Custom Search API
cx|The google custom search engine ID
resolution|This value is used to determine which set of configuration values to use
mouse_move_ms|Delay in ms after any mouse movement
mouse_press_ms|Delay in ms after the mouse button is pressed (can be reduced to 0)
mouse_end_ms|Delay in ms after a pixel is drawn on the screen
[res]_canvasX|X position of the canvas in pixels
[res]_canvasY|Y position of the canvas in pixels
[res]_paletteX|X position of the colours palette in pixels
[res]_paletteY|Y position of the colours palette in pixels
[res]_brushSize|Brush diameter in pixels
[res]_paletteXStep|How wide is a palette colour tile
[res]_paletteYStep|How tall is a palette colour tile

</br>**Note**: The delays regarding the mouse movement/action will have to be adjusted depending on your internet connection and CPU
