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

async function imprimirBoleta() {
    const clienteId = sessionStorage.getItem('clienteId');

    if (!clienteId) {
        alert("Debe iniciar sesión para solicitar un servicio.");
        window.location.href = "Login.html";
        return;
    }

    if (carrito.length === 0) {
        alert("El carrito está vacío.");
        return;
    }

    // Datos para el DTO que el backend sí acepta
    const solicitud = {
        clienteId: parseInt(clienteId),
        nombre: "Solicitud de servicio",
        descripcion: "",
        presupuesto: 0
    };

    try {
        const response = await fetch("http://localhost:8080/api/campanias/solicitar", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(solicitud)
        });

        if (response.ok) {
            carrito = [];
            renderizarCarrito();
            window.location.href = "Usuario.html?campanaCreada=1";
        } else {
            const errorText = await response.text();
            console.error("Error del backend:", errorText);
            alert("No se pudo registrar la solicitud.");
        }

    } catch (e) {
        console.error("Error al solicitar servicio:", e);
        alert("Error al enviar la solicitud.");
    }
}



// Inicializar al cargar el documento
document.addEventListener('DOMContentLoaded', () => {
    renderizarCarrito();
});
