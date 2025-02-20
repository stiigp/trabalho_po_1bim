public class LinkedList {

    private NoLista head, end;

    // primeiro construtor do objeto lista, recebe um NoLista
    public LinkedList() {
        this.head = this.end = null;
    }

    public NoLista getHead() {
        return head;
    }

    public void setHead(NoLista head) {
        this.head = head;
    }

    public NoLista getEnd() {
        return end;
    }

    public void setEnd(NoLista end) {
        this.end = end;
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

    // a implementação das versões mais antigas funciona estritamente em n^2, ou seja, Theta(n^2)
    // mas ajustei a implementação para parar caso o vetor já esteja ordenado usando a flag swap
    public void bubbleSort() {
        NoLista pointer_i;
        int len = this.len(), i = 0, j, temp;
        boolean swapped = true;

        while (swapped) {
            pointer_i = head;
            swapped = false;
            for (j = 0; j < len - i - 1; j ++) {
                if (pointer_i.getValor() > pointer_i.getProx().getValor()) {
                    swapped = true;
                    temp = pointer_i.getProx().getValor();
                    pointer_i.getProx().setValor(pointer_i.getValor());
                    pointer_i.setValor(temp);
                }
                pointer_i = pointer_i.getProx();
            }

            i ++;
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
        NoLista pointer_i = head.getProx(), pointer_j;
        int aux;

        while (pointer_i != null) {

            aux = pointer_i.getValor();
            pointer_j = pointer_i;

            while (pointer_j != head && aux < pointer_j.getAnt().getValor()) {
                pointer_j.setValor(pointer_j.getAnt().getValor());

                pointer_j = pointer_j.getAnt();
            }

            pointer_j.setValor(aux);

            pointer_i = pointer_i.getProx();
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
        boolean swapped = true;
        NoLista pointer_i, pointer_j;

        // em um bubbleSort, usaríamos somente a flag swapped na condição desse while,
        // porém nesse caso pode ser que não ocorra nenhuma troca E a lista não esteja
        // ordenada caso o gap seja maior do que 1, portanto ele continua enquanto o gap for maior do que 1
        // OU ele tenha feito uma troca na última iteração, garantindo que a lista seja ordenada em todos os casos
        while (gap > 1 || swapped) {
            pointer_i = pointer_j = head;
            swapped = false;

            if (gap == 0)
                gap = 1;

            for (i = 0; i < gap; i ++)
                pointer_j = pointer_j.getProx();

            while (pointer_j != null) {
                if (pointer_i.getValor() > pointer_j.getValor()) {
                    swapped = true;
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

    // o gnome lembra um pouco o bubble sort
    public void gnomeSort() {
        // pode começar do segundo elemento, pois sempre compara com o elemento atrás dele
        // caso a lista tenha um elemento só, nada acontece
        NoLista pointer = head.getProx();
        int temp;

        while (pointer != null) {
            if (pointer.getValor() < pointer.getAnt().getValor()) {
                temp = pointer.getValor();
                pointer.setValor(pointer.getAnt().getValor());
                pointer.getAnt().setValor(temp);

                if (pointer.getAnt().getAnt() != null)
                    pointer = pointer.getAnt();
            } else
                pointer = pointer.getProx();
        }
    }

    private void merge(int ini, int meio, int fim) {
        // intercalar duas sublistas
        // sublista A[ini:meio] com sublista B[meio + 1:fim]

        LinkedList A = new LinkedList(), B = new LinkedList();
        NoLista pointer = this.head;
        int i = 0, lenA, lenB;

        // posicionamento inicial da lista
        while (i < ini) {
            pointer = pointer.getProx();
            i ++;
        }

        // construindo as duas sublistas

        // por mais que seja esquisito, precisamos que seja "<=", pois no caso base da recursão dá merda se for i < meio
        // no caso da fusão de duas listas de 1 elemento, chamaríamos algo como merge(0, 0, 1)
        // e se fosse i < meio a sublista de dois elementos não seria dividida e seria retornada na mesma ordem
        // por isso é importante fazer teste de mesa do caso-base e precisamos de i <= meio
        while (i <= meio) {
            A.insert(pointer.getValor());
            pointer = pointer.getProx();
            i ++;
        }

        while (i <= fim) {
            B.insert(pointer.getValor());
            pointer = pointer.getProx();
            i ++;
        }

        // voltando o ponteiro para a posição ini
        pointer = head;
        i = 0;
        while (i < ini) {
            pointer = pointer.getProx();
            i ++;
        }

        // fazemos isso para evitar recalcular o len toda iteração, pois isso tornaria
        // o algoritmo extremamente ineficaz
        lenA = A.len();
        lenB = B.len();

        while (lenA > 0 && lenB > 0) {
            if (A.getHead().getValor() < B.getHead().getValor()) {
                pointer.setValor(A.getHead().getValor());

                // avançando o head da sublista
                A.setHead(A.getHead().getProx());
                lenA --;
            } else {
                pointer.setValor(B.getHead().getValor());

                B.setHead(B.getHead().getProx());
                lenB --;
            }

            pointer = pointer.getProx();
        }

        while (lenA > 0) {
            pointer.setValor(A.getHead().getValor());

            // avançando o head da sublista
            A.setHead(A.getHead().getProx());
            lenA --;

            pointer = pointer.getProx();
        }

        while (lenB > 0) {
            pointer.setValor(B.getHead().getValor());

            B.setHead(B.getHead().getProx());
            lenB --;

            pointer = pointer.getProx();
        }

    }


    // primeira chamada deve ser feita com ini = 0 e fim = len - 1
    public void mergeSort(int ini, int fim) {
        int meio = (ini + fim) / 2;
        if (ini < fim) {
            mergeSort(ini, meio);
            mergeSort(meio + 1, fim);

            merge(ini, meio, fim);
        }
    }

    public void shellSort() {
        NoLista pointer1, pointer2;
        int gap = this.len() / 2, i, temp;

        while (gap >= 1) {
            pointer1 = head;

            // posicionando o ponteiro no gap
            for (i = 0; i < gap; i ++)
                pointer1 = pointer1.getProx();

            // enquanto não chegar no final da lista com o pointer1
            while (pointer1 != null) {
                pointer2 = pointer1;
                temp = pointer1.getValor();

                // fazendo o posicionamento inicial do pointer2
                // "um elemento" atrás do pointer1 (na sublista)
                for (i = 0; i < gap && pointer2 != null; i ++) {
                    pointer2 = pointer2.getAnt();
                }

                // caminha até acabar a sublista ou até encontrar a posição correta na sublista ordenada
                // reordenando os elementos
                while (pointer2 != null && pointer2.getValor() > temp) {
                    pointer1.setValor(pointer2.getValor());
                    pointer1 = pointer2;

                    for (i = 0; i < gap && pointer2 != null; i ++) {
                        pointer2 = pointer2.getAnt();
                    }
                }

                // escreve o valor do pointer1 inicial no lugar certo dentro da sublista
                pointer1.setValor(temp);

                pointer1 = pointer1.getProx();
            }

            // refaz o processo com o gap menor
            gap /= 2;
        }
    }


    private int algarismoK(int num, int k) {
        /**
         * retorna o k-ésimo algarismo do número (da direita para a esquerda)
         * */
        int i, algarismo = 0;

        for (i = 0; i < k && num >= 0; i ++) {
            algarismo = num % 10;
            num -= algarismo;
            num /= 10;
        }

        if (num < 0)
            algarismo = 0;

        return algarismo;
    }

    public int numeroDeAlgarismos(int num) {
        int count = 0, ultimoAlgarismo;

        while (num > 0) {
            ultimoAlgarismo = num % 10;
            num -= ultimoAlgarismo;
            num /= 10;

            count ++;
        }

        return count;
    }

    private int[] radixCount(int k) {
        /**
         * retorna um array de contagem baseado no k-ésimo algarismo do número (da direita para a esquerda)
         * */
        int[] arrCount = new int[this.max()];
        NoLista pointer = head;
        int num;

        while (pointer != null) {
            num = pointer.getValor();

            num = algarismoK(num, k);

            arrCount[num] ++;

            pointer = pointer.getProx();
        }

        return arrCount;
    }
    private void countingSortAlgarismoK(int k) {
        NoLista pointer = head, pointer2;
        int i, j, pos, algarismo;
        int[] arrCount = radixCount(k), copia;

//        for (int ele: arrCount)
//            System.out.print(ele + " ");

        // aqui fazemos uma operação no vetor de contagem para usar esses valores como índices ao final do algoritmo
        // basicamente: pra cada elemento no vetor ele considera quantas das primeiras posições do vetor já estão ocupadas
        for (i = 1; i < arrCount.length; i ++)
            arrCount[i] = arrCount[i - 1] + arrCount[i];

        // vamos fazer também uma cópia da lista, pois é necessário para esse algoritmo
        copia = copiaLista();

        for (i = copia.length - 1; i >= 0; i --) {
            algarismo = algarismoK(copia[i], k);

            arrCount[algarismo] -= 1;
            pos = arrCount[algarismo];

            pointer = head;
            for (j = 0; j < pos; j ++) {
                pointer = pointer.getProx();
            }

            pointer.setValor(copia[i]);
        }
    }

    public void radixSort() {
        int numero_maximo_algarismos = numeroDeAlgarismos(max()), i;

        for (i = 0; i < numero_maximo_algarismos; i ++)
            countingSortAlgarismoK(i + 1);

    }

}