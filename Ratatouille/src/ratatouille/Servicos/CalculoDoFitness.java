package ratatouille.Servicos;

public class CalculoDoFitness {
    
    private int fitness, numeroUnicoDeFitnessTemporario, recebeSize;
    
    public int realizarCalculoDoFitness (int[] fit, int size) {
        
        recebeSize = 0;
        fitness = 0;
        numeroUnicoDeFitnessTemporario = 0;
        recebeSize = size - 1;
        
        for ( int i = 0; i < recebeSize; i++ ) 
        {
            numeroUnicoDeFitnessTemporario = ( (    fit[i]     )    -    (    fit[i+1]    ) );
            
            if (numeroUnicoDeFitnessTemporario < 0) 
                numeroUnicoDeFitnessTemporario = numeroUnicoDeFitnessTemporario * ( - 1);
            
            //System.out.print("\nNumero Unico: " + numeroUnicoDeFitnessTemporario);
            
            fitness = fitness + numeroUnicoDeFitnessTemporario + 1;
            
            //System.out.print("\n" + i + " ftness: " + fitness);
        }
        
        return fitness;
    }
}
