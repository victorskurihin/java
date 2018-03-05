package ru.otus.l121.cache;

import java.lang.ref.SoftReference;

/**
 * This class provides avoid re-serializing all the time. Particularly as
 * a cached will hold a reference to the element.
 *
 * @param <K> the type of the key for point to the element
 * @param <V> the type for strong reference to a value
 */
@SuppressWarnings("WeakerAccess")
class SoftReferenceElement<K, V> {
    private final K key;
    private final SoftReference<V> softReferenceValue;
    private final long creationTime;
    private long lastAccessTime;

    /**
     * The constructor with two parameters, the key and the strong reference.
     *
     * @param key the key for point to the element
     * @param value the value of the element or over words the strong reference
     *              to the value
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
     * The method returns the key.
     *
     * @return the key
     */
    public K getKey() {
        return key;
    }

    /**
     * The method returns the strong reference to the value.
     *
     * @return the strong raference to the value
     */
    public V getValue() {
        return softReferenceValue.get();
    }

    /**
     * The method returns the point in time when create the object.
     *
     * @return the timestamp
     */
    public long getCreationTime() {
        return creationTime;
    }

    /**
     * The method returns the last point in time when access to the object.
     *
     * @return the timestamp
     */
    public long getLastAccessTime() {
        return lastAccessTime;
    }

    /**
     * The method set the last point in time when access to the object.
     */
    public void setAccessed() {
        lastAccessTime = getCurrentTime();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
