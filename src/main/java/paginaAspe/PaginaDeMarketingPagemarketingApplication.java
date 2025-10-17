package paginaAspe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.awt.Desktop;
import java.net.URI;

@SpringBootApplication
public class PaginaDeMarketingPagemarketingApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaginaDeMarketingPagemarketingApplication.class, args);
        abrirNavegador("http://localhost:8080/HTML/Index.html");
    }

    private static void abrirNavegador(String url) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
