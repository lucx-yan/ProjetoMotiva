package br.com.fiap.motiva.excecao;

/** Usado quando um dado recebido não é válido: nulo, vazio, negativo ou fora do formato.
 * (Exemplo: escada com zero degraus ou sigla de estação em branco.)
 * */

public class DadoInvalidoException extends RuntimeException {
    public DadoInvalidoException(String mensagem) {
        super(mensagem);
    }
}
