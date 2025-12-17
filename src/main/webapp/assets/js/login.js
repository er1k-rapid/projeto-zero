document.addEventListener("DOMContentLoaded", () => {

    const form = document.getElementById("formLogin")
    const erroBox = document.getElementById("loginErro")

    form.addEventListener("submit", e => {
        e.preventDefault()

        erroBox.classList.add("hidden")
        erroBox.textContent = ""

        const params = new URLSearchParams(new FormData(form))

        fetch("/api/login", {
            method: "POST",
            body: params,
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
                "X-Requested-With": "XMLHttpRequest"
            }
        })
		.then(async resp => {

		    if (resp.ok) {
		        window.location.href = "/home"
		        return
		    }

		    let data = {}
		    try {
		        data = await resp.json()
		    } catch (e) {}

		    erroBox.classList.remove("hidden")

		    if (data.erro === "credenciais") {
		        erroBox.className =
		            "w-full px-4 py-3 rounded-xl text-sm font-medium text-center bg-red-100 text-red-700"
		        erroBox.textContent = "E-mail ou senha inválidos"
		        return
		    }

		    if (data.erro === "email_nao_verificado") {
		        erroBox.className =
		            "w-full px-4 py-3 rounded-xl text-sm font-medium text-center bg-yellow-100 text-yellow-800"
		        erroBox.innerHTML =
		            "Seu e-mail ainda não foi verificado.<br>Verifique sua caixa de entrada."
		        return
		    }

		    erroBox.className =
		        "w-full px-4 py-3 rounded-xl text-sm font-medium text-center bg-gray-100 text-gray-700"
		    erroBox.textContent = "Erro interno do sistema"
		})
    })
})
