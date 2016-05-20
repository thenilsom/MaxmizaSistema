package br.com.autocom.saa.controller.quartz;

import javax.inject.Inject;

import br.com.autocom.saa.bo.EmailBO;
import br.com.autocom.saa.bo.RelatorioGeralBO;
import br.com.autocom.saa.config.MessageCode;
import br.com.autocom.saa.exception.NegocioException;
import br.com.autocom.saa.to.PeriodoTO;
import br.com.autocom.saa.util.DataUtil;
import br.com.autocom.saa.util.LogUtil;
import br.com.autocom.saa.util.ResourceMessageUtil;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.tasks.scheduler.Scheduled;
import br.com.sw3.persistence.exception.DAOException;

/**
 * Classe responsavel por agendar os email periodicos que serão enviados
 * aos administradores
 * 
 * @author Denilson Godinho
 *
 */

@Controller
public class AgendadorEmail {

	@Inject
	RelatorioGeralBO relatorioBO;
	
	@Inject
	EmailBO emailBO;
	
	/**
	 * Envia um relatorio semanal toda segunda as 07:00hs
	 */
	@Post
	@Scheduled(cron = "0 0 07 ? * MON", concurrent = false)
	public void semanal(){
		
		PeriodoTO periodoTO = new PeriodoTO();
		periodoTO.setDataInicio(DataUtil.getPrimeiroDiaSemana());
		periodoTO.setDataFim(DataUtil.getUltimoDiaSemana());
		try {
			String caminhoRelatorio = relatorioBO.getRelatorioGeral("Sistema", periodoTO);
			emailBO.enviarEmail(caminhoRelatorio, ResourceMessageUtil.getDescricao(MessageCode.MSG_015));
			LogUtil.logsEmail("Email Semanal", "Email Enviado Com Sucesso.");
			
		} catch (DAOException | NegocioException e) {
			LogUtil.logsEmail("Email Semanal", e.getMessage());
		}

	}
	
	/**
	 * Envia um relatorio mensal  todo dia 1 de cada
	 * mes as 07:30hs
	 */
	@Post
	@Scheduled(cron = "0 30 07 1 JAN-DEC ?", concurrent = false)
	public void mensal(){
		PeriodoTO periodoTO = new PeriodoTO();
		periodoTO.setDataInicio(DataUtil.getPrimeiroDiaMes());
		periodoTO.setDataFim(DataUtil.getUltimoDiaMes());
		try {
			String caminhoRelatorio = relatorioBO.getRelatorioGeral("Sistema", periodoTO);
			emailBO.enviarEmail(caminhoRelatorio, ResourceMessageUtil.getDescricao(MessageCode.MSG_016));
			LogUtil.logsEmail("Email Mensal","Email Enviado Com Sucesso.");
			
		} catch (DAOException | NegocioException e) {
			LogUtil.logsEmail("Email Mensal", e.getMessage());
		}
	}
	
	
	/**
	 * Envia um relatorio anual todo dia 01 do ano
	 * as 08:00hs
	 */
	@Post
	@Scheduled(cron = "0 0 08 1 JAN ?", concurrent = false)
	public void anual(){
		PeriodoTO periodoTO = new PeriodoTO();
		periodoTO.setDataInicio(DataUtil.getPrimeiroDiaAno());
		periodoTO.setDataFim(DataUtil.getUltimoDiaAno());
		try {
			String caminhoRelatorio = relatorioBO.getRelatorioGeral("Sistema", periodoTO);
			emailBO.enviarEmail(caminhoRelatorio, ResourceMessageUtil.getDescricao(MessageCode.MSG_017));
			LogUtil.logsEmail("Email Anual", "Email Enviado Com Sucesso.");
			
		} catch (DAOException | NegocioException e) {
			LogUtil.logsEmail("Email Anual", e.getMessage());
		}
	}
}
