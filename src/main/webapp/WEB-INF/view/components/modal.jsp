<div id="modal"
     class="modal hidden fixed inset-0 z-[9999] flex items-center justify-center
            opacity-0 transition-opacity duration-200">

    <div class="modal-backdrop absolute inset-0 bg-black/40"
         data-close-modal></div>

    <div class="relative bg-white rounded-2xl shadow-xl p-6
                transform scale-95 opacity-0
                transition-all duration-200 ease-out">

        <div class="flex items-center justify-between mb-4">
            <h3 class="text-lg font-semibold modal-title"></h3>

            <button data-close-modal
                    class="w-8 h-8 flex items-center justify-center rounded-lg
                           hover:bg-gray-100">
                <i class="fa-solid fa-xmark"></i>
            </button>
        </div>

        <div class="modal-body"></div>
    </div>
</div>

<style>
.modal.open {
    opacity: 1;
}

.modal.open > div:last-child {
    opacity: 1;
    transform: scale(1);
}
</style>
