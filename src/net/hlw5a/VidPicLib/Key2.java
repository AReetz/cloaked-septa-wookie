package net.hlw5a.VidPicLib;

public class Key2<A, B> implements Comparable {

	public A key1;
	public B key2;
	
	public Key2(A key1, B key2) {
		this.key1 = key1;
		this.key2 = key2;
	}

	@Override
	public int compareTo(Object o) {
		if (o == null) return -1;
		if (!(o instanceof Key2<?, ?>)) return -1;
		Key2<A, B> dko = (Key2<A, B>)o;
		if (!dko.key1.equals(this.key1) || !dko.key2.equals(this.key2)) return -1;
		return 0;
	}
}