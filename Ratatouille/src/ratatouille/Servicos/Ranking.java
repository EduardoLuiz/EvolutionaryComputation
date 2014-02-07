package ratatouille.Servicos;

import java.util.ArrayList;
import java.util.Collections;

public class Ranking {
    
    public ArrayList<Caminho> listaPopulacaoRankeada;
    
    public Ranking (int taxaSelec, ArrayList<Caminho> listaParam) {
        Collections.sort(listaParam);
        listaPopulacaoRankeada = new ArrayList<Caminho>();
        for ( int i = 0; i < taxaSelec; i++ ) 
            listaPopulacaoRankeada.add(listaParam.get(i));
    }
}
