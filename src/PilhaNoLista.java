public class PilhaNoLista {
    NoPilhaNoLista topo;

    public PilhaNoLista() {
        this.topo = null;
    }

    public void push(NoLista noLista) {
        NoPilhaNoLista no = new NoPilhaNoLista(noLista);

        no.setProx(this.topo);
        this.topo = no;
    }

    public NoLista top() {
        NoLista no = new NoLista(this.topo.getNo().getValor());
        no.setProx(this.topo.getNo().getProx());
        no.setAnt(this.topo.getNo().getAnt());

        return no;
    }

    public NoLista pop() {
        NoLista no = this.topo.getNo();

        this.topo = topo.getProx();

        return no;
    }

    public boolean isEmpty() {
        return this.topo == null;
    }

}
