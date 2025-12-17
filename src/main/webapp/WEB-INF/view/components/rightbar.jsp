<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<aside class="w-52 h-screen fixed right-0 top-0 flex flex-col
              bg-gradient-to-b from-[#ECF1F3] to-[#E4EBEF]">

    <div class="h-20 flex items-center justify-between px-4">

        <c:if test="${sessionScope.usuario.tipoUsuario eq 'Supervisor' or sessionScope.usuario.tipoUsuario eq 'Admin'}">
            <button data-open-modal="empresa" data-modal-title="Configurações da Empresa" data-id="${sessionScope.usuario.empresaId}" class="w-10 h-10 flex items-center justify-center rounded-lg
                           hover:bg-black/10 transition">
                <i class="fa-solid fa-gear text-gray-700"></i>
            </button>
        </c:if>

        <div class="relative">

            <button onclick="toggleUserMenu()"
                    class="flex items-center gap-3 px-3 py-2 rounded-lg
                           hover:bg-black/10 transition">

                <div class="text-black text-2xl">
                    <i class="fa-solid fa-circle-user"></i>
                </div>

                <span class="text-sm font-medium text-gray-800 whitespace-nowrap">
                    ${sessionScope.usuario.nome}
                </span>

                <i class="fa-solid fa-chevron-down text-xs text-gray-500"></i>
            </button>

            <div id="userMenu"
                 class="hidden absolute right-0 mt-2 w-44 bg-white rounded-xl
                        shadow-xl ring-1 ring-black/5 overflow-hidden">

				<c:if test="${sessionScope.usuario.tipoUsuario eq 'Supervisor' or sessionScope.usuario.tipoUsuario eq 'Admin'}">
	                <a href="/usuarios"
	                   class="flex items-center gap-3 px-4 py-3 text-sm
	                          text-gray-700 hover:bg-gray-100 transition">
	                    <i class="fa-solid fa-user"></i>
	                    Gerenciar Usuários
	                </a>
                </c:if>

                <a href="/logout"
                   class="flex items-center gap-3 px-4 py-3 text-sm
                          text-red-600 hover:bg-red-50 transition">
                    <i class="fa-solid fa-right-from-bracket"></i>
                    Sair
                </a>
            </div>

        </div>
    </div>

</aside>

<div id="empresaModal" class="hidden">
	<jsp:include page="/WEB-INF/view/empresa/form.jsp"/>
</div>

<script>
	function toggleUserMenu() {
	    document.getElementById('userMenu').classList.toggle('hidden')
	}
	
	document.addEventListener('click', e => {
	    const menu = document.getElementById('userMenu')
	    if (!e.target.closest('.relative')) {
	        menu.classList.add('hidden')
	    }
	})
</script>
