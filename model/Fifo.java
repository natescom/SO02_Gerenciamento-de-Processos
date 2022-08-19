package model;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/****************************************************************
 * Autor: Nathan Ferraz da Silva
 * Matricula: 201911925
 * Inicio: 10/08/2022
 * Ultima alteracao: 14/08/2022
 * Nome: FIFO
 * Funcao: Organiza os processos de acordo a ordem de entrada deles
 * **************************************************************
 */
public class Fifo extends Agendador {

  public Fifo() {
    super(0);
  }

  @Override
  public List<BCP> order(List<BCP> list) {
    return list.stream()
        .sorted(Comparator.comparing(BCP::getOrdemDeCriacao))
        .collect(Collectors.toList());
  }

  @Override
  public List<BCP> addElement(List<BCP> list, BCP element) {
    list.add(element);
    return list;
  }


}
