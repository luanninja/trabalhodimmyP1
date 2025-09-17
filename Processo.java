public class Processo {
    private int id;
    private String nome;
    private int prioridade;
    private int ciclosNecessarios;
    private String recursoNecessario;
    private boolean discoSolicitadoAntes;

    public Processo(int id, String nome, int prioridade, int ciclosNecessarios, String recursoNecessario) {
        this.id = id;
        this.nome = nome;
        this.prioridade = prioridade;
        this.ciclosNecessarios = ciclosNecessarios;
        this.recursoNecessario = (recursoNecessario == null || recursoNecessario.trim().isEmpty()) ? null : recursoNecessario.trim().toUpperCase();
        this.discoSolicitadoAntes = false;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public int getPrioridade() { return prioridade; }
    public int getCiclosNecessarios() { return ciclosNecessarios; }
    public String getRecursoNecessario() { return recursoNecessario; }
    public boolean isDiscoSolicitadoAntes() { return discoSolicitadoAntes; }

    public void decrementarCiclo() { if (ciclosNecessarios > 0) ciclosNecessarios--; }
    public void setRecursoNecessario(String r) { recursoNecessario = (r == null || r.trim().isEmpty()) ? null : r.trim().toUpperCase(); }
    public void marcarDiscoSolicitado() { discoSolicitadoAntes = true; }
    public void limparSolicitacaoDisco() { discoSolicitadoAntes = false; recursoNecessario = null; }

    @Override
    public String toString() {
        return String.format("P{id:%d,n:%s,pr:%d,cic:%d,rec:%s,reqBefore:%b}", id, nome, prioridade, ciclosNecessarios, recursoNecessario == null ? "-" : recursoNecessario, discoSolicitadoAntes);
    }
}