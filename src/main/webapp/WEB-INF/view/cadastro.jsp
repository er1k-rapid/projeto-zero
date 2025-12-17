<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="flex items-center justify-center bg-gray-100">
    <div class="w-full min-h-screen  grid grid-cols-12 shadow-2xl overflow-hidden">

        <div class="bg-white flex flex-col justify-center px-10 py-10 col-span-5">

            <div class="h-16 flex items-center mb-4">
                <div class="w-full flex justify-center">
                    <img src="/assets/images/ZERO.png" class="h-16" alt="Zero Logo">
                </div>
            </div>

            <h3 class="text-3xl font-semibold text-gray-800 mb-1">
                Criar conta
            </h3>

            <p class="text-sm text-gray-500 mb-6">
                Preencha os dados para criar sua conta
            </p>

            <form id="formCadastro" class="space-y-3">

                <input
                    type="text"
                    name="nome"
                    placeholder="Nome"
                    required
                    class="w-full px-4 py-2.5 border border-gray-300 rounded-xl text-sm focus:outline-none focus:ring-2 focus:ring-[#021926]"
                >

                <input
                    type="email"
                    name="email"
                    placeholder="E-mail"
                    required
                    class="w-full px-4 py-2.5 border border-gray-300 rounded-xl text-sm focus:outline-none focus:ring-2 focus:ring-[#021926]"
                >

                <input
                    type="text"
                    name="cnpj"
                    placeholder="CNPJ"
                    required
                    class="w-full px-4 py-2.5 border border-gray-300 rounded-xl text-sm focus:outline-none focus:ring-2 focus:ring-[#021926]"
                >

                <input
                    type="password"
                    name="senha"
                    placeholder="Senha"
                    required
                    class="w-full px-4 py-2.5 border border-gray-300 rounded-xl text-sm focus:outline-none focus:ring-2 focus:ring-[#021926]"
                >

                <input
                    type="password"
                    name="confirmarSenha"
                    placeholder="Confirmar senha"
                    required
                    class="w-full px-4 py-2.5 border border-gray-300 rounded-xl text-sm focus:outline-none focus:ring-2 focus:ring-[#021926]"
                >

                <button
                    type="submit"
                    class="w-full py-2.5 bg-[#021926] text-white rounded-xl hover:bg-[#031f2f] transition font-medium tracking-wide"
                >
                    Criar conta
                </button>

                <div
                    id="cadastroErro"
                    class="hidden w-full px-4 py-3 rounded-xl text-sm font-medium text-center">
                </div>

            </form>
        </div>

        <div class="relative flex flex-col justify-center items-center text-white px-14 bg-[#021926] col-span-7">
            <div class="absolute inset-0 bg-[url('/assets/images/background.png')] bg-cover bg-center"></div>
            <div class="absolute inset-0"></div>

            <div class="relative z-10 text-left max-w-sm">
                <h2 class="text-4xl font-semibold mb-4">
                    Já possui conta?
                </h2>

                <p class="text-sm opacity-90 mb-10 leading-relaxed">
                    Faça login e continue utilizando todos os recursos da plataforma.
                </p>

                <div class="w-full flex justify-end">
                    <a
                        href="/login"
                        class="inline-block px-8 py-3 border border-white rounded-xl hover:bg-white hover:text-[#021926] transition font-medium"
                    >
                        Entrar
                    </a>
                </div>
            </div>
        </div>

    </div>
</div>

<script src="/assets/js/cadastro.js"></script>
