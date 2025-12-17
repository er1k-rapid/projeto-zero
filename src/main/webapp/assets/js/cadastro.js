document.addEventListener("DOMContentLoaded", () => {

    const form = document.getElementById("formCadastro")
    const erroBox = document.getElementById("cadastroErro")

    form.addEventListener("submit", async e => {
        e.preventDefault()

        erroBox.classList.add("hidden")
        erroBox.textContent = ""

        const data = Object.fromEntries(new FormData(form))
        const cnpjLimpo = data.cnpj.replace(/\D/g, '')

        if (data.senha !== data.confirmarSenha) {
            mostrarErro("As senhas não conferem", "red")
            return
        }

        try {
            const empresaApi = await fetch(`https://brasilapi.com.br/api/cnpj/v1/${cnpjLimpo}`)
            if (!empresaApi.ok) {
                mostrarErro("CNPJ inválido ou não encontrado", "red")
                return
            }

            const emp = await empresaApi.json()

			const empresaPayload = {
			    razaoSocial: emp.razao_social,
			    nomeFantasia: emp.nome_fantasia,
			    tipoPessoa: "Juridica",
			    nroInscricao: cnpjLimpo,
			    email: emp.email || "",
			    telefone: emp.ddd_telefone_1 || "",
			    cep: emp.cep || "",
			    logradouro: emp.logradouro || "",
			    numero: emp.numero || "",
			    bairro: emp.bairro || "",
			    cidade: emp.municipio || "",
			    uf: emp.uf || "",
			    observacoes: ""
			}

            const empresaResp = await fetch("/api/empresas", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(empresaPayload)
            })

            if (!empresaResp.ok) {
                mostrarErro("Erro ao criar empresa", "yellow")
                return
            }

			let empresaCriada
			try {
			    empresaCriada = await empresaResp.json()
			} catch {
			    mostrarErro("Empresa criada, mas resposta inválida", "yellow")
			    return
			}

			const empresaId = empresaCriada?.id || empresaCriada?.empresaId

			if (!empresaId) {
			    mostrarErro("Empresa criada, mas ID não retornado", "yellow")
			    return
			}

			const usuarioPayload = {
			    nome: data.nome,
			    email: data.email,
			    senha: data.senha,
			    tipoUsuario: "Admin",
			    status: "Ativo",
			    observacao: "",
			    empresaId
			}

			console.log("Payload usuário:", usuarioPayload)

			const usuarioResp = await fetch("/api/usuarios", {
			    method: "POST",
			    headers: { "Content-Type": "application/json" },
			    body: JSON.stringify(usuarioPayload)
			})

            window.location.href = "/login"

        } catch (err) {
            mostrarErro("Erro interno do sistema", "yellow")
        }
    })

    function mostrarErro(msg, tipo) {
        erroBox.classList.remove("hidden")
        erroBox.className =
            tipo === "red"
                ? "w-full px-4 py-3 rounded-xl text-sm font-medium text-center bg-red-100 text-red-700"
                : "w-full px-4 py-3 rounded-xl text-sm font-medium text-center bg-yellow-100 text-yellow-700"
        erroBox.textContent = msg
    }
})
