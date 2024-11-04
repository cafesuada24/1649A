package com.hahsm.datastructure.adt;

public interface Map<K, V> {
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
}
