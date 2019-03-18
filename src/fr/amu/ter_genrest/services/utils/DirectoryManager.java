package fr.amu.ter_genrest.services.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import fr.amu.ter_genrest.entities.project.Project;
import fr.amu.ter_genrest.entities.user.User;

@LocalBean
@Stateless
public class DirectoryManager {

	public String getWebContentPathFolder() throws UnsupportedEncodingException {

		String path = this.getClass().getClassLoader().getResource("").getPath();
		String fullPath = URLDecoder.decode(path, "UTF-8");
		String pathArr[] = fullPath.split("/WEB-INF/classes/");
		fullPath = pathArr[0];
		
		return fullPath;
	}
	
	public String zipDirectory(String folderToZip) {
		String zipName = folderToZip + ".zip";
		try {
			zip(Paths.get(folderToZip), Paths.get(zipName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return zipName;
	}

	public void zip(Path sourceFolderPath, Path zipPath) throws Exception {
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath.toFile()));
		Files.walkFileTree(sourceFolderPath, new SimpleFileVisitor<Path>() {
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				zos.putNextEntry(new ZipEntry(sourceFolderPath.relativize(file).toString()));
				Files.copy(file, zos);
				zos.closeEntry();
				return FileVisitResult.CONTINUE;
			}
		});
		zos.close();
	}
	
	public File createDirectory(String path, String nameFolder) {
		
		String separator = File.separator;
		
        File file = new File(path + separator + nameFolder);
        if (!file.exists()) {
            if (file.mkdirs()) {
                return file;
            } else {
                return null;
            }
        }
        
		return file;
	}
	public void deleteFolder(String directory) throws FileNotFoundException{

		// convert string to Path and get the file
		File folderToDelete = Paths.get(directory).toFile();

		// make sure directory exists
		if (!folderToDelete.exists()) {
			throw new FileNotFoundException ("File not found");
		} else {

			try {
				deleteDirectory(folderToDelete);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void deleteDirectory(File file) throws IOException {
		
		if (file.isDirectory()) {

			// directory is empty, then delete it
			if (file.list().length == 0) {
				file.delete();
			} else {

				// list all the directory contents
				String files[] = file.list();

				for (String temp : files) {
					// construct the file structure
					File fileDelete = new File(file, temp);

					// recursive delete
					deleteDirectory(fileDelete);
				}

				// check the directory again, if empty then delete it
				if (file.list().length == 0) {
					file.delete();
				}
			}

		} else {
			// if file, then delete it
			file.delete();
		}

	}

}