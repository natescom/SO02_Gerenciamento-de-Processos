package model;

import gui.BCPUtil;
import gui.ManagerGui;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Slider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/****************************************************************
 * Autor: Nathan Ferraz da Silva
 * Matricula: 201911925
 * Inicio: 10/08/2022
 * Ultima alteracao: 14/08/2022
 * Nome: ThreadManager
 * Funcao: Organiza os processos
 * **************************************************************
 */
public class ThreadManager extends Thread {
  private Slider timeSlicing;
  private ArrayList<BCP> threadsProntas;
  private ArrayList<BCP> threadsBloqueadas;
  private List<BCP> threadsInicializando;
  private Agendador agendador;
  private ManagerGui gui;
  private int contadorBlock = 0;
  private final Random random;
  private BCP currentThread;

  public ThreadManager(Slider timeSlicing, List<BCP> threads, Agendador agendador, ManagerGui gui) {
    this.timeSlicing = timeSlicing;
    this.threadsProntas = new ArrayList<>();
    threadsInicializando = threads;
    this.agendador = agendador;
    this.gui = gui;
    random = new Random();
    threadsBloqueadas = new ArrayList<>();
  }

  @Override
  public void run() {
    int guiPause = 300;
    while (true) {
      try {
        sleep(guiPause);
        if (threadsInicializando.isEmpty() && threadsProntas.isEmpty() && threadsBloqueadas.isEmpty())
          break;

        if (!threadsInicializando.isEmpty()) {
          threadsProntas.addAll(threadsInicializando);
          threadsProntas = (ArrayList<BCP>) agendador.order(threadsProntas);
          threadsInicializando.clear();
          /* GUI */Platform.runLater(() -> {
            gui.getVb_inicio().getChildren().clear();
            gui.getVb_pronto().getChildren().clear();
            threadsProntas.forEach(bcp -> BCPUtil.publishViewProcess(bcp, gui.getVb_pronto(), agendador.getType()));
          });
          sleep(guiPause);
        }

        if (!threadsProntas.isEmpty()) {
          currentThread = threadsProntas.get(0);
          threadsProntas.remove(0);

          /* GUI */ Platform.runLater(() -> {
            ObservableList<Node> lista = gui.getVb_pronto().getChildren();
            Node o = lista.get(0);
            lista.remove(0);
            gui.getVb_exec().getChildren().add(o);
          });
          sleep(guiPause);
        }

        if (currentThread != null)
          if (!currentThread.isStarted())
            currentThread.start();
          else
            currentThread.myresume();

        TimeUnit.MILLISECONDS.sleep((long) (timeSlicing.getValue() * 1000));

        if (!threadsBloqueadas.isEmpty())
          contadorBlock++;

        if (currentThread != null)
          if (currentThread.isAlive()) {
            currentThread.mysuspend();
            boolean vaiSerBloq = random.nextBoolean();
            if (vaiSerBloq) {
              threadsBloqueadas.add(currentThread);
              currentThread = null;
              /* GUI */ Platform.runLater(() -> {
                ObservableList<Node> list_exec = gui.getVb_exec().getChildren();
                Node process = list_exec.get(0);
                list_exec.remove(0);
                gui.getVb_bloq().getChildren().add(process);
              });
              sleep(guiPause);
            } else {
              threadsProntas = (ArrayList<BCP>) agendador.addElement(threadsProntas, currentThread);
              /* GUI */ Platform.runLater(() -> {
                ObservableList<Node> list_exec = gui.getVb_exec().getChildren();
                Node process = list_exec.get(0);
                list_exec.remove(0);
                gui.getVb_pronto().getChildren().add(0, process);
              });
              sleep(guiPause);
            }
          } else {
            /* GUI */ Platform.runLater(() -> {
              ObservableList<Node> listObservavel = gui.getVb_exec().getChildren();
              if (listObservavel.isEmpty())
                return;
              Node process = listObservavel.get(0);
              listObservavel.remove(0);
              gui.getVb_final().getChildren().add(0, process); // TODO: NO PROXIMO TRAB EU PRECISO SABER A POCISAO CERTA
            });
            sleep(guiPause);
          }

        if (!threadsBloqueadas.isEmpty() && contadorBlock == 2) {
          BCP bcp = threadsBloqueadas.get(0);
          threadsProntas = (ArrayList<BCP>) agendador.addElement(threadsProntas, bcp);
          threadsBloqueadas.remove(0);
          contadorBlock = 0;
          int indexOfBcp = threadsProntas.indexOf(bcp);
          /* GUI */ Platform.runLater(() -> {
            ObservableList<Node> list_bloq = gui.getVb_bloq().getChildren();
            Node processView = list_bloq.get(0);
            list_bloq.remove(0);
            gui.getVb_pronto().getChildren().add(indexOfBcp, processView);
          });
          sleep(guiPause);
        }

      } catch (InterruptedException e) {
        // e.printStackTrace();
      }
    }
    System.out.println("ACABOU");
  }

  @Override
  public void interrupt() {
    try {
      threadsProntas.forEach(t -> {
        t.interrupt();
      });
      threadsBloqueadas.forEach(t -> {
        t.interrupt();
      });
      threadsInicializando.forEach(t -> t.interrupt());
      currentThread.interrupt();
      threadsProntas = null;
      threadsBloqueadas = null;
      threadsInicializando = null;
      currentThread = null;
    } catch (Exception e) {
      // TODO: handle exception
    }
    super.interrupt();
  }

}

/*
 * 1) Tem processo pra ser inicializado?
 * SIM: Manda pra lista de pronto
 * 
 * 2) Tem processo pronto?
 * SIM: Tira da lista de processos e inicia
 * 
 * 3) Acabou o timeslicing: O processo terminou a execução?
 * SIM: Tira da lista de processos e Coloca na lista de finalizados
 * NÃO: Pausa
 * 
 * 4) Lista de bloq é vazia?
 * NÃO: Contador ++
 * 
 * 5) Processo vai ser bloqueado?
 * SIM: Manda pra lista de bloq e inicia um contador
 * NÃO: Coloca na lista de processos
 * 
 * 6) Contador é igual a 2? // POSSO COLOCAR UM NUMERO ALEATORIO
 * SIM: Tira o processo de bloq e coloca em pronto e reinicia o contador
 * 
 * 7) Tem processo na lista de inicialização, de processos e de bloqueado?
 * NÃO: Finaliza o programa
 * 
 * Atenção: Talvez seja necessário proteger o acesso a lista de inicialização
 */
