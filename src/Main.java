import java.util.Scanner;

public class Main {

    private static char[][] tabuleiro = new char[3][3];
    private static String jogador1, jogador2;
    private static int pontosJogador1 = 0, pontosJogador2 = 0, totalPartidas = 0;
    private static char jogadorAtual;
    private static String nomeJogador1, nomeJogador2;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("##################################################");
        System.out.println("#     Seja Bem vindo ao Jogo da Velha            #");
        System.out.println("##################################################");
        System.out.println();



        String entrada = "";
        while (!"q".equalsIgnoreCase(entrada)) {
            System.out.println("Digite a opção desejada:");
            System.out.println("1 - Jogar");
            System.out.println("2 - Regras");
            System.out.println("3 - Sobre");
            System.out.println("q - Sair");

            entrada = sc.nextLine();

            switch (entrada) {
                case "1":
                    jogar();
                    break;
                case "2":
                    regras();
                    break;
                case "3":
                    sobre();
                    break;
                case "q":
                    System.out.println("Saindo do jogo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }

        sc.close();
    }


    private static void jogar() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o nome do Jogador 1:");
        jogador1 = sc.nextLine();
        System.out.println("Digite o nome do Jogador 2:");
        jogador2 = sc.nextLine();

        int sortearJogador = (int) (Math.random() * 2);

        if (sortearJogador == 0) {
            nomeJogador1 = jogador1;
            nomeJogador2 = jogador2;
        } else {
            nomeJogador1 = jogador2;
            nomeJogador2 = jogador1;
        }

        System.out.println(nomeJogador1 + " será o Jogador 1 (X)");
        System.out.println(nomeJogador2 + " será o Jogador 2 (O)");

        inicializarTabuleiro();
        jogadorAtual = 'X';
        boolean jogoAtivo = true;
        String vencedor = null;

        while (jogoAtivo) {
            imprimirTabuleiro();
            System.out.println("Vez do jogador " + getNomeJogadorAtual() + " (" + jogadorAtual + ")");
            System.out.print("Digite sua jogada (LinhaColunaSímbolo) ou 'q' para sair: ");
            String entrada = sc.nextLine();

            if (entrada.equalsIgnoreCase("q")) {
                jogoAtivo = false;
                break;
            }

            if (validarEntrada(entrada)) {
                int linha = entrada.charAt(0) - '0';
                int coluna = entrada.charAt(1) - '0';
                char simbolo = entrada.charAt(2);

                if (tabuleiro[linha][coluna] == ' ' && simbolo == jogadorAtual) {
                    tabuleiro[linha][coluna] = simbolo;

                    if (verificarVencedor(simbolo)) {
                        imprimirTabuleiro();
                        System.out.println("O vencedor é: " + getNomeJogadorAtual());
                        atualizarPlacar(simbolo);
                        vencedor = getNomeJogadorAtual();
                        jogoAtivo = false;
                    } else if (tabuleiroCompleto()) {
                        imprimirTabuleiro();
                        System.out.println("Empate!");
                        jogoAtivo = false;
                    } else {
                        alternarJogador();
                    }
                } else {
                    System.out.println("Jogada inválida! Tente novamente.");
                }
            } else {
                System.out.println("Entrada inválida! Tente novamente.");
            }
        }

        if (vencedor != null) {
            System.out.println("Placar atual:");
            System.out.println(nomeJogador1 + ": " + pontosJogador1);
            System.out.println(nomeJogador2 + ": " + pontosJogador2);
            System.out.println("Total de partidas: " + totalPartidas);
        }
    }

    private static void inicializarTabuleiro() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tabuleiro[i][j] = ' ';
            }
        }
    }

    private static void imprimirTabuleiro() {
        System.out.println("  0 1 2");
        for (int i = 0; i < 3; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < 3; j++) {
                System.out.print(tabuleiro[i][j]);
                if (j < 2) System.out.print("|");
            }
            System.out.println();
            if (i < 2) System.out.println("  -----");
        }
    }

    private static String getNomeJogadorAtual() {
        return jogadorAtual == 'X' ? nomeJogador1 : nomeJogador2;
    }

    private static void alternarJogador() {
        jogadorAtual = (jogadorAtual == 'X') ? 'O' : 'X';
    }

    private static boolean validarEntrada(String entrada) {
        if (entrada.length() != 3) return false;
        if (!Character.isDigit(entrada.charAt(0)) || !Character.isDigit(entrada.charAt(1))) return false;
        if (entrada.charAt(2) != jogadorAtual) return false;

        int linha = entrada.charAt(0) - '0';
        int coluna = entrada.charAt(1) - '0';
        return linha >= 0 && linha < 3 && coluna >= 0 && coluna < 3;
    }

    private static boolean verificarVencedor(char simbolo) {
        for (int i = 0; i < 3; i++) {
            if (tabuleiro[i][0] == simbolo && tabuleiro[i][1] == simbolo && tabuleiro[i][2] == simbolo) return true;
            if (tabuleiro[0][i] == simbolo && tabuleiro[1][i] == simbolo && tabuleiro[2][i] == simbolo) return true;
        }
        if (tabuleiro[0][0] == simbolo && tabuleiro[1][1] == simbolo && tabuleiro[2][2] == simbolo) return true;
        return tabuleiro[0][2] == simbolo && tabuleiro[1][1] == simbolo && tabuleiro[2][0] == simbolo;
    }

    private static boolean tabuleiroCompleto() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro[i][j] == ' ') return false;
            }
        }
        return true;
    }

    private static void atualizarPlacar(char simbolo) {
        if (simbolo == 'X') {
            pontosJogador1++;
        } else {
            pontosJogador2++;
        }
        totalPartidas++;
    }

    private static void regras() {
        System.out.println("Regras do jogo:");
        System.out.println(" - O jogo é jogado em um tabuleiro 3x3");
        System.out.println(" - O jogador que conseguir alinhar 3 símbolos iguais vence");
        System.out.println(" - O jogador 1 começa com X ");
        System.out.println(" - O jogador 2 joga com O ");
        System.out.println();
        System.out.println("Como jogar:");
        System.out.println("Exemplo de entradas válidas para o jogador 1: 01X");
        System.out.println("Exemplo de entradas válidas para o jogador 2: 01O");
    }

    private static void sobre() {
        System.out.printf("- Me chamo Igor e sou o desenvolvedor deste jogo. A ideia do Jogo foi a partir de um desafio GameJam." +
                " Trata-se de um jogo da velha simples, onde o jogador 1 joga com X e o jogador 2 joga com O.\n- O jogador" +
                " que conseguir alinhar 3 símbolos iguais vence. Foi um desafio do modulo de logica de programação do curso " +
                "da B3 em conjunto com a ADA, o Jogo foi desenvolvido em Java.\n- O respositorio do desafio se encontra " +
                "no github: https://gist.github.com/rafarocha/e39981c9edfd522d304d5c51a3ef22f5 Espero que gostem do " +
                "jogo e se divirtam! \n- Caso queiram dar uma olhada no repositorio desse jogo, " +
                "segue o link:https://github.com/igorujiie/GameJam-Jovo-da-Velha.git\n\n");


    }
}

