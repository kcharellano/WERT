package com.game.wert.learn;

import java.util.HashMap;
public class DefaultMap<K, V> extends HashMap<K, V> {
	//TODO: Unsafe casting
	
	/**
	 * If a key doesn't exist in the map then
	 * insert a default value and return it
	 */
	@Override
    public V get(Object key) {
        V returnValue = super.get(key);
        if (returnValue == null) {
            returnValue = (V) new float[4];
            this.put((K) key, (V) returnValue);
        }
        return returnValue;
    }    
}
