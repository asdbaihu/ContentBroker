package br.com.allanlarangeiras.contentbroker.listeners;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import br.com.allanlarangeiras.contentbroker.config.JmsConfig;
import br.com.allanlarangeiras.contentbroker.dao.FileDao;
import br.com.allanlarangeiras.contentbroker.model.FileEntity;

@Component
public class PDFProcessorListener {

	private Logger logger = LoggerFactory.getLogger(PDFProcessorListener.class);

	@Value("${fileserver.path}")
	private String fileServerPath;
	
	private FileDao dao;

	@Autowired
	public PDFProcessorListener(FileDao dao) {
		super();
		this.dao = dao;
	}

	@JmsListener(destination = JmsConfig.PDF_PROCESSOR, containerFactory = "jmsFactory")
	public void receiveMessage(String filePath) {
		logger.info(String.format("Processing file: %s", filePath));
		File file = new File(filePath);
		if (file.exists() && file.isFile()) {
			FileEntity fileEntity = new FileEntity(file);
			if (fileEntity.isValid()) {
				dao.saveFile(fileEntity);
			} else {
				moveFileToStageDirectory(file);
			}
		} else {
			logger.info(String.format("The path %s is not valid file", filePath));
		}
	}

	private void moveFileToStageDirectory(File file) {
		if (file.renameTo(new File(getStagingDirectoryPath() + file.getName()))) {
			logger.info(String.format("The file %s was moved to staging directory", file.getAbsolutePath()));
			file.delete();
		}

	}

	private String getStagingDirectoryPath() {
		return fileServerPath + File.separator + "staging" + File.separator;
	}

}
