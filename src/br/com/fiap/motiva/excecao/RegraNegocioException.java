package br.com.fiap.motiva.excecao;

/** Usado quando os dados são válidos um a um, mas a combinação fere uma regra de negócio.
 * (Exemplo: escada com código da estação PIN cadastrada em outra estação.)
 * */

public class RegraNegocioException extends RuntimeException {
    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
