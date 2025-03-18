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
        int len = this.len();
        int j;
        int temp;
        boolean swapped = true;

        while (swapped) {
            pointer_i = head;
            swapped = false;
            for (j = 0; j < len - 1; j ++) {
                if (pointer_i.getValor() > pointer_i.getProx().getValor()) {
                    swapped = true;
                    temp = pointer_i.getProx().getValor();
                    pointer_i.getProx().setValor(pointer_i.getValor());
                    pointer_i.setValor(temp);
                }
                pointer_i = pointer_i.getProx();
            }
            len --;
        }
    }

    // análogo à partição de Lomuto
    // funciona em O(n), desconsiderando a travessia na lista inteira para posicionar o pivô
    // se considerar a travessia inicial acredito que fique O(2n), o que em notação assintótica acaba
    // virando O(n)
    private int particiona(int p, int r) {
        NoLista pivot = head, pointer_i= head, pointer_j = head;
        int i;
        int k;
        int temp;

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
        NoLista inicio = head, fim = end, pointer;
        int temp;
        boolean swapped = true;

        while (inicio != fim && swapped) {
            pointer = inicio;
            swapped = false;
            while (pointer != fim.getAnt()) {
                if (pointer.getValor() > pointer.getProx().getValor()) {
                    swapped = true;
                    temp = pointer.getValor();
                    pointer.setValor(pointer.getProx().getValor());
                    pointer.getProx().setValor(temp);
                }

                pointer = pointer.getProx();
            }
            fim = fim.getAnt();

            if (swapped && inicio != fim) {
                pointer = fim;
                swapped = false;
                while (pointer != inicio) {
                    if (pointer.getValor() < pointer.getAnt().getValor()) {
                        swapped = true;
                        temp = pointer.getValor();
                        pointer.setValor(pointer.getAnt().getValor());
                        pointer.getAnt().setValor(temp);
                    }

                    pointer = pointer.getAnt();
                }
                inicio = inicio.getProx();
            }
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
    private int buscaBinaria(int fim, int ele) {
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
        int i;
        int j;
        int temp;
        int len = this.len();
        int pos;

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
        int[] arrCount = new int[(int)max() + 1];

        while (pointer != null) {
            // um vetor em java automaticamente é iniciado com o valor 0 em todas as suas posições
            // portanto pode-se fazer isso sem se preocupar como valor ser lixo
            arrCount[(int) pointer.getValor()] += 1;
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
        int[] arrCount = count();
        int[] copia;

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
        int gap = (int) (this.len() / fat);
        int i;
        int temp;
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

        // voltando o ponteiro para a posição ini (não é possível só recuá-lo pois ele chega em null)
        // podemos usar um segundo ponteiro (ant) para evitar essa caminhada inteira novamente
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
        int gap = 1, pos;
        int i;
        int temp;

        while (gap * 2 + 1 < len())
            gap = gap * 2 + 1;

        while (gap >= 1) {
            pointer1 = head;

            // posicionando o ponteiro no gap
            for (i = 0; i < gap; i ++)
                pointer1 = pointer1.getProx();

            // var pos vai ser usada pra guardar a posição correta do pointer1 entre as múltiplas iterações com
            // um mesmo gap
            pos = gap;

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


                // colocando pointer1 na posição correta
                pos ++;

                pointer1 = this.head;
                for (i = 0; i < pos; i ++)
                    pointer1 = pointer1.getProx();
            }

            // refaz o processo com o gap menor
            gap /= 2;
        }
    }

    private double algarismoK(double num, int k) {
        /*
          retorna o k-ésimo algarismo do número (da direita para a esquerda)
          */
        int i;
        double algarismo = 0;

        for (i = 0; i < k && num >= 0; i ++) {
            algarismo = num % 10;
            num -= algarismo;
            num /= 10;
        }

        if (num < 0)
            algarismo = 0;

        return algarismo;
    }

    private int numeroDeAlgarismos(double num) {
        int count = 0;
        double ultimoAlgarismo;

        while (num > 0) {
            ultimoAlgarismo = num % 10;
            num -= ultimoAlgarismo;
            num /= 10;

            count ++;
        }

        return count;
    }

    private int[] radixCount(int k) {
        /*
          retorna um array de contagem baseado no k-ésimo algarismo do número (da direita para a esquerda)
          */
        int[] arrCount = new int[(int)this.max()];
        NoLista pointer = head;
        double num;

        while (pointer != null) {
            num = pointer.getValor();

            num = algarismoK(num, k);

            arrCount[(int)num] ++;

            pointer = pointer.getProx();
        }

        return arrCount;
    }

    private void countingSortAlgarismoK(int k) {
        NoLista pointer = head, pointer2;
        int i;
        int j;
        int pos;
        double algarismo;
        int[] arrCount = radixCount(k);
        int[] copia;

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

            arrCount[(int)algarismo] -= 1;
            pos = arrCount[(int)algarismo];

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

    private int minRun(int len) {
        /*
        * minRun = 6 bits mais significativos de len + 1 caso algum dos bits menos significativos seja 1
        * */
        int r = 0;

        while (len >= 32) { // vamos remover os bits menos significativos até sobrarem só os 6 mais significativos
            r |= (len & 1); // r captura o bit menos significativo para cumprir que se algum dos menos significativos for 1
            len >>= 1; // isso aqui desloca um bit para a direita (equivalente a len = len >> 1 e equivalente a dividir por 2)
        }

        return len + r;
    }

    private void timInsertionSort(int ini, int fim) {
        /*
        * é o insertionSort dentro de um intervalo especifico da lista.
        * usado para ordenar os run's do timSort
        * */
        NoLista pointer_i = head, pointer_j;
        int i;
        int aux;
        int j;

        // uso "<=" para fazer ele ir 1 elemento pra frente do inicial
        // pois não precisamos fazer comparação no primeiro elemento
        for (i = 0; i <= ini; i ++)
            pointer_i = pointer_i.getProx();

        for (; i < fim; i ++) {
            aux = pointer_i.getValor();
            pointer_j = pointer_i;
            j = i;

            while (j > ini && pointer_j.getAnt().getValor() > aux) {
                pointer_j.setValor(pointer_j.getAnt().getValor());
                pointer_j = pointer_j.getAnt();
                j --;
            }

            pointer_j.setValor(aux);

            pointer_i = pointer_i.getProx();
        }
    }

    private void inverteSubLista(int ini, int fim) {
        /*
          inverte a sublista no invertalo [ini:fim] (intervalo superior inclusivo)
          isso será usado caso a run do timSort esteja ordenada em ordem decrescente
         */
        NoLista pointer_i = head, pointer_j = head;
        int i;
        int temp;

        for (i = 0; i < ini; i ++) {
            pointer_i = pointer_i.getProx();
        }

        pointer_j = pointer_i;
        for (; i < fim; i ++)
            pointer_j = pointer_j.getProx();

        while (pointer_i != pointer_j) {
            temp = pointer_j.getValor();
            pointer_j.setValor(pointer_i.getValor());
            pointer_i.setValor(temp);

            pointer_i = pointer_i.getProx();

            if (pointer_i != pointer_j)
                pointer_j = pointer_j.getAnt();
        }
    }

    public void timSort() {
        int minRun = this.minRun(this.len());
//        System.out.println(minRun);
        PilhaPair pilha = new PilhaPair();
        Pair pair1, pair2;
        NoLista pointer = head, pointeraux;
        int i = 0, inicioRun;

        while (pointer != null) {

            // começando a run
            inicioRun = i;

            // avançamos esse ponteiro pois o tamanho mínimo de qualquer run é 2
            // (toda lista de 2 elementos está sempre ordenada, seja crescente ou decrescente)
            pointeraux = pointer;
            pointer = pointer.getProx();
            i ++;

            if (pointer != null && pointer.getValor() >= pointer.getAnt().getValor()) {
                // nesse caso a run começa crescente
                while (pointer != null && pointer.getValor() > pointer.getAnt().getValor()) {
                    pointeraux = pointer;
                    pointer = pointer.getProx();
                    i ++;
                }
            } else if (pointer != null && pointer.getValor() < pointer.getAnt().getValor()) {
                // nesse caso a run começa decrescente
                while (pointer != null && pointer.getValor() < pointer.getAnt().getValor()) {
                    pointeraux = pointer;
                    pointer = pointer.getProx();
                    i ++;
                }
            }

            if (i - inicioRun >= minRun) {
                // nesse caso a run está inteira ordenada, não sabemos em qual ordem -> vamos conferir os valores
                System.out.println("a");
                 if (pointeraux.getValor() < pointeraux.getAnt().getValor())
                     // caso seja decrescente inverte a lista
                    inverteSubLista(inicioRun, i - 1);
                 // caso contrário simplesmente não faz nada
            } else {
                // sublista não está ordenada, vamos incluir mais elementos na sublista até chegar em minRun
                // e então rodar o insertionSort
                while (pointer != null && i - inicioRun < minRun) {
                    i ++;
                    pointer = pointer.getProx();
                }
                System.out.println("Ordenando de " + inicioRun + " até " + i);
                timInsertionSort(inicioRun, i);
            }

            // adicionando as coordenadas na pilha de pairs
            pilha.push(inicioRun, i - 1);
        }

        while (pilha.getInicio() != null) {
            pair1 = pilha.pop().getPair();
            if (pilha.getInicio() != null) {
                pair2 = pilha.pop().getPair();

                merge(pair2.getFirst(), pair2.getSecond(), pair1.getSecond());

                pilha.push(pair2.getFirst(), pair1.getSecond());
            }
        }
    }

    private int nesimoAlgarismo(int num, int n) {
        int i, res = 0;

        for (i = 0; i < n; i ++) {
            res = num % 10;
            num /= 10;
        }

        return res;
    }

    private int nAlgarismos(int num) {
        int cont = 0;

        if (num == 0)
            return 1;

        while (num > 0) {
            num /= 10;
            cont ++;
        }

        return cont;
    }

    public void bucketSort() {
        /*
          o bucketSort foi feito para ser usado em números decimais menores que 1,
          portanto vou alterar a classe NoLista para conter um double ao invés de int,
          acredito que isso não vá prejudicar outras ordenações
          */

        // declarando o vetor de buckets que será utilizado nas ordenações
        // cada bucket é uma LinkedList
        int indice, maior = max(), primeiro_alg;

        // dessa maneira, haverá um bucket para cada grupo de 10 números da lista
        int tamanho = (maior - nesimoAlgarismo(maior, 1)) / 10 + 1;
        LinkedList[] vet = new LinkedList[tamanho];
        NoLista pointer1 = head, pointer2;

        // temos também que inicializar cada lista dentro desse vetor
        for (indice = 0; indice < tamanho; indice ++)
            vet[indice] = new LinkedList();

        while (pointer1 != null) {
            indice = pointer1.getValor() / 10;

            vet[indice].insert(pointer1.getValor());

            pointer1 = pointer1.getProx();
        }

        pointer1 = head;
        for (indice = 0; indice < tamanho; indice ++) {
            if (vet[indice].len() > 0) {
                vet[indice].insertionSort();
                pointer2 = vet[indice].getHead();

                while (pointer2 != null) {
                    pointer1.setValor(pointer2.getValor());
                    pointer2 = pointer2.getProx();
                    pointer1 = pointer1.getProx();
                }
            }
        }
    }

    public void particionaSemPivo(NoLista ini, NoLista fim) {
        NoLista i = ini, j = fim;
        int temp;
        while (i != j) {
            while (i != j && i.getValor() <= j.getValor())
                i = i.getProx();

            // poderia ter um if verificando se eles são diferentes, mas em vetor/lista
            // compensa eventualmente fazer uma troca que não precisaria ao invés de
            // fazer uma verificação toda vez
            temp = i.getValor();
            i.setValor(j.getValor());
            j.setValor(temp);

            while (i != j && i.getValor() <= j.getValor())
                j = j.getAnt();

            temp = i.getValor();
            i.setValor(j.getValor());
            j.setValor(temp);
        }

        if (i != fim && i.getProx() != fim)
            particionaSemPivo(i.getProx(), fim);

        if (i != ini && i.getAnt() != ini)
            particionaSemPivo(ini, i.getAnt());
    }

    public void quickSemPivo() {
        NoLista i = this.head, j = this.end;

        particionaSemPivo(i, j);
    }

    private int pai(int indiceFilho) {
        return indiceFilho / 2;
    }

    private int filhoEsq(int indicePai) {
        return indicePai * 2 + 1;
    }

    private int filhoDir(int indicePai) {
        return indicePai * 2 + 2;
    }

    private void buildHeap(int indiceAtual, int heapSize) {
        NoLista pi = head, pj;
        int i, j, temp;
        for (i = 0; i < indiceAtual; i ++)
            pi = pi.getProx();


        if (filhoEsq(indiceAtual) < heapSize) {
            // realiza a chamada recursiva primeiro, abordagem "de baixo pra cima" na árvore
            buildHeap(filhoEsq(indiceAtual), heapSize);

            // comparando com o filho esquerdo
            pj = pi;
            for (j = i; j < filhoEsq(indiceAtual); j ++)
                pj = pj.getProx();

            if (pj.getValor() > pi.getValor()) {
                // se filho esquerdo for maior, faz a troca
                temp = (int) pi.getValor();
                pi.setValor(pj.getValor());
                pj.setValor(temp);
            }
        }


        if (filhoDir(indiceAtual) < heapSize) {
            // realiza a chamada recursiva primeiro, abordagem "de baixo pra cima" na árvore
            buildHeap(filhoDir(indiceAtual), heapSize);

            //comparando com o filho direito
            pj = pi;
            for (j = i; j < filhoDir(indiceAtual); j ++)
                pj = pj.getProx();

            if (pj.getValor() > pi.getValor()) {
                // se filho esquerdo for maior, faz a troca
                temp = (int) pi.getValor();
                pi.setValor(pj.getValor());
                pj.setValor(temp);
            }
        }
    }

    private void buildHeapIter(int heapSize) {
        int pai = heapSize / 2 - 1, i, fe, fd;
        int aux;
        NoLista noPai, noFe, noFd, maiorF;

        while (pai >= 0) {
            noPai = this.head;
            noFd = this.head;
            noFe = this.head;

            fd = filhoDir(pai);
            fe = filhoEsq(pai);

            for (i = 0; i < pai; i ++)
                noPai = noPai.getProx();

            for (i = 0; i < fe; i ++)
                noFe = noFe.getProx();

            maiorF = noFe;

            if (fd < heapSize) {
                for (i = 0; i < fd; i ++)
                    noFd = noFd.getProx();

                if (noFd.getValor() > noFe.getValor())
                    maiorF = noFd;
            }

            if (maiorF.getValor() > noPai.getValor()) {
                aux = maiorF.getValor();
                maiorF.setValor(noPai.getValor());
                noPai.setValor(aux);
            }

            pai --;
        }
    }

    public void heapSort() {
        int i, len, tl = this.len(), j;
        int temp;
        NoLista pointer;
        for (len = this.len(); len > 1; len --) {
            // constroi o heap
//            buildHeap(0, len);
            buildHeapIter(len);

            // caminha o ponteiro do fim para o fim do heap
            pointer = this.head;
            for (j = 0; j < len - 1; j ++)
                pointer = pointer.getProx();

            // realiza o swap
            temp = this.head.getValor();
            this.head.setValor(pointer.getValor());
            pointer.setValor(temp);
        }
    }

    private void particaoMergeSort(LinkedList l1, LinkedList l2) {
        NoLista pointer = this.head;
        int tl = len(), i = 0;
        l1.inicializa(); l2.inicializa();

        while (i < tl / 2) {
            l1.insert(pointer.getValor());

            pointer = pointer.getProx();
            i ++;
        }

        while (i < tl) {
            l2.insert(pointer.getValor());

            pointer = pointer.getProx();
            i ++;
        }

    }

    private void fusaoMergeSort(LinkedList l1, LinkedList l2, int seq) {
        int i = 0, j = 0, seq_inicial = seq, len = len();
        NoLista pointer_i = l1.head, pointer_j = l2.head, pointer = this.head;

        while (seq <= len / 2) {
            while (i < seq && j < seq) {
                if (pointer_i.getValor() < pointer_j.getValor()) {
                    pointer.setValor(pointer_i.getValor());

                    pointer = pointer.getProx();
                    pointer_i = pointer_i.getProx();

                    i ++;
                } else {
                    pointer.setValor(pointer_j.getValor());

                    pointer = pointer.getProx();
                    pointer_j = pointer_j.getProx();

                    j ++;
                }
            }

            while (i < seq) {
                pointer.setValor(pointer_i.getValor());

                pointer = pointer.getProx();
                pointer_i = pointer_i.getProx();

                i ++;
            }

            while (j < seq) {
                pointer.setValor(pointer_j.getValor());

                pointer = pointer.getProx();
                pointer_j = pointer_j.getProx();

                j ++;
            }

            seq += seq_inicial;
        }

    }

    public void mergePrimeiraImplementacao() {
        // essa coisa bizonha só funciona para listas de tamanho múltiplo de 2

        int seq = 1, len = this.len();
        LinkedList ll1 = new LinkedList(), ll2 = new LinkedList();

        while (seq < len) {
            particaoMergeSort(ll1, ll2);
            fusaoMergeSort(ll1, ll2, seq);

            seq *= 2;
        }
    }
}