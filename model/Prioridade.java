package model;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/****************************************************************
 * Autor: Nathan Ferraz da Silva
 * Matricula: 201911925
 * Inicio: 10/08/2022
 * Ultima alteracao: 14/08/2022
 * Nome: Prioridade
 * Funcao: Organiza os processos de acordo a ordem de prioridade deles
 * **************************************************************
 */
public class Prioridade extends Agendador {

  public Prioridade() {
    super(1);
  }

  @Override
  public List<BCP> order(List<BCP> list) {
    return list.stream()
        .sorted(Comparator.comparing(BCP::getPrioridade).reversed())
        .collect(Collectors.toList());
  }

  @Override
  public List<BCP> addElement(List<BCP> list, BCP element) {
    if (list.isEmpty()) {
      list.add(element);
      return list;
    }
    for (int i = 0; i < list.size(); i++) {
      if (element.getPrioridade() >= list.get(i).getPrioridade()) {
        list.add(i, element);
        break;
      }
    }
    return list;
  }

}
