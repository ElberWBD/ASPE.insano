document.addEventListener('DOMContentLoaded', () => {
    const productsGrid = document.getElementById('products-grid');
    const inboxList = document.getElementById('inbox-list');
    const detailOverlay = document.getElementById('product-detail-overlay');
    const detailCloseBtn = document.getElementById('close-detail-btn');
    const detailTitle = document.getElementById('detail-product-title');
    const deliverablesList = document.getElementById('deliverables-list');
    const reviewItem = document.getElementById('review-item');
    const reportItem = document.getElementById('report-item');

    // Datos de ejemplo para simular una base de datos/API
    const productsData = [
        { id: 1, title: 'Campaña SEO Q3', description: 'Optimización de palabras clave y estructura web.', status: 'finalizado', progress: 100, detail: { deliverables: ['Keyword Research', 'Auditoría Técnica', 'Optimización On-Page'], review: 'Ninguno pendiente', report: 'Informe de resultados Q3 listo.' } },
        { id: 2, title: 'Redes Sociales Cliente A', description: 'Creación y programación de contenido para Instagram.', status: 'revision', progress: 85, detail: { deliverables: ['Calendario de Contenido (Agosto)', 'Diseños de 10 Posts', 'Revisión de Copywriting'], review: 'Pendiente aprobación del cliente.', report: 'Métricas de engagement (Julio).' } },
        { id: 3, title: 'Email Marketing Funnel', description: 'Diseño e implementación de secuencias de bienvenida.', status: 'en-proceso', progress: 40, detail: { deliverables: ['Diseño de 3 emails', 'Estructura del Funnel', 'Segmentación de Audiencia'], review: 'Primer borrador listo para QA.', report: 'N/A' } },
        { id: 4, title: 'Publicidad PPC Google', description: 'Lanzamiento de campaña de búsqueda para nuevo producto.', status: 'finalizado', progress: 100, detail: { deliverables: ['Estrategia de Bidding', 'Creación de Anuncios (5 sets)', 'Monitoreo inicial'], review: 'Campaña en vivo, todo aprobado.', report: 'Informe de rendimiento de la primera semana.' } }
    ];

    const inboxMessages = [
        { id: 1, message: 'Revisa las métricas de la campaña de Email.', checked: false },
        { id: 2, message: 'La nueva descripción del Producto 1 necesita tu visto bueno.', checked: true },
        { id: 3, message: 'Reunión de estrategia movida a las 11:00 AM.', checked: false },
    ];

    // ------------------ FUNCIONES DE RENDERIZADO ------------------

    // 1. Renderiza las tarjetas de producto
    function renderProductCards() {
        productsData.forEach(product => {
            const card = document.createElement('div');
            card.classList.add('product-card', `status--${product.status}`);
            card.dataset.productId = product.id; // Para identificar el producto al hacer clic

            const statusText = product.status.replace('-', ' ').toUpperCase();

            card.innerHTML = `
                <h3>${product.title}</h3>
                <p class="product-card__description">${product.description}</p>
                <div class="product-card__status-bar">
                    <div class="product-card__status-fill" style="width: ${product.progress}%;">
                        ${statusText}
                    </div>
                </div>
            `;
            productsGrid.appendChild(card);
        });
    }

    // 2. Renderiza la bandeja de entrada
    function renderInbox() {
        inboxMessages.forEach(item => {
            const listItem = document.createElement('li');
            listItem.classList.add('inbox__item');
            
            // Aplica estilos si ya está marcado como leído
            const messageStyle = item.checked ? 'text-decoration: line-through; color: #999;' : '';

            listItem.innerHTML = `
                <input type="checkbox" class="inbox__checkbox" ${item.checked ? 'checked' : ''}>
                <span class="inbox__message" style="${messageStyle}">${item.message}</span>
            `;
            inboxList.appendChild(listItem);
        });
    }

    // ------------------ FUNCIONES DE INTERACTIVIDAD ------------------

    // Maneja la apertura del modal de detalle
    function openProductDetail(productId) {
        const product = productsData.find(p => p.id === parseInt(productId));
        if (!product) return;

        // Carga los datos en el modal
        detailTitle.textContent = product.title;
        
        // Entregables
        deliverablesList.innerHTML = ''; // Limpia
        product.detail.deliverables.forEach(item => {
            const li = document.createElement('li');
            li.textContent = item;
            deliverablesList.appendChild(li);
        });
        
        // Por Revisar / Informe
        reviewItem.textContent = product.detail.review;
        reportItem.textContent = product.detail.report;

        // Muestra el modal
        detailOverlay.style.display = 'flex';
        document.body.style.overflow = 'hidden'; // Evita el scroll del fondo
    }

    // Cierra el modal de detalle
    function closeProductDetail() {
        detailOverlay.style.display = 'none';
        document.body.style.overflow = 'auto';
    }

    // ------------------ EVENT LISTENERS ------------------
    
    // Evento para abrir el modal al hacer clic en una tarjeta de producto
    productsGrid.addEventListener('click', (e) => {
        const card = e.target.closest('.product-card');
        if (card) {
            openProductDetail(card.dataset.productId);
        }
    });

    // Evento para cerrar el modal
    detailCloseBtn.addEventListener('click', closeProductDetail);
    detailOverlay.addEventListener('click', (e) => {
        // Cierra solo si se hace clic en el fondo oscuro
        if (e.target === detailOverlay) {
            closeProductDetail();
        }
    });

    // Evento para la interacción de la Bandeja de Entrada
    inboxList.addEventListener('change', (event) => {
        if (event.target.classList.contains('inbox__checkbox')) {
            const messageSpan = event.target.nextElementSibling;
            if (event.target.checked) {
                messageSpan.style.textDecoration = 'line-through';
                messageSpan.style.color = '#999';
            } else {
                messageSpan.style.textDecoration = 'none';
                messageSpan.style.color = '#333';
            }
        }
    });

    // ------------------ INICIALIZACIÓN ------------------
    renderProductCards();
    renderInbox();
});