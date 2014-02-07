package ratatouille.Servicos;

import java.util.ArrayList;
import java.util.Random;

public class RankingETorneio {
    
    public ArrayList<Caminho> listaPopulation;
    private static Random generator = new Random();
    Torneio tourney;
    Ranking ranking;

    public RankingETorneio (int taxaSelec, ArrayList listaParam) { 
        
        int separatorToTourney = generator.nextInt(listaParam.size());
        int separatorToRanking = separatorToTourney - listaParam.size();
        
        if(separatorToRanking < 0) separatorToRanking = separatorToRanking * -1;
        
        tourney = new Torneio(separatorToTourney, listaParam);
        ranking = new Ranking(separatorToRanking, listaParam);
        
        listaPopulation = tourney.listaPopulacaoQueSeraRetornada;
        listaPopulation.addAll(ranking.listaPopulacaoRankeada);
    }
}
