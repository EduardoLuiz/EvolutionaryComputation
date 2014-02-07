package ratatouille.Servicos;

import java.util.ArrayList;
import java.util.Collections;

public class MinhaGeneracao {
    
    private int taxaSelecaoDaMinhaGeracao, mecanismoDeSelecaoDaMinhaGeracao;
    private ArrayList<Caminho> listaPopulacaoDaMinhaGeracao;
    private ArrayList<Caminho> listaParaPreservarMeusPais, listaDaPonteControladora = new ArrayList<Caminho>();
    Ranking ranking;
    Torneio torneio;
    RankingETorneio rankingETorneio;
    Crossover crossover;
    Populacao populacao = new Populacao();

    public ArrayList<Caminho> getListaPopulation() {
        return listaPopulacaoDaMinhaGeracao;
    }
    
    public MinhaGeneracao(int taxaSelec, int tamanhoPop, int mecSelecao, ArrayList<Caminho> list) {
        taxaSelecaoDaMinhaGeracao = (int) ( (double) taxaSelec / 100 * tamanhoPop );
        mecanismoDeSelecaoDaMinhaGeracao = mecSelecao;
        listaDaPonteControladora = new ArrayList<Caminho>();
        listaDaPonteControladora = list;
        
        FacaMinhaGeracao();
    }
    
    private void FacaMinhaGeracao () {
        SelecioneMinhaGeracao();
        DupliqueMinhaGeracaoParaCrossover();
        FacaCrossoverEmMinhaGeracao();
        JuntePaisEFilhosParaMutacao();
    }
    
    private void SelecioneMinhaGeracao () {
        listaPopulacaoDaMinhaGeracao = new ArrayList<Caminho>();
        
        if(mecanismoDeSelecaoDaMinhaGeracao == 0) {
            ranking = new Ranking(taxaSelecaoDaMinhaGeracao, listaDaPonteControladora);
            listaPopulacaoDaMinhaGeracao = ranking.listaPopulacaoRankeada;          
        }
        else if (mecanismoDeSelecaoDaMinhaGeracao == 1) {
            torneio = new Torneio(taxaSelecaoDaMinhaGeracao, listaDaPonteControladora);
            listaPopulacaoDaMinhaGeracao = torneio.listaPopulacaoQueSeraRetornada;
        }
        else if (mecanismoDeSelecaoDaMinhaGeracao == 2) { 
            rankingETorneio = new RankingETorneio(taxaSelecaoDaMinhaGeracao, listaDaPonteControladora);
            listaPopulacaoDaMinhaGeracao = rankingETorneio.listaPopulation;
        }
    }
    
    private void DupliqueMinhaGeracaoParaCrossover() {
        listaParaPreservarMeusPais = new ArrayList<Caminho>();
        listaParaPreservarMeusPais.addAll(listaPopulacaoDaMinhaGeracao);
        
        Collections.copy(listaParaPreservarMeusPais, listaPopulacaoDaMinhaGeracao);
    }
    
    private void JuntePaisEFilhosParaMutacao () {
        int sizeNow = listaParaPreservarMeusPais.size();
        for ( int i = 0; i != sizeNow; i++ ) {
            listaPopulacaoDaMinhaGeracao.add(listaParaPreservarMeusPais.get(i));
        }
    }
    
    private void FacaCrossoverEmMinhaGeracao () {
        crossover = new Crossover(listaPopulacaoDaMinhaGeracao);
        listaPopulacaoDaMinhaGeracao = crossover.listaQueSeraRetornada;
        
        Collections.copy(listaPopulacaoDaMinhaGeracao, crossover.listaQueSeraRetornada);
    }
    
}