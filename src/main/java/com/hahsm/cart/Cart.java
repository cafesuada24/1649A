package com.hahsm.cart;

import com.hahsm.common.type.Pair;
import com.hahsm.datastructure.HashMap;

public class Cart<K, M> {
    private HashMap<K, Pair<Integer, M>> cart = new HashMap<>();

    public void addItem(K id, M item, int qty) {
        if (cart.containsKey(id)) {
            final var cartItem = cart.get(id);
            cartItem.setFirst(cartItem.getFirst() + qty);
        } else {
            cart.put(id, new Pair<>(qty, item));
        }
    }

    @Override
    public String toString() {
        if (cart.isEmpty()) {
            return "\tempty";
        }
        StringBuilder builder = new StringBuilder();
        cart.forEach((key, val) -> {
            builder.append("\t").append(val.getSecond().toString());
            builder.append("\n\t\tqty: ").append(val.getFirst()).append('\n');
        });
        return builder.toString();
    }

	public HashMap<K, Pair<Integer, M>> getCart() {
		return cart;
	}
}
