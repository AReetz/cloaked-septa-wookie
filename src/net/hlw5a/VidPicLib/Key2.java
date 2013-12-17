package net.hlw5a.VidPicLib;

public class Key2<A, B> implements Comparable<Key2<A, B>> {

	public A key1;
	public B key2;
	
	public Key2(A key1, B key2) {
		this.key1 = key1;
		this.key2 = key2;
	}

	@Override
	public int compareTo(Key2<A, B> o) {
		if (o == null) return 1;
		if (this.key1.hashCode() > o.key1.hashCode()) return 1;
		else if (this.key1.hashCode() < o.key1.hashCode()) return -1;
		if (this.key2.hashCode() > o.key2.hashCode()) return 1;
		else if (this.key2.hashCode() < o.key2.hashCode()) return -1;
		return 0;
	}
}