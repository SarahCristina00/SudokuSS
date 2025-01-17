/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sudokuss;

/**
 *
 * @author Sarah
 */

import java.util.HashSet;
import java.util.Set;


public class MatrizSudoku {
    private int[][] matriz;
    private Set<String> valores_fixos;

    //contrutor da Matriz/base do jogo
    public MatrizSudoku() {
        matriz = new int[9][9];
        valores_fixos = new HashSet<>();
    }

    public void aloca_valor_fixo(int linha, int coluna, int valor) {
        matriz[linha][coluna] = valor;
        valores_fixos.add(linha + "," + coluna);
    }

    public void aloca_valor(int linha, int coluna, int valor) {
        matriz[linha][coluna] = valor;
    }

    public int obterValor(int linha, int coluna) {
        return matriz[linha][coluna];
    }

    public boolean verifica_valor_fixo(int linha, int coluna) {
        return valores_fixos.contains(linha + "," + coluna);
    }

    public boolean verifica_movimento_valido(int linha, int coluna, int valor) {
    for (int i = 0; i < 9; i++) {
        if ((i != coluna && matriz[linha][i] == valor) || (i != linha && matriz[i][coluna] == valor)) { 
            return false;
        }
    }

    int inicio_linha = (linha / 3) * 3;
    int inicio_coluna = (coluna / 3) * 3;
    for (int i = inicio_linha; i < inicio_linha + 3; i++) {
        for (int j = inicio_coluna; j < inicio_coluna + 3; j++) {
            if ((i != linha || j != coluna) && matriz[i][j] == valor) {
                return false;
            }
        }
    }
    return true;
}

    
   public boolean venceu() {
    // Verifica se o tabuleiro está completo e com valores válidos
    for (int i = 0; i < 9; i++) {
        for (int j = 0; j < 9; j++) {
            int valor = matriz[i][j];
            if (valor == 0) {
                return false; 
            }
            // Verifica se o valor atual é válido
            if (!verifica_movimento_valido(i, j, valor)) {
                return false; 
            }
        }
    }
    return true; 
}



public void imprimi_matriz() {
    for (int i = 0; i < 9; i++) {
        if (i % 3 == 0 && i != 0) {
            // separa os blocos
            System.out.println("------<3------<3-------");
        }
        for (int j = 0; j < 9; j++) {
            if (j % 3 == 0 && j != 0) {
                // separa as colunas
                System.out.print(" | ");
            }
            System.out.print((matriz[i][j] == 0 ? " " : matriz[i][j]) + " ");
        }
        System.out.println();
    }
}
}
