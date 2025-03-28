public class NoPilhaNoLista {
    NoLista no;
    NoPilhaNoLista prox;

    public NoPilhaNoLista (NoLista no) {
        this.no = no;
        this.prox = null;
    }

    public NoLista getNo() {
        return no;
    }

    public void setNo(NoLista no) {
        this.no = no;
    }

    public NoPilhaNoLista getProx() {
        return prox;
    }

    public void setProx(NoPilhaNoLista prox) {
        this.prox = prox;
    }
}
