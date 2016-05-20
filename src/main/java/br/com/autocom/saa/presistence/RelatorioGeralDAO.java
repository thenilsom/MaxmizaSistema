package br.com.autocom.saa.presistence;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.com.autocom.saa.to.RelatorioGeralTO;
import br.com.sw3.persistence.exception.DAOException;


/**
 * Implementaçao dos métodos  para geração do relatório geral.
 * 
 * @author Danillo Santana D'Afonseca
 */

@Named
@Dependent
public class RelatorioGeralDAO extends SAAGenericDAO<RelatorioGeralTO, Long>  {
  
	
	/**
	 * Retorna a Lista com a quantidade de Atendimentos por usuário
	 * @throws DAOException 
	 * */
	public List<RelatorioGeralTO> getQtdAtendimentosUsuario(LocalDateTime dataInicio, LocalDateTime dataFim) throws DAOException{
		
		try{
			StringBuilder jpql = new StringBuilder();
			jpql.append("select usuarios.nome,count(atendimento.id) ");
			jpql.append("from Atendimento  atendimento ");
			jpql.append("join atendimento.usuarios as usuarios ");
			jpql.append("where atendimento.dataInicio between :from and :to ");
			jpql.append("and usuarios.ativo= ?1 group by usuarios.id , usuarios.nome");
		
			Query  query = getEntityManager().createQuery(jpql.toString());
			
			query.setParameter("from", dataInicio);
			query.setParameter("to", dataFim);
			query.setParameter(1, true);
			
			List<?> auxLista = query.getResultList();
			List<RelatorioGeralTO> lista = new ArrayList<RelatorioGeralTO>();
			RelatorioGeralTO relatorioGeralTO;
			for (int i = 0 ; i < query.getResultList().size(); i++) {
				Object[] aux = (Object[]) auxLista.get(i);
				Long atendimentosSolucionados = getQtdAtendimentosSolucaoUsuario(dataInicio, dataFim, aux[0].toString());
				relatorioGeralTO = new RelatorioGeralTO(aux[0].toString(),Long.parseLong(aux[1].toString()) , atendimentosSolucionados);
				lista.add(relatorioGeralTO);
			}
			return lista;
		}catch(PersistenceException pe){
			throw new DAOException(pe);
		}
		
	}
	
	/**
	 * Retorna a Lista com a quantidade de Atendimentos Solucionados por usuário
	 * @throws DAOException 
	 * */
	public Long getQtdAtendimentosSolucaoUsuario(LocalDateTime dataInicio, LocalDateTime dataFim , String usuario) throws DAOException{
		
		try{
			StringBuilder jpql = new StringBuilder();
			jpql.append("select count(atendimento.id) ");
			jpql.append("from Atendimento atendimento ");
			//jpql.append("join atendimento.usuarios as usuarios ");
			jpql.append("where atendimento.dataInicio between :from and :to ");
			jpql.append("and usuarioSolucao.nome= ?1");
		
			Query  query = getEntityManager().createQuery(jpql.toString());
			
			query.setParameter("from", dataInicio);
			query.setParameter("to", dataFim);
			query.setParameter(1, usuario);
			
			return (Long) query.getSingleResult();
		}catch(PersistenceException pe){
			throw new DAOException(pe);
		}
		
	}
	

	/**
	 * Retorna a Lista com a quantidade de Atendimentos por cliente
	 * @throws DAOException 
	 * */
	public List<RelatorioGeralTO> getQtdAtendimentosCliente(LocalDateTime dataInicio, LocalDateTime dataFim) throws DAOException{
		
		try{
			StringBuilder jpql = new StringBuilder();
			jpql.append("select cliente.nomeFantasia as nome,count(atendimento.id) as qtd from Atendimento  atendimento ");
			jpql.append("join atendimento.cliente as cliente ");
			jpql.append("where atendimento.dataInicio between :from and :to");
			jpql.append(" and cliente.ativo= ?1 group by cliente.id,cliente.nomeFantasia order by qtd DESC");
			
			
			Query  query = getEntityManager().createQuery(jpql.toString());
			
			query.setParameter("from", dataInicio);
			query.setParameter("to", dataFim);
			query.setParameter(1, true);
			
			List<?> auxLista = query.getResultList();
			List<RelatorioGeralTO> lista = new ArrayList<RelatorioGeralTO>();
			RelatorioGeralTO relatorioGeralTO;
			for (int i = 0 ; i < query.getResultList().size(); i++) {
				Object[] aux = (Object[]) auxLista.get(i);
				relatorioGeralTO = new RelatorioGeralTO(aux[0].toString(),Long.parseLong(aux[1].toString()));
				lista.add(relatorioGeralTO);
			}
			
			
			return lista;
		}catch(PersistenceException pe){
			throw new DAOException(pe);
		}
		
		
	}
	
	/**
	 * Retorna a Lista com a quantidade de Atendimentos por assunto
	 * @throws DAOException 
	 * */
	public List<RelatorioGeralTO> getQtdAtendimentosAssunto(LocalDateTime dataInicio, LocalDateTime dataFim) throws DAOException{
		try{
			StringBuilder jpql = new StringBuilder();
			jpql.append("select  assuntos.descricao as nome ,count(atendimento.id) as qtd from Atendimento  atendimento ");
			jpql.append("join atendimento.assuntos as assuntos ");
			jpql.append("where atendimento.dataInicio between :from and :to");
			jpql.append(" group by assuntos.id,assuntos.descricao order by qtd DESC ");
			
			
			Query  query = getEntityManager().createQuery(jpql.toString());
			
			query.setParameter("from", dataInicio);
			query.setParameter("to", dataFim);
			
			List<?> auxLista = query.getResultList();
			List<RelatorioGeralTO> lista = new ArrayList<RelatorioGeralTO>();
			RelatorioGeralTO relatorioGeralTO;
			for (int i = 0 ; i < query.getResultList().size(); i++) {
				Object[] aux = (Object[]) auxLista.get(i);
				relatorioGeralTO = new RelatorioGeralTO(aux[0].toString(),Long.parseLong(aux[1].toString()));
				lista.add(relatorioGeralTO);
			}
			
			return lista;
		}catch(PersistenceException pe){
			throw new DAOException(pe);
		}
	}
	
}
