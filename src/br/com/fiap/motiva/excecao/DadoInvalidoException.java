package br.com.fiap.motiva.excecao;

// Usado quando um dado recebido não é válido: nulo, vazio, negativo ou fora do formato

public class DadoInvalidoException extends RuntimeException {
    public DadoInvalidoException(String mensagem) {
        super(mensagem);
    }
}
