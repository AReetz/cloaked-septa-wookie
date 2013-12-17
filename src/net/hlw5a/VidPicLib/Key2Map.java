package net.hlw5a.VidPicLib;

import java.util.TreeMap;

public class Key2Map<K1, K2, V> extends TreeMap<Key2<K1, K2>, V> {
	private static final long serialVersionUID = -852357578943809242L;
	
	public V get(K1 key1, K2 key2) {
		Key2<K1, K2> ndk = new Key2<K1, K2>(key1, key2);
		return super.get(ndk);
	}
	
	public void put(K1 key1, K2 key2, V value) {
		Key2<K1, K2> ndk = new Key2<K1, K2>(key1, key2);
		super.put(ndk, value);
	}
	
	public void remove(K1 key1, K2 key2) {
		Key2<K1, K2> ndk = new Key2<K1, K2>(key1, key2);
		super.remove(ndk);
	}
}