public class Scheduler {
    private ListaDeProcessos listaAlta;
    private ListaDeProcessos listaMedia;
    private ListaDeProcessos listaBaixa;
    private ListaDeProcessos listaBloqueados;
    private int contadorCiclosAltaSequencia;
    private int cicloAtual;

    public Scheduler() {
        listaAlta = new ListaDeProcessos();
        listaMedia = new ListaDeProcessos();
        listaBaixa = new ListaDeProcessos();
        listaBloqueados = new ListaDeProcessos();
        contadorCiclosAltaSequencia = 0;
        cicloAtual = 0;
    }

    public ListaDeProcessos getListaAlta() { return listaAlta; }
    public ListaDeProcessos getListaMedia() { return listaMedia; }
    public ListaDeProcessos getListaBaixa() { return listaBaixa; }
    public ListaDeProcessos getListaBloqueados() { return listaBloqueados; }

    public void executarCicloDeCPU() {
        cicloAtual++;
        System.out.println("=== Ciclo " + cicloAtual + " ===");

        Processo desbloqueado = listaBloqueados.removeFront();
        if (desbloqueado != null) {
            desbloqueado.limparSolicitacaoDisco();
            switch (desbloqueado.getPrioridade()) {
                case 1: listaAlta.addEnd(desbloqueado); break;
                case 2: listaMedia.addEnd(desbloqueado); break;
                case 3: listaBaixa.addEnd(desbloqueado); break;
            }
            System.out.println("Evento: processo desbloqueado: " + desbloqueado);
        }

        Processo aExecutar = null;
        int origemPrioridade = -1;

        if (contadorCiclosAltaSequencia >= 5) {
            if (!listaMedia.isEmpty()) {
                aExecutar = listaMedia.removeFront();
                origemPrioridade = 2;
                System.out.println("Regra anti-inanição: executando média.");
                contadorCiclosAltaSequencia = 0;
            } else if (!listaBaixa.isEmpty()) {
                aExecutar = listaBaixa.removeFront();
                origemPrioridade = 3;
                System.out.println("Regra anti-inanição: executando baixa.");
                contadorCiclosAltaSequencia = 0;
            } else {
                contadorCiclosAltaSequencia = 0;
            }
        }

        if (aExecutar == null) {
            if (!listaAlta.isEmpty()) {
                aExecutar = listaAlta.removeFront();
                origemPrioridade = 1;
            } else if (!listaMedia.isEmpty()) {
                aExecutar = listaMedia.removeFront();
                origemPrioridade = 2;
            } else if (!listaBaixa.isEmpty()) {
                aExecutar = listaBaixa.removeFront();
                origemPrioridade = 3;
            }
        }

        System.out.println("Filas antes da execução:");
        System.out.println("Alta: " + (listaAlta.isEmpty() ? "-" : listaAlta.listarProcessos()));
        System.out.println("Media: " + (listaMedia.isEmpty() ? "-" : listaMedia.listarProcessos()));
        System.out.println("Baixa: " + (listaBaixa.isEmpty() ? "-" : listaBaixa.listarProcessos()));
        System.out.println("Bloqueados: " + (listaBloqueados.isEmpty() ? "-" : listaBloqueados.listarProcessos()));

        if (aExecutar == null) {
            System.out.println("Nenhum processo para executar.");
            return;
        }

        System.out.println("-> Executando: " + aExecutar);

        if ("DISCO".equals(aExecutar.getRecursoNecessario()) && !aExecutar.isDiscoSolicitadoAntes()) {
            aExecutar.marcarDiscoSolicitado();
            listaBloqueados.addEnd(aExecutar);
            System.out.println("Evento: bloqueado aguardando DISCO: " + aExecutar);
            return;
        }

        aExecutar.decrementarCiclo();
        System.out.println("Execução: " + aExecutar.getNome() + " ciclos restantes: " + aExecutar.getCiclosNecessarios());

        if (aExecutar.getCiclosNecessarios() <= 0) {
            System.out.println("Evento: processo finalizado: " + aExecutar);
            if (origemPrioridade == 1) contadorCiclosAltaSequencia++;
            else contadorCiclosAltaSequencia = 0;
        } else {
            switch (origemPrioridade) {
                case 1: listaAlta.addEnd(aExecutar); contadorCiclosAltaSequencia++; break;
                case 2: listaMedia.addEnd(aExecutar); contadorCiclosAltaSequencia = 0; break;
                case 3: listaBaixa.addEnd(aExecutar); contadorCiclosAltaSequencia = 0; break;
            }
        }
    }
}