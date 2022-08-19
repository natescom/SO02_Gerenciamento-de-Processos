package model;

import java.util.List;

/****************************************************************
 * Autor: Nathan Ferraz da Silva
 * Matricula: 201911925
 * Inicio: 05/08/2022
 * Ultima alteracao: 05/08/2022
 * Nome: Agendador
 * Funcao: Parte do Sistema Operacional que seleciona qual o
 * o proximo processo que sera executado.
 * **************************************************************
 */
public abstract class Agendador {
  private final int type;

  protected Agendador(int type) {
    this.type = type;
  }

  /**
   * Ordena a lista de processos de acordo os criterios de
   * ordenacao estabelecidos pelo algoritmo
   * 
   * @param list Lista de processos
   * @return Lista ordenada
   */
  public abstract List<BCP> order(List<BCP> list);

  /**
   * Adiciona um elemento na lista de acordo aos criterios de
   * ordenacao
   * 
   * @param list
   * @param element
   * @return
   */
  public abstract List<BCP> addElement(List<BCP> list, BCP element);

  public int getType() {
    return type;
  }
}
