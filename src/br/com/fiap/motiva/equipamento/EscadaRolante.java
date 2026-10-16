package br.com.fiap.motiva.equipamento;

import br.com.fiap.motiva.excecao.DadoInvalidoException;
import br.com.fiap.motiva.excecao.RegraNegocioException;
import br.com.fiap.motiva.localizacao.Estacao;

// Escada rolante instalada em uma estação

public class EscadaRolante {

    private static final double VELOCIDADE_PADRAO = 0.5; // m/s
    private static final int PASSO_PADRAO = 400; // mm
    private final CodigoSap codigoSap;
    private final Estacao estacao;
    private final String modelo; // modelo da escada
    private Criticidade criticidade;
    private final int numeroDegraus;
    private final double velocidadeNominal; // m/s
    private final int passoNominal; // mm

    public EscadaRolante(CodigoSap codigoSap, Estacao estacao, String modelo, Criticidade criticidade,
                         int numeroDegraus, double velocidadeNominal, int passoNominal) {
        validarNaoNulo(codigoSap, "Código SAP");
        validarNaoNulo(estacao, "estação");
        validarNaoNulo(criticidade, "criticidade");
        validarEstacaoDoCodigo(codigoSap, estacao);

        this.codigoSap = codigoSap;
        this.estacao = estacao;
        this.modelo = validarTexto(modelo, "modelo").toUpperCase();
        this.criticidade = criticidade;
        this.numeroDegraus = validarPositivo(numeroDegraus, "número de degraus");
        this.velocidadeNominal = validarPositivo(velocidadeNominal, "velocidade nominal");
        this.passoNominal = validarPositivo(passoNominal, "passo nominal");
    }

    // Construtor com velocidade e passo padrão (0,5m/s e 400mm)
    public EscadaRolante(CodigoSap codigoSap, Estacao estacao, String modelo, Criticidade criticidade, int numeroDegraus) {
        this(codigoSap, estacao, modelo, criticidade, numeroDegraus, VELOCIDADE_PADRAO, PASSO_PADRAO);
    }

    public Estacao getEstacao() {
        return estacao;
    }

    public CodigoSap getCodigoSap() {
        return codigoSap;
    }

    public String getModelo() {
        return modelo;
    }

    public Criticidade getCriticidade() {
        return criticidade;
    }

    public double getVelocidadeNominal() {
        return velocidadeNominal;
    }

    public int getPassoNominal() {
        return passoNominal;
    }

    public int getNumeroDegraus() {
        return numeroDegraus;
    }

    // Altera a criticidade (A Motiva pode reclassificar a escada)
    public void setCriticidade(Criticidade criticidade) {
        validarNaoNulo(criticidade, "criticidade" );
        this.criticidade = criticidade;
    }

    // Indica se a escada é crítica
    public boolean isCritica() {
        return criticidade == Criticidade.CRITICA;
    }

    // Mesmo modelo e criticidade, usa na comparação entre pares
    public boolean isMesmoGrupoDe(EscadaRolante outra) {
        validarNaoNulo(outra, "escada para comparação");
        return modelo.equals(outra.modelo) && criticidade == outra.criticidade;
    }

    // Estação que a escada está
    public String getLocalizacao() {
        return estacao.getIdentificacao();
    }

    @Override
    public String toString() {
        return "Escada " + codigoSap + " - " + getLocalizacao() + " - " + modelo + " - " + criticidade;
    }

    // Garante que o valor não seja nulo
    private static void validarNaoNulo(Object valor, String campo) {
        if (valor == null) {
            throw new DadoInvalidoException("O campo " + campo + " é obrigatório.");
        }
    }

    // Garante que o texto não seja nulo ou vazio
    private static String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new DadoInvalidoException("O campo " + campo + " é obrigatório.");
        }
        return valor.trim();
    }

    // Garante que o número inteiro seja maior que zero
    private static int validarPositivo(int valor, String campo) {
        if (valor <= 0) {
            throw new DadoInvalidoException("O campo " + campo + " deve ser maior que zero.");
        }
        return valor;
    }

    private static double validarPositivo(double valor, String campo) {
        if (valor <= 0) {
            throw new DadoInvalidoException("O campo " + campo + " deve ser maior que zero.");
        }
        return valor;
    }

    // Regra de negócio: a sigla dentro do código precisa ser a mesma da estação correspondente
    private static void validarEstacaoDoCodigo(CodigoSap codigoSap, Estacao estacao) {
        if (!codigoSap.getSiglaEstacao().equals(estacao.getSigla())) {
            throw new RegraNegocioException("O código " + codigoSap + " não pertence à estação " + estacao.getSigla() + ".");
        }
    }
}
