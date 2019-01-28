package br.com.allanlarangeiras.contentbroker.services;

import br.com.allanlarangeiras.contentbroker.dao.FileDao;
import br.com.allanlarangeiras.contentbroker.listeners.PDFProcessorListener;
import br.com.allanlarangeiras.contentbroker.model.FileEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

/**
 * Created by allan.larangeiras on 28/01/2019.
 */
@Service
public class PDFProcessorService {

    private FileDao dao;

    private Logger logger = LoggerFactory.getLogger(PDFProcessorListener.class);

    @Value("${fileserver.storage}")
    private String storagePath;

    public PDFProcessorService(FileDao dao) {
        this.dao = dao;
    }

    @Transactional
    public void saveFile(FileEntity fileEntity) {
        if (this.dao.fileAlreadPersisted(fileEntity)) {
            this.dao.removeFile(fileEntity);
        }
        this.dao.saveFile(fileEntity);

        fileEntity.store(storagePath);
        logger.info(String.format("File %s persisted and removed from file server temp", fileEntity.getName()));
    }

    public List<FileEntity> find(String uuid) {
        return dao.find(uuid, 1);
    }

    public FileEntity getFileInStorage(String uuid) {
        List<FileEntity> fileEntities = dao.find(uuid, 1);
        if (fileEntities != null && !fileEntities.isEmpty()) {
            FileEntity fileEntity = fileEntities.get(0);
            fileEntity.setFile(new File(storagePath + File.separator + uuid + ".pdf"));
            return fileEntity;

        }

        return null;
    }
}
