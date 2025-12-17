<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<main class="relative z-10 min-h-screen bg-white px-6 py-6 ml-52 mr-52 rounded-2xl">
    <div class="flex items-center justify-between mb-6">
        <h1 class="text-2xl font-semibold text-gray-900">
            Usuários
        </h1>

		<button data-open-modal="usuario" data-modal-title="Cadastro de Usuário" 
		   class="bg-[#063550] hover:bg-[#04293d] text-white px-5 py-2 rounded-2xl text-sm font-medium transition shadow">
		    NOVO <i class="fa-solid fa-plus mr-2"></i>
		</button>
    </div>
    
	<form id="filtroForm" class="flex flex-col md:flex-row items-start md:items-end justify-between mb-6 gap-6">
	
		<div class="flex flex-col w-full md:flex-2">
		    <label for="searchInput" class="mb-1 text-sm font-medium text-gray-700">O que está procurando?</label>
		    <div class="relative w-full">
		        <span class="absolute left-3 top-1/2 -translate-y-1/2 text-white">
		            <i class="fa fa-search"></i>
		        </span>
		        <input 
		            type="text" 
		            id="searchInput" 
		            name="nome" 
		            value="${param.search}" 
		            placeholder="Digite aqui o que gostaria de pesquisar..." 
		            class="w-full pl-10 pr-4 py-1 rounded-3xl bg-[#063550] text-white placeholder-white border border-transparent shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500">
		    </div>
		</div>
	
	    <div class="flex flex-col w-full md:w-1/4">
	        <label for="filterSelect" class="mb-1 text-sm font-medium text-gray-700">Filtro</label>
	        <select 
	            id="filterSelect" 
	            name="filter" 
	            class="w-full px-4 py-1 rounded-lg border border-gray-300 shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 text-gray-900">
	            <option value="nome" selected>Nome</option>
	        </select>
	    </div>
	
	    <div class="mt-4 md:mt-0">
	        <button 
	            type="submit" 
	            class="bg-[#063550] hover:bg-[#04293d] text-white px-5 py-2 rounded-2xl text-sm font-medium transition shadow">
	            Buscar
	        </button>
	    </div>
	</form>

   <div class="overflow-x-auto rounded-lg border border-gray-200 shadow-sm">
	    <table class="min-w-full border-collapse">
	        <thead class="bg-gray-50">
	            <tr>
	                <th class="px-4 py-1 text-center text-sm font-semibold text-gray-700">ID</th>
	                <th class="px-4 py-1 text-center text-sm font-semibold text-gray-700 w-[30%]">Nome</th>
	                <th class="px-4 py-1 text-center text-sm font-semibold text-gray-700">Cargo</th>
	                <th class="px-4 py-1 text-center text-sm font-semibold text-gray-700">Tipo de Usuário</th>
	                <th class="px-4 py-1 text-center text-sm font-semibold text-gray-700">Status</th>
	                <th class="px-4 py-1 text-center text-sm font-semibold text-gray-700"></th>
	            </tr>
	        </thead>
			<tbody id="usuariosTableBody" class="bg-white divide-y divide-gray-200"></tbody>
	    </table>
	</div>
</main>

<div id="usuarioModal" class="hidden">
	<jsp:include page="/WEB-INF/view/usuarios/form.jsp"/>
</div>

<div id="confirmaModal" class="hidden">
    <div class="bg-white rounded-2xl w-[380px] space-y-6">
        <p class="text-sm text-gray-600">
            Deseja realmente excluir este usuário?
        </p>

        <div class="flex justify-end gap-3">
            <button data-close-modal class="px-4 py-2 rounded-lg bg-gray-200 text-gray-800 hover:bg-gray-300 transition">
                Cancelar
            </button>

            <a href="#" id="confirmarExclusao"
                class="px-4 py-2 rounded-lg bg-red-600 text-white hover:bg-red-700 transition">
                Excluir
            </a>
        </div>
    </div>
</div>

<script src="/assets/js/usuario.js"></script>
  