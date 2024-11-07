package com.hahsm.common.type;

import java.util.Objects;

public class Pair<F, S> {
    private F first;
    private S second;

    public Pair() {}

    public Pair(F first, S second) {
        setFirst(first);
        setSecond(second);
    }

	public F getFirst() {
		return first;
	}
    
	public void setFirst(F first) {
		this.first = first;
	}

	public S getSecond() {
		return second;
	}

	public void setSecond(S second) {
		this.second = second;
	}

    @Override
    public int hashCode() {
        return Objects.hash(getFirst(), getSecond());
    }

    public boolean equals(Pair<F, S> other) {
        return getFirst() == other.getFirst() && getSecond() == other.getSecond();
    }
}
