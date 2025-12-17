<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<main class="relative z-10 min-h-screen bg-white px-6 py-6 ml-52 mr-52 rounded-2xl shadow-sm">

    <div class="flex items-center justify-between mb-8">
        <div class="flex items-center gap-4">
            <h2 class="text-2xl font-semibold tracking-tight text-gray-900">
                Gerenciar Clientes
            </h2>
        </div>

        <button type="button" 
                data-open-modal="cliente"
                class="flex items-center gap-2 bg-[#063550] hover:bg-blue-900 text-white 
                       rounded-full px-5 py-2 text-sm font-medium transition shadow-sm">
            <i class="fa-solid fa-plus"></i>
            <span>Novo Cliente</span>
        </button>
    </div>

    <div class="mb-6">
        <form id="filtroClienteForm" class="relative">
            <i class="fa-solid fa-search absolute left-4 top-1/2 -translate-y-1/2 text-gray-400"></i>
            <input type="text" 
                   id="searchInput" 
                   placeholder="Buscar por nome, fantasia ou documento..." 
                   class="w-full bg-gray-50 border border-gray-200 text-gray-700 text-sm rounded-xl 
                          focus:ring-2 focus:ring-[#063550] focus:border-transparent block pl-10 p-3 outline-none transition">
        </form>
    </div>

    <div class="relative overflow-x-auto rounded-xl border border-gray-100">
        <table class="w-full text-sm text-left text-gray-500">
            <thead class="text-xs text-gray-700 uppercase bg-gray-50 border-b border-gray-100">
                <tr>
                    <th scope="col" class="px-6 py-4 font-semibold text-center w-16">ID</th>
                    <th scope="col" class="px-6 py-4 font-semibold">Cliente / Fantasia</th>
                    <th scope="col" class="px-6 py-4 font-semibold text-center">Documento</th>
                    <th scope="col" class="px-6 py-4 font-semibold text-center">Contato</th>
                    <th scope="col" class="px-6 py-4 font-semibold text-center w-24">Status</th>
                    <th scope="col" class="px-6 py-4 font-semibold text-center w-28">Ações</th>
                </tr>
            </thead>
            <tbody id="clientesTableBody">
                <tr>
                    <td colspan="6" class="px-6 py-8 text-center text-gray-400">
                        <i class="fa-solid fa-spinner fa-spin mr-2"></i> Carregando dados...
                    </td>
                </tr>
            </tbody>
        </table>
    </div>

</main>

<script src="${pageContext.request.contextPath}/assets/js/cliente.js"></script>

<template id="modalClienteTemplate">
    <div class="p-6">
        <h3 class="text-xl font-semibold text-gray-900 mb-6 border-b pb-4">Dados do Cliente</h3>
        
        <form id="clienteForm" class="space-y-4">
            <input type="hidden" name="id" id="clienteId">
            
            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">Razão Social <span class="text-red-500">*</span></label>
                    <input type="text" name="razaoSocial" id="razaoSocial" required 
                           class="w-full rounded-lg border-gray-300 border px-3 py-2 text-sm focus:ring-2 focus:ring-[#063550] outline-none">
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">Nome Fantasia</label>
                    <input type="text" name="nomeFantasia" id="nomeFantasia" 
                           class="w-full rounded-lg border-gray-300 border px-3 py-2 text-sm focus:ring-2 focus:ring-[#063550] outline-none">
                </div>
                
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">Tipo Pessoa <span class="text-red-500">*</span></label>
                    <select name="tipoPessoa" id="tipoPessoa" required 
                            class="w-full rounded-lg border-gray-300 border px-3 py-2 text-sm bg-white focus:ring-2 focus:ring-[#063550] outline-none">
                        <option value="Juridica">Jurídica</option>
                        <option value="Fisica">Física</option>
                    </select>
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">Documento (CPF/CNPJ)</label>
                    <input type="text" name="numeroDocumento" id="numeroDocumento" 
                           class="w-full rounded-lg border-gray-300 border px-3 py-2 text-sm focus:ring-2 focus:ring-[#063550] outline-none">
                </div>

                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">E-mail</label>
                    <input type="email" name="email" id="email" 
                           class="w-full rounded-lg border-gray-300 border px-3 py-2 text-sm focus:ring-2 focus:ring-[#063550] outline-none">
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">Telefone</label>
                    <input type="text" name="telefone" id="telefone" 
                           class="w-full rounded-lg border-gray-300 border px-3 py-2 text-sm focus:ring-2 focus:ring-[#063550] outline-none">
                </div>
            </div>

            <div>
                <label class="block text-sm font-medium text-gray-700 mb-1">Observações</label>
                <textarea name="observacao" id="observacao" rows="2" 
                          class="w-full rounded-lg border-gray-300 border px-3 py-2 text-sm focus:ring-2 focus:ring-[#063550] outline-none resize-none"></textarea>
            </div>

            <div class="w-1/2">
                <label class="block text-sm font-medium text-gray-700 mb-1">Status</label>
                <select name="status" id="status" 
                        class="w-full rounded-lg border-gray-300 border px-3 py-2 text-sm bg-white focus:ring-2 focus:ring-[#063550] outline-none">
                    <option value="Ativo">Ativo</option>
                    <option value="Inativo">Inativo</option>
                </select>
            </div>

            <div class="flex justify-end gap-3 mt-6 pt-4 border-t border-gray-100">
                <button type="button" onclick="fecharModal()" 
                        class="px-4 py-2 bg-white border border-gray-300 rounded-lg text-sm font-medium text-gray-700 hover:bg-gray-50 transition">
                    Cancelar
                </button>
                <button type="submit" 
                        class="px-4 py-2 bg-[#063550] text-white rounded-lg text-sm font-medium hover:bg-blue-900 transition shadow-sm">
                    Salvar Cliente
                </button>
            </div>
        </form>
    </div>
</template>

<div class="hidden">
    <button id="confirmarExclusao" type="button" 
            class="w-full inline-flex justify-center rounded-lg border border-transparent shadow-sm px-4 py-2 bg-red-600 text-base font-medium text-white hover:bg-red-700 focus:outline-none sm:ml-3 sm:w-auto sm:text-sm">
        Sim, Excluir
    </button>
</div>