document.addEventListener('DOMContentLoaded', () => {
    // Estado inicial (0 = Hoje)
    let periodoAtual = 0; 
    
    // Elementos
    const btnFilter = document.getElementById("filterBtn");
    const menuFilter = document.getElementById("filterMenu");
    const labelFilter = document.getElementById("filterLabel");
    const btnRefresh = document.getElementById("refreshBtn");


    carregarDashboard(periodoAtual);


    btnFilter.addEventListener("click", (e) => {
        e.stopPropagation(); 
        menuFilter.classList.toggle("hidden");
    });

    menuFilter.querySelectorAll("button").forEach(item => {
        item.addEventListener("click", () => {
            const dias = item.getAttribute("data-days");
            const texto = item.textContent;


            labelFilter.textContent = texto;
            periodoAtual = dias;
            

            carregarDashboard(periodoAtual);


            menuFilter.classList.add("hidden");
        });
    });


    document.addEventListener("click", e => {
        if (!btnFilter.contains(e.target) && !menuFilter.contains(e.target)) {
            menuFilter.classList.add("hidden");
        }
    });


    btnRefresh.addEventListener('click', () => {

        const icon = btnRefresh.querySelector('i');
        icon.classList.add('fa-spin');
        
        carregarDashboard(periodoAtual).then(() => {
            setTimeout(() => icon.classList.remove('fa-spin'), 500);
        });
    });
});

async function carregarDashboard(dias) {
    try {
        const res = await fetch(`/api/dashboard?periodo=${dias}`);
        if(!res.ok) throw new Error("Erro na API");
        
        const data = await res.json();
        
        atualizarNumero('valTotal', data.totalProtocolos);
        atualizarNumero('valAberto', data.emAberto);
        atualizarNumero('valCritico', data.altaPrioridade);
        atualizarNumero('valConcluido', data.concluidos);


        renderizarTabelaRecentes(data.recentes);

    } catch (err) {
        console.error(err);
    }
}

function atualizarNumero(elementId, valorNovo) {
    const el = document.getElementById(elementId);
    if(el) el.textContent = valorNovo;
}

function renderizarTabelaRecentes(lista) {
    const tbody = document.getElementById('recentesTableBody');
    tbody.innerHTML = '';

    if (!lista || lista.length === 0) {
        tbody.innerHTML = `<tr><td colspan="5" class="text-center py-6 text-gray-500">Nenhuma atividade recente.</td></tr>`;
        return;
    }

    lista.forEach(p => {
        // Formata data
        const dataFormatada = new Date(p.dataAbertura).toLocaleDateString('pt-BR', {
            day: '2-digit', month: '2-digit', hour: '2-digit', minute: '2-digit'
        });

        tbody.insertAdjacentHTML('beforeend', `
            <tr class="bg-white border-b hover:bg-gray-50 transition">
                <td class="px-6 py-4 font-medium text-gray-900">#${p.id}</td>
                <td class="px-6 py-4 font-semibold text-gray-800">${p.clienteNome || '-'}</td>
                <td class="px-6 py-4 text-gray-600 truncate max-w-xs">${p.descricao}</td>
                <td class="px-6 py-4 text-center">
                    <span class="px-2 py-1 text-xs rounded-full font-semibold ${getStatusColor(p.status)}">
                        ${p.status}
                    </span>
                </td>
                <td class="px-6 py-4 text-center text-xs text-gray-500">
                    ${dataFormatada}
                </td>
            </tr>
        `);
    });
}

function getStatusColor(s) {
    if(s === 'Concluído') return 'bg-green-100 text-green-800';
    if(s === 'Cancelado') return 'bg-gray-100 text-gray-800';
    if(s === 'Em Andamento') return 'bg-blue-100 text-blue-800';
    return 'bg-gray-200 text-gray-700';
}