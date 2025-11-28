// Constante de Impuesto (IGV en Perú es 18%)
const IGV_RATE = 0.18; 

// Lista de Servicios/Productos (IDs y Precios deben coincidir con el HTML)
const servicios = [
    { id: 1, nombre: "E-commerce", precio: 800.00 },
    { id: 2, nombre: "Aplicaciones Web", precio: 1200.00 },
    { id: 3, nombre: "Consultoría en marketing", precio: 500.00 },
    { id: 4, nombre: "Eventos", precio: 750.00 },
    { id: 5, nombre: "Campañas BTL", precio: 600.00 },
    { id: 6, nombre: "Producción audiovisual", precio: 950.00 },
    { id: 7, nombre: "Campañas ATL", precio: 1500.00 }
];

let carrito = [];

// Referencias del DOM
const carritoSidebar = document.getElementById('carrito-sidebar');
const carritoLista = document.getElementById('carrito-lista');
const cartSubtotal = document.getElementById('cart-subtotal');
const cartIgv = document.getElementById('cart-igv');
const cartTotalFinal = document.getElementById('cart-total-final');
const cartCount = document.getElementById('cart-count');

// --- Control del Sidebar ---
function toggleSidebar(open) {
    if (open) {
        carritoSidebar.classList.add('open');
    } else {
        carritoSidebar.classList.remove('open');
    }
}

// --- Lógica del Carrito ---
function agregarAlCarrito(servicioId) {
    const servicio = servicios.find(p => p.id === servicioId);
    if (!servicio) return;

    const itemExistente = carrito.find(item => item.id === servicioId);

    if (itemExistente) {
        itemExistente.cantidad += 1;
    } else {
        carrito.push({
            id: servicio.id,
            nombre: servicio.nombre,
            precio: servicio.precio,
            cantidad: 1
        });
    }
    
    if (!carritoSidebar.classList.contains('open')) {
        toggleSidebar(true);
    }

    renderizarCarrito();
}

function eliminarDelCarrito(servicioId) {
    carrito = carrito.filter(item => item.id !== servicioId);
    renderizarCarrito();
}

// --- Cálculo de Totales ---
function calcularTotales() {
    let subtotal = carrito.reduce((acc, item) => acc + item.precio * item.cantidad, 0);
    let totalItems = carrito.reduce((acc, item) => acc + item.cantidad, 0);

    const igv = subtotal * IGV_RATE;
    const totalFinal = subtotal + igv;

    cartSubtotal.textContent = `$${subtotal.toFixed(2)}`;
    cartIgv.textContent = `$${igv.toFixed(2)}`;
    cartTotalFinal.textContent = `$${totalFinal.toFixed(2)}`;
    cartCount.textContent = totalItems;
}

// --- Renderizar (Mostrar) el Carrito ---
function renderizarCarrito() {
    carritoLista.innerHTML = '';
    
    if (carrito.length === 0) {
        carritoLista.innerHTML = `
            <div class="empty-cart-message">
                <i class="fa fa-shopping-basket"></i>
                <p>Tu pedido está vacío.</p>
                <p style="font-size: 0.8em; margin-top: 5px;">Los servicios aparecerán aquí.</p>
            </div>
        `;
    } else {
        carrito.forEach(item => {
            const li = document.createElement('li');
            li.classList.add('cart-item');
            const subtotalItem = item.precio * item.cantidad;

            li.innerHTML = `
                <div style="flex-grow: 1;">
                    ${item.nombre} x ${item.cantidad} 
                    <small style="color: #999;">($${item.precio.toFixed(2)} c/u)</small>
                </div>
                <strong>$${subtotalItem.toFixed(2)}</strong>
                <button 
                    style="background: none; border: none; color: red; margin-left: 10px; cursor: pointer;"
                    onclick="eliminarDelCarrito(${item.id})">
                    <i class="fa fa-times-circle"></i>
                </button>
            `;
            carritoLista.appendChild(li);
        });
    }
    
    calcularTotales();
}

// --- Función de Imprimir Boleta/Pedido ---
function imprimirBoleta() {
    if (carrito.length === 0) {
        alert("El pedido está vacío. Agregue un servicio para imprimir la boleta.");
        return;
    }

    const subtotalText = cartSubtotal.textContent;
    const igvText = cartIgv.textContent;
    const totalFinalText = cartTotalFinal.textContent;

    let boletaContent = `
        <div style="font-family: monospace; padding: 20px; max-width: 350px;">
            <h1 style="text-align: center; font-size: 1.5em;">AS.pe Pedido</h1>
            <p>Fecha: ${new Date().toLocaleDateString()} ${new Date().toLocaleTimeString()}</p>
            <hr style="border-top: 1px dashed black;">
            
            <p style="font-weight: bold; margin-bottom: 5px;">SERVICIOS:</p>
            ${carrito.map(item => `
                <p style="margin: 3px 0; display: flex; justify-content: space-between;">
                    <span>${item.nombre.substring(0, 20)} x ${item.cantidad}</span>
                    <span>$${(item.precio * item.cantidad).toFixed(2)}</span>
                </p>
            `).join('')}

            <hr style="border-top: 1px dashed black; margin-top: 15px;">
            <p style="margin: 3px 0; display: flex; justify-content: space-between;"><span>Subtotal:</span><span>${subtotalText}</span></p>
            <p style="margin: 3px 0; display: flex; justify-content: space-between;"><span>IGV (${(IGV_RATE * 100).toFixed(0)}%):</span><span>${igvText}</span></p>
            <p style="font-size: 1.1em; font-weight: bold; display: flex; justify-content: space-between; border-top: 1px solid black; padding-top: 5px;"><span>TOTAL:</span><span>${totalFinalText}</span></p>
            <p style="text-align: center; margin-top: 20px;">¡Gracias por tu pedido!</p>
        </div>
    `;

    const printWindow = window.open('', '', 'height=600,width=400');
    printWindow.document.write(`<html><head><title>Boleta de Pedido</title></head><body>${boletaContent}</body></html>`);
    printWindow.document.close();
    printWindow.focus(); 
    printWindow.print();
    printWindow.close();
}

// Inicializar al cargar el documento
document.addEventListener('DOMContentLoaded', () => {
    renderizarCarrito();
});
