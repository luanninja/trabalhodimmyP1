import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Uso: java Main <arquivo.csv>");
            return;
        }
        String arquivo = args[0];
        Scheduler scheduler = new Scheduler();

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            int lineNo = 0;
            while ((linha = br.readLine()) != null) {
                lineNo++;
                if (linha.trim().isEmpty() || linha.startsWith("#")) continue;
                String[] parts = linha.split(",");
                if (parts.length < 4) continue;
                try {
                    int id = Integer.parseInt(parts[0].trim());
                    String nome = parts[1].trim();
                    int prioridade = Integer.parseInt(parts[2].trim());
                    int ciclos = Integer.parseInt(parts[3].trim());
                    String recurso = parts.length >= 5 ? parts[4].trim() : null;
                    Processo p = new Processo(id, nome, prioridade, ciclos, recurso);
                    switch (prioridade) {
                        case 1: scheduler.getListaAlta().addEnd(p); break;
                        case 2: scheduler.getListaMedia().addEnd(p); break;
                        case 3: scheduler.getListaBaixa().addEnd(p); break;
                    }
                } catch (Exception e) {
                    System.out.println("Erro linha " + lineNo + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Erro arquivo: " + e.getMessage());
            return;
        }

        int maxCiclos = 2000;
        for (int i = 0; i < maxCiclos; i++) {
            boolean vazio = scheduler.getListaAlta().isEmpty() && scheduler.getListaMedia().isEmpty() && scheduler.getListaBaixa().isEmpty() && scheduler.getListaBloqueados().isEmpty();
            if (vazio) {
                System.out.println("Todas filas vazias. Encerrando.");
                break;
            }
            scheduler.executarCicloDeCPU();
        }
    }
}