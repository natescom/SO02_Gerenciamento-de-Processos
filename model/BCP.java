package model;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.util.Random;
import java.util.concurrent.TimeUnit;
/****************************************************************
 * Autor: Nathan Ferraz da Silva
 * Matricula: 201911925
 * Inicio: 10/08/2022
 * Ultima alteracao: 15/08/2022
 * Nome: BCP
 * Funcao: Simula um processo do SO
 * **************************************************************
 */
public class BCP extends Thread {
  private final int ordemDeCriacao;
  private static int ordemDeCriacaoFlag;
  private String nome;
  private int prioridade; // 1 - 10
  private int tempoDeExecucao;
  private int tempoDeInicio;
  private int tempoEmExecucao;
  private int deadline;
  private float progress; // 0.0f to 1.0f
  private boolean start;
  private boolean suspendFlag;

  private Slider timeslicing;
  private Label lbl_progress;
  private String color;

  private boolean stop = false;

  public BCP(String nome, int prioridade, int tempoDeExecucao) {
    this.nome = nome;
    this.prioridade = prioridade;
    this.tempoDeExecucao = tempoDeExecucao;
    ordemDeCriacao = ordemDeCriacaoFlag;
    ordemDeCriacaoFlag++;
    deadline = tempoDeExecucao + new Random().nextInt(10);
  }

  public String getNome() {
    return nome;
  }

  public int getPrioridade() {
    return prioridade;
  }

  public float getProgress() {
    return progress;
  }

  public int getTempoDeExecucao() {
    return tempoDeExecucao;
  }

  public int getOrdemDeCriacao() {
    return ordemDeCriacao;
  }

  public boolean isStarted() {
    return start;
  }

  public int getDeadline() {
    return deadline;
  }

  public void setTempoDeExecucao(int tempoDeExecucao) {
    this.tempoDeExecucao = tempoDeExecucao;
  }

  // TODO: TEM QUE SETAR PRO GARANTIDO FUNCIONAR
  public void setTempoDeInicio(int tempoDeInicio) {
    this.tempoDeInicio = tempoDeInicio;
  }

  public int getTempoDeInicio() {
    return tempoDeInicio;
  }

  public int getTempoEmExecucao() {
    return tempoEmExecucao;
  }

  public void setTimeslicing(Slider timeslicing) {
    this.timeslicing = timeslicing;
  }

  public void setLbl_progress(Label lbl_progress) {
    this.lbl_progress = lbl_progress;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  @Override
  public void run() {
    double pausa = 100;
    while (progress < 1.0f) {
      if(stop) break;
      try {
        double qtsPausas = (timeslicing.getValue()*1000)/pausa;
        double r = ((timeslicing.getValue())/tempoDeExecucao)/qtsPausas;

        progress = (float) ((progress+r)>1.0f ? 1.0f : progress+r);
        Platform.runLater(() -> lbl_progress.setText((int) (progress * 100) + "%"));
        if(progress == 1f)
        break;

        TimeUnit.MILLISECONDS.sleep((long) pausa);
        synchronized (this) {
          while (suspendFlag) {
            wait();
          }
        }
      } catch (InterruptedException e1) {
        // e1.printStackTrace();
      }
    }
  }

  @Override
  public synchronized void start() {
    start = true;
    super.start();
  }

  public synchronized void mysuspend() {
    System.out.println("Pausando: Thread" + getId());
    suspendFlag = true;
  }

  public synchronized void myresume() {
    System.out.println("Voltando: Thread" + getId());
    suspendFlag = false;
    notify();
  }

  @Override
  public void interrupt() {
    stop = true;
    super.interrupt();
  }

}
