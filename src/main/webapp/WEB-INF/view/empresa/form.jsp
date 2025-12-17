<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form id="empresaForm" class="w-[900px] space-y-6">
  
  <div class="grid grid-cols-12 gap-4">
    <div class="col-span-1">
      <label for="id" class="block text-sm font-medium text-gray-700 mb-1">ID</label>
      <input type="text" id="id" name="id" readonly class="w-full bg-gray-200 border border-gray-300 rounded-xl px-3 py-1 focus:outline-none">
    </div>
    <div class="col-span-6">
      <label for="razaoSocial" class="block text-sm font-medium text-gray-700 mb-1">Razão Social</label>
      <input type="text" id="razaoSocial" name="razaoSocial" class="w-full border border-gray-300 rounded-xl px-3 py-1 focus:outline-none focus:ring-2 focus:ring-blue-500">
    </div>
    <div class="col-span-2">
      <label for="tipoPessoa" class="block text-sm font-medium text-gray-700 mb-1">Tipo de Pessoa</label>
      <select id="tipoPessoa" name="tipoPessoa" class="w-full border border-gray-300 rounded-xl px-3 py-1 focus:outline-none focus:ring-2 focus:ring-blue-500">
        <option disabled selected>Selecione</option>
        <option>Fisica</option>
        <option>Juridica</option>
      </select>
    </div>
    <div class="col-span-3">
      <label for="inscricao" class="block text-sm font-medium text-gray-700 mb-1">Nro. de Inscrição</label>
      <input type="text" id="nroInscricao" name="nroInscricao" class="w-full border border-gray-300 rounded-xl px-3 py-1 focus:outline-none focus:ring-2 focus:ring-blue-500">
    </div>
  </div>

  <div class="mt-6 border-b border-gray-300">
    <ul class="flex space-x-4">
      <li class="pb-2 border-b-2 border-blue-500 text-blue-500">Dados Gerais</li>
    </ul>
  </div>

  <div class="grid grid-cols-12 gap-4 mt-4">
    <div class="col-span-3">
      <label for="nomeFantasia" class="block text-sm font-medium text-gray-700 mb-1">Nome Fantasia</label>
      <input type="text" id="nomeFantasia" name="nomeFantasia" class="w-full border border-gray-300 rounded-xl px-3 py-1 focus:outline-none focus:ring-2 focus:ring-blue-500">
    </div>
    <div class="col-span-5">
      <label for="email" class="block text-sm font-medium text-gray-700 mb-1">E-mail</label>
      <input type="email" id="email" name="email" class="w-full border border-gray-300 rounded-xl px-3 py-1 focus:outline-none focus:ring-2 focus:ring-blue-500">
    </div>
    <div class="col-span-2">
      <label for="telefone" class="block text-sm font-medium text-gray-700 mb-1">Telefone</label>
      <input type="text" id="telefone" name="telefone" class="w-full border border-gray-300 rounded-xl px-3 py-1 focus:outline-none focus:ring-2 focus:ring-blue-500">
    </div>
    <div class="col-span-2">
      <label for="cep" class="block text-sm font-medium text-gray-700 mb-1">CEP</label>
      <input type="text" id="cep" name="cep" class="w-full border border-gray-300 rounded-xl px-3 py-1 focus:outline-none focus:ring-2 focus:ring-blue-500">
    </div>
  </div>

  <div class="grid grid-cols-12 gap-4">
    <div class="col-span-4">
      <label for="logradouro" class="block text-sm font-medium text-gray-700 mb-1">Logradouro</label>
      <input type="text" id="logradouro" name="logradouro" class="w-full border border-gray-300 rounded-xl px-3 py-1 focus:outline-none focus:ring-2 focus:ring-blue-500">
    </div>
    <div class="col-span-2">
      <label for="numero" class="block text-sm font-medium text-gray-700 mb-1">Número</label>
      <input type="text" id="numero" name="numero" class="w-full border border-gray-300 rounded-xl px-3 py-1 focus:outline-none focus:ring-2 focus:ring-blue-500">
    </div>
    <div class="col-span-2">
      <label for="bairro" class="block text-sm font-medium text-gray-700 mb-1">Bairro</label>
      <input type="text" id="bairro" name="bairro" class="w-full border border-gray-300 rounded-xl px-3 py-1 focus:outline-none focus:ring-2 focus:ring-blue-500">
    </div>
    <div class="col-span-3">
      <label for="cidade" class="block text-sm font-medium text-gray-700 mb-1">Cidade</label>
      <input type="text" id="cidade" name="cidade" class="w-full border border-gray-300 rounded-xl px-3 py-1 focus:outline-none focus:ring-2 focus:ring-blue-500">
    </div>
    <div class="col-span-1">
      <label for="uf" class="block text-sm font-medium text-gray-700 mb-1">UF</label>
      <input type="text" id="uf" name="uf" class="w-full border border-gray-300 rounded-xl px-3 py-1 focus:outline-none focus:ring-2 focus:ring-blue-500">
    </div>
  </div>

<div class="grid grid-cols-12 gap-4 justify-between">
  <div class="col-span-10">
    <label for="observacoes" class="block text-sm font-medium text-gray-700 mb-1">Observações</label>
    <textarea id="observacoes" name="observacoes" rows="4" class="w-full border border-gray-300 rounded-xl px-3 py-1 focus:outline-none focus:ring-2 focus:ring-blue-500" style="height: 100px;"></textarea>
  </div>

  <div class="col-span-2">
    <label class="block text-sm font-medium text-gray-700 mb-1">Logo</label>
    <div class="flex items-center gap-4">
      <div class="relative w-[100px] h-[100px] border border-gray-300 rounded-xl overflow-hidden bg-gray-100 flex items-center justify-center">
        <img id="previewLogo" src="" alt="Pré-visualização" class="w-full h-full object-cover hidden">
        <span id="placeholderLogo" class="text-gray-400 text-center text-sm"></span>
      </div>
      <label for="logoInput" class="cursor-pointer bg-[#063550] w-8 h-8 text-white rounded-md hover:bg-[#045066] flex items-center justify-center text-lg">
        <i class="fa-solid fa-plus"></i>
      </label>
      <input type="file" id="logoInput" accept=".jpg,.jpeg,.png" class="hidden">
    </div>
  </div>
</div>

  <div class="flex justify-end">
    <button type="submit" class="bg-[#063550] text-white px-6 py-2 rounded-md transition hover:bg-[#045066]">
      Confirmar
    </button>
  </div>
</form>

<script src="/assets/js/empresa.js"></script>
