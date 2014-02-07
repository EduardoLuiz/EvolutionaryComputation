package ratatouille.Servicos;

import java.util.ArrayList;

public class Caminho implements Comparable<Caminho> {
    
    private ArrayList listaPrincipalDoMeuCaminho = new ArrayList();
    private Integer fitness;
    
    public Caminho (ArrayList list, Integer fitness) {
        this.fitness = 0;
        this.listaPrincipalDoMeuCaminho = list;
        this.fitness = fitness;
    }

    @Override
    public String toString () {
        return listaPrincipalDoMeuCaminho + " " + fitness;
    }
    
    public Object retorneListaUnicaDiretoDoCaminho (int pos) {
        return listaPrincipalDoMeuCaminho.get(pos);
    }
    
    public int retorneFitnessDiretoDoCaminho () {
        return fitness;
    }
    
    public int compareTo (Caminho c) {
        return fitness.compareTo(c.fitness);
    }
}
