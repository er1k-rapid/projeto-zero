document.addEventListener('DOMContentLoaded', () => {
    carregarProtocolos();

    // Filtro
    document.getElementById('filtroProtocoloForm')?.addEventListener('submit', (e) => {
        e.preventDefault();
        carregarProtocolos(document.getElementById('searchInput').value);
    });


    document.addEventListener('click', async e => {
        const trigger = e.target.closest('[data-open-modal]');
        if (!trigger) return;
        
        e.preventDefault();
        const key = trigger.dataset.openModal;
        const id = trigger.dataset.id;


        if (key === 'protocolo') {
            try {

                const resClientes = await fetch('/api/clientes');
                const clientes = await resClientes.json();

                let dadosProtocolo = null;

                if (id) {
                    const resProto = await fetch(`/api/protocolos?id=${id}`);
                    dadosProtocolo = await resProto.json();
                }

                despacharModal(key, id, { listaClientes: clientes, protocolo: dadosProtocolo });

            } catch (err) {
                console.error("Erro ao carregar dados:", err);
                alert("Erro ao carregar dados. Verifique o console.");
            }
        } 
        else {
            despacharModal(key, id, null);
        }
    });

    // --- GERENCIADOR DO MODAL ---
    document.addEventListener('modal:open', e => {
        const { key, id, dados } = e.detail;
        const modal = document.getElementById('modal');
        const modalBody = modal.querySelector('.modal-body');

        // --- MODAL DE CADASTRO/EDIÇÃO ---
        if (key === 'protocolo') {
            const template = document.getElementById('modalProtocoloTemplate');
            modalBody.innerHTML = '';
            modalBody.appendChild(template.content.cloneNode(true));

            const form = modalBody.querySelector('#protocoloForm');
            const selectCliente = form.querySelector('#clienteSelect');


            if (dados && dados.listaClientes) {
                selectCliente.innerHTML = '<option value="">Selecione um cliente...</option>';
                dados.listaClientes.forEach(c => {

                    const opt = document.createElement('option');
                    opt.value = c.id;

                    opt.textContent = c.nomeFantasia 
                        ? `${c.razaoSocial} (${c.nomeFantasia})` 
                        : c.razaoSocial;
                    selectCliente.appendChild(opt);
                });
            }

            if (id && dados && dados.protocolo) {
                const p = dados.protocolo;
                
                form.querySelector('#protocoloId').value = p.id;
                
                selectCliente.value = p.clienteId; 
                
                form.querySelector('#prioridade').value = p.prioridade;
                form.querySelector('#descricao').value = p.descricao;
                form.querySelector('#resolucao').value = p.resolucao || '';
                form.querySelector('#status').value = p.status;
            } else {
                form.reset();
                form.querySelector('#protocoloId').value = '';
            }

            form.addEventListener('submit', salvarProtocolo);
        }

        // --- MODAL DE CONFIRMAÇÃO ---
        else if (key === 'confirma-protocolo') {
             modalBody.innerHTML = `
                <div class="text-center p-6">
                    <p class="mb-4 text-gray-700 font-medium">Tem certeza que deseja excluir este protocolo?</p>
                    <div class="flex justify-center gap-3">
                         <button type="button" data-close-modal class="px-4 py-2 bg-gray-100 rounded text-gray-700 hover:bg-gray-200">Cancelar</button>
                         <button id="btnExcluirProto" class="px-4 py-2 bg-red-600 text-white rounded shadow-sm hover:bg-red-700">Sim, Excluir</button>
                    </div>
                </div>
            `;
            const btn = document.getElementById('btnExcluirProto');
            if(btn) btn.onclick = () => excluirProtocolo(id);
        }


        modal.classList.remove('hidden');
        setTimeout(() => modal.classList.add('open'), 10);
    });
    
    document.addEventListener('click', e => {
        if (e.target.closest('[data-close-modal]')) fecharModal();
    });
});

// --- FUNÇÕES AUXILIARES ---

function despacharModal(key, id, dados) {
    document.dispatchEvent(new CustomEvent('modal:open', { detail: { key, id, dados } }));
}

function carregarProtocolos(termo = '') {
    const url = termo ? `/api/protocolos?termo=${encodeURIComponent(termo)}` : `/api/protocolos`;
    const tbody = document.getElementById('protocolosTableBody');
    if(tbody) tbody.innerHTML = `<tr><td colspan="6" class="text-center py-8 text-gray-400"><i class="fa-solid fa-spinner fa-spin"></i> Carregando...</td></tr>`;

    fetch(url)
        .then(r => r.json())
        .then(lista => {
            if(!tbody) return;
            tbody.innerHTML = '';
            
            if(!lista || lista.length === 0) {
                tbody.innerHTML = `<tr><td colspan="6" class="text-center py-8 text-gray-500">Nenhum protocolo encontrado.</td></tr>`;
                return;
            }

            lista.forEach(p => {
                tbody.insertAdjacentHTML('beforeend', `
                    <tr class="bg-white border-b hover:bg-gray-50 transition">
                        <td class="px-6 py-4 text-center font-medium text-gray-900">#${p.id}</td>
                        <td class="px-6 py-4 font-semibold text-gray-800">
                            ${p.clienteNome || '<span class="text-red-400">Cliente Excluído</span>'}
                        </td>
                        <td class="px-6 py-4 text-gray-600 truncate max-w-xs" title="${p.descricao}">${p.descricao}</td>
                        <td class="px-6 py-4 text-center">
                            <span class="px-2 py-1 text-xs rounded-full font-semibold ${getPrioridadeColor(p.prioridade)}">${p.prioridade}</span>
                        </td>
                        <td class="px-6 py-4 text-center">
                            <span class="px-2 py-1 text-xs rounded-full font-semibold ${getStatusColor(p.status)}">${p.status}</span>
                        </td>
                        <td class="px-6 py-4 text-center">
                            <div class="flex justify-center gap-3">
                                <button type="button" data-id="${p.id}" data-open-modal="protocolo" class="text-[#063550] hover:text-blue-800 transition"><i class="fa-solid fa-pen"></i></button>
                                <button type="button" data-id="${p.id}" data-open-modal="confirma-protocolo" class="text-[#063550] hover:text-blue-800 transition"><i class="fa-solid fa-trash"></i></button>
                            </div>
                        </td>
                    </tr>
                `);
            });
        })
        .catch(err => console.error(err));
}

function salvarProtocolo(e) {
    e.preventDefault();
    const data = Object.fromEntries(new FormData(e.target));
    
    // Converte Strings para Inteiros
    if(data.id) data.id = parseInt(data.id); else delete data.id;
    if(data.clienteId) data.clienteId = parseInt(data.clienteId);

    fetch('/api/protocolos', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    }).then(r => {
        if(r.ok) { 
            carregarProtocolos(); 
            fecharModal(); 
        } else {
            r.text().then(t => alert('Erro: ' + t));
        }
    });
}

function excluirProtocolo(id) {
    fetch(`/api/protocolos?id=${id}`, { method: 'DELETE' })
        .then(r => {
            if(r.ok) { carregarProtocolos(); fecharModal(); }
            else alert('Erro ao excluir');
        });
}

function fecharModal() {
    const modal = document.getElementById('modal');
    if(!modal) return;
    modal.classList.remove('open');
    setTimeout(() => {
        modal.classList.add('hidden');
        const body = modal.querySelector('.modal-body');
        if(body) body.innerHTML = '';
    }, 200);
}

// CORES DAS LABELS
function getPrioridadeColor(p) {
    if(p === 'Alta') return 'bg-red-100 text-red-800';
    if(p === 'Média') return 'bg-yellow-100 text-yellow-800';
    return 'bg-blue-100 text-blue-800';
}

function getStatusColor(s) {
    if(s === 'Concluído') return 'bg-green-100 text-green-800';
    if(s === 'Cancelado') return 'bg-gray-100 text-gray-800';
    if(s === 'Em Andamento') return 'bg-blue-100 text-blue-800';
    return 'bg-gray-200 text-gray-700';
}