import Arquivo.Arquivo;
import Arquivo.Tabela;

public class Main {

    public void sortsLinkedList() {
        LinkedList ll = new LinkedList();
        // a lista abaixo foi gerada pelo chatGPT para testar o timSort, que precisa de uma lista bem grande para ser
        // devidamente testado
//        ll.insert(84);
//        ll.insert(17);
//        ll.insert(96);
//        ll.insert(50);
//        ll.insert(34);
//        ll.insert(12);
//        ll.insert(67);
//        ll.insert(89);
//        ll.insert(23);
//        ll.insert(78);
//        ll.insert(91);
//        ll.insert(10);
//        ll.insert(56);
//        ll.insert(44);
//        ll.insert(35);
//        ll.insert(29);
//        ll.insert(60);
//        ll.insert(80);
//        ll.insert(5);
//        ll.insert(38);
//        ll.insert(41);
//        ll.insert(92);
//        ll.insert(47);
//        ll.insert(26);
//        ll.insert(74);
//        ll.insert(19);
//        ll.insert(83);
//        ll.insert(54);
//        ll.insert(62);
//        ll.insert(71);
//        ll.insert(39);
//        ll.insert(21);
//        ll.insert(100);
//        ll.insert(31);
//        ll.insert(66);
//        ll.insert(45);
//        ll.insert(28);
//        ll.insert(76);
//        ll.insert(57);
//        ll.insert(99);
//        ll.insert(9);
//        ll.insert(81);
//        ll.insert(53);
//        ll.insert(14);
//        ll.insert(25);
//        ll.insert(95);
//        ll.insert(64);
//        ll.insert(32);
//        ll.insert(20);
//        ll.insert(77);
//        ll.insert(48);
//        ll.insert(63);
//        ll.insert(98);
//        ll.insert(22);
//        ll.insert(87);
//        ll.insert(70);
//        ll.insert(46);
//        ll.insert(15);
//        ll.insert(42);
//        ll.insert(52);
//        ll.insert(79);
//        ll.insert(85);
//        ll.insert(6);
//        ll.insert(27);
//        ll.insert(36);
//        ll.insert(93);
//        ll.insert(73);
//        ll.insert(8);
//        ll.insert(68);
//        ll.insert(33);
//        ll.insert(97);
//        ll.insert(16);
//        ll.insert(43);
//        ll.insert(59);
//        ll.insert(24);
//        ll.insert(13);
//        ll.insert(30);
//        ll.insert(86);
//        ll.insert(49);
//        ll.insert(1);
//        ll.insert(88);
//        ll.insert(37);
//        ll.insert(55);
//        ll.insert(72);
//        ll.insert(40);
//        ll.insert(61);
//        ll.insert(75);
//        ll.insert(4);
//        ll.insert(7);
//        ll.insert(58);
//        ll.insert(3);
//        ll.insert(51);
//        ll.insert(11);
//        ll.insert(82);
//        ll.insert(90);
//        ll.insert(2);
//        ll.insert(18);
//        ll.insert(65);
//        ll.insert(94);
//        ll.insert(69);
//        ll.insert(0);

        ll.insert(15);
        ll.insert(13);
        ll.insert(2);
        ll.insert(78);
        ll.insert(5);
        ll.insert(89);


//        ll.quickSort(0, ll.len());
//        ll.mergeSort(0, ll.len() - 1);
//        ll.quickCPRec(ll.getHead(), ll.getEnd(), 0, ll.len() - 1);
        ll.quickCPIter();
        ll.print();
    }

    public void ordenaArquivo() {
        Arquivo arquivo = new Arquivo("arquivo");
//        arquivo.geraArquivoOrdenado(64);
//        arquivo.geraArquivoReverso(64);
//        arquivo.geraArquivoRandomico(65);
//        arquivo.shellSort();
        arquivo.exibeArq();
//        arquivo.bucketSort();
//        arquivo.exibeArq();
//        arquivo.mergeIterativo();
//        arquivo.exibeArq();
//        arquivo.insercaoDireta();
//        arquivo.bubbleSort();
//        arquivo.selectionSort();
//        arquivo.shakerSort();
//        arquivo.heapSort();
        // estes devem ser utilizados antes de chamar o quickSort
        // não adianta chamar dentro pois ele é recursivo e isso comprometeria o resultado
//        arquivo.initMov();
//        arquivo.initComp();
//        arquivo.quickSort(0, arquivo.filesize() - 1);

//        arquivo.gnomeSort();
//        arquivo.shellSort();
//        arquivo.combSort();
//        arquivo.binaryInsertionSort();

//        arquivo.geraArquivoRandomico(64);
//        arquivo.quickSemPivo();
//        arquivo.exibeArq();
//        arquivo.bucketSort();

//        arquivo.geraArquivoRandomico(1328);
//        arquivo.bucketSort();
//        arquivo.exibeArq();
//        System.out.println(arquivo.filesize());
    }

    public static void main(String[] args) {
        Main main = new Main();
        Tabela table = new Tabela();
        table.geraTabela();
//        main.ordenaArquivo();
//        main.sortsLinkedList();


    }
}