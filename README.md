EvolutionaryComputation
=======================

O projeto teve como foco simular teorias inerentes a Computação Evolutiva, simulando a descoberta de um melhor caminho, em uma matriz de 20 x 20.

As entradas (input) que influenciaram são o Número de Gerações, Número de Cromossomos, Taxa de Cruzamento (Seleção), Mutação e Mecanismo de Seleção. Todas as entradas são possíveis de configurações para o usuário. 

Há dois mecanismos de seleção: Ranking e Torneio. O primeiro seleciona os melhores primeiros indivíduos de acordo com a porcentagem escolhida. O torneio realiza um embate entre pares e quem vence segue para a próxima geração. 

As saídas (output) obtidas são a partir de que: só é possível 'andar' de para cima, abaixo ou para frente. Assim cada 'passo' dá um ponto. Ao final das gerações se soma esses pontos e se obtém a média fitness do melhor indivíduo. Se o indivíduo alcançou a melhor pontuação máxima (o melhor caminho), se para a evolução e se obtém em qual geração isso ocorreu. 

As considerações finais sobre o projeto são que os algoritmos genéticos obtêm resultados mais inteligíveis em comparação (certamente) do que programado da forma convencional. Todo o algoritmo trabalha para ir aos poucos (ou ao contrário) moldando o resultado esperado, sendo correto dizer que o número de gerações é um fator preponderante para o sucesso final da população, já que mesmo que se busque o melhor caminho, é interessante também gerar o maior número possível de indivíduos que tenham esse potencial. 

Em relação aos testes mais específicos abordados no projeto, o Ranking obtém médias de fitness melhores do que o Torneio, embora este último obtenha, ou pelo menos tenha mais chance de conquistar o resultado mais rápido dependendo da configuração pré-estabelecida. Outro aspecto importante é que o valor de três por cento de taxa de mutação se mostrou melhor que os outros dois possíveis, ainda assim é certo que quem necessita de uma mutação maior é o mecanismo de Ranking, já que ele possui uma disposição maior de ficar estagnado em resultados iguais por um número de geração maior. Já o mecanismo de Torneio precisa de uma população maior para se tornar potencialmente capaz de obter um custo ótimo mais rapidamente. Isso provavelmente acontece pelo fato de que uma parte pior de um gene pode ser interessante junta á outra parte para evoluir (como um todo) de forma mais rápida. 
