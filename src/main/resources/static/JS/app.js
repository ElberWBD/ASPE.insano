// ===== CONSTANTES =====
const IGV_RATE = 0.18;

const servicios = [
    { id: 1, nombre: "E-commerce", precio: 100.00 },
    { id: 2, nombre: "Aplicaciones Web", precio: 200.00 },
    { id: 3, nombre: "Consultoría en marketing", precio: 300.00 },
    { id: 4, nombre: "Eventos", precio: 400.00 },
    { id: 5, nombre: "Campañas BTL", precio: 500.00 },
    { id: 6, nombre: "Producción audiovisual", precio: 600.00 },
    { id: 7, nombre: "Campañas ATL", precio: 700.00 }
];

let carrito = [];

// ===== REFERENCIAS DEL DOM =====
const carritoSidebar = document.getElementById('carrito-sidebar');
const carritoLista = document.getElementById('carrito-lista');
const cartSubtotal = document.getElementById('cart-subtotal');
const cartIgv = document.getElementById('cart-igv');
const cartTotalFinal = document.getElementById('cart-total-final');
const cartCount = document.getElementById('cart-count');

// ===== MOSTRAR/OCULTAR SIDEBAR =====
function toggleSidebar(open) {
    if (open) {
        carritoSidebar.classList.add('open');
    } else {
        carritoSidebar.classList.remove('open');
    }
}

// ===== AGREGAR AL CARRITO =====
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

    toggleSidebar(true);
    renderizarCarrito();
}

// ===== ELIMINAR ITEM =====
function eliminarDelCarrito(servicioId) {
    carrito = carrito.filter(item => item.id !== servicioId);
    renderizarCarrito();
}

// ===== CALCULAR TOTALES =====
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

// ===== RENDERIZAR CARRITO =====
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

    // 🟢 GUARDAR EN LOCALSTORAGE
    localStorage.setItem("carritoServicios", JSON.stringify(carrito));

    calcularTotales();
}

// ===== ENVIAR SOLICITUD (BOLETA) =====
async function imprimirBoleta() {
    const clienteId = sessionStorage.getItem('clienteId');
    const nombreCliente = sessionStorage.getItem('clienteNombre');

    if (!clienteId) {
        alert("Debe iniciar sesión para solicitar un servicio.");
        window.location.href = "Login.html";
        return;
    }

    if (carrito.length === 0) {
        alert("El carrito está vacío.");
        return;
    }

    const presupuestoReal = carrito.reduce((total, item) => total + item.precio * item.cantidad, 0);

    // Crear texto de servicios
    const serviciosTexto = carrito.map(item => `${item.nombre} (x${item.cantidad})`).join(", ");

    const solicitud = {
        clienteId: parseInt(clienteId),
        nombreCliente: nombreCliente,
        nombre: "Solicitud de servicio",
        descripcion: "Servicios: " + serviciosTexto,
        presupuesto: presupuestoReal,
        estado: "En Revisión"
    };

    try {
        const response = await fetch("http://localhost:8080/api/campanias/solicitar", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(solicitud)
        });

        if (response.ok) {
            const data = await response.json();

            carrito = [];
            renderizarCarrito();

            localStorage.removeItem("carritoServicios");

            window.location.href = `Usuario.html?campanaCreada=1&idCliente=${clienteId}&idCampania=${data.idCampania}`;
        } else {
            alert("No se pudo registrar la solicitud.");
        }
    } catch (e) {
        console.error("Error al solicitar servicio:", e);
        alert("Error al enviar la solicitud.");
    }
}

// ===== CARGAR CARRITO GUARDADO =====
document.addEventListener('DOMContentLoaded', () => {
    const guardado = localStorage.getItem("carritoServicios");

    if (guardado) {
        carrito = JSON.parse(guardado);
    }

    renderizarCarrito();
});
