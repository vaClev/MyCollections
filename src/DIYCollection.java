import java.util.Collection;

public interface DIYCollection<T> {
    public T getElem(int index);
    public int size();
    public void remove(int index);
    public void addAll(Collection<T> collection);
    public boolean isEmpty();
}
