package net.hlw5a.VidPicLib;

public class Mapping<A> {
	private Comparable key;
	private A value;
	
	public Mapping(Comparable key, A value) {
		this.key = key;
		this.value = value;
	}
	
	public Comparable getKey() {
		return key;
	}
	
	public A getValue() {
		return value;
	}
}
