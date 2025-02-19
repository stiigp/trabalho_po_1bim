public class Main {
    public static void main(String[] args) {
        LinkedList ll = new LinkedList();
        ll.insert(10); ll.insert(1); ll.insert(18);
        ll.insert(9); ll.insert(12); ll.insert(25); ll.insert(69);
        ll.insert(6); ll.insert(13); ll.insert(51); ll.insert(14);
        ll.insert(19); ll.insert(32); ll.insert(21); ll.insert(16);

//        ll.quickSort(0, ll.len());
        ll.mergeSort(0, ll.len()-1);
        ll.print();
    }
}