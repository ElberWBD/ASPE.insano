// cliente-campanias.js — Crear campaña y subir adjuntos (mantiene estilos)
document.addEventListener("DOMContentLoaded", () => {
  const frm = document.getElementById("frmNuevaCampania");
  const upl = document.getElementById("filesCampania");
  const lista = document.getElementById("listaMisCampanias");

  async function crearCampania(dto) {
    const res = await fetch("/api/campanias", {
      method: "POST",
      headers: {"Content-Type":"application/json"},
      body: JSON.stringify(dto)
    });
    if (!res.ok) throw new Error("No se pudo crear la campaña");
    return res.json();
  }

  async function subirAdjuntos(campaniaId, files) {
    const fd = new FormData();
    for (const f of files) fd.append("files", f);
    const res = await fetch(`/api/campanias/${campaniaId}/archivos`, {
      method: "POST",
      body: fd
    });
    if (!res.ok) throw new Error("No se pudieron subir los adjuntos");
    return true;
  }

  async function cargarMisCampanias(clienteId) {
    const res = await fetch(`/api/campanias/cliente/${clienteId}`);
    if (!res.ok) return;
    const data = await res.json();
    if (!lista) return;
    lista.innerHTML = data.map(c => `
      <tr>
        <td>${c.campañaId}</td>
        <td>${c.nombre||"-"}</td>
        <td>${c.estado||"-"}</td>
        <td><a class="btn-acc" href="/Dashcampaña.html?id=${c.campañaId}">Ver</a></td>
      </tr>`).join("");
  }

  if (frm) {
    frm.addEventListener("submit", async (e) => {
      e.preventDefault();
      const usuario = getUsuarioSesion?.() || null; // de sesion.js
      const clienteId = Number(document.getElementById("clienteId")?.value || usuario?.usuarioId || 0);
      const servicioId = Number(document.getElementById("servicioId")?.value || 0);
      const nombre = (document.getElementById("nombreCampania")?.value || "").trim();
      const descripcion = (document.getElementById("descripcionCampania")?.value || "").trim();
      if (!clienteId || !servicioId || !nombre) { alert("Completa los campos requeridos"); return; }

      const dto = { clienteId, servicioId, nombre, descripcion, estado: "CREADA" };
      try {
        const campania = await crearCampania(dto);
        if (upl && upl.files?.length) await subirAdjuntos(campania.campañaId, upl.files);
        alert("✅ Campaña creada");
        location.href = `/Dashcampaña.html?id=${campania.campañaId}`;
      } catch (err) {
        console.error(err); alert("❌ " + err.message);
      }
    });
  }

  // si hay tabla de “mis campañas” y variable clienteId, cárgala
  const clienteIdTabla = document.getElementById("clienteIdTabla")?.value;
  if (clienteIdTabla) cargarMisCampanias(Number(clienteIdTabla));
});
