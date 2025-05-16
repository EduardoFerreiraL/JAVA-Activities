
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        
        int numeros[] = new int[10];
        Scanner scan = new Scanner(System.in);
        boolean menu = true;

        for(int i= 0; i<numeros.length; i++){
           System.out.println("Digite um numero para o vetor"); 
           numeros[i] = scan.nextInt();
        }
        
        while (menu) { 
            
            System.out.println("\nOque você decide fazer? \n1 - Exibir todos os números\n2 - Calcular as somas dos número\n3 - Contar quantos números são pares\n4 - Exibir os números em ordem inverso\n5 - Buscar números\n6 - Sair");
            int escolha = scan.nextInt();
            
            switch(escolha){

                case 0 -> {
                    menu = false;
                    scan.close();
                }

                case 1 -> exibir(numeros);

                case 2 -> {
                    System.out.println("A soma dos números é " + somar(numeros,0));
                }

                case 3 -> {
                    System.out.println("A quantidade de pares é " + pares(numeros,0));
                }

                case 4 -> inverso(numeros);

                case 5 -> {
                    System.out.println("Digite a posicao que quer verificar: ");
                    int posicao = scan.nextInt();
                    buscar(numeros, posicao);
                }             
            }
            
        }}

        public static void exibir(int numeros[]){
            for(int i = 0; i < numeros.length; i++){
                System.out.println("Os números do vetor são " + numeros[i]);
            }

        }

        public static int somar(int numeros[],int tamanho){

            if (tamanho >= numeros.length){
                return 0;
            }        
            return numeros[tamanho] + somar(numeros, tamanho + 1);
        }

        public static int pares(int numeros[],int tamanho){
            if (tamanho >= numeros.length){
                return 0;
            }
            if (numeros[tamanho] % 2 != 0){
                return 0 + pares(numeros, tamanho + 1);
            }      
            return 1 + pares(numeros, tamanho + 1);
        }

        public static void inverso(int numeros[]){
            System.out.printf("O numero inverso é ");
            for(int i = numeros.length -1; i >=0; i--){
                System.out.print(numeros[i] + " ");
            }
         
        }

        public static void buscar(int numeros[], int posicao){
            for(int i = numeros.length -1; i <numeros.length; i++){
                if(numeros[i] == posicao){
                    System.out.print("A posição dele é " + i);
                }
            }
        }
    
    }

