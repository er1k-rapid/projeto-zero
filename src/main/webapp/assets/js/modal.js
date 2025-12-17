document.addEventListener('click', e => {
    const openBtn = e.target.closest('[data-open-modal]')
    const closeBtn = e.target.closest('[data-close-modal]')
    const modal = document.getElementById('modal')

    if (openBtn) {
        const key = openBtn.dataset.openModal
        const title = openBtn.dataset.modalTitle || ''
        const content = document.getElementById(key + 'Modal')

        if (!content) return

        document.querySelector('.modal-title').textContent = title
		
		const modalBody = document.querySelector('.modal-body');

		modalBody.replaceChildren(
		    ...content.cloneNode(true).childNodes
		);

        modal.classList.remove('hidden')

        requestAnimationFrame(() => {
            modal.classList.add('open')
        })
		
		document.dispatchEvent(new CustomEvent('modal:open', {
		    detail: { key }
		}));
    }

    if (closeBtn) {
        modal.classList.remove('open')

        setTimeout(() => {
            modal.classList.add('hidden')
            document.querySelector('.modal-body').innerHTML = ''
        }, 200)
    }
})

document.addEventListener('keydown', e => {
    if (e.key === 'Escape') {
        const modal = document.getElementById('modal')
        modal.classList.remove('open')

        setTimeout(() => {
            modal.classList.add('hidden')
            document.querySelector('.modal-body').innerHTML = ''
        }, 200)
    }
})
