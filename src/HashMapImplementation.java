import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class HashMapImplementation<K, V> implements Iterable<HashMapImplementation<K,V>.Entry> {

    public class Entry {
        private final K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() { return key; }
        public V getValue() { return value; }
        public void setValue(V value){ this.value = value; }

        @Override
        public String toString(){ return key + " : " + value; }
    }


    private int size;
    private int currentSize;
    private final double loadFactor = 0.75;
    private final List<List<Entry>> hashMap;

    public HashMapImplementation() {
        this.size = 16;
        this.hashMap = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            this.hashMap.add(null);
        }
    }

    public HashMapImplementation(int initialSize) {
        this.size = initialSize;
        this.hashMap = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            this.hashMap.add(null);
        }
    }

    public void put(K key, V value) {
        if((double) currentSize / size >= loadFactor){
            resize();
        }

        Entry entry = new Entry(key, value);
        int index = hash(entry.getKey(), this.size);

        if (hashMap.get(index) == null) {
            hashMap.set(index, new ArrayList<>());
        }

        List<Entry> bucket = hashMap.get(index);

        for (Entry e : bucket) {
            if (Objects.equals(e.getKey(), entry.getKey())) {
                e.setValue(entry.getValue());
                return;
            }
        }

        bucket.add(entry);
        currentSize++;
    }


    public void remove(K key) {
        int index = hash(key, this.size);

        if (hashMap.get(index) == null) {
            return;
        }

        List<Entry> entries = hashMap.get(index);
        Iterator<Entry> iterator = entries.iterator();

        while(iterator.hasNext()) {
            Entry entry = iterator.next();
            if (Objects.equals(entry.getKey(), key)) { // null safe
                iterator.remove();
                currentSize--;
                return;
            }
        }
    }

    public V get(K key) {
        int index = hash(key, this.size);

        if(hashMap.get(index) == null){
            return null;
        }

        List<Entry> entries = hashMap.get(index);

        for(Entry entry: entries){
            if(Objects.equals(entry.getKey(), key)){ // null safe
                return entry.getValue();
            }
        }

        return null;
    }

    private void resize() {
        int newSize = size * 2;
        List<List<Entry>> newHashMap = new ArrayList<>(newSize);
        for (int i = 0; i < newSize; i++) {
            newHashMap.add(null);
        }

        for (List<Entry> bucket : hashMap) {
            if (bucket != null) {
                for (Entry entry : bucket) {
                    int newIndex = hash(entry.getKey(), newSize);

                    if (newHashMap.get(newIndex) == null) {
                        newHashMap.set(newIndex, new ArrayList<>());
                    }

                    newHashMap.get(newIndex).add(entry);
                }
            }
        }

        this.hashMap.clear();
        this.hashMap.addAll(newHashMap);
        this.size = newSize;
    }

    @Override
    public Iterator<Entry> iterator() {
        List<Entry> entries = new ArrayList<>();
        for (List<Entry> bucket : hashMap) {
            if (bucket != null) {
                entries.addAll(bucket);
            }
        }
        return entries.iterator();
    }

    private int hash(K key, int size){
        String keyString = String.valueOf(key);
        byte[] bytes = keyString.getBytes();
        int hash = 0;
        for(byte b : bytes){
            hash = (31 * hash + b) % size;
        }
        return Math.abs(hash);
    }

    public int currentSize() {
        return this.currentSize;
    }
}
