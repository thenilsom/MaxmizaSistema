package br.com.autocom.saa.bo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.swing.ImageIcon;

import br.com.autocom.saa.dom.Usuario;
import br.com.autocom.saa.exception.NegocioException;
import br.com.autocom.saa.presistence.RelatorioGeralDAO;
import br.com.autocom.saa.to.PeriodoTO;
import br.com.autocom.saa.to.RelatorioGeralTO;
import br.com.sw3.persistence.exception.DAOException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Named
@Dependent
public class RelatorioGeralBO {

	@Inject
	private RelatorioGeralDAO relatorioGeralDAO;
	
	@Inject
	private HttpServletRequest request;
	
	
	/**
	 * Retorna path do relatorio geral
	 * @throws IOException 
	  * 	 */
	public String getRelatorioGeral(String nome, PeriodoTO periodo) throws DAOException, NegocioException {
		
		String saida = request.getSession().getServletContext().getRealPath("resources/Relatorios");
		
		File pasta = new File(saida);
		
		if(!pasta.exists()){
			pasta.mkdirs();
		}
		
		try {
					
			//Setando parametro para relatorio geral
			Map<String, Object> parametrosRG = new HashMap<String, Object>();
			parametrosRG.put("login", nome);
			parametrosRG.put("RG_DATA_INI",new java.text.SimpleDateFormat("dd/MM/yyyy").parse(periodo.getDataInicio().getDayOfMonth()+"/"+periodo.getDataInicio().getMonthValue()+"/"+periodo.getDataInicio().getYear()));
			parametrosRG.put("RG_DATA_FIM", new java.text.SimpleDateFormat("dd/MM/yyyy").parse(periodo.getDataFim().getDayOfMonth()+"/"+periodo.getDataFim().getMonthValue()+"/"+periodo.getDataFim().getYear()));
			String caminhoLogo = "/jasper/autocom.png";
            ImageIcon gto = new ImageIcon(getClass().getResource(caminhoLogo));
            parametrosRG.put("logo",gto.getImage());
			
			
			//Setando parametros para demais relatorios
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("DATA_INI", new java.text.SimpleDateFormat("dd/MM/yyyy").parse(periodo.getDataInicio().getDayOfMonth()+"/"+periodo.getDataInicio().getMonthValue()+"/"+periodo.getDataInicio().getYear()));
			parametros.put("DATA_FIM", new java.text.SimpleDateFormat("dd/MM/yyyy").parse(periodo.getDataFim().getDayOfMonth()+"/"+periodo.getDataFim().getMonthValue()+"/"+periodo.getDataFim().getYear()));
			
			//setando relatorio atendimentos_por_usuarios
			//somar 1 dia a data final
			periodo.setDataFim(periodo.getDataFim().plusDays(1));
			List<RelatorioGeralTO> qtdAtendimentosUsuarios = relatorioGeralDAO.getQtdAtendimentosUsuario(periodo.getDataInicio(), periodo.getDataFim());
			parametrosRG.put("listaAtendimentosUsuario", qtdAtendimentosUsuarios);
			
			//setando relatorio atendimentos_por_cliente
			//somar 1 dia a data final
			periodo.setDataFim(periodo.getDataFim().plusDays(1));
			List<RelatorioGeralTO> qtdAtendimentosCliente = relatorioGeralDAO.getQtdAtendimentosCliente(periodo.getDataInicio(),periodo.getDataFim());
			parametrosRG.put("listaAtendimentosCliente", qtdAtendimentosCliente);
			
			//setando relatorio atendimentos_por_assunto
			//somar 1 dia a data final
			periodo.setDataFim(periodo.getDataFim().plusDays(1));
			List<RelatorioGeralTO> qtdAtendimentosAssunto = relatorioGeralDAO.getQtdAtendimentosAssunto(periodo.getDataInicio(),periodo.getDataFim());
			parametrosRG.put("listaAtendimentosAssunto", qtdAtendimentosAssunto);
			
			//setando relatorio_geral
			Usuario usuario = new Usuario();
			usuario.setNome(nome);
			List<Usuario> lista=  new ArrayList<Usuario>();
			lista.add(usuario);
			JRDataSource dataSource = new JRBeanCollectionDataSource(lista);
			
			
			//gerando relatório_geral
			InputStream subInputStream = this.getClass().getResourceAsStream("/jasper/atendimentos_por_usuario.jasper");
			parametrosRG.put("SUBREPORT_INPUT_STREAM_USUARIO", subInputStream);
			subInputStream  = this.getClass().getResourceAsStream("/jasper/atendimentos_por_cliente.jasper");
			parametrosRG.put("SUBREPORT_INPUT_STREAM_CLIENTE", subInputStream);
			subInputStream  = this.getClass().getResourceAsStream("/jasper/atendimentos_por_assunto.jasper");
			parametrosRG.put("SUBREPORT_INPUT_STREAM_ASSUNTO", subInputStream);
			JasperPrint print = JasperFillManager.fillReport(this.getClass().getResourceAsStream("/jasper/relatorio_geral.jasper"), parametrosRG,dataSource);
			
			saida +="/relatorio_geral_"+usuario.getNome().replaceAll(" ","")+".pdf";
			JasperExportManager.exportReportToPdfFile(print,saida);
		    
		} catch (JRException e) {
			e.printStackTrace();
			throw new NegocioException(e);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new NegocioException(e);
		} catch(PersistenceException e){
			throw new DAOException(e);
		} 
		return saida;
	}
	
}
