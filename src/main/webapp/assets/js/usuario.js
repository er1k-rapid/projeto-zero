document.addEventListener('DOMContentLoaded', () => {
    carregarUsuarios();

    document.getElementById('filtroForm')?.addEventListener('submit', filtrarUsuarios);

    // --- LISTENER DE CLIQUE (Gatilho) ---
    document.addEventListener('click', e => {

        const trigger = e.target.closest('[data-open-modal]');
        if (!trigger) return;

        e.preventDefault();

        const key = trigger.dataset.openModal;
        const id = trigger.dataset.id;

        if (key === 'usuario') {

            if (id) {
                fetch(`/api/usuarios?id=${id}`)
                    .then(async r => {
                        if (!r.ok) {
                            const t = await r.text();
                            console.error(t);
                            throw new Error();
                        }
                        return r.json();
                    })
                    .then(preencherFormulario)
                    .catch(console.error);
            }
        }

        document.dispatchEvent(
            new CustomEvent('modal:open', { detail: { key, id } })
        );
    });

    // --- GERENCIADOR DO MODAL ---
    document.addEventListener('modal:open', e => {
        const key = e.detail.key;
        const id = e.detail.id;

        if (key === 'usuario') {
            carregarCargos();
            
            const form = document.querySelector('.modal-body #usuarioForm');
            if (!form) return;


            form.replaceWith(form.cloneNode(true));
            const newForm = document.querySelector('.modal-body #usuarioForm');
            

            if (!id) {
                newForm.reset();
                const idInput = newForm.querySelector('#id');
                if(idInput) idInput.value = '';
            }

            newForm.addEventListener('submit', salvarUsuario);
        }

        if (key === 'confirma') {
            const btn = document.querySelector('.modal-body #confirmarExclusao');
            if (!btn) return;

            btn.replaceWith(btn.cloneNode(true));
            const newBtn = document.querySelector('.modal-body #confirmarExclusao');
            
            // Lógica de exclusão
            newBtn.onclick = () => {
                fetch(`/api/usuarios?id=${id}`, { method: 'DELETE' })
                    .then(r => {
                        if (!r.ok) throw new Error('Erro ao excluir');
                        carregarUsuarios();
                        fecharModal();
                    })
                    .catch(console.error);
            };
        }
    });
});

// --- FUNÇÕES ---

function carregarUsuarios(nome = '') {
    const url = nome
        ? `/api/usuarios?nome=${encodeURIComponent(nome)}`
        : `/api/usuarios`;

    fetch(url)
        .then(r => r.json())
        .then(renderTabela)
        .catch(console.error);
}

function filtrarUsuarios(e) {
    e.preventDefault();
    const nome = document.getElementById('searchInput').value;
    carregarUsuarios(nome);
}

function renderTabela(usuarios) {
    const tbody = document.getElementById('usuariosTableBody');
    if (!tbody) return;
    tbody.innerHTML = '';

    if (!usuarios || usuarios.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="6" class="px-4 py-2 text-center text-sm text-gray-500">
                    Nenhum usuário encontrado
                </td>
            </tr>
        `;
        return;
    }

    usuarios.forEach(u => {
        tbody.insertAdjacentHTML('beforeend', `
            <tr class="hover:bg-gray-50 transition-colors duration-150">
                <td class="px-4 py-2 text-sm text-center">${u.id}</td>
                <td class="px-4 py-2 text-sm text-center">${u.nome}</td>
                <td class="px-4 py-2 text-sm text-center">${u.cargoDescricao || '-'}</td>
                <td class="px-4 py-2 text-sm text-center">${u.tipoUsuario}</td>
                <td class="px-4 py-2 text-sm text-center">
                    <span class="px-2 py-1 rounded-2xl text-xs ${
                        u.status === 'Ativo'
                            ? 'bg-blue-100 text-blue-700'
                            : 'bg-red-100 text-red-700'
                    }">
                        ${u.status}
                    </span>
                </td>
                <td class="px-4 py-2 text-sm text-center">
                    <div class="inline-flex items-center gap-3">
                        <a href="#"
                           data-id="${u.id}"
                           data-open-modal="usuario"
                           data-modal-title="Editar Usuário"
                           class="text-[#063550] transition">
                            <i class="fa-solid fa-pen"></i>
                        </a>
                        <a href="#"
                           data-id="${u.id}"
                           data-open-modal="confirma"
                           data-modal-title="Confirmar Exclusão"
                           class="text-[#063550] transition">
                            <i class="fa-solid fa-trash"></i>
                        </a>
                    </div>
                </td>
            </tr>
        `);
    });
}

function preencherFormulario(u) {
    if (!u) return;

    const modalBody = document.querySelector('.modal-body');

    modalBody.querySelector('#id').value = u.id || '';
    modalBody.querySelector('#nome').value = u.nome || '';
    modalBody.querySelector('#email').value = u.email || '';
    modalBody.querySelector('#observacao').value = u.observacao || '';
    

    const senha = modalBody.querySelector('#senha');
    const confSenha = modalBody.querySelector('#confirmaSenha');
    if(senha) senha.value = '';
    if(confSenha) confSenha.value = '';


    const cargoSelect = modalBody.querySelector('#cargo');
    if(cargoSelect) cargoSelect.value = u.cargoId || '';

    setSelectValueByText(modalBody.querySelector('#tipo'), u.tipoUsuario);
    setSelectValueByText(modalBody.querySelector('#status'), u.status);

    const data = u.dataCadastro ? new Date(u.dataCadastro).toISOString().slice(0,10) : '';
    const dataInput = modalBody.querySelector('#dataCadastro');
    if(dataInput) dataInput.value = data;
}

function setSelectValueByText(select, text) {
    if (!text || !select) return;
    const option = Array.from(select.options).find(o => o.text === text || o.value == text);
    if (option) select.value = option.value;
}

function fecharModal() {
    const modal = document.getElementById('modal');
    if (!modal) return;
    
    modal.classList.remove('open');

    setTimeout(() => {
        modal.classList.add('hidden');
        const body = document.querySelector('.modal-body');
        if(body) body.innerHTML = '';
    }, 200);
}

function salvarUsuario(e) {
    e.preventDefault();

    const form = e.target;
    const data = Object.fromEntries(new FormData(form));
    

    if (!data.cargoId || data.cargoId === "") {
        alert("Por favor, selecione um Cargo para o usuário.");
        return; 
    }


    if (data.id) data.id = parseInt(data.id);
    data.cargoId = parseInt(data.cargoId);

    fetch('/api/usuarios', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    })
    .then(async r => {
        if (!r.ok) {
            const text = await r.text();
            throw new Error(text || 'Erro ao salvar (Status ' + r.status + ')');
        }
        carregarUsuarios();
        fecharModal();
    })
    .catch(err => {
        console.error(err);
        alert("Erro ao salvar: " + err.message);
    });
}

async function carregarCargos() {
    try {
        const res = await fetch('/api/cargos');
        if (!res.ok) throw new Error('Erro ao carregar cargos');

        const cargos = await res.json();
        const selectCargo = document.querySelector('.modal-body #cargo');
        if (!selectCargo) return;

        selectCargo.innerHTML = '<option value="" disabled selected>Selecione</option>';

        cargos.forEach(c => {
            const option = document.createElement('option');
            option.value = c.id;
            option.textContent = c.descricao;
            selectCargo.appendChild(option);
        });

    } catch (err) {
        console.error(err);
    }
}