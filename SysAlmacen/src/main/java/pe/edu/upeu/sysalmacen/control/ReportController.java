package pe.edu.upeu.sysalmacen.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.RequiredArgsConstructor;
import pe.edu.upeu.sysalmacen.dtos.report.ProdMasVendidosDTO;
import pe.edu.upeu.sysalmacen.modelo.MediaFile;
import pe.edu.upeu.sysalmacen.servicio.IMediaFileService;
import pe.edu.upeu.sysalmacen.servicio.IProductoService;



@RequiredArgsConstructor
@RestController
@RequestMapping("/reporte")
public class ReportController {
    private final IProductoService productoService;
    private final IMediaFileService mfService;
    private final Cloudinary cloudinary;
    @GetMapping("/pmvendidos")
    public List<ProdMasVendidosDTO> getProductosMasVendidos() {
        return productoService.obtenerProductosMasVendidos();
    }

    @GetMapping(value = "/generateReport", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> generateReport() throws ReportProcessingException {
        try {
            byte[] data = productoService.generateReport();
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            throw new ReportProcessingException("Error generating report", e);
        }
    }

    @GetMapping(value = "/readFile/{idFile}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> readFile(@PathVariable("idFile") Long idFile) throws ReportProcessingException {
        try {
            byte[] data = mfService.findById(idFile).getContent();
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            throw new ReportProcessingException("Error reading file with ID: " + idFile, e);
        }
    }

    @PostMapping(value = "/saveFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveFile(@RequestParam("file") MultipartFile multipartFile) throws ReportProcessingException {
        try {
            MediaFile mf = new MediaFile();
            mf.setContent(multipartFile.getBytes());
            mf.setFileName(multipartFile.getOriginalFilename());
            mf.setFileType(multipartFile.getContentType());
            mfService.save(mf);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new ReportProcessingException("Error saving file to database", e);
        }
    }

    @PostMapping(value = "/saveFileCloud", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<Void> saveFileCloud(@RequestParam("file") MultipartFile multipartFile) throws ReportProcessingException {
    try {
        File f = this.convertToFile(multipartFile);
        cloudinary.uploader().upload(f, ObjectUtils.asMap("resource_type", "auto")); // Eliminada la variable "response"
        return ResponseEntity.ok().build();
    } catch (Exception e) {
        throw new ReportProcessingException("Error saving file to Cloudinary", e);
    }
}
    private File convertToFile(MultipartFile file) throws ReportProcessingException {
        try {
            File convFile = new File(file.getOriginalFilename());
            try (FileOutputStream fos = new FileOutputStream(convFile)) {
                fos.write(file.getBytes());
            }
            return convFile;
        } catch (IOException e) {
            throw new ReportProcessingException("Error converting MultipartFile to File", e);
        }
    }


    
public class FileProcessingException extends RuntimeException {
    public FileProcessingException(String message) {
        super(message);
    }

    public FileProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
public class ReportProcessingException extends RuntimeException {
    public ReportProcessingException(String message) {
        super(message);
    }

    public ReportProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}

}