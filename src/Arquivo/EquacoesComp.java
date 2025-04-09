package Arquivo;

public class EquacoesComp {
    public static int insertionMelhor(int n) {
        return n;
    }

    public static int insertionMedio(int n) {
        return (int) Math.pow(n, 2);
    }

    public static int insertionPior(int n) {
        return (int) Math.pow(n, 2);
    }

    public static int insercaoBinariaMelhor(int n) {
        int log2;

        log2 = (int) (Math.log(n) / Math.log(2));

        return n * log2;
    }

    public static int insercaoBinariaMedio(int n) {
        return (int) Math.pow(n, 2);
    }

    public static int insercaoBinariaPior(int n) {
        return (int) Math.pow(n, 2);
    }

    public static int selectionMelhor(int n) {
        return (int) Math.pow(n, 2);
    }

    public static int selectionMedio(int n) {
        return (int) Math.pow(n, 2);
    }

    public static int selectionPior(int n) {
        return (int) Math.pow(n, 2);
    }

    public static int bubbleMelhor(int n) {
        return n;
    }

    public static int bubbleMedio(int n) {
        return (int) Math.pow(n, 2);
    }

    public static int bubblePior(int n) {
        return (int) Math.pow(n, 2);
    }

    public static int shakerMelhor(int n) {
        return n;
    }

    public static int shakerMedio(int n) {
        return (int) Math.pow(n, 2);
    }

    public static int shakerPior(int n) {
        return (int) Math.pow(n, 2);
    }
}
