<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<main class="relative z-10 min-h-screen bg-white px-6 py-6 ml-52 mr-52 rounded-2xl shadow-sm">

    <div class="flex items-center justify-between mb-8">
        <div class="flex items-center gap-4">
            <h2 class="text-2xl font-semibold tracking-tight text-gray-900">Dashboard</h2>
            <button id="refreshBtn" class="group w-9 h-9 flex items-center justify-center rounded-full bg-gray-100 hover:bg-gray-200 transition">
                <i class="fa-solid fa-rotate text-gray-600 group-hover:rotate-180 transition-transform duration-300"></i>
            </button>
        </div>

        <div class="relative">
            <button id="filterBtn" class="flex items-center gap-3 bg-gray-50 border border-gray-200 rounded-full px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 transition">
                <i class="fa-solid fa-filter text-gray-400 text-sm"></i>
                <span id="filterLabel">Hoje</span>
                <i class="fa-solid fa-chevron-down text-xs text-gray-400"></i>
            </button>
        
            <div id="filterMenu" class="hidden absolute right-0 mt-2 w-44 bg-white border border-gray-200 rounded-xl shadow-lg overflow-hidden z-50">
                <button data-days="0" class="w-full text-left px-4 py-2 text-sm hover:bg-gray-100 text-gray-700">Hoje</button>
                <button data-days="7" class="w-full text-left px-4 py-2 text-sm hover:bg-gray-100 text-gray-700">Últimos 7 dias</button>
                <button data-days="30" class="w-full text-left px-4 py-2 text-sm hover:bg-gray-100 text-gray-700">Últimos 30 dias</button>
            </div>
        </div>
    </div>

    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
        
        <div class="bg-gray-50 rounded-xl p-5 border border-gray-100">
            <div class="flex justify-between items-start mb-4">
                <div class="bg-blue-100 p-2 rounded-lg">
                    <i class="fa-solid fa-folder-open text-blue-600"></i>
                </div>
                </div>
            <div class="text-3xl font-bold text-gray-900 mb-1" id="valTotal">0</div>
            <div class="text-sm text-gray-500">Total de Protocolos</div>
        </div>

        <div class="bg-gray-50 rounded-xl p-5 border border-gray-100">
            <div class="flex justify-between items-start mb-4">
                <div class="bg-yellow-100 p-2 rounded-lg">
                    <i class="fa-solid fa-clock text-yellow-600"></i>
                </div>
            </div>
            <div class="text-3xl font-bold text-gray-900 mb-1" id="valAberto">0</div>
            <div class="text-sm text-gray-500">Em Aberto</div>
        </div>

        <div class="bg-gray-50 rounded-xl p-5 border border-gray-100">
            <div class="flex justify-between items-start mb-4">
                <div class="bg-red-100 p-2 rounded-lg">
                    <i class="fa-solid fa-triangle-exclamation text-red-600"></i>
                </div>
            </div>
            <div class="text-3xl font-bold text-gray-900 mb-1" id="valCritico">0</div>
            <div class="text-sm text-gray-500">Prioridade Alta (Abertos)</div>
        </div>

        <div class="bg-gray-50 rounded-xl p-5 border border-gray-100">
            <div class="flex justify-between items-start mb-4">
                <div class="bg-green-100 p-2 rounded-lg">
                    <i class="fa-solid fa-check-circle text-green-600"></i>
                </div>
            </div>
            <div class="text-3xl font-bold text-gray-900 mb-1" id="valConcluido">0</div>
            <div class="text-sm text-gray-500">Concluídos</div>
        </div>
    </div>

    <div class="bg-white border border-gray-100 rounded-xl overflow-hidden">
        <div class="px-6 py-4 border-b border-gray-100 flex justify-between items-center bg-gray-50">
            <h3 class="font-semibold text-gray-800">Atividades Recentes</h3>
            <a href="${pageContext.request.contextPath}/protocolos" class="text-sm text-[#063550] hover:underline">Ver tudo</a>
        </div>
        <div class="overflow-x-auto">
            <table class="w-full text-sm text-left">
                <thead class="bg-gray-50 text-xs text-gray-500 uppercase">
                    <tr>
                        <th class="px-6 py-3 font-semibold">ID</th>
                        <th class="px-6 py-3 font-semibold">Cliente</th>
                        <th class="px-6 py-3 font-semibold">Descrição</th>
                        <th class="px-6 py-3 font-semibold text-center">Status</th>
                        <th class="px-6 py-3 font-semibold text-center">Data</th>
                    </tr>
                </thead>
                <tbody id="recentesTableBody">
                    <tr>
                        <td colspan="5" class="text-center py-6 text-gray-400">
                            <i class="fa-solid fa-spinner fa-spin"></i> Carregando...
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>

</main>

<script src="${pageContext.request.contextPath}/assets/js/Dashboard.js"></script>