package com.github.intermon.cache;

/*
 * Created by VSkurikhin at Sun Apr 25 13:20:34 MSK 2018.
 */

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;

public class CacheEngineImpl<K, V> implements CacheEngine<K, V> {
    private static final int TIME_THRESHOLD_MS = 100;

    private final int maxElements;
    private final long lifeTimeMs;
    private final long idleTimeMs;
    private final boolean isEternal;

    private final Map<K, SoftReferenceElement<K, V>> elements = new LinkedHashMap<>();
    private final Timer timer = new Timer();

    private int hit = 0;
    private int miss = 0;

    public
    CacheEngineImpl(int maxElements, long lifeTimeMs, long idleTimeMs, boolean isEternal) {
        this.maxElements = maxElements;
        this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
        this.idleTimeMs = idleTimeMs > 0 ? idleTimeMs : 0;
        this.isEternal = lifeTimeMs == 0 && idleTimeMs == 0 || isEternal;
    }

    public void put(K key, V value) {

        SoftReferenceElement<K, V> element = new SoftReferenceElement<>(key, value);

        if (elements.size() == maxElements + 1) {
            K firstKey = elements.keySet().iterator().next();
            elements.remove(firstKey);
        }

        elements.put(key, element);

        if (!isEternal) {
            if (lifeTimeMs != 0) {
                TimerTask lifeTimerTask = getTimerTask(
                    key, lifeElement -> lifeElement.getCreationTime() + lifeTimeMs
                );
                timer.schedule(lifeTimerTask, lifeTimeMs);
            }
            if (idleTimeMs != 0) {
                TimerTask idleTimerTask = getTimerTask(
                    key, idleElement -> idleElement.getLastAccessTime() + idleTimeMs
                );
                timer.schedule(idleTimerTask, idleTimeMs, idleTimeMs);
            }
        }
    }

    public V get(K key) {
        SoftReferenceElement<K, V> element = elements.get(key);
        if (element != null) {
            hit++;
            element.setAccessed();
            return element.getValue();
        } else {
            miss++;
        }
        return null;
    }

    public int getHitCount() {
        return hit;
    }

    public int getMissCount() {
        return miss;
    }

    @Override
    public void dispose() {
        timer.cancel();
    }

    private TimerTask
    getTimerTask(final K key, Function<SoftReferenceElement<K, V>, Long> timeFunction) {
        return new TimerTask() {
            @Override
            public void run() {
                SoftReferenceElement<K, V> element = elements.get(key);
                if (element == null
                    || isT1BeforeT2(
                        timeFunction.apply(element), System.currentTimeMillis())
                    ) {
                    elements.remove(key);
                    this.cancel();
                }
            }
        };
    }

    private boolean isT1BeforeT2(long t1, long t2) {
        return t1 < t2 + TIME_THRESHOLD_MS;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
