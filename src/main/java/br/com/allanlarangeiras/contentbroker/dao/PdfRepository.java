package br.com.allanlarangeiras.contentbroker.dao;

import br.com.allanlarangeiras.contentbroker.model.FileEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by allan.larangeiras on 29/01/2019.
 */
public interface PdfRepository extends MongoRepository<FileEntity, String> { }
