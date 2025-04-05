import java.util.*;

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


    private int currentSize;
    private final static double LOAD_FACTOR = 0.75;
    private final List<List<Entry>> buckets;

    public HashMapImplementation() {
        this.buckets = new ArrayList<>(Collections.nCopies(4, null));
    }

    public HashMapImplementation(int initialSize) {
        this.buckets = new ArrayList<>(Collections.nCopies(initialSize, null));
    }

    public void put(K key, V value) {
        if((double) currentSize / buckets.size() >= LOAD_FACTOR){
            resize();
        }

        Entry entry = new Entry(key, value);
        int index = hash(entry.getKey(), buckets.size());

        if (buckets.get(index) == null) {
            buckets.set(index, new ArrayList<>());
        }

        List<Entry> bucket = buckets.get(index);

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
        int index = hash(key, buckets.size());

        if (buckets.get(index) == null) {
            return;
        }

        List<Entry> entries = buckets.get(index);
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
        int index = hash(key, buckets.size());

        if(buckets.get(index) == null){
            return null;
        }

        List<Entry> entries = buckets.get(index);

        for(Entry entry: entries){
            if(Objects.equals(entry.getKey(), key)){ // null safe
                return entry.getValue();
            }
        }

        return null;
    }

    private void resize() {
        int newSize = buckets.size() * 2; //4 -> 8
        List<List<Entry>> newHashMap = new ArrayList<>(newSize);
        for (int i = 0; i < newSize; i++) {
            newHashMap.add(null);
        }

        for (List<Entry> bucket : buckets) {
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

        this.buckets.clear();
        this.buckets.addAll(newHashMap);
    }

    @Override
    public Iterator<Entry> iterator() {
        List<Entry> entries = new ArrayList<>();
        for (List<Entry> bucket : buckets) {
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

    public int currentSize() { return this.currentSize; }

}
