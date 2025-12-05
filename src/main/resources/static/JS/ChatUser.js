const clienteId = sessionStorage.getItem('clienteId');
if (!clienteId) window.location.href = 'Login.html';

let socket = null;
let esperandoUnion = false;
let ID_CAMPANIA_GLOBAL;

/* ----------------------------- CARGAR DATOS ----------------------------- */

async function cargarDatosCliente() {
    try {
        const res = await fetch(`http://localhost:8080/clientes/buscarPorId/${clienteId}`);
        if (!res.ok) throw new Error();
        const c = await res.json();
        document.getElementById('idCliente').value = c.id ?? '';
        document.getElementById('nombre').value = c.nombre ?? '';
        document.getElementById('apellido').value = c.apellido ?? '';
        document.getElementById('razonSocial').value = c.razonSocial ?? '';
        document.getElementById('email').value = c.email ?? '';
        document.getElementById('telefono').value = c.telefono ?? '';
        document.getElementById('password').value = c.password ?? '';
        document.getElementById('fechaCreacion').value = c.fechaCreacion ?? '';
    } catch (err) {}
}

/* ----------------------------- ACTUALIZAR DATOS ----------------------------- */

async function guardarCambios() {
    const data = {
        id: clienteId,
        nombre: document.getElementById('nombre').value,
        apellido: document.getElementById('apellido').value,
        razonSocial: document.getElementById('razonSocial').value,
        email: document.getElementById('email').value,
        telefono: document.getElementById('telefono').value,
        password: document.getElementById('password').value,
        fechaCreacion: document.getElementById('fechaCreacion').value
    };
    try {
        const res = await fetch(`http://localhost:8080/clientes/actualizar/${clienteId}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        if (res.ok) alert('Datos actualizados correctamente');
        else alert('No se pudo actualizar');
    } catch (err) {}
}

/* ----------------------------- CAMPANAS ----------------------------- */

function mostrarSeccion(id, elemento) {
    document.querySelectorAll('.seccion').forEach(s => s.classList.remove('activa'));
    document.getElementById(id).classList.add('activa');
    document.querySelectorAll('.barra ul li').forEach(li => li.classList.remove('activo'));
    if (elemento) elemento.classList.add('activo');

    if (id === 'campanas') {
        mostrarMisCampanas();
        cargarCampanas();
    }
}

function volverInicio() { window.location.href = 'Index.html'; }

async function cargarCampanas() {
    try {
        const res = await fetch(`http://localhost:8080/api/campanias/cliente/${clienteId}`);
        if (!res.ok) throw new Error();
        const campanias = await res.json();
        pintarCampanas(campanias);
    } catch (err) {}
}

function pintarCampanas(campanias) {
    const cont = document.getElementById('listaCampanas');
    cont.innerHTML = '';
    if (!campanias || campanias.length === 0) {
        cont.innerHTML = '<p>No tienes campañas registradas.</p>';
        return;
    }
    campanias.forEach(camp => {
        const card = document.createElement('div');
        card.className = 'card-campana';
        card.innerHTML = `
            <h4>${camp.nombre}</h4>
            <p><strong>Descripción:</strong> ${camp.descripcion ?? 'Sin descripción'}</p>
            <p><strong>Estado:</strong> ${camp.estado ?? 'Pendiente'}</p>
            <p><strong>Presupuesto:</strong> $${camp.presupuesto ?? '0'}</p>
            <button onclick='mostrarDetallesCampana(${JSON.stringify(camp)})'>Detalles</button>
            <button onclick="window.location.href='pago.html'">Pagar</button>

        `;
        cont.appendChild(card);
    });
}

function mostrarMisCampanas() {
    document.getElementById('mis-campanas').style.display = 'block';
    document.getElementById('nueva-campana').style.display = 'none';
}

function mostrarNuevaCampana() {
    document.getElementById('mis-campanas').style.display = 'none';
    document.getElementById('nueva-campana').style.display = 'block';
}

/* ----------------------------- CREAR CAMPANA ----------------------------- */

async function crearCampana(e) {
    e.preventDefault();
    const archivos = document.getElementById('entregablesCampana').files;
    const archivosBase64 = await convertirArchivosA64(archivos);
    const data = {
        nombre: document.getElementById('nombreCampana').value,
        descripcion: document.getElementById('descripcionCampana').value,
        fechaInicio: document.getElementById('fechaInicioCampana').value,
        fechaFin: document.getElementById('fechaFinCampana').value,
        presupuesto: document.getElementById('presupuestoCampana').value,
        entregables: archivosBase64
    };
    try {
        const res = await fetch(`http://localhost:8080/api/campanias/crear/${clienteId}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        if (res.ok) {
            alert('Campaña creada');
            mostrarMisCampanas();
            cargarCampanas();
        } else alert('Error al crear campaña');
    } catch (err) {}
}

function convertirArchivosA64(files) {
    return Promise.all([...files].map(f => new Promise((res, rej) => {
        const reader = new FileReader();
        reader.onload = e => res(e.target.result);
        reader.onerror = rej;
        reader.readAsDataURL(f);
    })));
}

/* ----------------------------- ELIMINAR CUENTA ----------------------------- */

async function eliminarCuenta() {
    if (!confirm('¿Eliminar cuenta?')) return;
    try {
        const res = await fetch(`http://localhost:8080/clientes/${clienteId}`, { method: 'DELETE' });
        if (res.ok) {
            alert('Cuenta eliminada');
            sessionStorage.clear();
            window.location.href = 'Index.html';
        } else alert('Error al eliminar la cuenta');
    } catch (err) {}
}

/* ----------------------------- DETALLES CAMPANA + CHAT ----------------------------- */

function mostrarDetallesCampana(camp) {
    ID_CAMPANIA_GLOBAL = camp.idCampania || camp.id;

    const modal = document.getElementById('modal');
    const modalContent = document.getElementById('modal-content');

    modalContent.innerHTML = `
        <div style="display:flex; gap:20px;">
            <div style="width:50%;">
                <span class="cerrar" onclick="cerrarModal()">&times;</span>
                <h3>${camp.nombre}</h3>
                <p><strong>ID Cliente:</strong> ${camp.idCliente}</p>
                <p><strong>Cliente:</strong> ${camp.cliente?.nombre ?? ''} ${camp.cliente?.apellido ?? ''}</p>
                <p><strong>Razón Social:</strong> ${camp.cliente?.razonSocial ?? ''}</p>
                <p><strong>Email:</strong> ${camp.cliente?.email ?? ''}</p>
                <p><strong>Teléfono:</strong> ${camp.cliente?.telefono ?? ''}</p>
                <p><strong>Descripción:</strong> ${camp.descripcion}</p>
                <p><strong>Fecha inicio:</strong> ${camp.fechaInicio}</p>
                <p><strong>Fecha fin:</strong> ${camp.fechaFin}</p>
                <p><strong>Presupuesto:</strong> $${camp.presupuesto}</p>
                <p><strong>Estado:</strong> ${camp.estado ?? 'Pendiente'}</p>
            </div>

            <div style="width:50%; display:flex; flex-direction:column;">
                <h3>Chat de la campaña</h3>

                <div id="chat-box" style="height:250px; overflow-y:auto; border:1px solid #ccc; padding:10px; border-radius:8px; background:#fafafa;"></div>

                <input type="file" id="archivo-input" style="margin-top:10px;" />

                <div style="display:flex; margin-top:10px; gap:5px;">
                    <input type="text" id="chat-input" placeholder="Escribe tu mensaje..." style="flex:1; padding:8px; border-radius:6px; border:1px solid #ccc;">
                </div>
            </div>
        </div>
    `;

    modal.style.display = 'flex';
    iniciarConexionWebSocket(ID_CAMPANIA_GLOBAL);
}

function cerrarModal() { document.getElementById('modal').style.display = 'none'; }

/* ----------------------------- WEBSOCKET ----------------------------- */

function iniciarConexionWebSocket(campaniaId) {
    if (socket && socket.readyState === WebSocket.OPEN) socket.close();

    socket = new WebSocket("ws://localhost:8080/ws/comentarios");
    esperandoUnion = false;

    socket.onopen = () => {
        socket.send(JSON.stringify({ tipo: "unirseSala", campaniaId }));
        esperandoUnion = true;
    };

    socket.onmessage = (event) => {
        const msg = JSON.parse(event.data);

        if (msg.tipo === "confirmacionUnion" && esperandoUnion) {
            esperandoUnion = false;
            cargarHistorial(campaniaId);
            return;
        }

        if (msg.tipo === "nuevoComentario") {
            const data = msg.comentario ? msg.comentario : msg;
            if (data.autorId == clienteId) return;
            agregarMensaje(data, false);
        }
    };

    socket.onerror = err => console.error(err);
    socket.onclose = () => setTimeout(() => iniciarConexionWebSocket(campaniaId), 1500);
}

/* ----------------------------- HISTORIAL ----------------------------- */

async function cargarHistorial(campaniaId) {
    try {
        const res = await fetch(`http://localhost:8080/api/campanias/${campaniaId}/comentarios`);
        if (!res.ok) return;
        const comentarios = await res.json();
        const box = document.getElementById("chat-box");
        box.innerHTML = "";
        comentarios.forEach(c => agregarMensaje(c, c.autorId === clienteId));
    } catch (err) {}
}

/* ----------------------------- AGREGAR MENSAJE ----------------------------- */

function agregarMensaje(m, esPropio = false) {
    const box = document.getElementById("chat-box");
    if (!box) return;
    const div = document.createElement("div");
    div.style.textAlign = esPropio ? "right" : "left";
    div.style.marginBottom = "10px";
    div.innerHTML = `
        <strong>${esPropio ? "Tú" : (m.autor ?? "Usuario")}</strong><br>
        ${m.mensaje ?? ""}
        ${m.archivoBase64 ? `<br><a href="${m.archivoBase64}" target="_blank">📎 Archivo adjunto</a>` : ""}
        <br><small style="font-size:0.7em;color:#666;">${new Date(m.fecha).toLocaleString()}</small>
    `;
    box.appendChild(div);
    box.scrollTop = box.scrollHeight;
}

/* ----------------------------- ENVIAR MENSAJE CORRECTO ----------------------------- */

async function enviarMensajeChat() {
    const input = document.getElementById("chat-input");
    const fileInput = document.getElementById("archivo-input");

    if (!input.value.trim() && !fileInput.files[0]) return;

    let comentario = {
        autorId: clienteId,
        mensaje: input.value,
        archivoBase64: null
    };

    if (fileInput.files[0]) {
        comentario.archivoBase64 = await new Promise(res => {
            const r = new FileReader();
            r.onload = e => res(e.target.result);
            r.readAsDataURL(fileInput.files[0]);
        });
    }

    const res = await fetch(`http://localhost:8080/api/campanias/${ID_CAMPANIA_GLOBAL}/comentarios`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(comentario)
    });

    if (!res.ok) {
        console.error("Error al guardar comentario");
        return;
    }

    const creado = await res.json();

    agregarMensaje(creado, true);

    input.value = "";
    fileInput.value = "";
}

/* ----------------------------- EVENTOS EXTRA ----------------------------- */

document.addEventListener('click', e => {
    if (e.target.id === 'togglePassword') {
        const passInput = document.getElementById('password');
        if (passInput.type === 'password') {
            passInput.type = 'text';
            e.target.textContent = '🙈';
        } else {
            passInput.type = 'password';
            e.target.textContent = '👁';
        }
    }
});

document.addEventListener('DOMContentLoaded', () => {
    cargarDatosCliente();
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.get("campanaCreada") === "1") {
        mostrarSeccion('campanas', document.getElementById('nav-campanas'));
        cargarCampanas();
    }
});

