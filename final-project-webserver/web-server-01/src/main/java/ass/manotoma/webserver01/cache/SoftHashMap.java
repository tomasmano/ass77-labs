package ass.manotoma.webserver01.cache;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class SoftHashMap<K, V> extends AbstractMap<K, V> {

    private Map<K, SoftValue<V>> map;
    private ReferenceQueue<V> queue = new ReferenceQueue<V>();

    public SoftHashMap() {
        map = new HashMap<K, SoftValue<V>>();
    }

    @SuppressWarnings("unchecked")
    private void processQueue() {
        while (true) {
            Reference<? extends V> o = queue.poll();
            if (o == null) {
                return;
            }
            SoftValue<V> k = (SoftValue<V>) o;
            Object key = k.key;
            map.remove(key);
        }
    }

    public V get(Object key) {
        processQueue();
        SoftReference<V> o = map.get(key);
        if (o == null) {
            return null;
        }
        return o.get();
    }

    /**
     * Store the object. The return value of this method is null or a
     * SoftReference.
     *
     * @param key the key
     * @param value the value
     * @return null or the old object.
     */
    public V put(K key, V value) {
        processQueue();
        SoftValue<V> old = map.put(key, new SoftValue<V>(value, queue, key));
        return old == null ? null : old.get();
    }

    /**
     * Remove an object.
     *
     * @param key the key
     * @return null or the old object
     */
    public V remove(Object key) {
        processQueue();
        SoftReference<V> ref = map.remove(key);
        return ref == null ? null : ref.get();
    }

    public void clear() {
        processQueue();
        map.clear();
    }

    public Set<Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }

    /**
     * A soft reference that has a hard reference to the key.
     */
    private static class SoftValue<T> extends SoftReference<T> {

        final Object key;

        public SoftValue(T ref, ReferenceQueue<T> q, Object key) {
            super(ref, q);
            this.key = key;
        }
    }
}
