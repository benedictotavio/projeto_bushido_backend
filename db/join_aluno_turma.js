db.turmas.aggregate([
	{
		$match: {
			"nome": "fundao"
		},
	},
	{
		$lookup: {
			from: "alunos",
			localField: "nome",
			foreignField: "turma",
			as: "alunos_turma"
		}
	},
	{
		$unwind: "$alunos_turma"
	},
	{
		$project: {
			"_id": 0,
			"nome": "$alunos_turma.nome",
			"rg": "$alunos_turma._id",
			"genero": "$alunos_turma.genero",
			"dataNascimento": "$alunos_turma.dataNascimento"
		}
	}
])