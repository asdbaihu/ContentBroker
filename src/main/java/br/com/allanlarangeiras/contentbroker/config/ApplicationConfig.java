package br.com.allanlarangeiras.contentbroker.config;

import br.com.allanlarangeiras.contentbroker.listeners.PDFProcessorListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by allan.larangeiras on 28/01/2019.
 */
@Component
public class ApplicationConfig implements ApplicationRunner {

    @Value("${fileserver.storage}")
    private String storagePath;

    @Value("${fileserver.tmp}")
    private String fileServerPath;

    private Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {

        logger.info("Executing Application Runner");

        String[] neededDirectories = new String[] {
                this.storagePath,
                this.fileServerPath,
                this.fileServerPath + File.separator + "staging"
        };

        for (String path : neededDirectories) {
            File filePath = new File(path);
            if (!filePath.exists()) {
                filePath.mkdirs();
                logger.info(String.format("Creating directory %s", filePath));
            }
        }

    }

}
