package ru.otus.l121.cache;

public interface CacheEngine<K, V> {

    void put(SoftReferenceElement<K, V> element);

    SoftReferenceElement<K, V> get(K key);

    int getHitCount();

    int getMissCount();

    void dispose();
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
