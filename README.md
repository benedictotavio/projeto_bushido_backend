# Documentação da API Aluno

Esta é a documentação da API Aluno, que permite realizar operações relacionadas aos alunos.

## Endpoints

### Buscar aluno por RG

```http
GET http://localhost:8080/api/V1/aluno?rg={rg} HTTP/1.1
```

### Adicionar aluno

``` http
POST http://localhost:8080/api/V1/aluno HTTP/1.1
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
## Adicionar falta ao aluno

```http
POST http://localhost:8080/api/V1/aluno/adicionarFalta/{rg} HTTP/1.1
```
```body
{
  "motivo":"Aluno se encontra doente",
  "observacao":"Luis trouxe atestado"
}
```
### Retirar falta do aluno
```http
DELETE http://localhost:8080/api/V1/aluno/retirarFalta/{rg}?data=dd-MM-yyyy HTTP/1.1
```
### Adicionar responsável ao aluno
```http
POST http://localhost:8080/api/V1/aluno/adicionarResponsavel/{rg} HTTP/1.1
```
```body
{
  "nome": "Teste",
  "cpf": "020202020201",
  "telefone": "{rg}",
  "email": "email@email.com.br",
  "filiacao": "MAE"
}
```

### Remover responsável do aluno

```http
DELETE http://localhost:8080/api/V1/aluno/removerResponsavel/{rg}?cpf=string HTTP/1.1
```
### Adicionar deficiência ao aluno

```http
POST http://localhost:8080/api/V1/aluno/deficiencia/{rg}?deficiencia=string HTTP/1.1
```

### Remover deficiência do aluno

```http
DELETE http://localhost:8080/api/V1/aluno/deficiencia/{rg}?deficiencia=string HTTP/1.1
```

### Adicionar acompanhamento de saúde ao aluno

```http
POST http://localhost:8080/api/V1/aluno/acompanhamentoSaude/{rg}?acompanhamento=string HTTP/1.1
```

### Remover acompanhamento de saúde do aluno

```http
DELETE http://localhost:8080/api/V1/aluno/acompanhamentoSaude/{rg}
```