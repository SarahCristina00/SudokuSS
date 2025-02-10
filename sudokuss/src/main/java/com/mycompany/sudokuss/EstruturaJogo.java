/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sudokuss;

/**
 *
 * @author Sarah
 */
import java.util.Scanner;

public class EstruturaJogo {
    private MatrizSudoku matriz;
    private Scanner opcao;

    //construtor
    public EstruturaJogo() {
        matriz = new MatrizSudoku();
        opcao = new Scanner(System.in);
    }

    public void inicio() {
        System.out.println("Bem vindo(a) ao Sudoku da Sarah *--* <3");
        System.out.println("Escolha uma opcao:");
        System.out.println("1 - Gerar um jogo com numeros aleatorios");
        System.out.println("2 - Definir os valores iniciais do jogo");

        int escolha = opcao.nextInt();
        opcao.nextLine();

        if (escolha == 1){
            gerar_aleatorio();
        } else 
            if (escolha == 2) {
            definir_iniciais();
        } 
            else {
            System.out.println("Opcao invalida :/. Tente novamente! :D ");
            inicio();
        }

        matriz.imprimi_matriz();
        jogar();
    }

    private void gerar_aleatorio() {
        System.out.println("Quantos numeros aleatorios voce deseja sortear?");
        int quantidade = opcao.nextInt();
        opcao.nextLine();
        
        //gera os números aleatatórios para linha, coluna e valor
        for (int i = 0; i < quantidade; i++) {
            int linha = (int) (Math.random() * 9);
            int coluna = (int) (Math.random() * 9);
            int valor = (int) (Math.random() * 9) + 1;
                
            if (matriz.verifica_movimento_valido(linha, coluna, valor)) {
                matriz.aloca_valor_fixo(linha, coluna, valor);
            } else {
                i--;
            }
        }
    }

    private void definir_iniciais() {
    System.out.println("Digite os valores no formato (linha,coluna,valor), ou (-1,-1,-1) para finalizar:");
    while (true) {
        String entradaTexto = opcao.nextLine();
        if (entradaTexto.equals("(-1,-1,-1)")) {
            break;
        }

        String[] partes = entradaTexto.replace("(", "").replace(")", "").split(",");
        if (partes.length == 3) {
            int linha = Integer.parseInt(partes[0]) - 1;
            int coluna = Integer.parseInt(partes[1]) - 1;
            int valor = Integer.parseInt(partes[2]);

            // Valida se o intervalo está dentro do dominio de 1 a 9
            if (linha < 1 || linha > 9 || coluna < 1 || coluna > 9 || valor < 1 || valor > 9) {
                System.out.println("Entrada invalida! :/ Da uma olhadinha, se os numeros estao no dominio de 1 a 9! :D");
                continue;
            }

            if (matriz.verifica_movimento_valido(linha, coluna, valor)) {
                matriz.aloca_valor_fixo(linha, coluna, valor);
            } else {
                System.out.println("Movimento invalido! :/ Tente novamente! :D");
            }
        } else {
            System.out.println("Formato invalido! :/ Use (linha,coluna,valor)! :D.");
        }
    }
}


    private void jogar() {
        while (true) {
            System.out.println("Escolha uma acao:");
            System.out.println("1. Adicionar jogada");
            System.out.println("2. Remover jogada");
            System.out.println("3. Verificar matriz");
            System.out.println("4. Dica");
            System.out.println("5. Sair");

            int escolha = opcao.nextInt();
            opcao.nextLine();

            switch (escolha) {
                case 1 -> adiciona();
                case 2 -> remover();
                case 3 -> analisa_matriz();
                case 4 -> dica();
                case 5 -> {
                    System.out.println("Obrigada por jogar Sudoku com a Sarah! <3");
                    return;
                }
                default -> System.out.println("Opcao invalida! :/ Tente novamente! :D");
            }
            if (matriz.venceu()) {
                 System.out.println("Parabéns!!! Você é inccrivel, completou o Sudoku! *--* ");
                 return;
            }

            matriz.imprimi_matriz();
        }
    }

    private void adiciona() {
    System.out.println("Digite as jogadas no formato (linha,coluna,valor) separadas por espaco, ou digite sair para cancelar:");

    String entradaTexto = opcao.nextLine();
    if (entradaTexto.equalsIgnoreCase("sair")) {
        System.out.println("Operacao cancelada.");
        return;
    }

    // separa as entradas de jogadas, para alocar de forma individual na matriz
    String[] jogadas = entradaTexto.split(" ");

    for (String jogada : jogadas) {
        // Removee os parênteses e separa linha, coluna e valor
        String[] partes = jogada.replace("(", "").replace(")", "").split(",");
        if (partes.length == 3) {
            int linha = Integer.parseInt(partes[0]) - 1;
            int coluna = Integer.parseInt(partes[1]) - 1;
            int valor = Integer.parseInt(partes[2]);

            //  Valida se o intervalo está dentro do dominio de 1 a 9
            if (linha < 0 || linha >= 9) {
                System.out.println("Erro na jogada (" + jogada + "): Linha " + (linha + 1) + " esta fora do dominio 1 a 9.");
                continue;
            }
            if (coluna < 0 || coluna >= 9) {
                System.out.println("Erro na jogada (" + jogada + "): Coluna " + (coluna + 1) + " esta fora do dominio 1 a 9.");
                continue;
            }
            if (valor < 1 || valor > 9) {
                System.out.println("Erro na jogada (" + jogada + "): Valor " + valor + " esta fora do dominio 1 a 9.");
                continue;
            }

            // Verifica se é uma posição é fixa
            if (matriz.verifica_valor_fixo(linha, coluna)) {
                System.out.println("Erro na jogada (" + jogada + "): A posicao (" + (linha + 1) + "," + (coluna + 1) + ") eh fixa e não pode ser alterada.");
                continue;
            }

            // Verificar se é um  movimento é válido
            if (matriz.verifica_movimento_valido(linha, coluna, valor)) {
                matriz.aloca_valor(linha, coluna, valor);
                System.out.println("Jogada adicionada: (" + (linha + 1) + "," + (coluna + 1) + "," + valor + ")");
            } else {
                System.out.println("Erro na jogada (" + jogada + "): O valor " + valor + " jah existe na linha, coluna ou subquadrado 3x3 dermacado.");
            }
        } else {
            System.out.println("Erro na jogada (" + jogada + "): Formato invalido! :/. Use (linha,coluna,valor)! :D.");
        }
    }
}



    private void remover() {
        System.out.println("Digite a posicao a ser removida no formato (linha,coluna):");
        String entradaTexto = opcao.nextLine();
        String[] partes = entradaTexto.replace("(", "").replace(")", "").split(",");

        if (partes.length == 2) {
            int linha = Integer.parseInt(partes[0]) - 1;
            int coluna = Integer.parseInt(partes[1]) - 1;

            if (matriz.verifica_valor_fixo(linha, coluna)) {
                System.out.println("A posicao (" + (linha + 1) + "," + (coluna + 1) + ") eh fixa e não pode ser removida.");
            } else if (matriz.obterValor(linha, coluna) == 0) {
                System.out.println("A posicao (" + (linha + 1) + "," + (coluna + 1) + ") esta vazia.");
            } else {
                matriz.aloca_valor(linha, coluna, 0);
                System.out.println("Jogada removida! :D");
            }
        } else {
            System.out.println("Formato invalido! :/ Use (linha,coluna)! :D");
        }
    }

    private void analisa_matriz() {
        boolean valido = true;

        for (int linha = 0; linha < 9; linha++) {
            for (int coluna = 0; coluna < 9; coluna++) {
                int valor = matriz.obterValor(linha, coluna);
                if (valor != 0 && !matriz.verifica_movimento_valido(linha, coluna, valor)) {
                    System.out.println("Quebra de regra na posicao (" + (linha + 1) + "," + (coluna + 1) + ") com o valor " + valor);
                    valido = false;
                }
            }
        }

        if (valido) {
            System.out.println("A matriz esta valida!");
        } else {
            System.out.println("A matriz possui erros.");
        }
    }

    private void dica() {
        System.out.println("Digite a posicao para obter uma dica no formato (linha,coluna):");
        String entradaTexto = opcao.nextLine();
        String[] partes = entradaTexto.replace("(", "").replace(")", "").split(",");

        if (partes.length == 2) {
            int linha = Integer.parseInt(partes[0]) - 1;
            int coluna = Integer.parseInt(partes[1]) - 1;

            if (matriz.obterValor(linha, coluna) != 0) {
                System.out.println("A posicao jah esta preenchida. Nao eh possivel fornecer uma dica.");
            } else {
                System.out.print("Valores possiveis para (" + (linha + 1) + "," + (coluna + 1) + "): ");
                for (int valor = 1; valor <= 9; valor++) {
                    if (matriz.verifica_movimento_valido(linha, coluna, valor)) {
                        System.out.print(valor + " ");
                    }
                }
                System.out.println();
            }
        } else {
            System.out.println("Formato invalido!:/ Use (linha,coluna)! :D");
        }
    }
}