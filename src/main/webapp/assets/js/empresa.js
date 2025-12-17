document.addEventListener('DOMContentLoaded', () => {

    document.addEventListener('click', onEditarClick);

    document.addEventListener('click', e => {
        const trigger = e.target.closest('[data-open-modal][data-id]');
        if (!trigger) return;

        const key = trigger.dataset.openModal;
        const id = trigger.dataset.id;

        document.dispatchEvent(new CustomEvent('modal:open', { detail: { key, id } }));
    });

    document.addEventListener('modal:open', e => {
        const key = e.detail.key;

        if (key === 'empresa') {
            const form = document.querySelector('.modal-body #empresaForm');
            if (!form) return;

            form.replaceWith(form.cloneNode(true));
            const newForm = document.querySelector('.modal-body #empresaForm');
            newForm.addEventListener('submit', salvarEmpresa);
			
			newForm.addEventListener('input', function (e) {
			    if (e.target.id !== 'nroInscricao') return;
			    const tipoPessoa = newForm.querySelector('#tipoPessoa')?.value;
			    e.target.value = aplicarMascaraNroInscricao(e.target.value, tipoPessoa);
			});

			newForm.addEventListener('change', e => {
			    if (e.target.id !== 'tipoPessoa') return;
				
			    const nro = newForm.querySelector('#nroInscricao');
			    if (nro) nro.value = '';
			});
			
			newForm.addEventListener('input', function(e) {
			    if (e.target.id === 'telefone') {
			        e.target.value = aplicarMascaraTelefone(e.target.value);
			    }
			});
			
			newForm.addEventListener('input', function(e) {
			    if (e.target.id === 'cep') {
			        e.target.value = aplicarMascaraCEP(e.target.value);
			    }
			});

            const logoInput = newForm.querySelector('#logoInput');
            const previewLogo = newForm.querySelector('#previewLogo');
            const placeholderLogo = newForm.querySelector('#placeholderLogo');

            logoInput.addEventListener('change', () => {
                const file = logoInput.files[0];
                if (file) {
                    const reader = new FileReader();
                    reader.onload = () => {
                        previewLogo.src = reader.result;
                        previewLogo.classList.remove('hidden');
                        placeholderLogo.classList.add('hidden');
                    };
                    reader.readAsDataURL(file);
                } else {
                    previewLogo.src = '';
                    previewLogo.classList.add('hidden');
                    placeholderLogo.classList.remove('hidden');
                }
            });
        }
    });
});

function aplicarMascaraNroInscricao(value, tipoPessoa) {
    let v = value.replace(/\D/g, '');

    if (tipoPessoa === 'Fisica') {
        v = v.slice(0, 11);
        v = v.replace(/(\d{3})(\d)/, '$1.$2');
        v = v.replace(/(\d{3})(\d)/, '$1.$2');
        v = v.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
    } else if (tipoPessoa === 'Juridica') {
        v = v.slice(0, 14);
        v = v.replace(/(\d{2})(\d)/, '$1.$2');
        v = v.replace(/(\d{3})(\d)/, '$1.$2');
        v = v.replace(/(\d{3})(\d)/, '$1/$2');
        v = v.replace(/(\d{4})(\d{1,2})$/, '$1-$2');
    }

    return v;
}

function aplicarMascaraTelefone(value) {
    let v = value.replace(/\D/g, ''); 
    v = v.slice(0, 11); 

    if (v.length > 10) { 
        v = v.replace(/(\d{2})(\d{5})(\d{4})/, '($1) $2-$3');
    } else if (v.length > 5) {
        v = v.replace(/(\d{2})(\d{4})(\d{0,4})/, '($1) $2-$3');
    } else if (v.length > 2) {
        v = v.replace(/(\d{2})(\d{0,5})/, '($1) $2');
    }

    return v;
}

function aplicarMascaraCEP(value) {
    let v = value.replace(/\D/g, '');
    v = v.slice(0, 8); 
    v = v.replace(/(\d{5})(\d{1,3})?/, function(_, p1, p2) {
        return p2 ? `${p1}-${p2}` : p1;
    });
    return v;
}

function limparNumeros(value) {
    return value.replace(/\D/g, '');
}

function onEditarClick(e) {
    const btn = e.target.closest('[data-open-modal="empresa"][data-id]');
    if (!btn) return;

    const id = btn.dataset.id;

    setTimeout(() => {
        fetch(`/api/empresas?id=${id}`)
            .then(r => r.json())
            .then(preencherFormularioEmpresa)
            .catch(console.error);
    }, 0);
}

function preencherFormularioEmpresa(emp) {
    if (!emp) return;

    const modalBody = document.querySelector('.modal-body');

    modalBody.querySelector('#id').value = emp.id || '';
    modalBody.querySelector('#razaoSocial').value = emp.razaoSocial || '';
    modalBody.querySelector('#tipoPessoa').value = emp.tipoPessoa || '';
	modalBody.querySelector('#nroInscricao').value = aplicarMascaraNroInscricao(
	    emp.nroInscricao || '',
	    emp.tipoPessoa || ''
	);
    modalBody.querySelector('#nomeFantasia').value = emp.nomeFantasia || '';
    modalBody.querySelector('#email').value = emp.email || '';
    modalBody.querySelector('#telefone').value = aplicarMascaraTelefone(emp.telefone || '');
	modalBody.querySelector('#cep').value = aplicarMascaraCEP(emp.cep || '');
	modalBody.querySelector('#logradouro').value = emp.logradouro || '';
	modalBody.querySelector('#numero').value = emp.numero || '';
	modalBody.querySelector('#bairro').value = emp.bairro || '';
	modalBody.querySelector('#cidade').value = emp.cidade || '';
	modalBody.querySelector('#uf').value = emp.uf || '';32
    modalBody.querySelector('#observacoes').value = emp.observacoes || '';

    const previewLogo = modalBody.querySelector('#previewLogo');
    const placeholderLogo = modalBody.querySelector('#placeholderLogo');
    if (emp.logo) {
        previewLogo.src = emp.logo;
        previewLogo.classList.remove('hidden');
        placeholderLogo.classList.add('hidden');
    } else {
        previewLogo.src = '';
        previewLogo.classList.add('hidden');
        placeholderLogo.classList.remove('hidden');
    }
}

function fecharModal() {
    const modal = document.getElementById('modal');
    modal.classList.remove('open');

    setTimeout(() => {
        modal.classList.add('hidden');
        document.querySelector('.modal-body').innerHTML = '';
    }, 200);
}

function salvarEmpresa(e) {
    e.preventDefault();

    const form = e.target;
    const data = new FormData(form);
	
	const tipoPessoa = form.querySelector('#tipoPessoa')?.value;

	let nroInscricao = form.querySelector('#nroInscricao')?.value || '';
	data.set('nroInscricao', limparNumeros(nroInscricao));

	let telefone = form.querySelector('#telefone')?.value || '';
	data.set('telefone', limparNumeros(telefone));

	let cep = form.querySelector('#cep')?.value || '';
	data.set('cep', limparNumeros(cep));

    const logoFile = form.querySelector('#logoInput')?.files[0];
    if (logoFile) {
        const reader = new FileReader();
        reader.onload = () => {
            data.set('logo', reader.result);
            enviarDadosEmpresa(Object.fromEntries(data.entries()));
        };
        reader.readAsDataURL(logoFile);
    } else {
        enviarDadosEmpresa(Object.fromEntries(data.entries()));
    }
}

function enviarDadosEmpresa(data) {
    fetch('/api/empresas', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    })
    .then(r => {
        if (!r.ok) throw new Error();
        fecharModal();
    })
    .catch(console.error);
}

async function buscarEnderecoPorCEP(cep) {
    const cepLimpo = cep.replace(/\D/g, '');
    if (cepLimpo.length !== 8) return null;

    try {
        const response = await fetch(`https://viacep.com.br/ws/${cepLimpo}/json/`);
        const data = await response.json();
        if (data.erro) return null;
        return data;
    } catch (err) {
        console.error('Erro ao buscar CEP:', err);
        return null;
    }
}
