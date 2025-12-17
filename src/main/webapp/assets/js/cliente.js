document.addEventListener('DOMContentLoaded', () => {
    console.log(">>> [DEBUG] Cliente.js iniciado.");
    
    carregarClientes();

    // Filtro
    document.getElementById('filtroClienteForm')?.addEventListener('submit', filtrarClientes);


    document.addEventListener('click', e => {

        const trigger = e.target.closest('[data-open-modal]');
        
        if (!trigger) return;

        e.preventDefault();
        console.log(">>> [DEBUG] Botão clicado:", trigger);

        const key = trigger.dataset.openModal;
        const id = trigger.dataset.id; 


        if (key === 'cliente' && id) {
            console.log(">>> [DEBUG] Modo Edição - Buscando ID:", id);
            fetch(`/api/clientes?id=${id}`)
                .then(async r => {
                    if (!r.ok) {
                        const t = await r.text();
                        console.error(t);
                        throw new Error();
                    }
                    return r.json();
                })
                .then(preencherFormularioCliente)
                .catch(console.error);
        }


        document.dispatchEvent(
            new CustomEvent('modal:open', { detail: { key, id } })
        );
    });

    // --- LISTENER DE ABERTURA DO MODAL ---
    document.addEventListener('modal:open', e => {
        const key = e.detail.key;
        console.log(">>> [DEBUG] Abrindo modal:", key);

        if (key === 'cliente') {
            const modalBody = document.querySelector('.modal-body');
            const template = document.getElementById('modalClienteTemplate');

            if (!modalBody || !template) {
                console.error(">>> [ERRO] modal-body ou template não encontrado!");
                return;
            }


            modalBody.innerHTML = '';
            modalBody.appendChild(template.content.cloneNode(true));


            const form = modalBody.querySelector('#clienteForm');
            if (form) {
                const newForm = form.cloneNode(true);
                form.replaceWith(newForm);
                

                newForm.addEventListener('submit', salvarCliente);

                if (!e.detail.id) {
                    console.log(">>> [DEBUG] Modo Novo - Resetando form.");
                    newForm.reset();
                    newForm.querySelector('#clienteId').value = ''; 
                }
            }
        }

        if (key === 'confirma-cliente') {
            const id = e.detail.id;
            const modalBody = document.querySelector('.modal-body');
            
            modalBody.innerHTML = `
                <div class="text-center p-4">
                    <p class="mb-4 text-gray-700">Tem certeza que deseja excluir este cliente?</p>
                    <div class="flex justify-end gap-3">
                         <button type="button" data-close-modal class="px-4 py-2 bg-gray-100 rounded text-gray-700">Cancelar</button>
                         <button id="btnConfirmarExclusao" class="px-4 py-2 bg-red-600 text-white rounded">Sim, Excluir</button>
                    </div>
                </div>
            `;

            const btn = modalBody.querySelector('#btnConfirmarExclusao');
            if (btn) {
                btn.onclick = () => {
                    console.log(">>> [DEBUG] Excluindo cliente ID:", id);
                    fetch(`/api/clientes?id=${id}`, { method: 'DELETE' })
                        .then(r => {
                            if (!r.ok) throw new Error('Erro ao excluir');
                            carregarClientes();
                            fecharModal();
                        })
                        .catch(console.error);
                };
            }
        }

        const modal = document.getElementById('modal');
        if (modal) {
            modal.classList.remove('hidden');

            setTimeout(() => modal.classList.add('open'), 10);
        }
    });
    
    document.addEventListener('click', e => {
        if (e.target.matches('[data-close-modal], [data-close-modal] *')) {
            fecharModal();
        }
    });
});

// --- FUNÇÕES ---

function carregarClientes(termo = '') {
    const url = termo
        ? `/api/clientes?termo=${encodeURIComponent(termo)}`
        : `/api/clientes`;

    const tbody = document.getElementById('clientesTableBody');
    if (tbody) tbody.innerHTML = `<tr><td colspan="6" class="text-center py-4"><i class="fa-solid fa-spinner fa-spin"></i> Carregando...</td></tr>`;

    fetch(url)
        .then(r => r.json())
        .then(renderTabelaClientes)
        .catch(console.error);
}

function filtrarClientes(e) {
    e.preventDefault();
    const termo = document.getElementById('searchInput').value;
    carregarClientes(termo);
}

function renderTabelaClientes(clientes) {
    const tbody = document.getElementById('clientesTableBody');
    if (!tbody) return;
    
    tbody.innerHTML = '';

    if (!clientes || clientes.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="6" class="px-4 py-8 text-center text-sm text-gray-500">
                    Nenhum cliente encontrado
                </td>
            </tr>
        `;
        return;
    }

    clientes.forEach(c => {
        tbody.insertAdjacentHTML('beforeend', `
            <tr class="bg-white border-b hover:bg-gray-50 transition-colors duration-150">
                <td class="px-6 py-4 text-center font-medium text-gray-900">#${c.id}</td>
                <td class="px-6 py-4">
                    <div class="font-semibold text-gray-900">${c.razaoSocial}</div>
                    ${c.nomeFantasia ? `<div class="text-xs text-gray-500">${c.nomeFantasia}</div>` : ''}
                </td>
                <td class="px-6 py-4 text-center text-gray-600">${c.numeroDocumento || '-'}</td>
                <td class="px-6 py-4 text-center text-gray-600">${c.telefone || '-'}</td>
                <td class="px-6 py-4 text-center">
                    <span class="px-2 py-1 text-xs font-semibold rounded-full ${
                        c.status === 'Ativo' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'
                    }">
                        ${c.status}
                    </span>
                </td>
                <td class="px-6 py-4 text-center">
                    <div class="flex justify-center gap-3">
                        <a href="#"
                           data-id="${c.id}"
                           data-open-modal="cliente"
                           class="text-[#063550] hover:text-blue-900 transition">
                            <i class="fa-solid fa-pen"></i>
                        </a>
                        <a href="#"
                           data-id="${c.id}"
                           data-open-modal="confirma-cliente"
                           class="text-[#063550] transition">
                            <i class="fa-solid fa-trash"></i>
                        </a>
                    </div>
                </td>
            </tr>
        `);
    });
}

function preencherFormularioCliente(c) {
    // Delay para garantir que o modal renderizou
    setTimeout(() => {
        const form = document.querySelector('.modal-body #clienteForm');
        if (!form) return;

        form.querySelector('#clienteId').value = c.id || '';
        form.querySelector('#razaoSocial').value = c.razaoSocial || '';
        form.querySelector('#nomeFantasia').value = c.nomeFantasia || '';
        form.querySelector('#numeroDocumento').value = c.numeroDocumento || '';
        form.querySelector('#email').value = c.email || '';
        form.querySelector('#telefone').value = c.telefone || '';
        form.querySelector('#observacao').value = c.observacao || '';
        
        const selTipo = form.querySelector('#tipoPessoa');
        if(selTipo) selTipo.value = c.tipoPessoa;
        
        const selStatus = form.querySelector('#status');
        if(selStatus) selStatus.value = c.status;

    }, 50);
}

function salvarCliente(e) {
    e.preventDefault();
    console.log(">>> [DEBUG] Salvando cliente...");

    const form = e.target;
    const data = Object.fromEntries(new FormData(form));
    
    if (data.id) data.id = parseInt(data.id); else delete data.id;

    fetch('/api/clientes', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    })
    .then(r => {
        if (!r.ok) throw new Error();
        carregarClientes();
        fecharModal();
    })
    .catch(console.error);
}

function fecharModal() {
    const modal = document.getElementById('modal');
    if (!modal) return;
    
    modal.classList.remove('open');
    setTimeout(() => {
        modal.classList.add('hidden');
        const mb = document.querySelector('.modal-body');
        if(mb) mb.innerHTML = '';
    }, 200);
}