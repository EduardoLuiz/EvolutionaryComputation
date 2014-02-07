package ratatouille.Servicos;

import java.util.ArrayList;
import java.util.Random;

public class RatatouilleCaminho {
    
    private int minhaCadeiaDeCaminhos [], cadeiaRandomicaDeCaminhos [], tamanhoCadeiaCorrente = 20;
    public int numeroFitness;
    private static ArrayList listaTemporaria = new ArrayList ();
    private static Random geradorRandomico = new Random();
    CalculoDoFitness fitnessCalc = new CalculoDoFitness(); 
    
    public ArrayList RealizeMeuCaminho (int ratatouilleBegin, int CheeseBegin) {
        numeroFitness = 0;
        listaTemporaria = new ArrayList () ;
        minhaCadeiaDeCaminhos = new int[tamanhoCadeiaCorrente];
        minhaCadeiaDeCaminhos = randomSubPopulacao();
        minhaCadeiaDeCaminhos[0] = ratatouilleBegin;
        minhaCadeiaDeCaminhos[tamanhoCadeiaCorrente-1] = CheeseBegin;
        numeroFitness = fitnessCalc.realizarCalculoDoFitness(minhaCadeiaDeCaminhos, tamanhoCadeiaCorrente);
        
        for( int num : minhaCadeiaDeCaminhos)
            listaTemporaria.add(num);
        
        return listaTemporaria;
    }
    
    private int[] randomSubPopulacao () {
        cadeiaRandomicaDeCaminhos = new int [tamanhoCadeiaCorrente];
        
        for ( int i = 0; i < tamanhoCadeiaCorrente; i++ ) {
            cadeiaRandomicaDeCaminhos[i] = geradorRandomico.nextInt(20);
        }
        
        return cadeiaRandomicaDeCaminhos;
    }

}
