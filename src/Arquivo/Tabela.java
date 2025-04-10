package Arquivo;

import Arquivo.Arquivo;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

public class Tabela {
    RandomAccessFile tabela, backup;
    final int TAMANHO_ARQUIVO = 1024;

    public Tabela() {
        try {
            tabela = new RandomAccessFile("tabela.txt", "rw");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void zeraTabela() {
        try {
            tabela = new RandomAccessFile("tabela.txt", "rw");
            tabela.seek(0);
            tabela.setLength(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void gravaLinha(RandomAccessFile arq, int compProgOrd, int compEquaOrd, int movProgOrd, int movEquaOrd, int tempoOrd, int compProgRev, int compEquaRev, int movProgRev, int movEquaRev, int tempoRev, int compProgRand, int compEquaRand, int movProgRand, int movEquaRand, int tempoRand) {
        long len;
        try {
            len = arq.length();
            arq.seek((int) len);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String linha = String.format("%12d | %11d | %9d | %9d | %5d | %12d | %11d | %9d | %9d | %5d | %13d | %11d | %9d | %9d | %5d\n",
                compProgOrd, compEquaOrd, movProgOrd, movEquaOrd, tempoOrd,
                compProgRev, compEquaRev, movProgRev, movEquaRev, tempoRev,
                compProgRand, compEquaRand, movProgRand, movEquaRand, tempoRand);


        try {
            arq.writeBytes(linha);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void gravaCabecalho(RandomAccessFile arq) {
        try {
            arq.writeBytes(String.format(
                    "%-55s| %-55s| %-55s\n",
                    "arq ordenado", "arq reverso", "arq randômico"));
            arq.writeBytes(String.format(
                    "%12s | %11s | %9s | %9s | %5s | %12s | %11s | %9s | %9s | %5s | %13s | %11s | %9s | %9s | %5s\n",
                    "comp. prog", "comp. equa", "mov prog", "mov equa", "tempo",
                    "comp. prog", "comp. equa", "mov prog", "mov equa", "tempo",
                    "comp. prog", "comp. equa", "mov prog", "mov equa", "tempo"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void geraTabela() {
        long tini, tfim;
        int tOrd, tRev, tRand, compProgOrd, movProgOrd, compProgRev, movProgRev, compProgRand, movProgRand;
        int compEquaOrd, movEquaOrd, compEquaRev, movEquaRev, compEquaRand, movEquaRand;

        zeraTabela();
        gravaCabecalho(tabela);

        Arquivo arqRand = new Arquivo("arquivoRand");
        Arquivo auxRand = new Arquivo("arquivoAuxRand");
        Arquivo arqRev = new Arquivo("arquivoReverso");
        Arquivo auxRev = new Arquivo("arquivoAuxReverso");
        Arquivo arqOrd = new Arquivo("arquivoOrdenado");

        arqRand.geraArquivoRandomico(TAMANHO_ARQUIVO);
        arqRev.geraArquivoReverso(TAMANHO_ARQUIVO);
        arqOrd.geraArquivoOrdenado(TAMANHO_ARQUIVO);

        // INSERTION SORT
        auxRand.copiaArquivo(arqRand.getFile());
        auxRev.copiaArquivo(arqRev.getFile());

        tini = System.currentTimeMillis();
        arqOrd.insercaoDireta();
        tfim = System.currentTimeMillis();
        tOrd = (int) ((tfim - tini) / 1000);

        tini = System.currentTimeMillis();
        auxRev.insercaoDireta();
        tfim = System.currentTimeMillis();
        tRev = (int) ((tfim - tini) / 1000);

        tini = System.currentTimeMillis();
        auxRand.insercaoDireta();
        tfim = System.currentTimeMillis();
        tRand = (int) ((tfim - tini) / 1000);

        compProgOrd = arqOrd.getComp();
        movProgOrd = arqOrd.getMov();
        compProgRev = auxRev.getComp();
        movProgRev = auxRev.getMov();
        compProgRand = auxRand.getComp();
        movProgRand = auxRand.getMov();

        compEquaOrd = EquacoesComp.insertionMelhor(TAMANHO_ARQUIVO);
        movEquaOrd = EquacoesMov.insertionMelhor(TAMANHO_ARQUIVO);

        compEquaRev = EquacoesComp.insertionPior(TAMANHO_ARQUIVO);
        movEquaRev = EquacoesMov.insertionPior(TAMANHO_ARQUIVO);

        compEquaRand = EquacoesComp.insertionMedio(TAMANHO_ARQUIVO);
        movEquaRand = EquacoesMov.insertionMedio(TAMANHO_ARQUIVO);

        gravaLinha(tabela, compProgOrd, compEquaOrd, movProgOrd, movEquaOrd, tOrd, compProgRev, compEquaRev, movProgRev, movEquaRev, tRev, compProgRand, compEquaRand, movProgRand, movEquaRand, tRand);

        // INSERTION BINARIA
        auxRand.copiaArquivo(arqRand.getFile());
        auxRev.copiaArquivo(arqRev.getFile());

        tini = System.currentTimeMillis();
        arqOrd.binaryInsertionSort();
        tfim = System.currentTimeMillis();
        tOrd = (int) ((tfim - tini) / 1000);

        tini = System.currentTimeMillis();
        auxRev.binaryInsertionSort();
        tfim = System.currentTimeMillis();
        tRev = (int) ((tfim - tini) / 1000);

        tini = System.currentTimeMillis();
        auxRand.binaryInsertionSort();
        tfim = System.currentTimeMillis();
        tRand = (int) ((tfim - tini) / 1000);

        compProgOrd = arqOrd.getComp();
        movProgOrd = arqOrd.getMov();
        compProgRev = auxRev.getComp();
        movProgRev = auxRev.getMov();
        compProgRand = auxRand.getComp();
        movProgRand = auxRand.getMov();

        compEquaOrd = EquacoesComp.insercaoBinariaMelhor(TAMANHO_ARQUIVO);
        movEquaOrd = EquacoesMov.insercaoBinariaMelhor(TAMANHO_ARQUIVO);

        compEquaRev = EquacoesComp.insercaoBinariaPior(TAMANHO_ARQUIVO);
        movEquaRev = EquacoesMov.insercaoBinariaPior(TAMANHO_ARQUIVO);

        compEquaRand = EquacoesComp.insercaoBinariaMedio(TAMANHO_ARQUIVO);
        movEquaRand = EquacoesMov.insercaoBinariaMedio(TAMANHO_ARQUIVO);

        gravaLinha(tabela, compProgOrd, compEquaOrd, movProgOrd, movEquaOrd, tOrd, compProgRev, compEquaRev, movProgRev, movEquaRev, tRev, compProgRand, compEquaRand, movProgRand, movEquaRand, tRand);

        // SELECTION
        auxRand.copiaArquivo(arqRand.getFile());
        auxRev.copiaArquivo(arqRev.getFile());

        tini = System.currentTimeMillis();
        arqOrd.selectionSort();
        tfim = System.currentTimeMillis();
        tOrd = (int) ((tfim - tini) / 1000);

        tini = System.currentTimeMillis();
        auxRev.selectionSort();
        tfim = System.currentTimeMillis();
        tRev = (int) ((tfim - tini) / 1000);

        tini = System.currentTimeMillis();
        auxRand.selectionSort();
        tfim = System.currentTimeMillis();
        tRand = (int) ((tfim - tini) / 1000);

        compProgOrd = arqOrd.getComp();
        movProgOrd = arqOrd.getMov();
        compProgRev = auxRev.getComp();
        movProgRev = auxRev.getMov();
        compProgRand = auxRand.getComp();
        movProgRand = auxRand.getMov();

        compEquaOrd = EquacoesComp.selectionMelhor(TAMANHO_ARQUIVO);
        movEquaOrd = EquacoesMov.selectionMelhor(TAMANHO_ARQUIVO);

        compEquaRev = EquacoesComp.selectionPior(TAMANHO_ARQUIVO);
        movEquaRev = EquacoesMov.selectionPior(TAMANHO_ARQUIVO);

        compEquaRand = EquacoesComp.selectionMedio(TAMANHO_ARQUIVO);
        movEquaRand = EquacoesMov.selectionMedio(TAMANHO_ARQUIVO);

        gravaLinha(tabela, compProgOrd, compEquaOrd, movProgOrd, movEquaOrd, tOrd, compProgRev, compEquaRev, movProgRev, movEquaRev, tRev, compProgRand, compEquaRand, movProgRand, movEquaRand, tRand);

        // BUBBLE
        auxRand.copiaArquivo(arqRand.getFile());
        auxRev.copiaArquivo(arqRev.getFile());

        tini = System.currentTimeMillis();
        arqOrd.bubbleSort();
        tfim = System.currentTimeMillis();
        tOrd = (int) ((tfim - tini) / 1000);

        tini = System.currentTimeMillis();
        auxRev.bubbleSort();
        tfim = System.currentTimeMillis();
        tRev = (int) ((tfim - tini) / 1000);

        tini = System.currentTimeMillis();
        auxRand.bubbleSort();
        tfim = System.currentTimeMillis();
        tRand = (int) ((tfim - tini) / 1000);

        compProgOrd = arqOrd.getComp();
        movProgOrd = arqOrd.getMov();
        compProgRev = auxRev.getComp();
        movProgRev = auxRev.getMov();
        compProgRand = auxRand.getComp();
        movProgRand = auxRand.getMov();

        compEquaOrd = EquacoesComp.bubbleMelhor(TAMANHO_ARQUIVO);
        movEquaOrd = EquacoesMov.bubbleMelhor(TAMANHO_ARQUIVO);

        compEquaRev = EquacoesComp.bubblePior(TAMANHO_ARQUIVO);
        movEquaRev = EquacoesMov.bubblePior(TAMANHO_ARQUIVO);

        compEquaRand = EquacoesComp.bubbleMedio(TAMANHO_ARQUIVO);
        movEquaRand = EquacoesMov.bubbleMedio(TAMANHO_ARQUIVO);

        gravaLinha(tabela, compProgOrd, compEquaOrd, movProgOrd, movEquaOrd, tOrd, compProgRev, compEquaRev, movProgRev, movEquaRev, tRev, compProgRand, compEquaRand, movProgRand, movEquaRand, tRand);

        // SHAKER
        auxRand.copiaArquivo(arqRand.getFile());
        auxRev.copiaArquivo(arqRev.getFile());

        tini = System.currentTimeMillis();
        arqOrd.shakerSort();
        tfim = System.currentTimeMillis();
        tOrd = (int) ((tfim - tini) / 1000);

        tini = System.currentTimeMillis();
        auxRev.shakerSort();
        tfim = System.currentTimeMillis();
        tRev = (int) ((tfim - tini) / 1000);

        tini = System.currentTimeMillis();
        auxRand.shakerSort();
        tfim = System.currentTimeMillis();
        tRand = (int) ((tfim - tini) / 1000);

        compProgOrd = arqOrd.getComp();
        movProgOrd = arqOrd.getMov();
        compProgRev = auxRev.getComp();
        movProgRev = auxRev.getMov();
        compProgRand = auxRand.getComp();
        movProgRand = auxRand.getMov();

        compEquaOrd = EquacoesComp.shakerMelhor(TAMANHO_ARQUIVO);
        movEquaOrd = EquacoesMov.shakerMelhor(TAMANHO_ARQUIVO);

        compEquaRev = EquacoesComp.shakerPior(TAMANHO_ARQUIVO);
        movEquaRev = EquacoesMov.shakerPior(TAMANHO_ARQUIVO);

        compEquaRand = EquacoesComp.shakerMedio(TAMANHO_ARQUIVO);
        movEquaRand = EquacoesMov.shakerMedio(TAMANHO_ARQUIVO);

        gravaLinha(tabela, compProgOrd, compEquaOrd, movProgOrd, movEquaOrd, tOrd, compProgRev, compEquaRev, movProgRev, movEquaRev, tRev, compProgRand, compEquaRand, movProgRand, movEquaRand, tRand);

        // SHELL
        auxRand.copiaArquivo(arqRand.getFile());
        auxRev.copiaArquivo(arqRev.getFile());

        tini = System.currentTimeMillis();
        arqOrd.shellSort();
        tfim = System.currentTimeMillis();
        tOrd = (int) ((tfim - tini) / 1000);

        tini = System.currentTimeMillis();
        auxRev.shellSort();
        tfim = System.currentTimeMillis();
        tRev = (int) ((tfim - tini) / 1000);

        tini = System.currentTimeMillis();
        auxRand.shellSort();
        tfim = System.currentTimeMillis();
        tRand = (int) ((tfim - tini) / 1000);

        compProgOrd = arqOrd.getComp();
        movProgOrd = arqOrd.getMov();
        compProgRev = auxRev.getComp();
        movProgRev = auxRev.getMov();
        compProgRand = auxRand.getComp();
        movProgRand = auxRand.getMov();

        compEquaOrd = 0;
        movEquaOrd = 0;

        compEquaRev = 0;
        movEquaRev = 0;

        compEquaRand = 0;
        movEquaRand = 0;

        gravaLinha(tabela, compProgOrd, compEquaOrd, movProgOrd, movEquaOrd, tOrd, compProgRev, compEquaRev, movProgRev, movEquaRev, tRev, compProgRand, compEquaRand, movProgRand, movEquaRand, tRand);

        // HEAP
        auxRand.copiaArquivo(arqRand.getFile());
        auxRev.copiaArquivo(arqRev.getFile());

        tini = System.currentTimeMillis();
        arqOrd.heapSort();
        tfim = System.currentTimeMillis();
        tOrd = (int) ((tfim - tini) / 1000);

        tini = System.currentTimeMillis();
        auxRev.heapSort();
        tfim = System.currentTimeMillis();
        tRev = (int) ((tfim - tini) / 1000);

        tini = System.currentTimeMillis();
        auxRand.heapSort();
        tfim = System.currentTimeMillis();
        tRand = (int) ((tfim - tini) / 1000);

        compProgOrd = arqOrd.getComp();
        movProgOrd = arqOrd.getMov();
        compProgRev = auxRev.getComp();
        movProgRev = auxRev.getMov();
        compProgRand = auxRand.getComp();
        movProgRand = auxRand.getMov();

        gravaLinha(tabela, compProgOrd, compEquaOrd, movProgOrd, movEquaOrd, tOrd, compProgRev, compEquaRev, movProgRev, movEquaRev, tRev, compProgRand, compEquaRand, movProgRand, movEquaRand, tRand);

        // QUICK SP
        auxRand.copiaArquivo(arqRand.getFile());
        auxRev.copiaArquivo(arqRev.getFile());

        tini = System.currentTimeMillis();
        arqOrd.quickSemPivo();
        tfim = System.currentTimeMillis();
        tOrd = (int) ((tfim - tini) / 1000);

        tini = System.currentTimeMillis();
        auxRev.quickSemPivo();
        tfim = System.currentTimeMillis();
        tRev = (int) ((tfim - tini) / 1000);

        tini = System.currentTimeMillis();
        auxRand.quickSemPivo();
        tfim = System.currentTimeMillis();
        tRand = (int) ((tfim - tini) / 1000);

        compProgOrd = arqOrd.getComp();
        movProgOrd = arqOrd.getMov();
        compProgRev = auxRev.getComp();
        movProgRev = auxRev.getMov();
        compProgRand = auxRand.getComp();
        movProgRand = auxRand.getMov();

        gravaLinha(tabela, compProgOrd, compEquaOrd, movProgOrd, movEquaOrd, tOrd, compProgRev, compEquaRev, movProgRev, movEquaRev, tRev, compProgRand, compEquaRand, movProgRand, movEquaRand, tRand);

        // QUICK COM PIVO => TEM QUE INICIAR COMP E MOV ANTES DO ALGORITMO POIS ELE É RECURSIVO
        auxRand.copiaArquivo(arqRand.getFile());
        auxRev.copiaArquivo(arqRev.getFile());

        tini = System.currentTimeMillis();
        arqOrd.initMov(); arqOrd.initComp();
        arqOrd.quickSort(0, TAMANHO_ARQUIVO - 1);
        tfim = System.currentTimeMillis();
        tOrd = (int) ((tfim - tini) / 1000);

        tini = System.currentTimeMillis();
        auxRev.initMov(); auxRev.initComp();
        auxRev.quickSort(0, TAMANHO_ARQUIVO - 1);
        tfim = System.currentTimeMillis();
        tRev = (int) ((tfim - tini) / 1000);

        tini = System.currentTimeMillis();
        auxRand.initMov(); auxRand.initComp();
        auxRand.quickSort(0, TAMANHO_ARQUIVO - 1);
        tfim = System.currentTimeMillis();
        tRand = (int) ((tfim - tini) / 1000);

        compProgOrd = arqOrd.getComp();
        movProgOrd = arqOrd.getMov();
        compProgRev = auxRev.getComp();
        movProgRev = auxRev.getMov();
        compProgRand = auxRand.getComp();
        movProgRand = auxRand.getMov();

        gravaLinha(tabela, compProgOrd, compEquaOrd, movProgOrd, movEquaOrd, tOrd, compProgRev, compEquaRev, movProgRev, movEquaRev, tRev, compProgRand, compEquaRand, movProgRand, movEquaRand, tRand);

        // MERGE PRIMEIRA IMPLEMENTACAO
        auxRand.copiaArquivo(arqRand.getFile());
        auxRev.copiaArquivo(arqRev.getFile());

        tini = System.currentTimeMillis();
        arqOrd.mergePrimeiraImplementacao();
        tfim = System.currentTimeMillis();
        tOrd = (int) ((tfim - tini) / 1000);

        tini = System.currentTimeMillis();
        auxRev.mergePrimeiraImplementacao();
        tfim = System.currentTimeMillis();
        tRev = (int) ((tfim - tini) / 1000);

        tini = System.currentTimeMillis();
        auxRand.mergePrimeiraImplementacao();
        tfim = System.currentTimeMillis();
        tRand = (int) ((tfim - tini) / 1000);

        compProgOrd = arqOrd.getComp();
        movProgOrd = arqOrd.getMov();
        compProgRev = auxRev.getComp();
        movProgRev = auxRev.getMov();
        compProgRand = auxRand.getComp();
        movProgRand = auxRand.getMov();

        gravaLinha(tabela, compProgOrd, compEquaOrd, movProgOrd, movEquaOrd, tOrd, compProgRev, compEquaRev, movProgRev, movEquaRev, tRev, compProgRand, compEquaRand, movProgRand, movEquaRand, tRand);

        // MERGE SEGUNDA IMPLEMENTACAO => TEM QUE INICIAR COMP E MOV ANTES DO ALGORITMO POIS ELE É RECURSIVO
        auxRand.copiaArquivo(arqRand.getFile());
        auxRev.copiaArquivo(arqRev.getFile());

        tini = System.currentTimeMillis();
        arqOrd.initMov(); arqOrd.initComp();
        arqOrd.mergeSort(0, TAMANHO_ARQUIVO - 1);
        tfim = System.currentTimeMillis();
        tOrd = (int) ((tfim - tini) / 1000);

        tini = System.currentTimeMillis();
        auxRev.initMov(); auxRev.initComp();
        auxRev.mergeSort(0, TAMANHO_ARQUIVO - 1);
        tfim = System.currentTimeMillis();
        tRev = (int) ((tfim - tini) / 1000);

        tini = System.currentTimeMillis();
        auxRand.initMov(); auxRand.initComp();
        auxRand.mergeSort(0, TAMANHO_ARQUIVO - 1);
        tfim = System.currentTimeMillis();
        tRand = (int) ((tfim - tini) / 1000);

        compProgOrd = arqOrd.getComp();
        movProgOrd = arqOrd.getMov();
        compProgRev = auxRev.getComp();
        movProgRev = auxRev.getMov();
        compProgRand = auxRand.getComp();
        movProgRand = auxRand.getMov();

        gravaLinha(tabela, compProgOrd, compEquaOrd, movProgOrd, movEquaOrd, tOrd, compProgRev, compEquaRev, movProgRev, movEquaRev, tRev, compProgRand, compEquaRand, movProgRand, movEquaRand, tRand);

        // COUNTING
        auxRand.copiaArquivo(arqRand.getFile());
        auxRev.copiaArquivo(arqRev.getFile());

        tini = System.currentTimeMillis();
        arqOrd.countingSort();
        tfim = System.currentTimeMillis();
        tOrd = (int) ((tfim - tini) / 1000);

        tini = System.currentTimeMillis();
        auxRev.countingSort();
        tfim = System.currentTimeMillis();
        tRev = (int) ((tfim - tini) / 1000);

        tini = System.currentTimeMillis();
        auxRand.countingSort();
        tfim = System.currentTimeMillis();
        tRand = (int) ((tfim - tini) / 1000);

        compProgOrd = arqOrd.getComp();
        movProgOrd = arqOrd.getMov();
        compProgRev = auxRev.getComp();
        movProgRev = auxRev.getMov();
        compProgRand = auxRand.getComp();
        movProgRand = auxRand.getMov();

        gravaLinha(tabela, compProgOrd, compEquaOrd, movProgOrd, movEquaOrd, tOrd, compProgRev, compEquaRev, movProgRev, movEquaRev, tRev, compProgRand, compEquaRand, movProgRand, movEquaRand, tRand);

        // BUCKET
        auxRand.copiaArquivo(arqRand.getFile());
        auxRev.copiaArquivo(arqRev.getFile());

        tini = System.currentTimeMillis();
        arqOrd.bucketSort();
        tfim = System.currentTimeMillis();
        tOrd = (int) ((tfim - tini) / 1000);

        tini = System.currentTimeMillis();
        auxRev.bucketSort();
        tfim = System.currentTimeMillis();
        tRev = (int) ((tfim - tini) / 1000);

        tini = System.currentTimeMillis();
        auxRand.bucketSort();
        tfim = System.currentTimeMillis();
        tRand = (int) ((tfim - tini) / 1000);

        compProgOrd = arqOrd.getComp();
        movProgOrd = arqOrd.getMov();
        compProgRev = auxRev.getComp();
        movProgRev = auxRev.getMov();
        compProgRand = auxRand.getComp();
        movProgRand = auxRand.getMov();

        gravaLinha(tabela, compProgOrd, compEquaOrd, movProgOrd, movEquaOrd, tOrd, compProgRev, compEquaRev, movProgRev, movEquaRev, tRev, compProgRand, compEquaRand, movProgRand, movEquaRand, tRand);

        // RADIX
        auxRand.copiaArquivo(arqRand.getFile());
        auxRev.copiaArquivo(arqRev.getFile());

        tini = System.currentTimeMillis();
        arqOrd.radixSort();
        tfim = System.currentTimeMillis();
        tOrd = (int) ((tfim - tini) / 1000);

        tini = System.currentTimeMillis();
        auxRev.radixSort();
        tfim = System.currentTimeMillis();
        tRev = (int) ((tfim - tini) / 1000);

        tini = System.currentTimeMillis();
        auxRand.radixSort();
        tfim = System.currentTimeMillis();
        tRand = (int) ((tfim - tini) / 1000);

        compProgOrd = arqOrd.getComp();
        movProgOrd = arqOrd.getMov();
        compProgRev = auxRev.getComp();
        movProgRev = auxRev.getMov();
        compProgRand = auxRand.getComp();
        movProgRand = auxRand.getMov();

        gravaLinha(tabela, compProgOrd, compEquaOrd, movProgOrd, movEquaOrd, tOrd, compProgRev, compEquaRev, movProgRev, movEquaRev, tRev, compProgRand, compEquaRand, movProgRand, movEquaRand, tRand);

        // COMB
        auxRand.copiaArquivo(arqRand.getFile());
        auxRev.copiaArquivo(arqRev.getFile());

        tini = System.currentTimeMillis();
        arqOrd.combSort();
        tfim = System.currentTimeMillis();
        tOrd = (int) ((tfim - tini) / 1000);

        tini = System.currentTimeMillis();
        auxRev.combSort();
        tfim = System.currentTimeMillis();
        tRev = (int) ((tfim - tini) / 1000);

        tini = System.currentTimeMillis();
        auxRand.combSort();
        tfim = System.currentTimeMillis();
        tRand = (int) ((tfim - tini) / 1000);

        compProgOrd = arqOrd.getComp();
        movProgOrd = arqOrd.getMov();
        compProgRev = auxRev.getComp();
        movProgRev = auxRev.getMov();
        compProgRand = auxRand.getComp();
        movProgRand = auxRand.getMov();

        gravaLinha(tabela, compProgOrd, compEquaOrd, movProgOrd, movEquaOrd, tOrd, compProgRev, compEquaRev, movProgRev, movEquaRev, tRev, compProgRand, compEquaRand, movProgRand, movEquaRand, tRand);

        // GNOME SORT
        auxRand.copiaArquivo(arqRand.getFile());
        auxRev.copiaArquivo(arqRev.getFile());

        tini = System.currentTimeMillis();
        arqOrd.gnomeSort();
        tfim = System.currentTimeMillis();
        tOrd = (int) ((tfim - tini) / 1000);

        tini = System.currentTimeMillis();
        auxRev.gnomeSort();
        tfim = System.currentTimeMillis();
        tRev = (int) ((tfim - tini) / 1000);

        tini = System.currentTimeMillis();
        auxRand.gnomeSort();
        tfim = System.currentTimeMillis();
        tRand = (int) ((tfim - tini) / 1000);

        compProgOrd = arqOrd.getComp();
        movProgOrd = arqOrd.getMov();
        compProgRev = auxRev.getComp();
        movProgRev = auxRev.getMov();
        compProgRand = auxRand.getComp();
        movProgRand = auxRand.getMov();

        gravaLinha(tabela, compProgOrd, compEquaOrd, movProgOrd, movEquaOrd, tOrd, compProgRev, compEquaRev, movProgRev, movEquaRev, tRev, compProgRand, compEquaRand, movProgRand, movEquaRand, tRand);

        // TIMSORT
        auxRand.copiaArquivo(arqRand.getFile());
        auxRev.copiaArquivo(arqRev.getFile());

        tini = System.currentTimeMillis();
        arqOrd.timSort();
        tfim = System.currentTimeMillis();
        tOrd = (int) ((tfim - tini) / 1000);

        tini = System.currentTimeMillis();
        auxRev.timSort();
        tfim = System.currentTimeMillis();
        tRev = (int) ((tfim - tini) / 1000);

        tini = System.currentTimeMillis();
        auxRand.timSort();
        tfim = System.currentTimeMillis();
        tRand = (int) ((tfim - tini) / 1000);

        compProgOrd = arqOrd.getComp();
        movProgOrd = arqOrd.getMov();
        compProgRev = auxRev.getComp();
        movProgRev = auxRev.getMov();
        compProgRand = auxRand.getComp();
        movProgRand = auxRand.getMov();

        gravaLinha(tabela, compProgOrd, compEquaOrd, movProgOrd, movEquaOrd, tOrd, compProgRev, compEquaRev, movProgRev, movEquaRev, tRev, compProgRand, compEquaRand, movProgRand, movEquaRand, tRand);
    }

}
