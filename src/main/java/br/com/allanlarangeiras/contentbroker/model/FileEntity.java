package br.com.allanlarangeiras.contentbroker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.File;
import java.util.Date;

@Document(collection = "pdf")
public class FileEntity {

    @Id
	private String id;
	private String name;
	private long length;
	private Date lasModified;

	@JsonIgnore
    @Transient
	private File file;

    public FileEntity() {}

    public FileEntity(File file) {
		this.file = file;
		name = file.getName();
		length = file.length();
		lasModified = new Date(file.lastModified());
	}

	public boolean isValid() {
		return isPdf();
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public Date getLasModified() {
		return lasModified;
	}

	public void setLasModified(Date lasModified) {
		this.lasModified = lasModified;
	}

    @JsonIgnore
    public void store(String storagePath) {
        boolean success = this.file.renameTo(new File(storagePath + File.separator + this.getId() + ".pdf"));
        if (success) {
            this.file.delete();
        } else {
            throw new IllegalArgumentException("Error when storing the file");

        }

    }

	private boolean isPdf() {
		String[] fileNameComponents = file.getName().split("\\.");
		if (fileNameComponents.length > 1) {
			String extension = fileNameComponents[fileNameComponents.length -1];
			if (extension.equalsIgnoreCase("PDF")) {
				return true;
			}
		}
		return false;
	}

}
