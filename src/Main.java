import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        myArrayListTests();
        myLinkedListTests();
    }

    public static void myArrayListTests() {
        System.out.println("\n\nТесты DIYArrayList<Integer>");
        DIYArrayList<Integer> intArr = new DIYArrayList<Integer>();
        testAddIntElems(intArr);
        testRemoveElems(intArr);
        testAddAllCollectionInt(intArr);
        testForEach(intArr);
        testSort(intArr);

        System.out.println("\n\nтесты DIYArrayList<String>");
        DIYArrayList<String> strArr = new DIYArrayList<String>();
        testAddStringElems(strArr);
        testRemoveElems(strArr);
        testForEach(strArr);
        testSort(strArr);

        //*со зведочкой
        System.out.println("\n\nтесты со звездочкой");
        testCreateFromCollection();
        testStaticSortAnotherCollection();
    }
    private static void testStaticSortAnotherCollection() {
        List<Byte> listByte = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            Random random = new Random();
            listByte.add((byte) random.nextInt(100));
        }
        DIYArrayList.bubleSortToHigh(listByte);
        System.out.println(listByte.toString());
        DIYArrayList.bubleSortToLow(listByte);
        System.out.println(listByte.toString());
    }
    private static void testCreateFromCollection() {
        List<Byte> listByte = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            listByte.add((byte) i);
        }
        DIYArrayList<Byte> myBiteList = new DIYArrayList<>(listByte);
        myBiteList.showArray();
    }
    private static void testAddStringElems(DIYArrayList<String> strArr) {
        strArr.addElem("hello");
        strArr.addElem("my");
        strArr.addElem("dear");
        strArr.addElem("friend");
        strArr.showArray();
    }
    private static void testAddAllCollectionInt(DIYArrayList<Integer> intArr) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            arrayList.add(i);
        }
        intArr.addAll(arrayList);
        intArr.showArray();
    }
    private static void testAddIntElems(DIYArrayList<Integer> arr) {
        arr.addElem(12);
        arr.addElem(54);
        arr.addElem(65);
        arr.addElem(40);
        arr.showArray();
    }
    private static <T> void testRemoveElems(DIYArrayList<T> diyArrayList) {
        diyArrayList.remove(1);
        diyArrayList.showArray();
    }
    private static <T> void testForEach(DIYArrayList<T> arr) {
        System.out.println("вывод элементов intArr через forEach");
        for (T elem : arr) {
            System.out.print(elem + "\t");
        }
        System.out.println();
    }
    private static <T> void testSort(DIYArrayList<T> arr) {
        arr.sortBubbleToHigh();
        arr.showArray();
        arr.sortBubbleToLow();
        arr.showArray();
    }

    public static void myLinkedListTests() {
        System.out.println("\n\nDIYLinkedList<Integer> test:");
        DIYLinkedList<Integer> integerDIYLinkedList = new DIYLinkedList<Integer>();
        testAddElems(integerDIYLinkedList);
        testRemoveElms(integerDIYLinkedList);
        testAddAllElemsAndSort(integerDIYLinkedList);
    }
    private static void testAddElems(DIYLinkedList<Integer> integerDIYLinkedList) {
        integerDIYLinkedList.addFirst(1);
        integerDIYLinkedList.addFirst(2);
        integerDIYLinkedList.addFirst(3);
        integerDIYLinkedList.addLast(43);
        for (Object elem : integerDIYLinkedList) {
            System.out.print(elem + "\t");
        }
        System.out.println();
        System.out.println("linkedList size: " + integerDIYLinkedList.size());
    }
    private static void testRemoveElms(DIYLinkedList<Integer> integerDIYLinkedList) {
        integerDIYLinkedList.remove(3);
        integerDIYLinkedList.remove(2);
        integerDIYLinkedList.removeFirst();
        integerDIYLinkedList.showList();
    }
    private static void testAddAllElemsAndSort(DIYLinkedList<Integer> integerDIYLinkedList) {
        ArrayList<Integer> stdArr = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            stdArr.add(i*2);
        }
        integerDIYLinkedList.addAll(stdArr);
        integerDIYLinkedList.showList();
        integerDIYLinkedList.sort();
        integerDIYLinkedList.showList();
    }
}