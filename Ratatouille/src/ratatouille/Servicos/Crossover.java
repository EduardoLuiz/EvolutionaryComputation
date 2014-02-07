package ratatouille.Servicos;

import java.util.ArrayList;
import java.util.Random;

public class Crossover {
    
    protected int numeroCrossover;
    public ArrayList<Caminho> listaQueRecebePopulacaoDeMinhaGeracao, listaTemporarioDoCrossover, listaQueSeraRetornada;
    private ArrayList segundaListaTemporariaDoCrossover;
    private CalculoDoFitness classFitnessCalc = new CalculoDoFitness();
    private int primeiraCadeiaDeCaminhos [], segundaCadeiaDeCaminhos[];
    private int tamanhoDaPrimeiraParteDoCaminho = 10, tamanhoDaUltimaParteDoCaminho = 19, tamanhoTotalDoCaminho = 20;
    private static Random geradorRandomico = new Random();
    
    public Crossover (ArrayList listaParam) {
        this.listaQueRecebePopulacaoDeMinhaGeracao = new ArrayList();
        this.listaQueRecebePopulacaoDeMinhaGeracao = listaParam;
        produzaCrossover();
    }
    
    private void produzaCrossover () {
        CrossoverEmTodos();
    }
    
    private void CrossoverEmTodos() {
        
        listaQueSeraRetornada = new ArrayList();
        int tamanhoAtualDaListaMinhaGeracao = listaQueRecebePopulacaoDeMinhaGeracao.size() / 2;
        
        if (listaQueRecebePopulacaoDeMinhaGeracao.size() / 2 % 2 == 1)
            listaQueRecebePopulacaoDeMinhaGeracao.remove(listaQueRecebePopulacaoDeMinhaGeracao.size() - 1);
        
        for(int iterator = 0; iterator != tamanhoAtualDaListaMinhaGeracao; iterator++ ) 
        {
            int random = geradorRandomico.nextInt(listaQueRecebePopulacaoDeMinhaGeracao.size());
            int randomDois = geradorRandomico.nextInt(listaQueRecebePopulacaoDeMinhaGeracao.size());
            
            if (random == randomDois) {
                randomDois = geradorRandomico.nextInt(listaQueRecebePopulacaoDeMinhaGeracao.size());
            }
            
            segundaListaTemporariaDoCrossover = new ArrayList();
            listaTemporarioDoCrossover = new ArrayList();
            primeiraCadeiaDeCaminhos = new int[tamanhoTotalDoCaminho];
            segundaCadeiaDeCaminhos = new int[tamanhoTotalDoCaminho];
            int fitness;
            
            Caminho primeiroCaminho = listaQueRecebePopulacaoDeMinhaGeracao.get(random);
            Caminho segundoCaminho = listaQueRecebePopulacaoDeMinhaGeracao.get(randomDois);
            
            for ( int iteratorTwo = 1; iteratorTwo != tamanhoDaPrimeiraParteDoCaminho; iteratorTwo++) {
                primeiraCadeiaDeCaminhos[iteratorTwo] = (Integer) segundoCaminho.retorneListaUnicaDiretoDoCaminho(iteratorTwo);
                segundaCadeiaDeCaminhos[iteratorTwo] = (Integer) primeiroCaminho.retorneListaUnicaDiretoDoCaminho(iteratorTwo);
            }
            
            for ( int iteratorTwo = tamanhoDaPrimeiraParteDoCaminho, it2 = 1; iteratorTwo != tamanhoDaUltimaParteDoCaminho; iteratorTwo++, it2++) {
                segundaCadeiaDeCaminhos[iteratorTwo] = primeiraCadeiaDeCaminhos[it2];
            }
            
            fitness = classFitnessCalc.realizarCalculoDoFitness(segundaCadeiaDeCaminhos, tamanhoTotalDoCaminho);
            
            for( int num : segundaCadeiaDeCaminhos) {
                segundaListaTemporariaDoCrossover.add(num);
            }
            
            segundaListaTemporariaDoCrossover.set(0, listaQueRecebePopulacaoDeMinhaGeracao.get(0).retorneListaUnicaDiretoDoCaminho(0));
            segundaListaTemporariaDoCrossover.set(tamanhoDaUltimaParteDoCaminho, listaQueRecebePopulacaoDeMinhaGeracao.get(0).retorneListaUnicaDiretoDoCaminho(tamanhoDaUltimaParteDoCaminho));
            listaTemporarioDoCrossover.addAll(segundaListaTemporariaDoCrossover);
            Caminho terceiroCaminho = new Caminho(listaTemporarioDoCrossover, fitness);
            listaQueSeraRetornada.add(terceiroCaminho); 
            
            // ---------------
            
            segundaListaTemporariaDoCrossover =  new ArrayList<Caminho>();
            listaTemporarioDoCrossover = new ArrayList<Caminho>();
            primeiraCadeiaDeCaminhos = new int[tamanhoTotalDoCaminho];
            segundaCadeiaDeCaminhos = new int[tamanhoTotalDoCaminho];
            
            for ( int iteratorTwo = tamanhoDaPrimeiraParteDoCaminho; iteratorTwo != tamanhoDaUltimaParteDoCaminho; iteratorTwo++) {
                primeiraCadeiaDeCaminhos[iteratorTwo] = (Integer) segundoCaminho.retorneListaUnicaDiretoDoCaminho(iteratorTwo);
                segundaCadeiaDeCaminhos[iteratorTwo] = (Integer) primeiroCaminho.retorneListaUnicaDiretoDoCaminho(iteratorTwo);
            }
            
            for ( int iteratorTwo = 1, it2 = tamanhoDaPrimeiraParteDoCaminho; iteratorTwo != tamanhoDaPrimeiraParteDoCaminho; iteratorTwo++, it2++) {
                primeiraCadeiaDeCaminhos[iteratorTwo] = segundaCadeiaDeCaminhos[it2];
            }
            
            classFitnessCalc = new CalculoDoFitness();
            fitness = classFitnessCalc.realizarCalculoDoFitness(primeiraCadeiaDeCaminhos, tamanhoTotalDoCaminho);
            
            for( int num : primeiraCadeiaDeCaminhos)
                segundaListaTemporariaDoCrossover.add(num);
            
            segundaListaTemporariaDoCrossover.set(0, listaQueRecebePopulacaoDeMinhaGeracao.get(0).retorneListaUnicaDiretoDoCaminho(0));
            segundaListaTemporariaDoCrossover.set(tamanhoDaUltimaParteDoCaminho, listaQueRecebePopulacaoDeMinhaGeracao.get(0).retorneListaUnicaDiretoDoCaminho(tamanhoDaUltimaParteDoCaminho));            
            listaTemporarioDoCrossover.addAll(segundaListaTemporariaDoCrossover);
            terceiroCaminho = new Caminho(listaTemporarioDoCrossover, fitness);
            listaQueSeraRetornada.add(terceiroCaminho);
        }
        
    }
    
}