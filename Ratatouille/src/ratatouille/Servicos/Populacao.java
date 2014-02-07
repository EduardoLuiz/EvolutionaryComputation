package ratatouille.Servicos;

import java.util.ArrayList;
import java.util.Iterator;

public class Populacao {
    
    public ArrayList<Caminho> listaPopulacional = new ArrayList();
    private ArrayList listaPopulacionalTemporaria = new ArrayList();
    Iterator iteracaoParaArrayLists;
    RatatouilleCaminho classCaminhoDoRatatouille = new RatatouilleCaminho();
    
    public void realizePorRandomMinhaPopulacao(int numPopulation, int beginRat, int beginCheese) {
        for ( int i = 0; i != numPopulation; i++ ) {            
            listaPopulacionalTemporaria = new ArrayList();
            listaPopulacionalTemporaria.addAll(classCaminhoDoRatatouille.RealizeMeuCaminho(beginRat, beginCheese));
            Caminho way = new Caminho(listaPopulacionalTemporaria, classCaminhoDoRatatouille.numeroFitness);
            listaPopulacional.add(way);
        }
    }
    
    public void imprimePopulacaoCorrenteCriada () {
        for ( int i = 0; i < listaPopulacional.size(); i++ ) 
            System.out.print(listaPopulacional.get(i) + "\n");
    }
    
    public void imprimaQualquerArrayList (ArrayList list) {
        iteracaoParaArrayLists = list.iterator();
        while( iteracaoParaArrayLists.hasNext() ) {
            System.out.print( "\n" + iteracaoParaArrayLists.next() + " ");
        }
    }
    
}
