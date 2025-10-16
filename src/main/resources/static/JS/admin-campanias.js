// admin-campanias.js — Bandeja y detalle admin con cambio de estado y descargas
document.addEventListener("DOMContentLoaded", () => {

  async function listarCampanias() {
    const res = await fetch("/api/campanias");
    if (!res.ok) return;
    const data = await res.json();
    const tbody = document.getElementById("listaCampaniasAdmin");
    if (!tbody) return;
    tbody.innerHTML = data.map(c => `
      <tr>
        <td>${c.campañaId}</td>
        <td>${c.nombre||"-"}</td>
        <td>${c.clienteId||"-"}</td>
        <td><span class="badge ${("estado-"+(c.estado||"")).toLowerCase()}">${c.estado||"-"}</span></td>
        <td><a class="btn-acc" href="/Dashcampaña.html?id=${c.campañaId}">Abrir</a></td>
      </tr>`).join("");
  }

  async function cargarDetalle(id) {
    const res = await fetch(`/api/campanias/${id}`);
    if (!res.ok) return;
    const c = await res.json();
    document.getElementById("lblCampaniaNombre")?.innerText = c.nombre || ("Campaña " + id);
    document.getElementById("lblCampaniaEstado")?.innerText = c.estado || "-";

    const rA = await fetch(`/api/campanias/${id}/archivos`);
    const archivos = rA.ok ? await rA.json() : [];
    const listA = document.getElementById("listaAdjuntos");
    if (listA) listA.innerHTML = archivos.map(a => 
      `<li><a href="/api/campanias/${id}/archivos/${a.id}">${a.nombreOriginal}</a> <small>${a.mime} • ${a.tamano} bytes</small></li>`
    ).join("");

    const rH = await fetch(`/api/campanias/${id}/historial`);
    const historial = rH.ok ? await rH.json() : [];
    const listH = document.getElementById("historialEstados");
    if (listH) listH.innerHTML = historial.map(h => 
      `<li><b>${h.estado}</b> — ${h.observacion||""} <small>${h.eventoEn}</small></li>`
    ).join("");
  }

  async function cambiarEstado(id, estado, observacion, usuarioId) {
    const res = await fetch(`/api/admin/campanias/${id}/estado`, {
      method:"PATCH",
      headers: {"Content-Type":"application/json"},
      body: JSON.stringify({ estado, observacion, usuarioId })
    });
    if (!res.ok) throw new Error("No se pudo cambiar el estado");
    return res.json();
  }

  // Hook UI
  const params = new URLSearchParams(location.search);
  const campaniaId = params.get("id");
  if (document.getElementById("listaCampaniasAdmin")) listarCampanias();
  if (campaniaId) cargarDetalle(campaniaId);

  const btnCambiar = document.getElementById("btnCambiarEstado");
  if (btnCambiar) {
    btnCambiar.addEventListener("click", async () => {
      const estado = document.getElementById("selEstado")?.value;
      const obs = document.getElementById("txtObs")?.value || "";
      const usuario = getUsuarioSesion?.(); // Admin en sesión
      try {
        await cambiarEstado(campaniaId, estado, obs, Number(usuario?.usuarioId||0));
        alert("✅ Estado actualizado");
        location.reload();
      } catch(e) { alert("❌ "+e.message); }
    });
  }
});
