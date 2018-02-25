package ru.otus.l121.cache;

import java.lang.ref.SoftReference;

/**
 * TODO
 * @param <K>
 * @param <V>
 */
@SuppressWarnings("WeakerAccess")
public class SoftReferenceElement<K, V> {
    private final K key;
    private final SoftReference<V> softReferenceValue;
    private final long creationTime;
    private long lastAccessTime;

    /**
     * TODO
     * @param key
     * @param value
     */
    public SoftReferenceElement(K key, V value) {
        this.key = key;
        this.softReferenceValue = new SoftReference<>(value);
        this.creationTime = getCurrentTime();
        this.lastAccessTime = getCurrentTime();
    }

    protected long getCurrentTime() {
        return System.currentTimeMillis();
    }

    /**
     * TODO
     * @return
     */
    public K getKey() {
        return key;
    }

    /**
     * TODO
     * @return
     */
    public V getValue() {
        return softReferenceValue.get();
    }

    /**
     * TODO
     * @return
     */
    public long getCreationTime() {
        return creationTime;
    }

    /**
     * TODO
     * @return
     */
    public long getLastAccessTime() {
        return lastAccessTime;
    }

    /**
     * TODO
     */
    public void setAccessed() {
        lastAccessTime = getCurrentTime();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
