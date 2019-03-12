package fr.amu.ter_genrest.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

@LocalBean
@Stateless
public class DirectoryManager {

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
	
	public File createDirectory(String path) {
		
		// get os separator file path
		String seperator = File.separator;
		
		UUID uuid = UUID.randomUUID();
        String UIDName = uuid.toString();

        File file = new File(path+seperator+UIDName);
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