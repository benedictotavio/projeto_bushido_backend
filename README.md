# Documentação da API Bushido

Esta é a documentação da API Aluno, que permite realizar operações relacionadas ao cadastramento de alunos.

```http 
https://projeto-bushido-backend.onrender.com
```

## Descrição

O desenvolvimento de uma API RESTful para cadastro de alunos em um projeto social de aulas de caratê demanda a utilização de tecnologias como Java, Spring Boot, Docker e MongoDB. Java oferece robustez e flexibilidade no desenvolvimento, enquanto Spring Boot simplifica a criação de aplicativos web. O Docker facilita a implantação e gerenciamento de contêineres, garantindo portabilidade e escalabilidade. MongoDB é ideal para armazenar dados flexíveis e não estruturados, com alta performance em consultas. Essas tecnologias combinadas garantem uma solução eficiente, escalável e altamente performática para o projeto.

# Autorização

Antes de tudo o usuario deve possuir um cadastro de admnistrador, o cadastro devera ser feito por outro administrador com acesso ilimitados. Apos efetuar o cadastro, o usuário devera relizar um _[login](#login)_ para receber o token de autorização para cada requisição.

## Sign up

#### Cadastro para novos admintradores que serão responsaveis por realizar o cadastro dos alunos.

```http
POST /api/V1/admin/signup
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

#### Retorna um token que sera utilizado nas demais requisições


```http
POST /api/V1/admin/login
```

```body
{
  email: string
  password: string
}
```


# Aluno

### Buscar aluno por RG

```http
GET /api/V1/aluno?rg={rg}
```

### Adicionar aluno

```http
POST /api/V1/aluno
```

```body
{
  "nome": "string",
  "genero": "M" ou "F",
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

## Adicionar falta ao aluno no dia atual

```http
POST /api/V1/aluno/adicionarFalta/{rg}
```

```body
{
  "motivo":"Aluno se encontra doente",
  "observacao":"Luis trouxe atestado"
}
```

## Adicionar falta ao aluno no dia atual

```http
POST /api/V1/aluno/adicionarFalta/{rg}/00000000000
```

```body
{
  "motivo":"Aluno se encontra doente",
  "observacao":"Luis trouxe atestado"
}
```

### Retirar falta do aluno

```http
DELETE /api/V1/aluno/retirarFalta/{rg}?data=dd-MM-yyyy
```

### Adicionar responsável ao aluno

```http
POST /api/V1/aluno/adicionarResponsavel/{rg}
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

### Remover responsável do aluno

```http
DELETE /api/V1/aluno/removerResponsavel/{rg}?cpf=string
```

### Adicionar deficiência ao aluno

```http
POST /api/V1/aluno/deficiencia/{rg}?deficiencia=string
```

### Remover deficiência do aluno

```http
DELETE /api/V1/aluno/deficiencia/{rg}?deficiencia=string
```

### Adicionar acompanhamento de saúde ao aluno

```http
POST /api/V1/aluno/acompanhamentoSaude/{rg}?acompanhamento=string
```

### Remover acompanhamento de saúde do aluno

```http
DELETE /api/V1/aluno/acompanhamentoSaude/{rg}
```
