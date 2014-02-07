package ratatouille.Servicos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Mutacao {

    private int numeroGenes, tamanhoPopulacao, tamanhoTotalDoCaminho = 20, fitness;
    private int cadeiaDeCaminhos [];
    private ArrayList<Caminho> populacaoDaPonteControladora;
    private ArrayList populacaoTemporaria;
    private CalculoDoFitness calculoDoFitness = new CalculoDoFitness();
    private static Random geradorRandomico = new Random();
    
    public Mutacao(int taxaMut, int taxaSelec, int tamanhoPop, ArrayList<Caminho> populacaoParam) {
        this.tamanhoPopulacao = tamanhoPop;
        int parteDoCalculo = 18 * populacaoParam.size();
        
        numeroGenes = (int) ( ( parteDoCalculo  *  taxaMut / 100 ) );
        
        populacaoDaPonteControladora =  new ArrayList<Caminho>();
        populacaoDaPonteControladora.addAll(populacaoParam);
        Collections.copy(populacaoDaPonteControladora, populacaoParam);
        
        RealizePorRandomAMutacao();
    }
    
    private void RealizePorRandomAMutacao () {
        
        cadeiaDeCaminhos = new int[tamanhoTotalDoCaminho];
        Caminho caminho;
        int numRandomico;
                
        for (int i = 0; i != numeroGenes; i++) {
            
            numRandomico = geradorRandomico.nextInt(populacaoDaPonteControladora.size());
            caminho = populacaoDaPonteControladora.get(numRandomico);
            populacaoDaPonteControladora.remove(numRandomico);
            numRandomico = geradorRandomico.nextInt(17);
            
            int tempNumRandomico = numRandomico + 1;
            
            for (int iterator = 0; iterator != tamanhoTotalDoCaminho; iterator++) {
                if ( tempNumRandomico != iterator )
                    cadeiaDeCaminhos[iterator] = (Integer) caminho.retorneListaUnicaDiretoDoCaminho(iterator);
                else {
                    numRandomico = geradorRandomico.nextInt(19);
                    cadeiaDeCaminhos[iterator] = numRandomico;
                }
            }
            
            fitness = calculoDoFitness.realizarCalculoDoFitness(cadeiaDeCaminhos, tamanhoTotalDoCaminho);
            populacaoTemporaria = new ArrayList();
            
            for (int num : cadeiaDeCaminhos)
                populacaoTemporaria.add(num);
            
            Caminho novoCaminho = new Caminho (populacaoTemporaria, fitness);
            populacaoDaPonteControladora.add(novoCaminho);
            
        }
        
        CalculeFitnessNovamenteParaTodos(populacaoDaPonteControladora);
        RetornaParaPopulacaoInicial();
        
    }
    
    private void CalculeFitnessNovamenteParaTodos(ArrayList<Caminho> array) {    
        cadeiaDeCaminhos = new int[tamanhoTotalDoCaminho];
        Caminho caminho;
        
        for (int i = 0; i < array.size(); i++)
        {
            caminho = array.get(i);
            array.remove(i);
            
            for (int iterator = 0; iterator != tamanhoTotalDoCaminho; iterator++) {
                cadeiaDeCaminhos[iterator] = (Integer) caminho.retorneListaUnicaDiretoDoCaminho(iterator);
            }
            
            fitness = 0;
            
            fitness = calculoDoFitness.realizarCalculoDoFitness(cadeiaDeCaminhos, tamanhoTotalDoCaminho);
            populacaoTemporaria = new ArrayList();
            
            for (int num : cadeiaDeCaminhos)
                populacaoTemporaria.add(num);
            
            Caminho novoCaminho = new Caminho (populacaoTemporaria, fitness);
            array.add(novoCaminho);
        }
    }

    public ArrayList<Caminho> getPopulacaoDaPonteControladora() {        
        return populacaoDaPonteControladora;
    }
    
    private void RetornaParaPopulacaoInicial () {
        int tamanhoRemocao;
        tamanhoRemocao = populacaoDaPonteControladora.size() - tamanhoPopulacao;
        Collections.sort(populacaoDaPonteControladora);
        Collections.reverse(populacaoDaPonteControladora);
        
        for ( int i = 0; i < tamanhoRemocao; i++ ) {
            populacaoDaPonteControladora.remove(0);
        }
        
        //Collections.sort(populacaoDaPonteControladora);
        CalculeFitnessNovamenteParaTodos(populacaoDaPonteControladora);
        Collections.sort(populacaoDaPonteControladora);
    }
    
}

