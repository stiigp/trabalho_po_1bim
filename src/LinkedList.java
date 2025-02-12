public class LinkedList {

    NoLista head;

    // primeiro construtor do objeto lista, recebe um NoLista
    public LinkedList(NoLista head) {
        this.head = head;
    }

    // sobrecarga do primeiro construtor, recebe apenas o inteiro que vai constituir o valor da cabeça da lista
    public LinkedList(int valor) {
        this(new NoLista(valor));
    }

    // insere ao final da lista
    public void insert(int valor) {
        // usa um ponteiro do tipo NoLista
        NoLista pointer = head;

        while (pointer.getProx() != null)
            pointer = pointer.getProx();

        pointer.setProx(new NoLista(valor));
    }

    // calcula o tamanho da lista
    // pode ser util em alguns algo's de ordenação
    public int len() {
        NoLista pointer = head;
        int res = 0;

        while (pointer != null) {
            res ++;
            pointer = pointer.getProx();
        }

        return res;
    }

    // função pra exibir a lista pra facilitar depuração
    public void print() {
        NoLista pointer = head;
        while (pointer != null) {
            System.out.print(pointer.getValor() + " ");
            pointer = pointer.getProx();
        }
    }

    public void selectionSort() {
        NoLista pointer_i = head, pointer_j, menor;
        int menor_val;

        while (pointer_i.getProx() != null) {
            menor_val = pointer_i.getValor();
            menor = pointer_i;
            pointer_j = pointer_i.getProx();

            while (pointer_j != null) {
                if (pointer_j.getValor() < menor_val) {
                    menor = pointer_j;
                    menor_val = pointer_j.getValor();
                }
                pointer_j = pointer_j.getProx();
            }

            menor.setValor(pointer_i.getValor());
            pointer_i.setValor(menor_val);

            pointer_i = pointer_i.getProx();
        }

    }

    public void bubbleSort() {
        NoLista pointer_i;
        int len = this.len(), i, j, temp;

        for (i = 0; i < len; i ++) {
            pointer_i = head;

            for (j = 0; j < len - i - 1; j ++) {
                if (pointer_i.getValor() > pointer_i.getProx().getValor()) {
                    temp = pointer_i.getProx().getValor();
                    pointer_i.getProx().setValor(pointer_i.getValor());
                    pointer_i.setValor(temp);
                }
                pointer_i = pointer_i.getProx();
            }
        }
    }

    // análogo à partição de Lomuto
    // funciona em O(n), desconsiderando a travessia na lista inteira para posicionar o pivô
    private int particiona(int p, int r) {
        NoLista pivot = head, pointer_i= head, pointer_j = head;
        int i, k, temp;

        // colocando o pivô na última posição
        for (k = 0; k < r - 1; k ++) {
            pivot = pivot.getProx();
        }

        // fazendo o posicionamento inicial dos ponteiros i e j:

        // ponteiro i: vai avançar antes de cada troca que houver,
        // ele representa o limiar dos números menores que o pivô
        // ponteiro j: avança para fazer as comparações com o valor
        // do pivô e verificar se haverá troca
        i = p;
        for (k = 0; k < i; k ++) {
            // na implementação para vetores de lomuto o i inicia em p - 1, porém em lista encadeada
            // isso não é muito viável, portanto i vai começar no primeiro elemento e será tratado diferente
            // nas substituições
            pointer_i = pointer_i.getProx();
            pointer_j = pointer_j.getProx();
        }

        for (k = p; k < r - 1; k ++) { // vai até r - 1 pois não pode chegar no pivô
            if (pointer_j.getValor() < pivot.getValor()) { // se achamos um elemento menor que o pivô ocorre a troca
                temp = pointer_i.getValor();
                pointer_i.setValor(pointer_j.getValor());
                pointer_j.setValor(temp);

                // avançamos o pointer_i após a troca, ao contrário da implementação original de lomuto, que avança antes
                // isso ocorre por conta do ponteiro ter começado uma posição a frente do que acontece na versão original
                pointer_i = pointer_i.getProx();
                i ++; // incrementamos o i pois precisamos retornar seu valor
            }
            pointer_j = pointer_j.getProx();
        }

        // colocando o pivô no meio, em sua posição final
        temp = pointer_i.getValor();
        pointer_i.setValor(pivot.getValor());
        pivot.setValor(temp);

        return i;
    }

    public void quickSort(int p, int r) {
        int i;

        if (p < r) {
            i = particiona(p, r);
            quickSort(p, i);
            quickSort(i + 1, r);
        }
    }
//
//    public void shakerSort() {
//        LinkedList pointer = this, pointer2;
//        int len = this.len(), temp, j, k;
//
//        for (int i = 0; i < len; i ++) {
//            while (pointer.next != null) {
//                if (pointer.getValue() > pointer.next.getValue()) {
//                    temp = pointer.getValue();
//                    pointer.setValue(pointer.next.getValue());
//                    pointer.next.setValue(temp);
//                }
//                pointer = pointer.next;
//            }
//
//            j = 0;
//            while (len - j > 1) {
//                pointer = this;
//                for (k = 0; k < len - j - 1; k ++) {
//                    pointer = pointer.next;
//                }
//
//                pointer2 = this;
//                while (pointer2.next != pointer) {
//                    pointer2 = pointer2.next;
//                }
//
//                if (pointer.getValue() < pointer2.getValue()) {
//                    temp = pointer.getValue();
//                    pointer.setValue(pointer2.getValue());
//                    pointer2.setValue(temp);
//                }
//
//                j ++;
//            }
//        }
//    }

}