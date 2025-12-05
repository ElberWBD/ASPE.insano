// ===========================================
//  Cargar datos del trabajador desde SessionStorage
// ===========================================
const userId = Number(sessionStorage.getItem("idUsuario"));
let campaniaId = null;

// ===========================================
//  Conexión WebSocket
// ===========================================
let socket = new WebSocket("ws://localhost:8080/ws/comentarios");

socket.onopen = () => console.log("🟢 WebSocket conectado (Trabajador)");

socket.onerror = (err) => console.error("❌ WebSocket error:", err);

socket.onclose = () => {
    console.warn("🔴 WebSocket cerrado. Intentando reconectar en 3s...");
    setTimeout(() => location.reload(), 3000);
};

// ===========================================
//  Recepción de mensajes desde el servidor
// ===========================================
socket.onmessage = (event) => {
    const msg = JSON.parse(event.data);

    switch (msg.tipo) {
        case "historial":
            cargarHistorial(msg.comentarios);
            break;

        case "nuevoComentario":
            if (msg.comentario.autorId !== userId) {
                agregarMensaje(msg.comentario);
            }
            break;
    }
};

// ===========================================
//  Cargar historial
// ===========================================
function cargarHistorial(lista) {
    const box = document.getElementById("chatB_box");
    box.innerHTML = ""; // ✔ SE LIMPIA SOLO AQUÍ

    lista.forEach(m => agregarMensaje(m));
}

// ===========================================
//  Agregar mensaje al chat (CORREGIDO)
// ===========================================
function agregarMensaje(m) {
    const box = document.getElementById("chatB_box");

    const div = document.createElement("div");
    div.classList.add("chat-msg");

    const esPropio = m.autorId === userId;
    div.classList.add(esPropio ? "propio" : "otros");

    div.innerHTML = `
        <div class="meta">
            <strong>${m.autor ?? m.autorNombre ?? "Usuario"}</strong> —
            ${new Date(m.fecha).toLocaleString()}
        </div>
        <div>${m.mensaje}</div>
    `;

    box.appendChild(div);
    box.scrollTop = box.scrollHeight;
}

// ===========================================
//  Abrir chat del trabajador
// ===========================================
function verChat(idCampania) {
    campaniaId = Number(idCampania);

    document.getElementById("chat-worker-section").style.display = "block";

    if (socket.readyState === WebSocket.OPEN) {
        socket.send(JSON.stringify({
            tipo: "unirseSala",
            campaniaId: campaniaId
        }));
    } else {
        setTimeout(() => verChat(idCampania), 500);
    }
}

// ===========================================
//  Enviar mensaje
// ===========================================
function enviarMensaje() {
    const input = document.getElementById("chatB_input");
    const texto = input.value.trim();

    if (!texto || !campaniaId) return;

    const mensaje = {
        tipo: "nuevoComentario",
        campaniaId: campaniaId,
        autorId: userId,
        autor: "Trabajador",
        mensaje: texto,
        fecha: new Date().toISOString()
    };

    agregarMensaje(mensaje);

    if (socket.readyState === WebSocket.OPEN) {
        socket.send(JSON.stringify(mensaje));
    }

    input.value = "";
}

// ===========================================
//  Eventos enviar mensaje
// ===========================================
document.getElementById("chatB_send").addEventListener("click", enviarMensaje);
document.getElementById("chatB_input").addEventListener("keypress", (e) => {
    if (e.key === "Enter") enviarMensaje();
});
