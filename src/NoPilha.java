public class NoPilha {
    private Pair pair;
    private NoPilha prox;

    public NoPilha(Pair pair, NoPilha prox) {
        this.pair = pair;
        this.prox = prox;
    }

    public Pair getPair() {
        return pair;
    }

    public void setPair(Pair pair) {
        this.pair = pair;
    }

    public NoPilha getProx() {
        return prox;
    }

    public void setProx(NoPilha prox) {
        this.prox = prox;
    }
}
