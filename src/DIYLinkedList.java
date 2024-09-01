import java.util.*;
import java.util.function.Consumer;

public class DIYLinkedList<T> implements DIYCollection<T>, Iterable<T> {
    Node<T> first;
    Node<T> last;

    public DIYLinkedList() {
        this.first = null;
        this.last = null;
    }

    @Override
    public T getElem(int index) {
        return getNodeByIndex(index).data;
    }
    private Node<T> getNodeByIndex(int index) {
        Objects.checkIndex(index, size());
        DIYListItr<T> iterator = new DIYListItr<T>();
        int count = 0;
        while (true) {
            if (index == count) {
                return iterator.getNode();
            }
            count++;
            iterator.next();
        }
    }
    public boolean isEmpty() {
        return first == null;
    }

    public void addFirst(T elem) {
        Node<T> newElem = new Node<T>(elem);
        if (isEmpty()) {
            first = newElem;
            last = newElem;
        } else {
            first.prev = newElem;
            newElem.next = first;
            first = newElem;
        }
    }
    public void addLast(T elem) {
        Node<T> newElem = new Node<T>(elem);
        if (isEmpty()) {
            first = newElem;
            last = newElem;
        } else {
            last.next = newElem;
            newElem.prev = last;
            last = newElem;
        }
    }
    @Override
    public void addAll(Collection<T> collection) {
        for (T elem : collection) {
            addLast(elem);
        }
    }
    private void addAll(Iterable<T> collection) {
        for (T elem : collection) {
            addLast(elem);
        }
    }

    @Override
    public int size() {
        if (first == null) {
            return 0;
        }
        int size = 0;
        for (T elem : this) {
            size++;
        }
        return size;
    }
    @Override
    public void remove(int index) {
        Node<T> current = getNodeByIndex(index);
        if (current == first) {
            removeFirst();
        } else if (current == last) {
            removeLast();
        } else {
            removeCurrent(current);
        }
    }
    public void clear() {
        first = null;
        last = null;
    }
    private void removeCurrent(Node<T> current) {
        current.data = null;
        current.prev.next = current.next;
        current.next.prev = current.prev;
        current.prev = null;
        current.next = null;
    }
    public void removeLast() {
        if (!isListSize0or1Remove()) {
            last.data = null;
            last = last.prev;
            last.next.prev = null;
            last.next = null;
        }

    }
    public void removeFirst() {
        if (!isListSize0or1Remove()) {
            first.data = null;
            first = first.next;
            first.prev.next = null;
            first.prev = null;
        }
    }
    private boolean isListSize0or1Remove() {
        if (isEmpty()) {
            return true;
        }
        if (size() == 1) {
            first.data = null;
            first = null;
            last = null;
            return true;
        }
        return false;
    }

    public void showList() {
        for (T elem : this) {
            System.out.print(elem + " ");
        }
        System.out.println();
    }
    public void sort() {
        DIYArrayList<T> arrayList = new DIYArrayList<T>(size());
        for (T elem : this) {
            arrayList.addElem(elem);
        }
        arrayList.sortBubbleToHigh();
        clear();
        addAll(arrayList);
    }

    //для ForEach
    @Override
    public Iterator<T> iterator() {
        return new DIYListItr<T>();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return Iterable.super.spliterator();
    }

    private class DIYListItr<E> implements Iterator<T> {
        Node<T> cursor = first;       // index of next element to return

        @Override
        public boolean hasNext() {
            return cursor != null;
        }

        @Override
        public T next() {
            T data = cursor.data;
            cursor = cursor.next;
            return data;
        }

        //Следующие два метода сгенерились автоматом -- нужно еще разобрать зачем они
        @Override
        public void remove() {
            Iterator.super.remove();
        }

        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            Iterator.super.forEachRemaining(action);
        }

        public Node<T> getNode() {
            return cursor;
        }
    }
}

class Node<T> {
    T data;
    Node<T> next;
    Node<T> prev;

    public Node(T data) {
        this.data = data;
        next = null;
        prev = null;
    }

    public void displayNode() {
        System.out.println(data);
    }
}
