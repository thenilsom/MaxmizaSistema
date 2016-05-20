package br.com.autocom.saa.controller.quartz;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import br.com.autocom.saa.util.DataUtil;
import br.com.autocom.saa.util.LogUtil;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.tasks.scheduler.Scheduled;

/**
 * Classe responsavel por agendar os email periodicos que serão enviados
 * aos administradores
 * 
 * @author Denilson Godinho
 *
 */

@Controller
public class AgendadorBackup {

	/**
	 * Envia um relatorio semanal toda segunda as 07:00hs
	 */
	@Post
	@Scheduled(cron = "0 0 8-19/1 * * ?", concurrent = false)
	public void backup(){
	
		LocalDateTime data = DataUtil.getLocalDateTime();
		String arquivo = "/home/autocom/Backup/atendimento";
		arquivo += "/" + String.valueOf(data.getYear());
		arquivo += "/" + String.valueOf(data.getMonthValue());
		arquivo += "/" + String.valueOf(data.getDayOfMonth());
		
		File pasta = new File(arquivo);
		if(!pasta.exists()){
			pasta.mkdirs();
		}
		
		String nomeBackup = arquivo + "/" +DataUtil.getDataFormatada3() +".backup";
		
		String dump = "/usr/pgsql-9.4/bin/pg_dump ";
		String parametros = " -h localhost -p 5432 -U autocom -Fc -v -f ";
		String comando = dump + parametros + nomeBackup + " saa";
		String[] env = {"PGPASSWORD=Autocom"};
		
		try {
			
			Runtime.getRuntime().exec(comando,env);
			
			LogUtil.logsBackup("Sucesso","Backup Efetuado Com Sucesso.");
			
		} catch (IOException e) {
			
			LogUtil.logsBackup("Erro",e.getMessage());
		}
		
		
	}
}
