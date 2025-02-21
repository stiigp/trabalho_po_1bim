public class Pair {
    /**
     * essa classe será usada no timSort dentro de uma pilha, onde o int first representará a coord inicial do run
     * e o int second representará o tamanho do run
     * */
    private int first, second;

    public Pair(int first, int second) {
        this.first = first;
        this.second = second;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }
}
