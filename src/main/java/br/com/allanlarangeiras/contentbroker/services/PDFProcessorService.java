package br.com.allanlarangeiras.contentbroker.services;

import br.com.allanlarangeiras.contentbroker.dao.PdfRepository;
import br.com.allanlarangeiras.contentbroker.listeners.PDFProcessorListener;
import br.com.allanlarangeiras.contentbroker.model.FileEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * Created by allan.larangeiras on 28/01/2019.
 */
@Service
public class PDFProcessorService {

    private PdfRepository repository;

    private Logger logger = LoggerFactory.getLogger(PDFProcessorListener.class);

    @Value("${fileserver.storage}")
    private String storagePath;

    @Autowired
    public PDFProcessorService(PdfRepository pdfRepository) {
        this.repository = pdfRepository;
    }

    @Transactional
    public void saveFile(FileEntity fileEntity) {
        this.repository.save(fileEntity);
        fileEntity.store(storagePath);
        logger.info(String.format("File %s persisted and removed from file server temp", fileEntity.getName()));
    }

    public List<FileEntity> find(String uuid) {
        return this.repository.findAll();
    }

    public Optional<FileEntity> getFileInStorage(String id) {
        Optional<FileEntity> result = this.repository.findById(id);
        if (result.isPresent()) {
            FileEntity fileEntity = result.get();
            fileEntity.setFile(new File(storagePath + File.separator + id + ".pdf"));
        }

        return result;
    }
}
