# Gerenciamento de Processos
Segundo trabalho da disciplina de Sistemas Operacionais.
## Objetivo
Desenvolver um simulador que mostre como um SO gerencia seus processos aplicando conceitos de Time Slice e algoritmos de agendamento de processos.
## Algoritmos
- FIFO
- Agendamento por Prioridade
- Shortest Job First
## Compilação e Execução
    javac Principal.java
    java Principal
## GUI
A interface e o trabalho foram construídos puramente em Java 8 e JavaFX.
Num SO os processos transitam entre os estados de Inicializando, Pronto, Executando, Bloqueado e Finalizado a depender da sua atividade corrente.
![Estados](https://www.boscojr.com/so/figuras/estados-processos.png)
Dessa forma o simulador irá dispor os processos em listas onde é possivel ver o seu progresso até serem concluídos. 
![Software](/img/readme/print.png)

