public class Main {
    public static void main(String[] args) {
        LinkedList ll = new LinkedList(17);
        ll.insert(13); ll.insert(21); ll.insert(96); ll.insert(15);
        ll.insert(25); ll.insert(35); ll.insert(10); ll.insert(5);

        ll.print();
        System.out.println();
        ll.quickSort(0, ll.len());
        ll.print();
    }
}