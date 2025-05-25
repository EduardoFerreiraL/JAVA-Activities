import java.util.Scanner;

public class trabalho2{

    public static void main(String[] args){

        Scanner scan = new Scanner(System.in);
        String [] nomes = new String[5];
        int escolha = -1;

        System.out.println("\nDigite 5 nomes:");        
        for (int i = 0; i < nomes.length; i++) {
            nomes [i] = scan.nextLine();

            if(i == 4){
                System.out.println("\n");
            }
        }
        while(escolha != 0){
            System.out.println("\nDigite o que deseja fazer:\n1 - Exibir todos os nomes\n2 - Busca nome por indice\n3 - Converter maiusculas\n4 - Mostrar o maior nome\n5 - Contar quantos nomes começam com uma letra\n0 - Sair \n");

            escolha = scan.nextInt();

            switch(escolha){
                case 0:
                    break;

                case 1:
                    imprimeNomes(nomes);
                    break;

                case 2:
                    System.out.println("\nDigite um Indece pra busca:");
                    int indice = scan.nextInt();
                    System.out.println("No indece " + indice + "° está o nome " + buscaPeloIndice(nomes, indice));
                    break;

                case 3:
                    System.out.println("\n");
                    for (int i = 0; i < nomes.length; i++) {
                        System.out.println(converterMaiusculas(nomes, i));                                              
                    }
                    break;

                case 4:
                    System.out.println("O maior nome é " + maiorNome(nomes) + "\n");                                         
                    break;

                case 5:
                    String procuraChar = scan.nextLine();
                    while(procuraChar.length() != 1){
                        System.out.println("\nDigite APENAS uma letra, para ser feita a busca:");
                        procuraChar = scan.nextLine();
                    }
                    System.out.println("\nExistem " + nomeComecaCom(nomes, procuraChar) + " nomes que começam com " + procuraChar.toUpperCase());
                    break;
            }
        }
        scan.close();
        
    }

    public static void imprimeNomes(String [] nomes) {
        for (int i = 0; i <nomes.length; i++) {
            System.out.println("O nome na posição " + (i+1) + "°" + " é " + nomes[i]);

            if(i == 4){
                System.out.println("\n");
            }
        }
    }

    public static String buscaPeloIndice(String [] nomes, int indice){
        return nomes[indice-1];
        
    }

    public static String converterMaiusculas(String [] nomes, int i){
        return nomes[i].toUpperCase();
    }

    public static String maiorNome(String [] nomes){
        int ContadorNome = 0;
        int maior = 0;
        for (int i = 0; i <nomes.length; i++) {
            String nomeAtual = nomes[i];
            int tamanhoAtual = nomeAtual.length();
            if(tamanhoAtual > ContadorNome){
                ContadorNome = tamanhoAtual;
                maior = i;
            }
        }
        return nomes[maior] ;
    }

    public static int nomeComecaCom(String [] nomes, String procuraChar){
        int contadorNomes = 0;
        
        for (int i = 0; i < nomes.length; i++) {
            String nomeAtual = nomes[i];
            if(nomeAtual != null){
                if (nomeAtual.toLowerCase().startsWith(String.valueOf(procuraChar).toLowerCase())){
                    contadorNomes ++;
                }
            }
        }

        return contadorNomes;
    }

}