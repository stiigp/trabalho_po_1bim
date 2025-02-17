public class LinkedList {

    NoLista head, end;

    // primeiro construtor do objeto lista, recebe um NoLista
    public LinkedList() {
        this.head = this.end = null;
    }


    public void inicializa() {
        this.head = this.end = null;
    }

    // insere ao final da lista
    public void insert(int valor) {
        // usa um ponteiro do tipo NoLista
        NoLista pointer = head;

        if (pointer != null) {
            while (pointer.getProx() != null)
                pointer = pointer.getProx();

            pointer.setProx(new NoLista(valor, pointer));
            this.end = pointer.getProx();
        } else {
            this.head = new NoLista(valor);
        }
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

    // essa eu fiz mais pra testar se o apontamento dos ant está correto
    public void printReversed() {
        NoLista pointer = head;
        while (pointer.getProx() != null) {
            pointer = pointer.getProx();
        }

        while (pointer != null) {
            System.out.print(pointer.getValor() + " ");
            pointer = pointer.getAnt();
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
    // se considerar a travessia inicial acredito que fique O(2n), o que em notação assintótica acaba
    // virando O(n)
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

    // a chamada inicial para o quickSort deve ser feita passando 0 e n como parâmetros p e r
    public void quickSort(int p, int r) {
        int i;

        if (p < r) {
            i = particiona(p, r);
            quickSort(p, i);
            quickSort(i + 1, r);
        }
    }

    public void shakerSort() {
        NoLista pointer = head;
        int len = this.len(), temp, j;

        // vai haver n / 2 iterações principais, sendo n o tamanho da lista
        for (int i = 0; i < len/2; i ++) {

            // essa é a operação normal do bubbleSort, que "empurra" o maior elemento até o final
            // porém j começa em i pois o shakerSort ordena "nas duas pontas"
            // então a sublista lista[0:i] (intervalo superior exclusivo) já está ordenada
            for (j = i; j < len - 1 - i; j ++) {
                if (pointer.getValor() > pointer.getProx().getValor()) {
                    temp = pointer.getValor();
                    pointer.setValor(pointer.getProx().getValor());
                    pointer.getProx().setValor(temp);
                }
                pointer = pointer.getProx();
            }

            System.out.println("j parou em: " + j);

            // aqui vamos fazer basicamente a mesma operação, mas trazendo o menor elemento para o começo da lista
            // ponteiro para no primeiro elemento da sublista ordenada do topo, então recuamos 1 para evitar
            // uma comparação desnecessária

            pointer = pointer.getAnt();
            for (j --; j > i; j--) {
                if (pointer.getValor() < pointer.getAnt().getValor()) {
                    temp = pointer.getValor();
                    pointer.setValor(pointer.getAnt().getValor());
                    pointer.getAnt().setValor(temp);
                }
                pointer = pointer.getAnt();
            }

            System.out.println("j parou em: " + j);

            // avançamos o ponteiro aqui ao final para ele já ficar na posição certa para a próxima iteração
            pointer = pointer.getProx();
        }
    }

    public void insertionSort() {
        NoLista pointer;
        int i, j, len = this.len(), temp;

        for (i = 0; i < len; i ++) {
            pointer = head;
            for (j = 0; j < i; j ++) {
                pointer = pointer.getProx();
            }

            while (pointer.getAnt() != null && pointer.getValor() < pointer.getAnt().getValor()) {
                temp = pointer.getValor();
                pointer.setValor(pointer.getAnt().getValor());
                pointer.getAnt().setValor(temp);

                pointer = pointer.getAnt();
            }
        }
    }

    // essa busca não retorna -1 caso o elemento não seja achado, retorna o índice em que o elemento deveria estar,
    // já que sabemos que o elemento não será encontrado
    public int buscaBinaria(int fim, int ele) {
        // feito para ser usado no binaryInsertionSort, não possui o parâmetro ini, pois a sublista ordenada
        // sempre vai ser list[0:x]
        int ini = 0, meio = (ini + fim) / 2, i;
        NoLista pointer = head;

        for (i = 0; i < meio; i ++)
            pointer = pointer.getProx();

        while (ini <= fim && pointer.getValor() != ele) {

            if (ele > pointer.getValor())
                ini = meio + 1;
            else
                fim = meio - 1;

            meio = (ini + fim) / 2;

            pointer = head;
            for (i = 0; i < meio; i ++)
                pointer = pointer.getProx();
        }

        pointer = head;
        for (i = 0; i < meio; i ++)
            pointer = pointer.getProx();

        // o if existe para retornar a coordenada exata em que o elemento precisa estar
        if (ele > pointer.getValor())
            return meio + 1;
        return meio;
    }

    public void binaryInsertionSort() {
        NoLista pointer;
        int i, j, temp, len = this.len(), pos;

        for (i = 0; i < len; i ++) {
            pointer = head;
            for (j = 0; j < i; j ++) {
                pointer = pointer.getProx();
            }

            pos = this.buscaBinaria(i, pointer.getValor());

            for (; j > pos; j --) {
                temp = pointer.getValor();
                pointer.setValor(pointer.getAnt().getValor());
                pointer.getAnt().setValor(temp);

                pointer = pointer.getAnt();
            }
        }
    }

    // retorna o maior elemento da lista
    // é usado dentro do countingSort
    private int max() {
        NoLista pointer = head;
        int maior = 0;
        while (pointer != null) {
            if (pointer.getValor() > maior) {
                maior = pointer.getValor();
            }
            pointer = pointer.getProx();
        }
        return maior;
    }

    // retorna um vetor de contagem tal que arrCount[i] = número de vezes que i aparece na LinkedList
    private int[] count() {
        NoLista pointer = head;
        int[] arrCount = new int[max() + 1];

        while (pointer != null) {
            // um vetor em java automaticamente é iniciado com o valor 0 em todas as suas posições
            // portanto pode-se fazer isso sem se preocupar como valor ser lixo
            arrCount[pointer.getValor()] += 1;
            pointer = pointer.getProx();
        }

        return arrCount;
    }

    // retorna um vetor com os elementos da lista copiados
    private int[] copiaLista() {
        int[] arr = new int[len()];
        NoLista pointer = head;
        int i = 0;

        while (pointer != null) {
            arr[i] = pointer.getValor();
            pointer = pointer.getProx();
            i ++;
        }

        return arr;
    }

    public void countingSort() {
        NoLista pointer = head, pointer2;
        int i, j, pos;
        int[] arrCount = count(), copia;

        // aqui fazemos uma operação no vetor de contagem para usar esses valores como índices ao final do algoritmo
        // basicamente: pra cada elemento no vetor ele considera quantas das primeiras posições do vetor já estão ocupadas
        for (i = 1; i < arrCount.length; i ++)
            arrCount[i] = arrCount[i - 1] + arrCount[i];

        // vamos fazer também uma cópia da lista, pois é necessário para esse algoritmo
        copia = copiaLista();

        for (i = copia.length - 1; i >= 0; i --) {
            arrCount[copia[i]] -= 1;
            pos = arrCount[copia[i]];

            pointer = head;
            for (j = 0; j < pos; j ++) {
                pointer = pointer.getProx();
            }
            pointer.setValor(copia[i]);
        }
    }

    public void combSort() {
        double fat = 1.3;
        int gap = (int) (this.len() / fat), i, temp;
        NoLista pointer_i, pointer_j;

        while (gap >= 1) {
            pointer_i = pointer_j = head;

            for (i = 0; i < gap; i ++)
                pointer_j = pointer_j.getProx();

            while (pointer_j != null) {
                if (pointer_i.getValor() > pointer_j.getValor()) {
                    temp = pointer_j.getValor();
                    pointer_j.setValor(pointer_i.getValor());
                    pointer_i.setValor(temp);
                }

                pointer_i = pointer_i.getProx();
                pointer_j = pointer_j.getProx();
            }

            gap = (int) (gap / fat);
        }

    }
}