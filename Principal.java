import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/****************************************************************
 * Autor: Nathan Ferraz da Silva
 * Matricula: 201911925
 * Inicio: 29/07/2021
 * Ultima alteracao: 29/07/2021
 * Nome: Principal
 * Funcao: Inicializa o programa
 * ************************************************************** */
public class Principal extends Application{
  
  public static void main(String[] args){
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    new Controller();
    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
    primaryStage.setTitle("Sistemas Operacionais - Gerenciador de Processos");
    primaryStage.setScene(new Scene(root));
    primaryStage.setOnCloseRequest(t -> {
      Platform.exit();
      System.exit(0);
    }); // Ao fechar a janela todos os processos sao fechados tambem
    primaryStage.show();
  }
}