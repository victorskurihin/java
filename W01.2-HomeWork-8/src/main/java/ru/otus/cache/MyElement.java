package ru.otus.cache;

/*
 * Created by VSkurikhin at winter 2018.
 */

import java.lang.ref.SoftReference;

@SuppressWarnings("WeakerAccess")
public class MyElement<K, V> {
    private final K key;
    private final SoftReference<V> softReferenceValue;
    private final long creationTime;
    private long lastAccessTime;


    public MyElement(K key, V value) {
        this.key = key;
        this.softReferenceValue = new SoftReference<>(value);
        this.creationTime = getCurrentTime();
        this.lastAccessTime = getCurrentTime();
    }

    protected long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return softReferenceValue.get();
    }

    public long getCreationTime() {
        return creationTime;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setAccessed() {
        lastAccessTime = getCurrentTime();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
