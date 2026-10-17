# ProjetoMotiva: manutenção baseada na condição de escadas rolantes

Projeto do **Challenge FIAP 2026-2027**, em parceria com a **Motiva Trilhos**, sobre a manutenção das escadas rolantes da **Linha 4-Amarela** (ViaQuatro).

Hoje a manutenção das escadas segue principalmente o calendário (rotinas P1, P6, P12...), mesmo quando o equipamento não mostra sinal de desgaste. A proposta do grupo é apoiar uma manutenção **baseada na condição**: usar dados de sensores e o histórico real do SAP para decidir quando e onde intervir, sem abrir mão da segurança dos passageiros.

Este repositório traz o **modelo de domínio em Java (DDD)** da primeira sprint: localização, cadastro das escadas e histórico de manutenção preventiva e corretiva.

## Integrantes

| Nome | RM |
|---|---|
| Gustavo Macedo Daniel | 567594 |
| João Victor Melo Santos | 566640 |
| Murilo Jeronimo Ferreira Nunes | 560641 |
| Vinicius Kozonoe Guaglini | 567264 |
| Yan Lucas Gonçalves da Silva | 567046 |

## Estrutura

```
src/br/com/fiap/motiva/
├── Main.java          storyboard: o fluxo do sistema de ponta a ponta
├── equipamento/       CodigoSap (Value Object), Criticidade (enum), EscadaRolante
├── excecao/           DadoInvalidoException, RegraNegocioException
├── localizacao/       Linha, Estacao
├── manutencao/        Manutencao (abstrata), ManutencaoPreventiva, ManutencaoCorretiva, RotinaPreventiva (enum)
└── teste/             LocalizacaoTeste, EquipamentoTeste, ManutencaoTeste
```

## Como executar

**IntelliJ IDEA:** abra a pasta do projeto (JDK 17 ou superior) e rode a `Main`. Para os testes isolados, rode cada classe do pacote `teste`.

**Terminal (Git Bash):**

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out br.com.fiap.motiva.Main
```

## Requisitos da sprint

| Requisito | Onde está |
|---|---|
| Exceções personalizadas | `excecao/DadoInvalidoException` (dado inválido) e `excecao/RegraNegocioException` (regra de negócio quebrada) |
| Value Object com validação | `equipamento/CodigoSap`: imutável, valida o formato e compara pelo valor (`equals`/`hashCode`) |
| Enums | `equipamento/Criticidade` e `manutencao/RotinaPreventiva` |
| Construtores | `EscadaRolante` (dois construtores, com `this(...)`) e `Manutencao` (construtor `protected` chamado pelas filhas com `super(...)`) |
| Apenas getters e setters necessários | setters só no que pode mudar sem a entidade virar outra: `Linha.setConcessionaria`, `Estacao.setNome`, `EscadaRolante.setCriticidade` |
| Classes driver para testes isolados | pacote `teste`: uma classe por pacote, cada uma com o próprio `main` e os casos de erro |
| Storyboard em comentários | topo da `Main.java` |

Também foram usados herança, classe abstrata e polimorfismo (`Manutencao` e suas filhas) e sobrecarga de métodos (`validarPositivo` para `int` e `double`).

## Regras de negócio

- O código SAP da escada precisa seguir o formato `SP04-6-ECR-PIN-ER32`.
- A sigla da estação dentro do código precisa ser a da estação onde a escada está.
- Uma manutenção preventiva só aceita rotinas do plano da criticidade da escada: críticas P1/P6/P12/P36/P72; não críticas P1/P9/P18/P36 (PMP-6ECR-0001).
- A data de uma manutenção não pode estar no futuro.
- Número de degraus, velocidade e passo precisam ser positivos; horas de parada não podem ser negativas.

## Sobre os dados

- **Reais:** as escadas e os códigos SAP vêm do dataset do grupo, montado sobre o cadastro da Motiva. As manutenções corretivas da `Main` e dos testes são **notas reais do SAP** da escada SP04-6-ECR-PIN-ER32.
- **Exemplo:** a manutenção preventiva usada nos testes é fictícia e está marcada como `DEMO`, porque o dataset não traz ordens preventivas.

## Decisões de projeto

- **Diagnóstico opcional na corretiva:** objeto, sintoma, causa e ação podem vir em branco, porque no histórico real do SAP muitas notas não têm esses campos. O método `isDiagnosticoCompleto()` aponta quais notas estão incompletas.
- **Comparação entre pares por modelo e criticidade:** o dataset não traz o fabricante de cada escada com fonte, então o grupo de comparação usa o modelo (TUGELA ou ESCAL) e a criticidade.
- **Valores padrão:** as 18 escadas do dataset têm 0,5 m/s e 400 mm de passo, por isso a `EscadaRolante` tem um construtor que já usa esses valores.
- **Rotina PD48:** fica de fora até o grupo confirmar com a Motiva onde ela entra, porque não aparece no plano PMP.

## Próximas etapas

DTOs, controllers, services e banco de dados (não avaliados nesta sprint) e as classes das medições do StepScan.

## Tecnologias

Java 17 · IntelliJ IDEA · Git e GitHub, com commits semânticos (Conventional Commits)