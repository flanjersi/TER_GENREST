package fr.amu.ter_genrest.services.zip;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;


//Source code to create a zip file from a given folder
//This example program recursively adds all files in the folder
//Works only with Java 7 and above
@LocalBean
@Stateless
public class ZipFolder {

public void zipFolder(Path sourceFolderPath, Path zipPath) throws Exception {
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
}