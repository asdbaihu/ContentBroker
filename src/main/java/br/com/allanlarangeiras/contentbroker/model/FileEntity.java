package br.com.allanlarangeiras.contentbroker.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.UUID;

public class FileEntity {

	private String uuid;
	private String name;
	private long length;
	private Date lasModified;
	private File file;

	public FileEntity(File file) {
		this.file = file;
		try {
			uuid = UUID.nameUUIDFromBytes(Files.readAllBytes(file.toPath())).toString();
		} catch (IOException e) {
			uuid = UUID.randomUUID().toString();
		}
		name = file.getName();
		length = file.length();
		lasModified = new Date(file.lastModified());
	}
	
	public boolean isValid() {
		return isPdf();
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

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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
