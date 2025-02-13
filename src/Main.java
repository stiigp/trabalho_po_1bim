public class Main {
    public static void main(String[] args) {
        LinkedList ll = new LinkedList(15);
        ll.insert(13); ll.insert(11); ll.insert(17);
        ll.insert(76); ll.insert(45); ll.insert(39); ll.insert(10);

        ll.print();
        System.out.println();
        // ll.quickSort(0, ll.len());
        ll.insertionSort();
        ll.print();
        System.out.println();
        ll.printReversed();
    }
}