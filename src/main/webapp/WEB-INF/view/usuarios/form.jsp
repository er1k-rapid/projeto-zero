<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form id="usuarioForm" class="w-[750px] space-y-6">
	<div class="grid grid-cols-12 gap-4">
      <div class="col-span-2">
        <label for="id" class="block text-sm font-medium text-gray-700 mb-1">ID</label>
        <input type="text" id="id" name="id" readonly class="w-full bg-gray-200 border border-gray-300 rounded-xl px-3 py-1 focus:outline-none">
      </div>
      <div class="col-span-7">
        <label for="nome" class="block text-sm font-medium text-gray-700 mb-1">Nome</label>
        <input type="text" id="nome" name="nome" class="w-full border border-gray-300 rounded-xl px-3 py-1 focus:outline-none focus:ring-2 focus:ring-blue-500">
      </div>
	  <div class="col-span-3">
	  	<label for="cargo" class="block text-sm font-medium text-gray-700 mb-1">Cargo</label>
    	<select name="cargoId" id="cargo" class="w-full border border-gray-300 rounded-xl px-3 py-1 focus:outline-none focus:ring-2 focus:ring-blue-500"></select>
	  </div>
    </div>

    <div class="grid grid-cols-12 gap-4">
      <div class="col-span-3">
        <label for="tipo" class="block text-sm font-medium text-gray-700 mb-1">Tipo de Usuário</label>
        <select id="tipo" name="tipoUsuario" required class="w-full border border-gray-300 rounded-xl px-3 py-1 focus:outline-none focus:ring-2 focus:ring-blue-500">
          <option disabled selected>Selecione</option>
          <option>Admin</option>
          <option>Supervisor</option>
          <option>Tecnico</option>
        </select>
      </div>
      <div class="col-span-7">
        <label for="email" class="block text-sm font-medium text-gray-700 mb-1">E-mail</label>
        <input type="email" id="email" name="email" class="w-full border border-gray-300 rounded-xl px-3 py-1 focus:outline-none focus:ring-2 focus:ring-blue-500">
      </div>
      <div class="col-span-2">
        <label for="status" class="block text-sm font-medium text-gray-700 mb-1">Status</label>
        <select id="status" name="status" class="w-full border border-gray-300 rounded-xl px-3 py-1 focus:outline-none focus:ring-2 focus:ring-blue-500">
          <option>Ativo</option>
          <option>Inativo</option>
        </select>
      </div>
    </div>

    <div class="grid grid-cols-12 gap-4">
      <div class="space-y-4 col-span-4">
        <div>
          <label for="senha" class="block text-sm font-medium text-gray-700 mb-1">Senha</label>
          <input type="password" id="senha" name="senha" class="w-full border border-gray-300 rounded-xl px-3 py-1 focus:outline-none focus:ring-2 focus:ring-blue-500">
        </div>
        <div>
          <label for="confirme_senha" class="block text-sm font-medium text-gray-700 mb-1">Confirme a Senha</label>
          <input type="password" id="confirmaSenha" disabled class="w-full border border-gray-300 rounded-xl px-3 py-1 focus:outline-none focus:ring-2 focus:ring-blue-500">
        </div>
      </div>
      <div class="space-y-4 col-span-8">
        <div>
          <label for="observacao" class="block text-sm font-medium text-gray-700 mb-1">Observação</label>
          <textarea id="observacao" rows="4" name="observacao" class="w-full border border-gray-300 rounded-xl px-3 py-1 focus:outline-none focus:ring-2 focus:ring-blue-500" style="height: 125px;"></textarea>
        </div>
      </div>
    </div>

	<div class="flex items-end gap-4">
	  <div class="flex-1">
	    <label for="dataCadastro" class="block text-sm font-medium text-gray-700 mb-1">Data Cadastro</label>
	    <input type="date" id="dataCadastro" name="dataCadastro" disabled class="w-36 bg-gray-200 border border-gray-300 rounded-xl px-3 py-1 focus:outline-none">
	  </div>
	  <div>
	    <button type="submit" class="bg-[#063550] text-white px-6 py-2 rounded-md transition hover:bg-[#045066]">
	      Confirmar
	    </button>
	  </div>
	</div>
</form>
  
<script src="/assets/js/usuario.js"></script>
  