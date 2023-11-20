public class mHashMap<K,V> {
    private static int l = 8;
    private linkList[] linkArr;
    private int size;

    public mHashMap() {
        this(l);
    }

    public mHashMap(int size) {
        this.size = size;
        linkArr = new linkList[size];
        for (int i = 0; i < size; i++) {
            linkArr[i] = new linkList();
        }
    }

    public void put(K key, V value) {
        if (key == null) {
            int nullKeyIndex = size - 1;
            linkList nullKeyLink = linkArr[nullKeyIndex];
            nullKeyLink.add(key, value);
            return;
        }
        int index = hash(key);
        linkList link = linkArr[index];
        link.add(key, value);
    }

    public Object get(K key) {
        if (key == null) {
            int nullKeyIndex = size - 1;
            linkList nullKeyLink = linkArr[nullKeyIndex];
            return nullKeyLink.find(key);
        }
        int index = hash(key);
        linkList link = linkArr[index];
        return link.find(key);
    }

    public int hash(K key) {
        int hashCode = key.hashCode();
        int hashValue = Math.abs(hashCode) % size;
        return hashValue;
    }
}