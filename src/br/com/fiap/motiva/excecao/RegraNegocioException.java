package br.com.fiap.motiva.excecao;

// Usado quando os dados são válidos, mas a combinação fere uma regra de negócio.

public class RegraNegocioException extends RuntimeException {
    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
