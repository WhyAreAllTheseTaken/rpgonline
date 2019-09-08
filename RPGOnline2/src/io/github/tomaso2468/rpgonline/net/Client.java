package io.github.tomaso2468.rpgonline.net;

import io.github.tomaso2468.rpgonline.audio.AmbientMusic;

/**
 * An interface representing an instance of the game client's connection with the server.
 * @author Tomas
 *
 */
public interface Client extends TickBased {
	/**
	 * Gets the music that should play.
	 * @return An AmbientMusic instance.
	 */
	public AmbientMusic getMusic();

	/**
	 * Gets the type of the server.
	 * @return
	 * 
	 * @see io.github.tomaso2468.rpgonline.RPGGame#getVersionFlavour()
	 */
	public String getServerType();
	
}
