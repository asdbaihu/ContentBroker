package br.com.allanlarangeiras.contentbroker.controller;

import br.com.allanlarangeiras.contentbroker.model.ErrorEntity;
import br.com.allanlarangeiras.contentbroker.model.FileEntity;
import br.com.allanlarangeiras.contentbroker.services.PDFProcessorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by allan.larangeiras on 28/01/2019.
 */
@Controller
public class PDFController {

    private PDFProcessorService service;
    private Gson gson;

    @Autowired
    public PDFController(PDFProcessorService service, Gson gson) {
        this.gson = gson;
        this.service = service;
    }

    @RequestMapping(value = {"/pdf/list", "/pdf/list/{uuid}"}, method = RequestMethod.GET)
    @ResponseBody
    public List<FileEntity> list(@PathVariable(required = false) String uuid) {
        return service.find(uuid);
    }

    @RequestMapping(value = "/pdf/download/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> downloadFile(@PathVariable String uuid) throws IOException {
        FileEntity fileEntity = service.getFileInStorage(uuid);
        if (fileEntity != null) {
            Path path = Paths.get(fileEntity.getFile().getAbsolutePath());
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            headers.add("Content-disposition", "attachment; filename="+ fileEntity.getName());


            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(fileEntity.getFile().length())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(resource);

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(gson.toJson(new ErrorEntity("NOT_FOUND")));
        }

    }
}
