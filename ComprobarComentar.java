package org.notariado.ancert.agn.migration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ComentarConfiguracion {

	public static void main(String[] args) {
		String rootPath = "../server"; // Ruta de la carpeta principal
		modifyFilesInDirectory(new File(rootPath));
	}

	private static void modifyFilesInDirectory(File directory) {
		File[] files = directory.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					modifyFilesInDirectory(file); // Llamada recursiva para explorar subdirectorios
				} else {
					if (isTargetFile(file)) {
						modifyConfig(file);
					}
				}
			}
		}
	}

	private static boolean isTargetFile(File file) {
		String fileName = file.getName();
		return fileName.endsWith(".java")
				&& (fileName.equals("Archivo1.java") 
						|| fileName.equals("Archivo2.java") 
						|| fileName.equals("Archivo3.java") 
						|| fileName.equals("Archivo4.java") 
						|| fileName.equals("Archivo5.java")
						|| fileName.equals("Archivo6.java") 
						|| fileName.equals("Archivo7.java"));
	}

	private static void modifyConfig(File file) {
		StringBuilder contentBuilder = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.contains("@Configuration") && !line.contains("//")) {
					line = "//" + line;
				}
				contentBuilder.append(line).append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try (FileWriter writer = new FileWriter(file); OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8")) {
			out.write(contentBuilder.toString());
			System.out.println("Se modificó correctamente el archivo " + file.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
