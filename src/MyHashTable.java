import java.util.LinkedList;


class myHashMap<K extends Object, V>{
    private LinkedList<Node<K,V>>[] table;
    private static final int DEFAULT_TABLE_SIZE = 16;
    public myHashMap()
    {
        table = new LinkedList[DEFAULT_TABLE_SIZE];
        for (int i =0; i<DEFAULT_TABLE_SIZE; i++)
        {
            table[i]=new LinkedList<Node<K,V>>();
        }
    }
    private class Node<K, V>{
        final int hashCode;
        final K key;
        V value;
        Node<K,V> next;
        Node(K key, V value, int hashCode)
        {
            this.key = key;
            this.value = value;
            this.hashCode = hashCode;
        }
        Node(K key, int hashCode)
        {
            this.key = key;
            this.hashCode = hashCode;
        }
    }
    
    public boolean add(K key, V value)
    {
        int hashCode = getHashCodeByKey(key);
        int indexInTable = hashCode % table.length;
        System.out.print("hashCode"+hashCode+" index="+indexInTable);
        //поиск абсолютной копии и возрат Node для записи в него значений (K, value, hashCode)
        Node<K, V> nodeToWrite = getNodeToWrite(hashCode, key, table[indexInTable]);
        nodeToWrite.value = value;
        return true;
    }
    int getHashCodeByKey(K key){
        if(key!=null)
        {
            return key.hashCode();
        }
        return 0;
    }
    private Node<K, V> getNodeToWrite(int hashCode, K key, LinkedList<Node<K,V>>nodeList)
    {
        Node<K,V> ourNode = getEqualNode(hashCode, key, nodeList);
        if(ourNode==null){
            ourNode = new Node<>(key, hashCode);
            nodeList.addLast(ourNode);
        }
        return ourNode;
    }
    private Node<K,V> getEqualNode(int hashCode, K key, LinkedList<Node<K,V>>nodeList)
    {
        for(var node: nodeList)
        {
            if(node.hashCode == hashCode && node.key.equals(key))
            {
                return node;
            }
        }
        return null;
    }
    
    public V getValueByKey(K key)
    {
        int hashCode = getHashCodeByKey(key);
        int indexInTable = hashCode % table.length;
        LinkedList<Node<K, V>>nodeList = table[indexInTable];
        Node<K,V> ourNode = getEqualNode(hashCode, key, nodeList);
        if(ourNode==null){
            return null;
        }else{
            return ourNode.value;
        }
    }
    
}
    