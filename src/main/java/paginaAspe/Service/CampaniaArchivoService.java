package paginaAspe.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import paginaAspe.Model.CampaniaArchivo;
import paginaAspe.Repository.CampaniaArchivoRepository;

import java.io.IOException;
import java.util.List;

@Service
public class CampaniaArchivoService {

    private final CampaniaArchivoRepository repo;
    private final StorageService storage;

    public CampaniaArchivoService(CampaniaArchivoRepository repo, StorageService storage) {
        this.repo = repo;
        this.storage = storage;
    }

    public CampaniaArchivo guardar(CampaniaArchivo meta, MultipartFile file) throws IOException {
        String ruta = storage.save(file);
        meta.setRuta(ruta);
        meta.setNombreOriginal(file.getOriginalFilename());
        meta.setMimeType(file.getContentType());
        meta.setTamanio(file.getSize());
        return repo.save(meta);
    }

    public List<CampaniaArchivo> listarPorCampania(Long campaniaId) {
        return repo.findByCampaniaId(campaniaId);
    }

    public byte[] leerBytes(String ruta) throws IOException {
        return storage.read(ruta);
    }
}
