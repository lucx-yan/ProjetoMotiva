package br.com.fiap.motiva;
import br.com.fiap.motiva.localizacao.Linha;

public class Main {
    public static void main(String[] args) {
        Linha linha4 = new Linha(4, "Amarela", "ViaQuatro");
        System.out.println(linha4);
        System.out.println(linha4.getNomeCompleto());

        try {
            new Linha(-4, "Amarela", "ViaQuatro");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro esperado: " + e.getMessage());
        }
    }
}
