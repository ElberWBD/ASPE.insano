
document.getElementById("bot-float-btn").addEventListener("click", () => {
    document.getElementById("chatbot-window").classList.toggle("hidden");
    startChat();
});

document.getElementById("close-chatbot").addEventListener("click", () => {
    document.getElementById("chatbot-window").classList.add("hidden");
});

const chatBody = document.getElementById("chatbot-body");
const footer = document.getElementById("chatbot-footer");

// Iniciar chat
function startChat() {
    chatBody.innerHTML = "";
    botMessage("Hola 👋 ¿En qué puedo ayudarte hoy?");

    loadOptions();
}

// Cargar opciones de preguntas
function loadOptions() {
    footer.innerHTML = `
        <button class="option-btn" onclick="selectOption('precios')">💸 Ver precios</button>
        <button class="option-btn" onclick="selectOption('servicios')">🛠️ Ver servicios</button>
        <button class="option-btn" onclick="selectOption('soporte')">✍️ Escribir a soporte</button>
    `;
}

// Mensaje del bot
function botMessage(text) {
    chatBody.innerHTML += `<div class="bot-msg">${text}</div>`;
    chatBody.scrollTop = chatBody.scrollHeight;
}

// Mensaje del usuario
function userMessage(text) {
    chatBody.innerHTML += `<div class="user-msg">${text}</div>`;
    chatBody.scrollTop = chatBody.scrollHeight;
}

// Cuando se elige una opción
function selectOption(option) {

    if (option === "precios") {
        userMessage("Ver precios");
        botMessage("Nuestros precios dependen del servicio que necesites. ¿Te gustaría saber más sobre:");
        botMessage("• Desarrollo Web 🌐<br>• Marketing Digital 📣<br>• Apps Móviles 📱");
        return;
    }

    if (option === "servicios") {
        userMessage("Ver servicios");
        botMessage("Ofrecemos:\n✔ Desarrollo web\n✔ Apps móviles\n✔ Marketing digital\n✔ Branding y diseño\n✔ Publicidad ATL/BTL");
        return;
    }

    if (option === "soporte") {
        userMessage("Quiero escribir a soporte");
        botMessage("Claro 👇 escribe tu mensaje y uno de nuestros agentes lo recibirá:");

        footer.innerHTML = `
            <input id="supportText" class="support-input" placeholder="Escribe tu mensaje...">
            <button class="send-btn" onclick="sendSupport()">Enviar</button>
        `;
    }
}

// Enviar mensaje manual a soporte
function sendSupport() {
    const message = document.getElementById("supportText").value;

    if (message.trim() === "") return;

    userMessage(message);
    document.getElementById("supportText").value = "";

    botMessage("Gracias, tu mensaje ha sido enviado a soporte 📨. Te responderemos pronto.");

    // Después de enviar, volver a mostrar opciones
    setTimeout(loadOptions, 1500);
}
