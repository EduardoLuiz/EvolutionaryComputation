package ratatouille.Controlador;

import java.io.IOException;
import ratatouille.Servicos.Mutacao;
import ratatouille.Servicos.MinhaGeneracao;
import ratatouille.Servicos.Populacao;
import ratatouille.Servicos.Caminho;
import java.util.ArrayList;
//import ratatouille.Servicos.CalculoDoFitness;

public class PonteControladora {
    
    private int coordenadasDoRato, coordenadasQueijo, tamanhoPopulacao, taxaSelecao;
    private int taxaMutacao, numeroGeracoes, mecanismoSelecao;
    private Populacao populacao = new Populacao();
    private int cadeiaRetornada[];
    private ArrayList<Caminho> listaPopulacaoDaPonteControladora = new ArrayList<Caminho>();
    private MinhaGeneracao minhaGeracao;
    private Mutacao mutacao;
    private int custoOtimo;
    public int geracaoParou;
    
    public PonteControladora ( int ratoCoord, int queijoCoord, int tamanhoPop, int taxaSelec,
                            int taxaMut, int numGerac, int mecSelec ) throws IOException {
        
        this.coordenadasDoRato = ratoCoord;
        this.coordenadasQueijo = queijoCoord;
        this.tamanhoPopulacao = tamanhoPop;
        this.taxaSelecao = taxaSelec;
        this.taxaMutacao = taxaMut;
        this.numeroGeracoes = numGerac;
        this.mecanismoSelecao = mecSelec;
        listaPopulacaoDaPonteControladora = new ArrayList<Caminho> ();
        populacao = new Populacao();
        
        //int i[] = {6, 2, 2, 2, 2, 1, 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 0};
        
        //CalculoDoFitness calculoDoFitness = new CalculoDoFitness();
        
        //System.out.print(calculoDoFitness.realizarCalculoDoFitness(i, 20));
        
        CalculeCustoOtimo();
        RealizeOsServicosIniciais();
    }
    
    private void CalculeCustoOtimo() {
        
        int temp = coordenadasDoRato - coordenadasQueijo;
        if ( temp < 0 ) temp = temp * - 1;
        custoOtimo = temp + 19;
        // custoOtimo = | origem - destino | + 19
        
        System.out.print("\n Custo Otimo: " + custoOtimo);
    }
    
    private void RealizeOsServicosIniciais() {
        populacao.realizePorRandomMinhaPopulacao(tamanhoPopulacao, coordenadasDoRato, coordenadasQueijo);
        
        RealizeOsServicosPrincipais();
    }
    
    private void RealizeOsServicosPrincipais () {

        int flag = 0;
        
        for (int i = 0; i < numeroGeracoes; i++) {
            
            if ( flag == 1) {
                minhaGeracao = new MinhaGeneracao(taxaSelecao, tamanhoPopulacao, mecanismoSelecao, mutacao.getPopulacaoDaPonteControladora());
            }
            else {
                minhaGeracao = new MinhaGeneracao(taxaSelecao, tamanhoPopulacao, mecanismoSelecao, populacao.listaPopulacional);
                flag = 1;
            }
            
            listaPopulacaoDaPonteControladora = new ArrayList<Caminho>();
            listaPopulacaoDaPonteControladora = minhaGeracao.getListaPopulation();
            mutacao = new Mutacao(taxaMutacao, taxaSelecao, tamanhoPopulacao, listaPopulacaoDaPonteControladora);
            System.out.print("\n\n\tMelhor Individuo Da Geração " + (i + 1) + ": \n");
            System.out.print(mutacao.getPopulacaoDaPonteControladora().get(0));
            
            if ( mutacao.getPopulacaoDaPonteControladora().get(0).retorneFitnessDiretoDoCaminho() <= custoOtimo ) {
                System.out.print("\n\n\t ----> Paramos Por Ter Encontrado o Melhor Caminho na Geração " + ( i + 1 ) + "\n\n");
                geracaoParou = i + 1;
                break;
            }
            
            if ( i == numeroGeracoes - 1 ) { 
                System.out.print("\n\n\t ---> Paramos Por Ter Alcançado O Número De Gerações Limite\n\n"); 
                geracaoParou = i + 1;
            }
            
        }

    }
    
    public int [] getMelhorCaminhoEncontrado () 
    {
        cadeiaRetornada = new int[20];
        Caminho caminho;
        caminho = mutacao.getPopulacaoDaPonteControladora().get(0);
        for (int i = 0; i != 20; i++) {
            cadeiaRetornada[i] = (Integer) caminho.retorneListaUnicaDiretoDoCaminho(i);
        }
        return cadeiaRetornada;
    }
    
}
