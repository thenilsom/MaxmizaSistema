package br.com.maximiza.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogUtil {

	public static void logsEmail(String tipo, String erro) {
		
		String caminho = System.getProperty("catalina.base") + "/webapps/saa/resources/LogsEmail";
		
		File arqLogEmail = new File(caminho);
		
		if(!arqLogEmail.exists()){
			arqLogEmail.mkdirs();
		}
		
		arqLogEmail = new File(caminho + "/log.txt");
		
		FileWriter arqOut;
		
		try {
			arqOut = new FileWriter(arqLogEmail, true);
			arqOut.write(DataUtil.getDataFormatada2() + " = " + tipo + ":" + erro 
					+ System.getProperty("line.separator"));
			arqOut.close();
		} catch (IOException e) {
			System.out.println("Erro ao Criar Arquivo de Logs. " + e);
		}

	}

	public static void logsBackup(String tipo, String erro) {
		
		String caminho = System.getProperty("catalina.base") + "/webapps/saa/resources/LogsBackup";
		
		File arqLogEmail = new File(caminho);
		
		if(!arqLogEmail.exists()){
			arqLogEmail.mkdirs();
		}
		
		arqLogEmail = new File(caminho + "/log.txt");
		
		FileWriter arqOut;
		
		try {
			arqOut = new FileWriter(arqLogEmail, true);
			arqOut.write(DataUtil.getDataFormatada2() + " = " + tipo + ":" + erro 
					+ System.getProperty("line.separator"));
			arqOut.close();
		} catch (IOException e) {
			System.out.println("Erro ao Criar Arquivo de Logs. " + e);
		}

	}
}
