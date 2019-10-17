import com.gerenciador.Gerenciador;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner ler = new Scanner(System.in);
        Gerenciador gerenciador = new Gerenciador();
        int iniciar;

        while(true)
        {
            System.out.println("\n============== LEILÃO DE ENTREGAS =============");
            System.out.println("1 - Entradas ");
            System.out.println("2 - Entregas ");
            System.out.println("0 - Sair ");
            iniciar = ler.nextInt();

            if(iniciar == 1)
            {
                gerenciador.addNo();
            }

        }
    }
}