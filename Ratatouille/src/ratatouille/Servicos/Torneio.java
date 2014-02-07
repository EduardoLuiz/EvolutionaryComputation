package ratatouille.Servicos;

import java.util.ArrayList;
import java.util.Random;

public class Torneio {
    
    public ArrayList<Caminho> listaPopulacaoQueSeraRetornada;
    private static Random gerePorRandomProximoOponente = new Random();
    
    public Torneio (int taxaSelec, ArrayList<Caminho> listaPassadaComoParametro) {
        listaPopulacaoQueSeraRetornada = new ArrayList<Caminho>();
        int opponentOne, opponentTwo;
        
        for (int i = 0; i != taxaSelec; i++) {
            opponentOne = gerePorRandomProximoOponente.nextInt(listaPassadaComoParametro.size() -1);
            opponentTwo = gerePorRandomProximoOponente.nextInt(listaPassadaComoParametro.size() -1);
            
            if(listaPassadaComoParametro.get(opponentOne).retorneFitnessDiretoDoCaminho() < listaPassadaComoParametro.get(opponentTwo).retorneFitnessDiretoDoCaminho())
                listaPopulacaoQueSeraRetornada.add(listaPassadaComoParametro.get(opponentOne));
            else
                listaPopulacaoQueSeraRetornada.add(listaPassadaComoParametro.get(opponentTwo));
            
        }
    }
}
