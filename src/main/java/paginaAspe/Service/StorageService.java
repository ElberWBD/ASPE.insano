package paginaAspe.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;
import java.util.UUID;

@Service
public class StorageService {
    private final Path root;

    public StorageService(@Value("${uploads.dir}") String dir) throws IOException {
        this.root = Paths.get(dir).toAbsolutePath().normalize();
        Files.createDirectories(this.root);
    }

    public String save(MultipartFile file) throws IOException {
        String clean = StringUtils.cleanPath(file.getOriginalFilename());
        String name = UUID.randomUUID() + "_" + clean;
        Path target = root.resolve(name);
        try (InputStream in = file.getInputStream()) {
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        }
        return target.toString();
    }

    public byte[] read(String path) throws IOException {
        return Files.readAllBytes(Paths.get(path));
    }
}
