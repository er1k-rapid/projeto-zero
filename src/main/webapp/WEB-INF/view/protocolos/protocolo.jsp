<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<main class="relative z-10 min-h-screen bg-white px-6 py-6 ml-52 mr-52 rounded-2xl shadow-sm">

    <div class="flex items-center justify-between mb-8">
        <h2 class="text-2xl font-semibold tracking-tight text-gray-900">Protocolos</h2>

        <button type="button" data-open-modal="protocolo"
                class="flex items-center gap-2 bg-[#063550] hover:bg-blue-900 text-white rounded-full px-5 py-2 text-sm font-medium transition shadow-sm">
            <i class="fa-solid fa-plus"></i>
            <span>Novo Protocolo</span>
        </button>
    </div>

    <div class="mb-6">
        <form id="filtroProtocoloForm" class="relative">
            <i class="fa-solid fa-search absolute left-4 top-1/2 -translate-y-1/2 text-gray-400"></i>
            <input type="text" id="searchInput" placeholder="Buscar por descrição ou cliente..." 
                   class="w-full bg-gray-50 border border-gray-200 text-gray-700 text-sm rounded-xl focus:ring-2 focus:ring-[#063550] outline-none block pl-10 p-3">
        </form>
    </div>

    <div class="relative overflow-x-auto rounded-xl border border-gray-100">
        <table class="w-full text-sm text-left text-gray-500">
            <thead class="text-xs text-gray-700 uppercase bg-gray-50 border-b border-gray-100">
                <tr>
                    <th class="px-6 py-4 font-semibold w-16 text-center">ID</th>
                    <th class="px-6 py-4 font-semibold">Cliente</th>
                    <th class="px-6 py-4 font-semibold">Descrição</th>
                    <th class="px-6 py-4 font-semibold text-center">Prioridade</th>
                    <th class="px-6 py-4 font-semibold text-center">Status</th>
                    <th class="px-6 py-4 font-semibold text-center">Ações</th>
                </tr>
            </thead>
            <tbody id="protocolosTableBody">
                <tr><td colspan="6" class="px-6 py-8 text-center"><i class="fa-solid fa-spinner fa-spin"></i> Carregando...</td></tr>
            </tbody>
        </table>
    </div>
</main>

<script src="${pageContext.request.contextPath}/assets/js/protocolo.js"></script>

<template id="modalProtocoloTemplate">
    <div class="p-6">
        <h3 class="text-xl font-semibold text-gray-900 mb-6 border-b pb-4">Dados do Protocolo</h3>
        <form id="protocoloForm" class="space-y-4">
            <input type="hidden" name="id" id="protocoloId">
            
            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">Cliente *</label>
                    <select name="clienteId" id="clienteSelect" required 
                            class="w-full rounded-lg border-gray-300 border px-3 py-2 text-sm bg-white focus:ring-2 focus:ring-[#063550] outline-none">
                        <option value="">Carregando clientes...</option>
                    </select>
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">Prioridade *</label>
                    <select name="prioridade" id="prioridade" required 
                            class="w-full rounded-lg border-gray-300 border px-3 py-2 text-sm bg-white focus:ring-2 focus:ring-[#063550] outline-none">
                        <option value="Baixa">Baixa</option>
                        <option value="Média" selected>Média</option>
                        <option value="Alta">Alta</option>
                    </select>
                </div>
            </div>

            <div>
                <label class="block text-sm font-medium text-gray-700 mb-1">Descrição do Problema *</label>
                <textarea name="descricao" id="descricao" required rows="3" 
                          class="w-full rounded-lg border-gray-300 border px-3 py-2 text-sm resize-none focus:ring-2 focus:ring-[#063550] outline-none"></textarea>
            </div>

            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div class="md:col-span-2">
                    <label class="block text-sm font-medium text-gray-700 mb-1">Resolução Técnica</label>
                    <textarea name="resolucao" id="resolucao" rows="3" 
                              class="w-full rounded-lg border-gray-300 border px-3 py-2 text-sm resize-none focus:ring-2 focus:ring-[#063550] outline-none"></textarea>
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">Status</label>
                    <select name="status" id="status" 
                            class="w-full rounded-lg border-gray-300 border px-3 py-2 text-sm bg-white focus:ring-2 focus:ring-[#063550] outline-none">
                        <option value="Não Iniciado">Não Iniciado</option>
                        <option value="Em Andamento">Em Andamento</option>
                        <option value="Concluído">Concluído</option>
                        <option value="Cancelado">Cancelado</option>
                    </select>
                </div>
            </div>

            <div class="flex justify-end gap-3 mt-6 pt-4 border-t border-gray-100">
                <button type="button" data-close-modal class="px-4 py-2 bg-white border border-gray-300 rounded-lg text-sm text-gray-700 hover:bg-gray-50">Cancelar</button>
                <button type="submit" class="px-4 py-2 bg-[#063550] text-white rounded-lg text-sm hover:bg-blue-900 shadow-sm">Salvar Protocolo</button>
            </div>
        </form>
    </div>
</template>