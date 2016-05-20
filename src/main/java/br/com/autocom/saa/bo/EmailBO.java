package br.com.autocom.saa.bo;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

import br.com.autocom.saa.exception.NegocioException;
import br.com.caelum.vraptor.environment.Environment;

/**
 * Classe responsavel pelo envio de email
 * 
 * @author Denilson Godinho
 *
 */

@Resource
public class EmailBO {
	
	@Inject
	private Environment environment;
	
	/**
	 * Envia email com anexo
	 * @param caminhoRelatorio
	 * @param assunto
	 * @throws NegocioException
	 */
	public void enviarEmail(String caminhoRelatorio, String assunto) throws NegocioException{
		
		try {
			
			MultiPartEmail email = new MultiPartEmail();
			
			/*configuracao do remetente*/
			email.setFrom(environment.get("autocom.from"));
			email.setAuthenticator(new DefaultAuthenticator(environment.get("autocom.username"), environment.get("autocom.password")));
			email.setHostName(environment.get("autocom.server"));
			
			/*configuracao do destinatário*/
			email.addTo(environment.get("email.destinatario01"));
			email.addTo(environment.get("email.destinatario02"));
			
			/*dados do email*/
			email.setSubject(assunto);
			email.setMsg(environment.get("email.mensagem"));
			
			/*configuracoes gerais*/
			email.setSmtpPort(Integer.valueOf(environment.get("autocom.port")));
			email.setTLS(Boolean.valueOf(environment.get("autocom.tls")));
			email.setDebug(false);
			
			/*configuracoes do anexo*/
			EmailAttachment anexo = new EmailAttachment();
			anexo.setPath(caminhoRelatorio);
			anexo.setDisposition(EmailAttachment.ATTACHMENT);
			anexo.setDescription("relatorio");
			email.attach(anexo);
			
			email.send();
			
		} catch (EmailException e) {
			throw new NegocioException(e);
		}
		
		
	}
}
