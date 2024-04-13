function addAlunos() {
    const primeiroNome = [
        "João",
        "Maria",
        "Pedro",
        "Ana",
        "Lucas",
        "Julia",
        "Larissa",
        "Gabriel",
        "Vitor",
        "Bruno",
        "Matheus",
        "Felipe",
        "Gabriela",
        "Isabela",
        "Lara",
        "Luciana",
        "Letiça",
    ]

    const sobrenome = [
        "Silva",
        "Santos",
        "Pereira",
        "Ferreira",
        "Gomes",
        "Rodrigues",
        "Almeida",
        "Nunes",
        "Costa",
        "Lima",
        "Farias",
        "Gonçalves",
        "Barros",
        "Lopes",
        "Cardoso",
        "Mendes",
        "Alves",
        "Moura",
    ]
    const totalDocuments = 6000
    const batchSize = 100;
    let documents = []

    function generateRg() {
        return Math.floor(Math.random() * 999999999);
    }

    function getRandomTurma() {
        const options = ["turma 1", "turma 2", "turma 3"];
        const randomIndex = Math.floor(Math.random() * options.length);
        return options[randomIndex];
    }

    function getRandomDateTenYearsAgo() {
        const currentDate = new Date(); // Get the current date
        const pastDate = new Date(currentDate); // Clone the current date
        const pastYear = currentDate.getFullYear() - 10; // Subtract ten years

        pastDate.setFullYear(pastYear); // Set the year to ten years ago
        pastDate.setMonth(Math.floor(Math.random() * 12)); // Set a random month (0-11)
        pastDate.setDate(Math.floor(Math.random() * 28) + 1); // Set a random day (1-28)

        return pastDate;
    }

    function getRandomGender() {
        const options = ["M", "F", "OUTRO"];
        const randomIndex = Math.floor(Math.random() * options.length);
        return options[randomIndex];
    }

    db.getSiblingDB("bushido_test")

    let index = 0

    for (let i = 0; i < totalDocuments; i++) {
        index++
        const nome = primeiroNome[Math.floor(Math.random() * primeiroNome.length)] + " " + sobrenome[Math.floor(Math.random() * sobrenome.length)]

        const document = {
            _id: generateRg().toString(),
            nome: nome,
            dataNascimento: getRandomDateTenYearsAgo(),
            genero: getRandomGender(),
            turma: getRandomTurma(),
            "dadosEscolares": {
                "turno": "TARDE",
                "escola": "Emef Luis Ziraldo",
                "serie": "5"
            },
            "dataPreenchimento": new Date(),
            "endereco": {
                "cidade": "Osasco",
                "estado": "SP",
                "cep": "06140-140",
                "numero": ""
            },
            "dadosSociais": {
                "bolsaFamilia": true,
                "auxilioBrasil": true,
                "imovel": "ALUGADO",
                "numerosDePessoasNaCasa": 5,
                "contribuintesDaRendaFamiliar": 2,
                "alunoContribuiParaRenda": false,
                "rendaFamiliar": 0
            },
            "responsaveis": [
                {
                    "nome": "Marcio Domingues",
                    "cpf": "45961366880",
                    "telefone": "11973800547",
                    "email": "otavio.bene2212@gmail.com",
                    "filiacao": "PAI"
                }
            ],
            "graduacao": [
                {
                    "kyu": 1,
                    "faltas": [],
                    "status": true,
                    "frequencia": 100,
                    "inicioGraduacao": new Date("2024-04-13T03:00:00.000Z"),
                    "fimGraduacao": new Date("2024-08-13T03:00:00.000Z"),
                    "aprovado": false,
                    "cargaHoraria": 0,
                    "dan": 4
                }
            ],
            "historicoSaude": {
                "tipoSanguineo": "A_NEGATIVO",
                "usoMedicamentoContinuo": {
                    "resposta": true,
                    "tipo": "11111111111"
                },
                "doencaCronica": {
                    "resposta": false,
                    "tipo": ""
                },
                "alergia": {
                    "resposta": false,
                    "tipo": ""
                },
                "cirurgia": {
                    "resposta": false,
                    "tipo": ""
                },
                "deficiencias": [
                    "ddddddddd"
                ],
                "acompanhamentoSaude": [
                    "dddddddddd"
                ]
            },
            "_class": "br.org.institutobushido.models.aluno.Aluno"
        };

        documents.push(document)

        if (documents.length === batchSize || i === totalDocuments - 1) {
            db.alunos.insertMany(documents);
            documents = [];
        }

        if (i % 1000 === 0) {
            print("Inserted " + i + " documents");
        }
    }
    print("Inserted " + totalDocuments + " documents");
}

addAlunos()