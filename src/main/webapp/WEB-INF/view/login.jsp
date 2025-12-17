<%
    String verificado = request.getParameter("verificado");
    String erro = request.getParameter("erro");
%>

<% if ("true".equals(verificado)) { %>
    <div class="mb-6 px-4 py-3 rounded-xl bg-green-100 text-green-700 text-sm font-medium">
        E-mail verificado com sucesso! Faça login.
    </div>
<% } %>

<% if ("token".equals(erro)) { %>
    <div class="mb-6 px-4 py-3 rounded-xl bg-red-100 text-red-700 text-sm font-medium">
        Link de verificação inválido ou expirado.
    </div>
<% } %>


<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="min-h-screen flex items-center justify-center bg-gray-100">
    <div class="w-full min-h-screen grid grid-cols-12 shadow-2xl">

        <div class="bg-white flex flex-col justify-center px-16 col-span-5">

            <div class="h-32 flex items-center">
                <div class="w-full flex justify-center">
                    <img src="/assets/images/ZERO.png" class="h-32" alt="Zero Logo">
                </div>
            </div>

            <h3 class="text-3xl font-semibold text-gray-800 mb-2">
                Login
            </h3>

            <p class="text-sm text-gray-500 mb-8">
                Faça login para continuar ajudando quem precisa
            </p>

            <form id="formLogin" class="space-y-5">

                <input
                    type="email"
                    name="email"
                    placeholder="E-mail"
                    required
                    class="w-full px-4 py-3 border border-gray-300 rounded-xl text-sm focus:outline-none focus:ring-2 focus:ring-[#021926]"
                >

                <input
                    type="password"
                    name="senha"
                    placeholder="Senha"
                    required
                    class="w-full px-4 py-3 border border-gray-300 rounded-xl text-sm focus:outline-none focus:ring-2 focus:ring-[#021926]"
                >

                <div class="flex justify-between items-center text-sm">
                    <span></span>
                    <a href="esqueci-senha.jsp" class="text-[#021926] hover:underline">
                        Esqueci minha senha
                    </a>
                </div>

                <button
                    type="submit"
                    class="w-full py-3 bg-[#021926] text-white rounded-xl hover:bg-[#031f2f] transition font-medium tracking-wide"
                >
                    Entrar
                </button>

                <div
                    id="loginErro"
                    class="hidden w-full px-4 py-3 rounded-xl text-sm font-medium text-center">
                </div>

            </form>
        </div>

        <div class="relative flex flex-col justify-center items-center text-white px-14 bg-[#021926] col-span-7">
            <div class="absolute inset-0 bg-[url('/assets/images/background.png')] bg-cover bg-center"></div>
            <div class="absolute inset-0"></div>

            <div class="relative z-10 text-left max-w-sm">
                <h2 class="text-4xl font-semibold mb-4">
                    Bem-vindo
                </h2>
                <p class="text-sm opacity-90 mb-10 leading-relaxed">
                    Ainda não possui uma conta? Crie agora e aproveite todos os recursos da plataforma.
                </p>

                <div class="w-full flex justify-end">
                    <a
                        href="/cadastro"
                        class="inline-block px-8 py-3 border border-white rounded-xl hover:bg-white hover:text-[#021926] transition font-medium"
                    >
                        Criar conta
                    </a>
                </div>
            </div>
        </div>

    </div>
</div>

<script src="/assets/js/login.js"></script>
