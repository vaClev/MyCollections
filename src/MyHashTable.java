class myHashMap<K extends Object, V> {
    private static final int DEFAULT_TABLE_SIZE = 16;
    private static final double OVERFLOW_COEFFICIENT = 0.75; //75%
    private static final double INCREASE_COEFFICIENT = 2.0;
    private Node[] table;
    private int filledBuckets;

    public myHashMap() {
        this(DEFAULT_TABLE_SIZE);
    }

    public myHashMap(int size) {
        table = new Node[size];
        filledBuckets = 0;
    }

    private class Node<K, V> {
        final int hashCode;
        final K key;
        V value;
        Node<K, V> next;

        Node(K key, V value, int hashCode) {
            this.key = key;
            this.value = value;
            this.hashCode = hashCode;
        }

        Node(K key, int hashCode) {
            this.key = key;
            this.hashCode = hashCode;
        }
    }

    public boolean add(K key, V value) {
        int hashCode = getHashCodeByKey(key);
        int indexInTable = getBucketNumber(hashCode);
        System.out.println("hashCode" + hashCode + " index=" + indexInTable);
        Node<K, V> nodeToWrite = getNodeToWrite(hashCode, key, indexInTable);//поиск абсолютной копии или возврат нового Node для записи в него значения (value)
        nodeToWrite.value = value;
        return true;
    }

    private int getBucketNumber(int hashCode) {
        return hashCode % table.length;
    }

    int getHashCodeByKey(K key) {
        if (key != null) {
            int hashCode = key.hashCode();
            if (hashCode < 0) hashCode = -hashCode;
            return hashCode;
        }
        return 0;
    }

    private Node<K, V> getNodeToWrite(int hashCode, K key, int indexInTable) {
        Node<K, V> nodeList = table[indexInTable];//указатель на первый элемент
        Node<K, V> ourNode;
        if (nodeList == null)//впервые добавляется элемент в этот bucket
        {
            filledBuckets++;
            if (isOverflow()) {
                increaseHashTableSize();
                //сам себя запусти с новым параметрами
                return getNodeToWrite(hashCode, key, hashCode % table.length);
            }
            ourNode = new Node<>(key, hashCode);
        } else //в данном бакете уже есть элементы
        {
            ourNode = getEqualNode(hashCode, key, nodeList);
            if (ourNode == null) {
                ourNode = new Node<>(key, hashCode);
                ourNode.next = nodeList;
            }
        }
        table[indexInTable] = ourNode;
        return ourNode;
    }

    private void increaseHashTableSize() {
        System.out.println("Increasing HashTableSize");
        int newSize = (int) (table.length * INCREASE_COEFFICIENT);
        myHashMap<K, V> newHashMap = new myHashMap<>(newSize);

        //перемещение всех Node из this в newHashMap
        for (int i = 0; i < table.length; i++) {
            Node<K, V> pointer = table[i];
            while (pointer != null) {
                Node<K, V> next = pointer.next;
                newHashMap.insertUnicNodeFromOldSizeHashMap(pointer);
                pointer = next;
            }
        }
        //приравнивание ссылок this и newHashMap
        this.table = newHashMap.table;
        this.filledBuckets = newHashMap.filledBuckets;
    }
    private void insertUnicNodeFromOldSizeHashMap(Node<K, V> oldNode) {
        int indexInTable = getBucketNumber(oldNode.hashCode);
        Node<K, V> nodeList = table[indexInTable];//указатель на первый элемент в bucket
        if (nodeList == null) //bucket пуст
        {
            filledBuckets++;
            System.out.println("Buckets in new table - " + filledBuckets);
            if (isOverflow()) {//это теоретически не должно происходить никогда, но вдруг
                System.out.println("Переполнение достигнуто в момент расширения --- " +
                        "крайне неудачное совпадение хэшкодов");
                increaseHashTableSize();
                insertUnicNodeFromOldSizeHashMap(oldNode);
                return;
            }
            oldNode.next = null; //теперь он никуда не показывает -- стираем ссылку на старый его next
        } else //bucket не пуст
        {
            System.out.println("bucket не пуст" + filledBuckets);
            oldNode.next = nodeList;
        }
        table[indexInTable] = oldNode; //новый элемент всегда попадет в bucket на первую позицию
    }
    private boolean isOverflow() {
        return (double) filledBuckets / table.length > OVERFLOW_COEFFICIENT;
    }

    private Node<K, V> getEqualNode(int hashCode, K key, Node<K, V> nodeList) {
        Node<K, V> pointer = nodeList;
        while (pointer != null) {
            if (pointer.hashCode == hashCode && pointer.key.equals(key)) {
                return pointer;
            }
            pointer = pointer.next;
        }
        return null;
    }

    public V getValueByKey(K key) {
        int hashCode = getHashCodeByKey(key);
        int indexInTable = hashCode % table.length;
        Node<K, V> ourNode = getEqualNode(hashCode, key, table[indexInTable]);
        if (ourNode == null) {
            return null;
        } else {
            return ourNode.value;
        }
    }

    public boolean deleteElemByKey(K key) {
        int hashCode = getHashCodeByKey(key);
        int indexInTable = hashCode % table.length;
        Node<K, V> ourNode = getEqualNode(hashCode, key, table[indexInTable]);
        if (ourNode == null) {
            return false;
        }
        if (ourNodeIsFirstInBucket(indexInTable, ourNode)) {
            table[indexInTable] = ourNode.next;
        } else {
            //итерируемся и ищем предыдущий элемент
            myHashMap.Node pointer = table[indexInTable];
            while (pointer.next != ourNode) {
                pointer = pointer.next;
            }
            pointer.next = ourNode.next;
        }
        return true;
    }

    private boolean ourNodeIsFirstInBucket(int indexInTable, Node<K, V> ourNode) {
        return table[indexInTable] == ourNode;
    }

    public void printAllElms() {
        System.out.println("Table:");
        for (int i = 0; i < table.length; i++) {
            Node<K, V> pointer = table[i];
            System.out.println(i + " -bucket:");
            while (pointer != null) {
                System.out.println("\t key= " + pointer.key + "\tvalue:" + pointer.value);
                pointer = pointer.next;
            }
        }
    }
}
    