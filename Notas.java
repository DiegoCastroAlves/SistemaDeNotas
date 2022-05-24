import java.util.Objects;
import java.util.Scanner;

class Notas {

    static Scanner console = new Scanner(System.in);

    static final int TOTAL_AVALIACOES = 3;
    static final String[] NOMES_AVALIACOES = { "A1", "A2", "A3" };
    static final double[] NOTA_MAX_AVALIACOES = { 30.00, 30.00, 40.00 };

    static double[] notas = new double [TOTAL_AVALIACOES];
    static String notaRecuperacao = "";

    /**
     * Ler uma nota do usuário
     * @param mensagem O texto que aparecerá na tela
     * @return um número double representando a nota.
     */
    static double lerNota(String mensagem, double notaMaxima) {

        double nota;

        do {

            System.out.printf("%s = ", mensagem);
            nota = console.nextDouble();

        } while (nota < 0.00 || nota > notaMaxima);

        return nota;
    }


    /**
     * Atualiza o valor da respectiva nota do estudante
     * @param indiceNota um número inteiro representando o índice (posição) da nota no vetor
     */
    static void atualizarNota(int indiceNota) {

        System.out.println();
        notas[indiceNota] = lerNota(NOMES_AVALIACOES[indiceNota], NOTA_MAX_AVALIACOES[indiceNota]);

    } // Fim do método atualizarNota


    /**
     * @param notaFinal A soma de todas as avalições feita pelo estudante ao longo do semestre
     * @return uma string representando o status final do estudante, são eles: APROVADO, REPROVADO, EM RECUPERAÇÃO.
     */
    static String avaliarSituacao(double notaFinal) {

        if(notaFinal < 30)
            return "REPROVADO";
        else if (notaFinal < 70 && notaRecuperacao.equals(""))
            return "EM RECUPERAÇÃO";
        //recuperacao diferente de vazio significa que o estudante já fez a recuperação
        else if (notaFinal < 70)
            return "REPROVADO";
        else
            return "APROVADO";


    } // Fim do método avaliarSituacao()


    /**
     * Mostra na tela um relatório das notas do estudante
     */

    //faça um metodo para pegar a menor nota e retorne o indice da nota
    static int menorNota(){
        int menorNota = 0;
        for(int i = 0; i < notas.length; i++){
            if(notas[i] < notas[menorNota] && !Objects.equals(NOMES_AVALIACOES[i], "A3")){
                menorNota = i;
            }
        }
        return menorNota;
    }

    static void recuperacao(int menorNota){
        notaRecuperacao = "FEITA";

        System.out.printf("\n\nInfelizmente você precisa de recuperação, sua menor nota foi %s: %.2f", NOMES_AVALIACOES[menorNota], notas[menorNota]);
        System.out.printf("\nProva %s será substituida por uma nova prova 'AI'\n\t***AI***", NOMES_AVALIACOES[menorNota]);
        atualizarNota(menorNota);
        System.out.printf("\nO estudante está %s\n", avaliarSituacao(somarNotas()));

    }
    static double somarNotas(){
        double notaFinal = 0.0;
        for (double nota : notas) {
            notaFinal += nota;
        }
        return notaFinal;
    }
    static void mostrarNotas() {

        System.out.println("\n\t\tNOTAS");
        System.out.println();

        for (int i = 0; i < TOTAL_AVALIACOES; i++) {
            System.out.printf("Avaliação %s = %.2f pts\n", NOMES_AVALIACOES[i], notas[i]);
        }
        double notaFinal = somarNotas();
        String situacao = avaliarSituacao(notaFinal);

        System.out.printf("\n  Nota Final = %.2f pts", notaFinal);
        System.out.printf("\n  Situação = %s", situacao);
        System.out.printf("\n  Media = %.2f", calcularMedia(notaFinal));
        System.out.printf("\n  Maior nota = %s", maiorNota());


        if (situacao.equals("EM RECUPERAÇÃO") && notaRecuperacao.equals("")) {
            int indiceMenorNota = menorNota();
            recuperacao(indiceMenorNota);
        }

    } // Fim do método mostrarNotas()


    /**
     * Exibe o menu principal da aplicação
     */
    static void mostrarMenu() {

        System.out.println("\n\n");
        System.out.println("\t\tMENU");
        System.out.println();

        System.out.println("[1] Cadastrar Notas A1");
        System.out.println("[2] Cadastrar Nota A2");
        System.out.println("[3] Cadastrar Nota A3");
        System.out.println("[4] Mostrar Notas");
        System.out.println("[0] SAIR");

        System.out.print("\nDigite uma opção:  ");
        byte opcao = console.nextByte();


        switch (opcao) {
            case 0 -> System.exit(0);
            case 1 -> atualizarNota(0);
            case 2 -> atualizarNota(1);
            case 3 -> atualizarNota(2);
            case 4 -> mostrarNotas();
            default -> mostrarMenu();
        }

        mostrarMenu();

    } // Fim do método mostrarMenu()

    static double calcularMedia(double notaFinal) {
        return notaFinal / notas.length;
    }

    static String maiorNota() {
        double maiorNota = 0.0;
        String nomeMaiorNota = "";
        for (int i = 0; i < TOTAL_AVALIACOES; i++){
            if (notas[i] > maiorNota){
                maiorNota = notas[i];
                nomeMaiorNota = NOMES_AVALIACOES[i] + ": " + maiorNota;
            }
        }
        return nomeMaiorNota;
    }



    public static void main(String[] args) {

        mostrarMenu();

    } // Fim do método main();

} // Fim da classe Main