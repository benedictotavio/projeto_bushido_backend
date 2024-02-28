# Documentação da API Bushido

Esta é a documentação da API Aluno, que permite realizar operações relacionadas ao cadastramento de alunos.

```http
https://projeto-bushido-backend.onrender.com/api/V1
```

## Descrição

O desenvolvimento de uma API RESTful para cadastro de alunos em um projeto social de aulas de caratê demanda a utilização de tecnologias como Java, Spring Boot, Docker e MongoDB. Java oferece robustez e flexibilidade no desenvolvimento, enquanto Spring Boot simplifica a criação de aplicativos web. O Docker facilita a implantação e gerenciamento de contêineres, garantindo portabilidade e escalabilidade. MongoDB é ideal para armazenar dados flexíveis e não estruturados, com alta performance em consultas. Essas tecnologias combinadas garantem uma solução eficiente, escalável e altamente performática para o projeto.

# Autorização

Antes de tudo o usuario deve possuir um cadastro de admnistrador, o cadastro devera ser feito por outro administrador com acesso ilimitados. Apos efetuar o cadastro, o usuário devera relizar um _[login](#login)_ para receber o token de autorização para cada requisição.

## Sign up

#### Cadastro para novos admintradores que serão responsaveis por realizar o cadastro dos alunos.

```http
POST /admin/signup
```

```body
{
  "nome": string,
  "email": string,
  "cargo": string,
  "senha": string,
  "role": string
}
```

## Login

Retorna um token que sera utilizado nas demais requisições

## Request
```http
POST /admin/login
```

```body
{
  email: string
  password: string
}
```

## Response

<p>
<details>
<summary><i>200</i></summary>

```json
    {
      "token":"string"
    }
```

</details>

<details>
<summary><i>403</i></summary>

```json
{
  "message": "Senha ou e-mail inválido",
  "code": 403
}
```

</details>

</details>

</p>

# Aluno

## Buscar aluno por RG

Retorna um objeto de uma aluno com base no rg passado uma Query String como parâmetro.

### Request

- **rg:** string

```http
GET /aluno?rg={rg}
```

### Response

<p>
<details>
<summary><i>200</i></summary>

```json
    {
      "nome": "string",
      "genero": "M" | "F",
      "dataNascimento": "dd-MM-yyy",
      "dadosSociais":{
        "bolsaFamilia": true,
        "auxilioBrasil": false,
        "imovel": "CEDIDO", -> enum
        "numerosDePessoasNaCasa": 0,
        "contribuintesDaRendaFamiliar": 0,
        "alunoContribuiParaRenda": true,
        "rendaFamiliarEmSalariosMinimos": 0
      },
      "dadosEscolares": {
        "turno": "MANHA", -> enum
        "escola": "string",
        "serie": 0
      },
      "dataPreenchimento": "2023-01-01T12:00:00",
      "endereco":{
        "cidade": "string",
        "estado": "string",
        "cep":"string",
        "numero":"string"
      },
      "rg": "{rg}",
      "responsaveis": [
          {
          "nome":"string",
          "cpf": "string",
          "telefone":"string",
          "email":"string",
          "filiacao":"PAI" -> enum
          }
        ],
      "graduacao": {
            "kyu": 0,
            "frequencia": 0
        },
        "historicoSaude": {
        "tipoSanguineo": "A_POSITIVO",
        "fatorRh": "POSITIVO",
        "usoMedicamentoContinuo": {
          "resposta": "string",
          "qualMedicamento": "string"
        },
        "cirurgia": {
          "resposta": true,
          "tipo": "string"
        },
        "alergia": {
          "resposta": true,
          "tipo": "string"
        },
        "doencaCronica": {
          "resposta": true,
          "tipo": "string"
        },
        "deficiencia":["string"],
        "acompanhamentoSaude":["string"]
      }
  }
```

</details>

<details>
<summary><i>400</i></summary>

```json
{
  "message": "Rg não foi encontrado.",
  "code": 400
}
```

</details>

</details>

</p>

## Adicionar aluno

Adiciona um aluno baseado nos dados cadastrais abaixo.

| Campo                                                 | Tipo             | Descrição                                         |
| ----------------------------------------------------- | ---------------- | :------------------------------------------------ |
| nome                                                  | string           | Nome completo do aluno                            |
| genero                                                | string           | Gênero do aluno (M ou F)                          |
| dataNascimento                                        | string           | Data de nascimento do aluno (dd-MM-yyyy)          |
| dadosSociais                                          | objeto           | Dados sociais do aluno                            |
| dadosSociais.bolsaFamilia                             | boolean          | Indica se o aluno recebe Bolsa Família            |
| dadosSociais.auxilioBrasil                            | boolean          | Indica se o aluno recebe Auxílio Brasil           |
| dadosSociais.imovel                                   | string (enum)    | </br>CEDIDO</br>ALUGADO</br>PROPRIO</li></ul>     |
| dadosSociais.numerosDePessoasNaCasa                   | integer          | Número de pessoas na casa do aluno                |
| dadosSociais.contribuintesDaRendaFamiliar             | integer          | Número de contribuintes da renda familiar         |
| dadosSociais.alunoContribuiParaRenda                  | boolean          | Indica se o aluno contribui para a renda familiar |
| dadosSociais.rendaFamiliarEmSalariosMinimos           | integer          | Renda familiar em salários mínimos                |
| dadosEscolares                                        | objeto           | Dados escolares do aluno                          |
| dadosEscolares.turno                                  | string (enum)    | Turno escolar do aluno (MANHA ou outro)           |
| dadosEscolares.escola                                 | string           | Nome da escola do aluno                           |
| dadosEscolares.serie                                  | integer          | Série em que o aluno está matriculado             |
| dataPreenchimento                                     | string           | Data e hora do preenchimento do formulário        |
| endereco                                              | objeto           | Endereço do aluno                                 |
| endereco.cidade                                       | string           | Cidade do aluno                                   |
| endereco.estado                                       | string           | Estado do aluno                                   |
| endereco.cep                                          | string           | CEP do aluno                                      |
| endereco.numero                                       | string           | Número do endereço do aluno                       |
| rg                                                    | string           | Número do RG do aluno                             |
| responsaveis                                          | array de objetos | Responsáveis legais do aluno                      |
| responsaveis.nome                                     | string           | Nome do responsável legal                         |
| responsaveis.cpf                                      | string           | CPF do responsável legal                          |
| responsaveis.telefone                                 | string           | Telefone do responsável legal                     |
| responsaveis.email                                    | string           | Email do responsável legal                        |
| responsaveis.filiacao                                 | string (enum)    | Filiação do responsável legal (PAI ou outro)      |
| graduacao                                             | objeto           | Informações sobre a graduação do aluno            |
| graduacao.kyu                                         | integer          | Nível de graduação (Kyu) do aluno                 |
| graduacao.frequencia                                  | integer          | Frequência nas aulas de caratê                    |
| historicoSaude                                        | objeto           | Histórico de saúde do aluno                       |
| historicoSaude.tipoSanguineo                          | string (enum)    | Tipo sanguíneo do aluno                           |
| historicoSaude.fatorRh                                | string (enum)    | Fator Rh do aluno                                 |
| historicoSaude.usoMedicamentoContinuo                 | objeto           | Uso de medicamento contínuo pelo aluno            |
| historicoSaude.usoMedicamentoContinuo.resposta        | string           | Resposta sobre uso de medicamento contínuo        |
| historicoSaude.usoMedicamentoContinuo.qualMedicamento | string           | Nome do medicamento contínuo                      |
| historicoSaude.cirurgia                               | objeto           | Informações sobre cirurgia do aluno               |
| historicoSaude.cirurgia.resposta                      | boolean          | Indica se o aluno fez alguma cirurgia             |
| historicoSaude.cirurgia.tipo                          | string           | Tipo de cirurgia realizada, se houver             |
| historicoSaude.alergia                                | objeto           | Informações sobre alergias do aluno               |
| historicoSaude.alergia.resposta                       | boolean          | Indica se o aluno tem alguma alergia              |
| historicoSaude.alergia.tipo                           | string           | Tipo de alergia, se houver                        |
| historicoSaude.doencaCronica                          | objeto           | Informações sobre doenças crônicas do aluno       |
| historicoSaude.doencaCronica.resposta                 | boolean          | Indica se o aluno tem alguma doença crônica       |
| historicoSaude.doencaCronica.tipo                     | string           | Tipo de doença crônica, se houver                 |
| historicoSaude.deficiencia                            | array de strings | Deficiências do aluno                             |
| historicoSaude.acompanhamentoSaude                    | array de strings | Acompanhamento médico do aluno                    |

### Request

```http
POST /aluno
```

```body
{
  "nome": "string",
  "genero": "M" ou "F",
  "dataNascimento": "dd-MM-yyyy",
  "dadosSociais":{
    "bolsaFamilia": true,
    "auxilioBrasil": false,
    "imovel": "CEDIDO", -> enum
    "numerosDePessoasNaCasa": 0,
    "contribuintesDaRendaFamiliar": 0,
    "alunoContribuiParaRenda": true,
    "rendaFamiliarEmSalariosMinimos": 0
  },
   "dadosEscolares": {
    "turno": "MANHA", -> enum
    "escola": "string",
    "serie": 0
  },
  "dataPreenchimento": "2023-01-01T12:00:00",
  "endereco":{
    "cidade": "string",
    "estado": "string",
    "cep":"string",
    "numero":"string"
  },
  "rg": "{rg}",
  "responsaveis": [
      {
      "nome":"string",
      "cpf": "string",
      "telefone":"string",
      "email":"string",
      "filiacao":"PAI" -> enum
      }
    ],
   "graduacao": {
        "kyu": 0,
        "frequencia": 0
    },
    "historicoSaude": {
    "tipoSanguineo": "A_POSITIVO",
    "fatorRh": "POSITIVO",
    "usoMedicamentoContinuo": {
      "resposta": "string",
      "qualMedicamento": "string"
    },
    "cirurgia": {
      "resposta": true,
      "tipo": "string"
    },
    "alergia": {
      "resposta": true,
      "tipo": "string"
    },
    "doencaCronica": {
      "resposta": true,
      "tipo": "string"
    },
    "deficiencia":["string"],
    "acompanhamentoSaude":["string"]
  }
}
```

### Response

<p>
<details>
<summary><i>200</i></summary>

```json
{
  "rg_aluno": "string",
  "code": 200
}
```

</details>

<details>
<summary><i>400</i></summary>

```json
{
  "message": "string",
  "code": 400
}
```

</details>

</details>

</p>

## Adicionar falta ao aluno no dia atual

Adciona uma falta ao aluno na data no momento exato em que a requisição foi feita.

### Request

- **rg:** string

```http
POST /aluno/adicionarFalta/{rg}
```

```body
{
  "motivo":"Aluno se encontra doente",
  "observacao":"Luis trouxe atestado"
}
```
### Response

<p>
<details>
<summary><i>200</i></summary>

```json
    {
      "qtd_faltas":0,
      "code":200
    }
```

</details>

<details>
<summary><i>400</i></summary>

```json
{
  "message": "Data inválida",
  "code": 400
}
```

</details>

</details>

</p>

## Adicionar falta ao aluno na data especifica

### Request
```http
POST /aluno/adicionarFalta/{rg}/00000000000
```

```body
{
  "motivo":"Aluno se encontra doente",
  "observacao":"Luis trouxe atestado"
}
```

### Response

<p>
<details>
<summary><i>200</i></summary>

```json
    {
      "qtd_faltas":0,
      "code":200
    }
```

</details>

<details>
<summary><i>400</i></summary>

```json
{
  "message": "Data inválida",
  "code": 400
}
```

</details>

</details>

</p>

## Retirar falta do aluno

```http
DELETE /aluno/retirarFalta/{rg}?data=dd-MM-yyyy
```

## Adicionar responsável ao aluno

```http
POST /aluno/adicionarResponsavel/{rg}
```

```body
{
  "nome": "string",
  "cpf": "cpf",
  "telefone": "string",
  "email": "string",
  "filiacao": "MAE" -> enum
}
```

## Remover responsável do aluno

```http
DELETE /aluno/removerResponsavel/{rg}?cpf=string
```

## Adicionar deficiência ao aluno

```http
POST /aluno/deficiencia/{rg}?deficiencia=string
```

## Remover deficiência do aluno

```http
DELETE /aluno/deficiencia/{rg}?deficiencia=string
```

## Adicionar acompanhamento de saúde ao aluno

```http
POST /aluno/acompanhamentoSaude/{rg}?acompanhamento=string
```

## Remover acompanhamento de saúde do aluno

```http
DELETE /aluno/acompanhamentoSaude/{rg}
```
