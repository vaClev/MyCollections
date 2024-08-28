import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
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
        //TODO
        return null;
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
    public void showList() {
        Node<T> cursor = first;
        while (cursor != null) {
            System.out.println(cursor.data + " ");
            cursor = cursor.next;
        }
    }

    @Override
    public int size() {
        //TODO
        return 0;
    }

    @Override
    public void remove(int index) {
        //TODO
    }

    @Override
    public void addAll(Collection<T> collection) {
        //TODO
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
