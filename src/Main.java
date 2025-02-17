public class Main {
    public static void main(String[] args) {
        LinkedList ll = new LinkedList();
        ll.insert(14); ll.insert(10); ll.insert(11);
        ll.insert(9); ll.insert(8); ll.insert(4); ll.insert(12);


//        ll.quickSort(0, ll.len());
        ll.insertionSort();
        ll.print();
    }
}