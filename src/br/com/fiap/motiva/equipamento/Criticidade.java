package br.com.fiap.motiva.equipamento;

/** Criticidade da escada rolante, como aparece no dataset (critica / nao critica).
 *
 * @author Gustavo Macedo Daniel
 * @author João Victor Melo Santos
 * @author Murilo Jeronimo Ferreira Nunes
 * @author Vinicius Kozonoe Guaglini
 * @author Yan Lucas Gonçalves da Silva
 * */

public enum Criticidade {

    CRITICA("Crítica"),
    NAO_CRITICA("Não crítica");

    private final String descricao;

    Criticidade(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
