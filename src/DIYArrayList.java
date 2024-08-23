import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class DIYArrayList<T> implements Iterable<T> {
    private Object[] array;
    private int size;
    private int nElms;
    private static final double MEMORY_EXPANSION_COEFFICIENT = 1.5;


    public DIYArrayList() {
        this(10);
    }

    public DIYArrayList(int size) {
        if (size < 1) throw new IllegalArgumentException();
        this.array = new Object[size];
        this.size = size;
        this.nElms = 0;
    }

    public T getElem(int index) {
        return (T) array[index];
    }

    public void addElem(T elem) {
        if (nElms != size) {
            array[nElms] = elem;
            nElms++;
        } else {
            increaseArraySize();
            addElem(elem);
        }
    }

    private void increaseArraySize() {
        int newSize = (int) Math.ceil(this.size * MEMORY_EXPANSION_COEFFICIENT);
        Object[] newArr = new Object[newSize];
        if (this.size >= 0) System.arraycopy(this.array, 0, newArr, 0, this.size); //копирование элементов массива
        this.array = newArr;
        this.size = newSize;
    }

    public int size() {
        return nElms;
    }

    public void remove(int index) {
        if (index >= nElms || index < 0) throw new IllegalArgumentException();
        for (int i = index; i < nElms - 1; i++) {
            array[i] = array[i + 1];
        }
        nElms--;
    }

    public void showArray() {
        for (int i = 0; i < nElms; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    private void showFullArray() {
        for (int i = 0; i < size; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public void addAll(Collection<T> collection) {
        for (T elem : collection) {
            addElem(elem);
        }
    }

    public void sortBubbleToHigh() {
        BubbleSort(true);
    }

    public void sortBubbleToLow() {
        BubbleSort(false);
    }

    private void BubbleSort(boolean toHigh) {
        if (getElem(0) instanceof Comparable) {
            for (int i = 0; i < nElms; i++) {
                boolean isSorted = true;
                for (int j = 0; j < nElms - i - 1; j++) {
                    int compareToRes = ((Comparable) getElem(j)).compareTo(array[j + 1]);
                    if (compareToRes > 0 && toHigh || compareToRes < 0 && !toHigh) {
                        Object temp = array[j];
                        array[j] = array[j + 1];
                        array[j + 1] = temp;
                        isSorted = false;
                    }
                }
                if(isSorted) break;
            }
        } else {
            System.out.println("NOT COMPARABLE");
        }
    }

    //далее методы по контракту с интефесом Iterable<T>
    @Override
    public Iterator<T> iterator() {
        return new DIYItr<T>();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return Iterable.super.spliterator();
    }

    //чтоб работал ForEach с моим самописным классом.
    //создан самописный Iterator
    private class DIYItr<E> implements Iterator<E> {
        int cursor;       // index of next element to return

        @Override
        public boolean hasNext() {
            return cursor != nElms;
        }

        @Override
        public E next() {
            return (E) array[cursor++];
        }

        //Следующие два метода сгенерились автоматом -- нужно еще разобрать зачем они
        @Override
        public void remove() {
            Iterator.super.remove();
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            Iterator.super.forEachRemaining(action);
        }
    }
}
