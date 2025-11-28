package paginaAspe.Controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class PdfController {

    @GetMapping("/generar-boleta")
    public String generarBoletaPdf() {
        String rutaArchivo = "boleta_factura.pdf";

        try {
            // Crear documento PDF con márgenes
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(document, new FileOutputStream(rutaArchivo));
            document.open();

            // === ESTILOS ===
            Font tituloFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLACK);
            Font subFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.DARK_GRAY);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK);
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.BLACK);

            // === ENCABEZADO ===
            Paragraph titulo = new Paragraph("BOLETA DE VENTA ELECTRÓNICA", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            document.add(new Paragraph("ASPE.insano E.I.R.L", boldFont));
            document.add(new Paragraph("RUC: 20543218765", normalFont));
            document.add(new Paragraph("Dirección: Av. Los Olivos 456 - Lima", normalFont));
            document.add(new Paragraph("Teléfono: (01) 567-4321", normalFont));
            document.add(new Paragraph(" "));

            // Fecha actual
            String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            document.add(new Paragraph("Fecha de emisión: " + fecha, normalFont));
            document.add(new Paragraph("N° Boleta: B001-000123", normalFont));
            document.add(new Paragraph(" "));

            // === DATOS DEL CLIENTE ===
            document.add(new Paragraph("Cliente: Juan Pérez", normalFont));
            document.add(new Paragraph("DNI: 74569852", normalFont));
            document.add(new Paragraph("Dirección: Jr. San Martín 789 - Lima", normalFont));
            document.add(new Paragraph(" "));

            // === TABLA DE DETALLE ===
            PdfPTable tabla = new PdfPTable(4);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[]{3, 1, 1, 1});

            // Encabezado tabla
            String[] headers = {"DESCRIPCIÓN", "CANT.", "P.U.", "IMPORTE"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, boldFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                tabla.addCell(cell);
            }

            // Filas de detalle
            Object[][] items = {
                    {"Servicio de Marketing Web", "1", "800.00", "800.00"},
                    {"Diseño de Logotipo", "1", "250.00", "250.00"},
                    {"Mantenimiento mensual", "1", "150.00", "150.00"}
            };

            for (Object[] item : items) {
                for (Object col : item) {
                    PdfPCell cell = new PdfPCell(new Phrase(col.toString(), normalFont));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tabla.addCell(cell);
                }
            }

            document.add(tabla);
            document.add(new Paragraph(" "));

            // === TOTALES ===
            Paragraph subtotal = new Paragraph("SUBTOTAL:      S/ 1200.00", boldFont);
            subtotal.setAlignment(Element.ALIGN_RIGHT);
            document.add(subtotal);

            Paragraph igv = new Paragraph("IGV (18%):     S/ 216.00", boldFont);
            igv.setAlignment(Element.ALIGN_RIGHT);
            document.add(igv);

            Paragraph total = new Paragraph("TOTAL A PAGAR: S/ 1416.00", subFont);
            total.setAlignment(Element.ALIGN_RIGHT);
            document.add(total);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Método de pago: Transferencia bancaria", normalFont));
            document.add(new Paragraph("Cuenta: 001-1234567890 - Banco de Crédito del Perú", normalFont));
            document.add(new Paragraph(" "));

            Paragraph gracias = new Paragraph("Gracias por su preferencia.", normalFont);
            gracias.setAlignment(Element.ALIGN_CENTER);
            document.add(gracias);

            document.add(new Paragraph("\n\n"));
            Paragraph firma = new Paragraph("__________________________\nFirma del Cliente", normalFont);
            firma.setAlignment(Element.ALIGN_CENTER);
            document.add(firma);

            // Cerrar documento
            document.close();

            // === ABRIR PDF AUTOMÁTICAMENTE (solo en Windows) ===
            try {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + rutaArchivo);
            } catch (IOException ex) {
                System.out.println("No se pudo abrir automáticamente el PDF.");
            }

            return "✅ Boleta generada correctamente: " + rutaArchivo;

        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Error al generar la boleta: " + e.getMessage();
        }
    }
}
