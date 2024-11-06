package com.hahsm.datastructure.adt;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface Map<K, V> extends Iterable<Map.Entry<K, V>> {
    public static interface Entry<K, V> {
        K getKey();
        V getValue();
        //int hashCode();
        V setValue(V value);
        boolean equals(Object o);
    }

    void clear();
    boolean containsKey(K key);
    boolean containsValue(V value);
    boolean put(K key, V value);
    boolean isEmpty();
    V get(K key);
    V remove(K key);
    int size();
    List<Entry<K, V>> entries();
    List<V> values();
    List<K> keys();

    default void forEach(BiConsumer<? super K, ? super V> action) {
        for (final Entry<K, V> element : this) {
           action.accept(element.getKey(), element.getValue()); 
        }
    }
}
