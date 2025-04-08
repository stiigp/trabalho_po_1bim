package Arquivo;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

public class Arquivo
{
    private String nomearquivo;
    private RandomAccessFile arquivo;
    private int comp, mov;

    public Arquivo(String nomearquivo) {
        try
        {
            arquivo = new RandomAccessFile(nomearquivo, "rw");
        } catch (IOException e)
        { }
    }

    public int getComp() {
        return this.comp;
    }

    public int getMov() {
        return this.mov;
    }

    public void copiaArquivo(RandomAccessFile arquivoOrigem){
        this.arquivo = arquivoOrigem;
    }

    private void copiaArquivoInterno(Arquivo arquivo) {
        Registro reg = new Registro(0);
        arquivo.seekArq(0);
        this.seekArq(0);

        reg.leDoArq(arquivo.arquivo);
        while (!arquivo.eof()) {
            reg.gravaNoArq(this.arquivo);
            reg.leDoArq(arquivo.arquivo);
        }
    }

    public RandomAccessFile getFile() {
        return this.arquivo;
    }

    public void truncate(long pos) {
        try {
            this.arquivo.setLength(pos * Registro.length());
        } catch (IOException e) {

        }
    }

    public boolean eof() {
        boolean retorno = false;
        try {
            if (arquivo.getFilePointer() == arquivo.length()) {
                retorno = true;
            }
        } catch (IOException e) {

        }

        return retorno;
    }

    public void seekArq(int pos) {
        try {
            arquivo.seek(pos * Registro.length());
        } catch (IOException e) {

        }
    }

    public int filesize() {
        try {
            return (int) (arquivo.length() / Registro.length());
        } catch (IOException e) {
            return -1;
        }
    }

    public void initComp() {
        this.comp = 0;
    }

    public void initMov() {
        this.mov = 0;
    }

    public void exibeArq() {
        Registro reg = new Registro(0);
        reg.leDoArq(this.arquivo);
        while (!this.eof()) {
            System.out.print(reg.getNumero() + " ");
            reg.leDoArq(this.arquivo);
        }
        System.out.println(reg.getNumero());
    }

    public void insercaoDireta() {
        initComp(); initMov();
        Registro reg_i = new Registro(0), reg_j = new Registro(0);
        int i = 0, j;

        while (i < filesize()) {
            seekArq(i);
            reg_i.leDoArq(this.arquivo);
            j = i - 1;

            if (j >= 0) {
                seekArq(j);
                reg_j.leDoArq(this.arquivo);
            }

            while (j >= 0 && reg_j.getNumero() > reg_i.getNumero()) {
                comp ++; mov ++;
//                reg_i.setNumero(reg_i.getNumero());
//                seekArq(j + 1);
                reg_j.gravaNoArq(this.arquivo);


                j --;
                if (j >= 0) {
                    seekArq(j);
                    reg_j.leDoArq(this.arquivo);
                }
            }

            seekArq(j + 1);
            reg_i.gravaNoArq(this.arquivo);
            mov ++;
            i ++;
        }
    }
    //demais metodos de ordenacao

    public void bubbleSort() {
        Registro reg_i = new Registro(0), reg_ant = new Registro(0);
        int i, len = filesize();
        boolean trocou = true;

        initComp(); initMov();

        while (trocou) {
            trocou = false;

            for (i = 0; i < len - 1; i ++) {
                seekArq(i);
                reg_ant.leDoArq(this.arquivo);
                reg_i.leDoArq(this.arquivo);

                if (reg_ant.getNumero() > reg_i.getNumero()) {
                    trocou = true;
                    mov += 2;
                    seekArq(i);
                    reg_i.gravaNoArq(this.arquivo);
                    reg_ant.gravaNoArq(this.arquivo);
                }
                comp ++;
            }
            len --;
        }
    }

    private void bubbleSortBucket() {
        Registro reg_i = new Registro(0), reg_ant = new Registro(0);
        int i, len = filesize();
        boolean trocou = true;

        // identico ao bubble sort mas não inicia comp ou mov
        // feito pra gerar a tabela do jeito certo no bucket

        while (trocou) {
            trocou = false;

            for (i = 0; i < len - 1; i ++) {
                seekArq(i);
                reg_ant.leDoArq(this.arquivo);
                reg_i.leDoArq(this.arquivo);

                if (reg_ant.getNumero() > reg_i.getNumero()) {
                    trocou = true;
                    mov += 2;
                    seekArq(i);
                    reg_i.gravaNoArq(this.arquivo);
                    reg_ant.gravaNoArq(this.arquivo);
                }
                comp ++;
            }
            len --;
        }
    }

    public void selectionSort() {
        Registro reg_i = new Registro(0), pointer_j = new Registro(0), pointer_menor = new Registro(0);
        int menor, pos_menor, i, j;

        initMov(); initComp();

        for (i = 0; i < filesize(); i ++) {
            seekArq(i);
            reg_i.leDoArq(this.arquivo);
            menor = reg_i.getNumero();
            pos_menor = i;

            j = i + 1;

            while (j < filesize()) {
                pointer_j.leDoArq(this.arquivo);

                if (pointer_j.getNumero() < menor) {
                    menor = pointer_j.getNumero();
                    pos_menor = j;
                }
                comp ++;
                j ++;
            }

            seekArq(pos_menor);
            pointer_menor.leDoArq(arquivo);
            seekArq(pos_menor);
            reg_i.gravaNoArq(arquivo);
            seekArq(i);
            pointer_menor.gravaNoArq(arquivo);

            mov += 2;
        }
    }

    public void shakerSort() {
        Registro reg_ant = new Registro(0), reg_prox = new Registro(0);
        int indice_comeco = 0, indice_fim = filesize(), valor_ant, i, valor_prox;
        boolean trocou = true;

        initComp(); initMov();

        while (trocou && indice_comeco < indice_fim) {
            trocou = false;

            i = indice_comeco;
            while (i < indice_fim){
                seekArq(i);
                reg_ant.leDoArq(this.arquivo);
                reg_prox.leDoArq(this.arquivo);

                if (reg_ant.getNumero() > reg_prox.getNumero()) {
                    // realiza o swap
                    trocou = true;
                    mov += 2;
                    seekArq(i);
                    reg_prox.gravaNoArq(this.arquivo);
                    reg_ant.gravaNoArq(this.arquivo);
                }
                comp ++;

                i ++;
            }

            indice_fim --;

            if (trocou) {
                trocou = false;

                i = indice_fim;
                while (i > indice_comeco) {
                    seekArq(i - 1);
                    reg_prox.leDoArq(arquivo);
                    reg_ant.leDoArq(this.arquivo);

                    if (reg_ant.getNumero() < reg_prox.getNumero()) {
                        trocou = true;
                        mov += 2;
                        seekArq(i - 1);
                        reg_ant.gravaNoArq(this.arquivo);
                        reg_prox.gravaNoArq(this.arquivo);
                    }
                    comp ++;
                    i --;
                }
                indice_comeco ++;
            }
        }
    }

    public void heapSort() {
        int tl = filesize(), pai, fe, fd, maior_f;
        Registro reg_fe = new Registro(0), reg_fd = new Registro(0), reg_pai = new Registro(0), reg_maior_f = new Registro(0);
        initComp(); initMov();

        while (tl > 1) {
            pai = tl / 2 - 1;

            while (pai >= 0) {
                fe = pai * 2 + 1;
                fd = fe + 1;

                seekArq(pai);
                reg_pai.leDoArq(arquivo);

                seekArq(fe);
                reg_fe.leDoArq(arquivo);
                reg_maior_f = reg_fe;
                maior_f = fe;

                if (fd < tl) {
                    reg_fd.leDoArq(arquivo);
                    if (reg_fd.getNumero() > reg_fe.getNumero()) {
                        reg_maior_f = reg_fd;
                        maior_f = fd;
                    }
                }

                if (reg_maior_f.getNumero() > reg_pai.getNumero()) {
                    seekArq(maior_f);
                    reg_pai.gravaNoArq(arquivo);
                    seekArq(pai);
                    reg_maior_f.gravaNoArq(arquivo);
                }

                pai --;
            }

            seekArq(0);
            reg_pai.leDoArq(arquivo);
            seekArq(tl - 1);
            reg_maior_f.leDoArq(arquivo);

            seekArq(0);
            reg_maior_f.gravaNoArq(arquivo);
            seekArq(tl - 1);
            reg_pai.gravaNoArq(arquivo);

            tl --;
        }

    }

    // partição *com pivô*, partição de lomuto, usa último elemento como pivô
    private int particiona(int ini, int fim) {
        int i = ini - 1, j = ini;
        Registro reg_i = new Registro(0), reg_j = new Registro(0), reg_pivo = new Registro(0);

        seekArq(fim - 1);
        reg_pivo.leDoArq(this.arquivo);

        while (j < fim - 1) {
            // j vai representar o elemento andando no arquivo, o que vamos usar para fazer as comparações com o pivô
            // enquanto isso, i vai representar o limiar da sublista de elementos menores que o pivô
            seekArq(j);
            reg_j.leDoArq(this.arquivo);

            if (reg_j.getNumero() < reg_pivo.getNumero()) {
                // vamos colocar o elemento de j como o último elemento da sublista de elementos menores que o pivô
                // e incrementar o i (que representa até onde essa sublista vai)

                i ++;

                seekArq(i);
                reg_i.leDoArq(this.arquivo);
                seekArq(i);
                reg_j.gravaNoArq(this.arquivo);
                seekArq(j);
                reg_i.gravaNoArq(this.arquivo);

                // executa permutação, portanto:
                mov += 2;
            }
            comp ++;

            j ++;
        }
        // i tem que ser incrementado, pois dentro do loop o i é incrementado antes da troca, então aqui ele tem que ser incrementado também
        i ++;

        seekArq(i);
        reg_i.leDoArq(this.arquivo);
        seekArq(i);
        reg_pivo.gravaNoArq(this.arquivo);
        seekArq(fim - 1);
        reg_i.gravaNoArq(this.arquivo);

        // mais uma permutação, mas sem comparação:
        mov += 2;

        // retorna para usarmos como o meio nas chamadas recursivas
        return i;
    }

    public void quickSort(int ini, int fim) {
        int meio;

        if (ini < fim) {
            meio = particiona(ini, fim);
            quickSort(ini, meio);
            quickSort(meio + 1, fim);
        }
    }

    public void gnomeSort() {
        initComp(); initMov();

        Registro reg_ant = new Registro(0), reg_prox = new Registro(0);
        int ant = 0, len = this.filesize();

        while (ant < len - 1) {
            seekArq(ant);
            reg_ant.leDoArq(this.arquivo);
            reg_prox.leDoArq(this.arquivo);

            if (reg_prox.getNumero() < reg_ant.getNumero()) {
                seekArq(ant);
                reg_prox.gravaNoArq(this.arquivo);
                reg_ant.gravaNoArq(this.arquivo);

                // permutação, portanto:
                mov += 2;

                if (ant > 0)
                    ant --;
            } else
                ant ++;

            comp ++;
        }
    }

    public void shellSort() {
        int len = this.filesize(), gap = 1, i, j;
        Registro reg_i = new Registro(0), reg_aux = new Registro(0);
        initMov();
        initComp();

        while (gap * 2 + 1 < len)
            gap = gap * 2 + 1;

        while (gap >= 1) {

            for (i = gap; i < len; i ++) {
                seekArq(i);
                reg_i.leDoArq(this.arquivo);

                j = i - gap;

                seekArq(j);
                reg_aux.leDoArq(this.arquivo);

                while (j >= 0 && reg_aux.getNumero() > reg_i.getNumero()) {
                    comp ++;

                    seekArq(j + gap);
                    reg_aux.gravaNoArq(this.arquivo);

                    // não ocorre permutação, portanto:
                    mov ++;

                    j -= gap;

                    if (j >= 0) {
                        seekArq(j);
                        reg_aux.leDoArq(this.arquivo);
                    }
                }

                seekArq(j + gap);
                reg_i.gravaNoArq(this.arquivo);

                // não ocorre permutação, portanto:
                mov ++;
            }

            gap /= 2;
        }

    }

    public void combSort() {
        initMov(); initComp();
        Registro reg_atual = new Registro(0), reg_prox = new Registro(0);
        int len = this.filesize(), gap = len, i;
        boolean swapped = true;


        gap /= 1.3;
        while (gap > 1 || swapped) {
            swapped = false;

            for (i = 0; i + gap < len; i ++) {
                seekArq(i);
                reg_atual.leDoArq(this.arquivo);

                seekArq(i + gap);
                reg_prox.leDoArq(this.arquivo);

                if (reg_atual.getNumero() > reg_prox.getNumero()) {
                    // "permutação", portanto:
                    mov += 2;

                    swapped = true;

                    seekArq(i);
                    reg_prox.gravaNoArq(this.arquivo);

                    seekArq(i + gap);
                    reg_atual.gravaNoArq(this.arquivo);
                }

                comp ++;
            }
            gap /= 1.3;
        }

    }

    // não é uma busca binária comum, é adaptada para funcionar com o binaryInsertionSort
    private int buscaBinaria(int ini, int fim, int ele) {
        Registro reg = new Registro(0);
        int meio = -1;

        while (ini <= fim) {
            meio = (ini + fim) / 2;

            seekArq(meio);
            reg.leDoArq(this.arquivo);

            if (ele < reg.getNumero())
                fim = meio - 1;
            else
                ini = meio + 1;
            comp ++;
        }

        comp ++;
        if (ele > reg.getNumero())
            return meio + 1;
        return meio;
    }

    public void binaryInsertionSort() {
        initComp(); // as comparações ocorrem dentro da busca binária, não aqui
        initMov();
        Registro reg_atual = new Registro(0), reg_ant = new Registro(0);
        int i, len = this.filesize(), pos, j;

        for (i = 1; i < len; i ++) {
            seekArq(i);
            reg_atual.leDoArq(this.arquivo);

            pos = buscaBinaria(0, i, reg_atual.getNumero());

            for (j = i - 1; j >= pos; j --) {
                seekArq(j);
                reg_ant.leDoArq(this.arquivo);
                reg_ant.gravaNoArq(this.arquivo);
                mov ++;
            }

            seekArq(pos);
            reg_atual.gravaNoArq(this.arquivo);
            mov ++;
        }
    }

    private void copiaParaArquivoAuxiliar(int ini, int fim) {
        Registro reg = new Registro(0);
        Arquivo aux = new Arquivo("arq_aux");
        int i;

        // copiando para o arquivo auxiliar
        this.seekArq(ini);
        for (i = ini; i < fim; i ++) {
            reg.leDoArq(this.arquivo);
            reg.gravaNoArq(aux.arquivo);
        }
    }

    private void merge(int ini, int meio, int fim) {
        // acredito que a melhor abordagem seja copiar do início ao fim
        // em um novo arquivo auxiliar, para então fazer a intercalação
        // desse arquivo auxiliar no arquivo original
        copiaParaArquivoAuxiliar(ini, fim);
        Arquivo aux = new Arquivo("arq_aux");

        // um registro para a primeira metade do arquivo, outro para a segunda metade
        Registro reg_prim = new Registro(0), reg_seg = new Registro(0);
        int indice_prim = 0, indice_seg = meio - ini;

        aux.seekArq(indice_prim);
        reg_prim.leDoArq(aux.arquivo);

        aux.seekArq(indice_seg);
        reg_seg.leDoArq(aux.arquivo);

        this.seekArq(ini);
        while (indice_prim < meio - ini && indice_seg < fim - ini) {
            comp ++;
            mov ++;
            if (reg_prim.getNumero() < reg_seg.getNumero()) {
                reg_prim.gravaNoArq(this.arquivo);
                indice_prim ++;

                if (indice_prim < meio) {
                    aux.seekArq(indice_prim);
                    reg_prim.leDoArq(aux.arquivo);
                }
            } else {
                reg_seg.gravaNoArq(this.arquivo);
                indice_seg ++;

                if (indice_seg < fim) {
                    aux.seekArq(indice_seg);
                    reg_seg.leDoArq(aux.arquivo);
                }
            }
        }

        aux.seekArq(indice_prim);
        while (indice_prim < meio - ini) {
            mov ++;
            reg_prim.leDoArq(aux.arquivo);
            reg_prim.gravaNoArq(this.arquivo);
            indice_prim ++;
        }

        aux.seekArq(indice_seg);
        while (indice_seg < fim - ini) {
            mov ++;
            reg_seg.leDoArq(aux.arquivo);
            reg_seg.gravaNoArq(this.arquivo);
            indice_seg ++;
        }
    }

    public void mergeSort(int ini, int fim) {

        int meio = (ini + fim) / 2;
        if (fim - ini > 1) {
            mergeSort(ini, meio);
            mergeSort(meio, fim);
            merge(ini, meio, fim);
        }
    }

    public int max() {
        Registro reg = new Registro(0);
        int maior = 0;

        seekArq(0);

        reg.leDoArq(this.arquivo);

        while (!eof()) {
            if (reg.getNumero() > maior) {
                maior = reg.getNumero();
            }

            reg.leDoArq(this.arquivo);
        }

        if (reg.getNumero() > maior) {
            maior = reg.getNumero();
        }

        return maior;
    }

    public void countingSort() {
        initComp(); initMov();

        Arquivo arq_aux = new Arquivo("arq_aux");
        Registro reg = new Registro(0);
        int maior = max(), i, len = filesize();
        int[] arr = new int[maior + 1];

        // preenchendo o vetor de count
        seekArq(0);
        reg.leDoArq(this.arquivo);
        while (!eof()) {
            arr[reg.getNumero()] ++;
            reg.leDoArq(this.arquivo);
        }
        arr[reg.getNumero()] ++;

        // adicionando o prefixo nos elementos do vetor
        seekArq(0);
        for (i = 1; i < arr.length; i ++) {
            arr[i] += arr[i - 1];
        }

        // efetivamente ordenando o arquivo
        for (i = len - 1; i >= 0; i --) {
            arq_aux.seekArq(i);
            reg.leDoArq(arq_aux.arquivo);

            arr[reg.getNumero()] --;

            seekArq(arr[reg.getNumero()]);
            reg.gravaNoArq(this.arquivo);

            // o algoritmo simplesmente não tem comparações e faz somente n movimentações
            // sua grande desvantagem é a complexidade em memória
            mov ++;
        }
    }

    private int nesimoAlgarismo(int num, int n) {
        // retorna o n-ésimo algarismo do número da direita pra esquerda

        int aux = 0, i;
        for (i = 0; i < n; i ++) {
            aux = num % 10;
            num /= 10;
        }

        return aux;

    }

    private int numeroDeAlgarismos(int num) {
        // retorna o número de algarismos de um número

        int cont = 0;

        if (num == 0)
            return 1;

        while (num > 0) {
            num /= 10;
            cont ++;
        }

        return cont;
    }

    private int maximoAlgarismoN(int algarismo) {
        Registro reg = new Registro(0);
        int max = 0;

        seekArq(0);
        reg.leDoArq(this.arquivo);
        while (!eof()) {
            if (nesimoAlgarismo(reg.getNumero(), algarismo) > max)
                max = nesimoAlgarismo(reg.getNumero(), algarismo);

            reg.leDoArq(this.arquivo);
        }

        if (nesimoAlgarismo(reg.getNumero(), algarismo) > max)
            max = nesimoAlgarismo(reg.getNumero(), algarismo);

        return max;
    }

    private int[] vetorCount(int algarismo) {
        // retorna um vetor de contagem baseado em um algarismo específico
        int[] vet = new int[maximoAlgarismoN(algarismo) + 1];
        int i;

        Registro reg = new Registro(0);

        seekArq(0);
        reg.leDoArq(this.arquivo);
        while (!eof()) {
            vet[nesimoAlgarismo(reg.getNumero(), algarismo)] ++;
            reg.leDoArq(this.arquivo);
        }
        vet[nesimoAlgarismo(reg.getNumero(), algarismo)] ++;

        for (i = 1; i < vet.length; i ++) {
            vet[i] += vet[i - 1];
        }

        return vet;
    }

    private void radixCountingSort(int algarismo) {
        Arquivo arq_aux = new Arquivo("arq_aux");
        Registro reg = new Registro(0);
        arq_aux.copiaArquivoInterno(this);

        int[] vet = vetorCount(algarismo);
        int len = filesize(), i;

        for (i = len - 1; i >= 0; i --) {
            arq_aux.seekArq(i);
            reg.leDoArq(arq_aux.arquivo);

            vet[nesimoAlgarismo(reg.getNumero(), algarismo)] --;

            seekArq(vet[nesimoAlgarismo(reg.getNumero(), algarismo)]);
            reg.gravaNoArq(this.arquivo);

            // novamente, o algoritmo não usa comparações, somente algumas movimentações
            mov ++;
        }
    }

    public void radixSort() {
        int maior = max(), nAlgarismosMaior = numeroDeAlgarismos(maior), n;
        initMov(); initComp();

        for (n = 1; n <= nAlgarismosMaior; n ++) {
//            System.out.println(n);
            radixCountingSort(n);
        }
    }

    public void bucketSort() {
        // bucket sort originalmente foi pensado para números decimais entre 0 e 1
        // aqui estou adaptando para inteiros, gerando um bucket para cada intervalo de 10 valores do conjunto

        initMov(); initComp();

        int maior = max();
        int i, tamanhoVetor = (maior) / 10 + 1, pos;
        Arquivo[] vet = new Arquivo[tamanhoVetor];
        Registro reg = new Registro(0);

        System.out.println(tamanhoVetor);

        for (i = 0; i < tamanhoVetor; i ++) {
            String nome = "bucket" + i;
            vet[i] = new Arquivo(nome);
            vet[i].truncate(0);
        }

        seekArq(0);
        reg.leDoArq(this.arquivo);
        while (!eof()) {
            pos = reg.getNumero() / 10;
            reg.gravaNoArq(vet[pos].arquivo);

//            System.out.println(reg.getNumero());

            reg.leDoArq(this.arquivo);
        }

        pos = reg.getNumero() / 10;

        reg.gravaNoArq(vet[pos].arquivo);

//        System.out.println(reg.getNumero());

        seekArq(0);

        for (i = 0; i < tamanhoVetor; i ++) {
            vet[i].seekArq(0);
            vet[i].bubbleSortBucket();

            vet[i].seekArq(0);
            reg.leDoArq(vet[i].arquivo);
            while (!(vet[i].eof())) {
                reg.gravaNoArq(arquivo);
                reg.leDoArq(vet[i].arquivo);
            }
            reg.gravaNoArq(arquivo);
        }
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
        Registro reg = new Registro(0), reg_ant = new Registro(0);
        int i, j, numero_i;

        for (i = ini + 1; i < fim; i ++) {
            seekArq(i);
            reg.leDoArq(this.arquivo);
            numero_i = reg.getNumero();

            j = i - 1;
            seekArq(j);
            reg_ant.leDoArq(this.arquivo);

            while (j >= ini && reg_ant.getNumero() > numero_i) {
                comp ++;
                mov ++;
                // não precisa usar seekArq pois já está na posição correta (sempre uma pos a frente)
                reg_ant.gravaNoArq(this.arquivo);

                j --;
                if (j >= ini) {
                    seekArq(j);
                    reg_ant.leDoArq(this.arquivo);
                }
            }

            mov ++;
            seekArq(j + 1);
            reg.gravaNoArq(this.arquivo);
        }
    }

    private boolean isOrdenadoCresc(int ini, int fim) {
        boolean retorno = true;
        Registro reg = new Registro(0);
        int i = ini, valor_ant;

        seekArq(i);
        reg.leDoArq(this.arquivo);
        valor_ant = reg.getNumero();
        i ++;

        while (i < fim && retorno) {
            reg.leDoArq(this.arquivo);
            comp ++;

            if (reg.getNumero() < valor_ant)
                retorno = false;

            valor_ant = reg.getNumero();

            i ++;
        }

        return retorno;
    }

    private boolean isOrdenadoDecresc(int ini, int fim) {
        boolean retorno = true;
        Registro reg = new Registro(0);
        int i = ini, valor_ant;

        seekArq(i);
        reg.leDoArq(this.arquivo);
        valor_ant = reg.getNumero();
        i ++;

        while (i < fim && retorno) {
            comp ++;
            reg.leDoArq(this.arquivo);

            if (reg.getNumero() > valor_ant)
                retorno = false;

            valor_ant = reg.getNumero();

            i ++;
        }

        return retorno;
    }

    private void inverteArq(int ini, int fim) {
        Registro reg1 = new Registro(0), reg2 = new Registro(0);
        int i = ini, j = fim - 1;

        while (i < j) {
            // isso aqui é análogo a uma permutação, portanto:
            mov += 2;

            seekArq(i);
            reg1.leDoArq(this.arquivo);

            seekArq(j);
            reg2.leDoArq(this.arquivo);

            seekArq(j);
            reg1.gravaNoArq(this.arquivo);

            seekArq(i);
            reg2.gravaNoArq(this.arquivo);

            i ++;
            j --;
        }
    }

    public void timSort() {
        initMov(); initComp();

        int minRun = this.minRun(this.filesize()), inicio_atual = 0, tamanho_atual, len = filesize(), valor_ant;
        Registro reg = new Registro(0);
        PilhaPair pilha = new PilhaPair();
        NoPilha noPilha1, noPilha2;

        while (inicio_atual < len) {
            tamanho_atual = 0;

            while (tamanho_atual < minRun && inicio_atual + tamanho_atual < len) {
                tamanho_atual ++;
            }

            if (isOrdenadoCresc(inicio_atual, inicio_atual + tamanho_atual)) {
                // tem que continuar lendo até achar o ponto em que a sublista não estará ordenada
                seekArq(inicio_atual + tamanho_atual - 1);
                reg.leDoArq(this.arquivo);
                valor_ant = reg.getNumero();
                reg.leDoArq(this.arquivo);

                while (reg.getNumero() >= valor_ant && inicio_atual + tamanho_atual < len) {
                    comp ++;
                    valor_ant = reg.getNumero();
                    reg.leDoArq(this.arquivo);

                    tamanho_atual ++;
                }
            } else if (isOrdenadoDecresc(inicio_atual, inicio_atual + tamanho_atual)) {
                // mesma coisa
                seekArq(inicio_atual + tamanho_atual - 1);
                reg.leDoArq(this.arquivo);
                valor_ant = reg.getNumero();
                reg.leDoArq(this.arquivo);

                while (reg.getNumero() <= valor_ant && inicio_atual + tamanho_atual < len) {
                    comp ++;
                    valor_ant = reg.getNumero();
                    reg.leDoArq(this.arquivo);

                    tamanho_atual ++;
                }

                inverteArq(inicio_atual, inicio_atual + tamanho_atual);
            } else {
                // aqui vai ordenar usando timInsertionSort
                timInsertionSort(inicio_atual, inicio_atual + tamanho_atual);
            }

            // e aqui somente colocar na pilha os índices
            pilha.push(inicio_atual, inicio_atual + tamanho_atual);

            inicio_atual += tamanho_atual;
        }

        while (pilha.top() != null) {
            noPilha1 = pilha.pop();

            if (pilha.top() != null) {
                noPilha2 = pilha.pop();
                merge(noPilha2.getPair().getFirst(), noPilha2.getPair().getSecond(), noPilha1.getPair().getSecond());

                pilha.push(noPilha2.getPair().getFirst(), noPilha1.getPair().getSecond());
            }
        }
    }

    private void particionaSemPivo(int ini, int fim) {
        Registro reg_i = new Registro(0), reg_j = new Registro(0);
        int i = ini, j = fim;

        while (i < j) {
            seekArq(i);
            reg_i.leDoArq(this.arquivo);

            seekArq(j);
            reg_j.leDoArq(this.arquivo);

            while (i < j && reg_i.getNumero() <= reg_j.getNumero()) {
                comp ++;
                i ++;
                seekArq(i);
                reg_i.leDoArq(this.arquivo);
            }

            if (i < j) {
                // permutação, portanto:
                mov += 2;

                seekArq(i);
                reg_j.gravaNoArq(this.arquivo);

                seekArq(j);
                reg_i.gravaNoArq(this.arquivo);
            }

            while (i < j && reg_i.getNumero() <= reg_j.getNumero()) {
                comp ++;
                j --;
                seekArq(j);
                reg_j.leDoArq(this.arquivo);
            }

            if (i < j) {
                // permutação, portanto
                mov += 2;

                seekArq(i);
                reg_j.gravaNoArq(this.arquivo);

                seekArq(j);
                reg_i.gravaNoArq(this.arquivo);
            }
        }

        if (fim > j + 1)
            particionaSemPivo(i + 1, fim);
        if (ini < i - 1)
            particionaSemPivo(ini, i - 1);
    }

    public void quickSemPivo() {
        initMov(); initComp();
        int ini = 0, fim = filesize() - 1;

        particionaSemPivo(ini, fim);
    }

    private void particaoMergeSort() {
        int len = filesize(), i;
        Arquivo particao1 = new Arquivo("particao1");
        Arquivo particao2 = new Arquivo("particao2");

        Registro reg = new Registro(0);

        particao1.truncate(0);
        particao2.truncate(0);

        particao1.seekArq(0);
        particao2.seekArq(0);

        i = 0;
        seekArq(0);

        while (i < len / 2) {
            reg.leDoArq(this.arquivo);
            reg.gravaNoArq(particao1.arquivo);
            i ++;
            mov ++;
        }

        while (i < len) {
            reg.leDoArq(this.arquivo);
            reg.gravaNoArq(particao2.arquivo);
            i ++;
            mov ++;
        }
    }

    private void fusao(int seq) {
        Arquivo particao1 = new Arquivo("particao1");
        Arquivo particao2 = new Arquivo("particao2");

        int seq_inicial = seq, i, j, len = filesize();
        Registro reg_i = new Registro(0), reg_j = new Registro(0);

        i = j = 0;

        seekArq(0);
        particao1.seekArq(0);
        particao2.seekArq(0);

        reg_i.leDoArq(particao1.arquivo);
        reg_j.leDoArq(particao2.arquivo);

        while (seq <= len / 2) {

            while (i < seq && j < seq) {
                if (reg_i.getNumero() < reg_j.getNumero()) {
                    reg_i.gravaNoArq(this.arquivo);
                    reg_i.leDoArq(particao1.arquivo);
                    i ++;
                } else {
                    reg_j.gravaNoArq(this.arquivo);
                    reg_j.leDoArq(particao2.arquivo);
                    j ++;
                }
                comp ++;
                mov ++;
            }

            while (i < seq) {
                reg_i.gravaNoArq(this.arquivo);
                reg_i.leDoArq(particao1.arquivo);
                i ++;
                mov ++;
            }

            while (j < seq) {
                reg_j.gravaNoArq(this.arquivo);
                reg_j.leDoArq(particao2.arquivo);
                j ++;
                mov ++;
            }

            seq += seq_inicial;
        }
    }

    public void mergePrimeiraImplementacao() {

        initComp(); initMov();
        // só funciona para tamanhos múltiplos de 2
        int seq = 1, tl = filesize();

        while (seq < tl) {
            particaoMergeSort();
            fusao(seq);

            seq *= 2;
        }
    }

    public void mergeIterativo() {
        PilhaPair pilhaPair = new PilhaPair();
        Pair pair1, pair2;
        FilaPair filaPair = new FilaPair();
        int ini = 0, fim = this.filesize() - 1, meio;

        pilhaPair.push(ini, fim);

        while (pilhaPair.getInicio() != null) {
            pair1 = pilhaPair.pop().getPair();

            if (pair1.getFirst() == pair1.getSecond())
                filaPair.enqueue(pair1.getFirst(), pair1.getSecond());

            else {
                meio = (pair1.getFirst() + pair1.getSecond()) / 2;
                pilhaPair.push(meio + 1, pair1.getSecond());
                pilhaPair.push(pair1.getFirst(), meio);
            }
        }

        while (filaPair.getIni() != null) {
            pair1 = filaPair.dequeue();
            if (filaPair.getIni() != null) {
                pair2 = filaPair.dequeue();

                // isso aqui é feito para garantir que as sublistas mescladas sejam adjacentes
                if (pair1.getSecond() + 1 != pair2.getFirst()) {
                    filaPair.enqueue(pair1.getFirst(), pair1.getSecond());
                    pair1 = pair2;
                    pair2 = filaPair.dequeue();
                }

                fusaoIterativo(pair1, pair2);

                filaPair.enqueue(pair1.getFirst(), pair2.getSecond());
            }
        }

    }

    public void fusaoIterativo(Pair pair1, Pair pair2) {
        Arquivo arq_aux = new Arquivo("arq_aux");
        arq_aux.seekArq(0);
        Registro reg_i = new Registro(0), reg_j = new Registro(0);
        int i = pair1.getFirst(), j = pair2.getFirst();

        while (i <= pair1.getSecond() && j <= pair2.getSecond()) {
            seekArq(i);
            reg_i.leDoArq(this.arquivo);

            seekArq(j);
            reg_j.leDoArq(this.arquivo);

            if (reg_i.getNumero() < reg_j.getNumero()) {
                reg_i.gravaNoArq(arq_aux.arquivo);
                i ++;
            } else {
                reg_j.gravaNoArq(arq_aux.arquivo);
                j ++;
            }
        }

        seekArq(i);
        while (i <= pair1.getSecond()) {
            reg_i.leDoArq(this.arquivo);
            reg_i.gravaNoArq(arq_aux.arquivo);
            i ++;
        }

        seekArq(j);
        while (j <= pair2.getSecond()) {
            reg_j.leDoArq(this.arquivo);
            reg_j.gravaNoArq(arq_aux.arquivo);
            j ++;
        }

        arq_aux.seekArq(0);
        i = pair1.getFirst();
        seekArq(i);

        while (i <= pair2.getSecond()) {
            reg_i.leDoArq(arq_aux.arquivo);
            reg_i.gravaNoArq(this.arquivo);

            i ++;
        }
    }

    public void geraArquivoOrdenado(int nRegistros) {
        for (int i = 0; i < nRegistros; i ++) {
            Registro reg = new Registro(i);
            reg.gravaNoArq(this.arquivo);
        }
        this.truncate(nRegistros);
    }

    public void geraArquivoReverso(int nRegistros) {
        for (int i = nRegistros - 1; i >= 0; i --) {
            Registro reg = new Registro(i);
            reg.gravaNoArq(this.arquivo);
        }
        this.truncate(nRegistros);
    }

    public void geraArquivoRandomico(int nRegistros) {
        int[] vet = new int[nRegistros];
        int temp, random_int;
        Random rand = new Random();
        Registro reg = new Registro(0);

        for (int i = 0; i < nRegistros; i ++) {
            vet[i] = i;
        }

        for (int i = 0; i < nRegistros; i ++) {
            random_int = rand.nextInt(nRegistros);

            temp = vet[random_int];
            vet[random_int] = vet[i];
            vet[i] = temp;
        }

        for (int ele: vet) {
            reg.setNumero(ele);
            reg.gravaNoArq(this.arquivo);
        }
        this.truncate(nRegistros);
    }
}
