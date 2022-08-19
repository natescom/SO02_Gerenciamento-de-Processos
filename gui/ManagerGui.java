package gui;

import javafx.application.Platform;
import javafx.scene.layout.VBox;
import model.BCP;

public class ManagerGui {
  private final VBox vb_inicio;
  private final VBox vb_pronto;
  private final VBox vb_exec;
  private final VBox vb_bloq;
  private final VBox vb_final;

  public ManagerGui(VBox vb_inicio, VBox vb_pronto, VBox vb_exec, VBox vb_bloq, VBox vb_final) {
    this.vb_inicio = vb_inicio;
    this.vb_pronto = vb_pronto;
    this.vb_exec = vb_exec;
    this.vb_bloq = vb_bloq;
    this.vb_final = vb_final;
  }

  public void publishProcessInicializando(BCP bcp, int type) {
    Platform.runLater(() -> BCPUtil.publishViewProcess(bcp, vb_inicio, type));
  }

  public void publishProcessPronto(BCP bcp, int type) {
    Platform.runLater(() -> BCPUtil.publishViewProcess(bcp, vb_pronto, type));
  }

  public void publishProcessExec(BCP bcp, int type) {
    Platform.runLater(() -> BCPUtil.publishViewProcess(bcp, vb_exec, type));
  }

  public void publishProcessBloq(BCP bcp, int type) {
    Platform.runLater(() -> BCPUtil.publishViewProcess(bcp, vb_bloq, type));
  }

  public void publishProcessFinal(BCP bcp, int type) {
    Platform.runLater(() -> BCPUtil.publishViewProcess(bcp, vb_final, type));
  }

  public VBox getVb_inicio() {
    return vb_inicio;
  }

  public VBox getVb_pronto() {
    return vb_pronto;
  }

  public VBox getVb_exec() {
    return vb_exec;
  }

  public VBox getVb_bloq() {
    return vb_bloq;
  }

  public VBox getVb_final() {
    return vb_final;
  }
}
