# iCEVOS - Escalonador (Trabalho P1)

## Estrutura do Projeto
- codigo/ (arquivos .java)
- processos.csv
- relatorio_analise.pdf

## Como Compilar
cd codigo
javac *.java

## Como Executar
java Main ../processos.csv

## Observações Importantes
- Não usa ArrayList/LinkedList prontas.
- Os logs são exibidos de forma detalhada no console.
- O sistema inclui mecanismos de anti-inanição e também trata o bloqueio de acesso ao DISCO.
