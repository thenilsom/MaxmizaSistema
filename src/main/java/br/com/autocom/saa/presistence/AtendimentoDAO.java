package br.com.autocom.saa.presistence;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.autocom.saa.dom.Atendimento;
import br.com.autocom.saa.dom.enums.Status;
import br.com.autocom.saa.dom.filtro.AtendimentoFiltro;
import br.com.autocom.saa.util.Util;
import br.com.sw3.persistence.exception.DAOException;

@Named
@Dependent
public class AtendimentoDAO extends SAAGenericDAO<Atendimento, Long> {

	/**
	 * Metodo responsavel por trazer todos os Atendimentos.
	 * @param filtro
	 * @return
	 * @throws DAOException
	 */
	
	public List<Atendimento> listarTudo() throws DAOException {

		try {

			StringBuilder jpql = new StringBuilder();
			jpql.append("SELECT atendimento FROM Atendimento atendimento ORDER BY case ");
			jpql.append("when atendimento.status = 'EM_ANDAMENTO' then 0 ");
			jpql.append("when atendimento.status = 'PENDENTE' then 1 ");
			jpql.append("else 2 end asc , atendimento.dataInicio DESC");

			TypedQuery<Atendimento> query = getEntityManager().createQuery(jpql.toString(), Atendimento.class);
			query.setMaxResults(100);
			return query.getResultList();
		} catch (PersistenceException e) {
			throw new DAOException(e);
		}

	};
	
	/**
	 * Metodo responsavel por trazer os Atendimentos do login passado
	 * @param login
	 * @return
	 * @throws DAOException
	 */
	public List<Atendimento> listarTudo(String login) throws DAOException {

		try {

			StringBuilder jpql = new StringBuilder();
			jpql.append("SELECT atendimento FROM Atendimento atendimento ");
			jpql.append("INNER JOIN atendimento.usuarios usuarios ");
			jpql.append("WHERE usuarios.login = ?1 ORDER BY case ");
			jpql.append("when atendimento.status = 'EM_ANDAMENTO' then 0 ");
			jpql.append("when atendimento.status = 'PENDENTE' then 1 ");
			jpql.append("else 2 end asc , atendimento.dataInicio DESC");

			TypedQuery<Atendimento> query = getEntityManager().createQuery(jpql.toString(), Atendimento.class);
			query.setParameter(1, login);
			query.setMaxResults(100);
			return query.getResultList();
		} catch (PersistenceException e) {
			throw new DAOException(e);
		}

	}
	
	/**
	 * Retorna a Quantidade de Atendimentos Pendentes / Em Atendimento
	 * @param usuario
	 * @return 
	 * @throws DAOException
	 */
	
	public Long  qtdAtendimentos(String login) throws DAOException {
		
		try {
			
			StringBuilder jpql = new StringBuilder();
			jpql.append("select count(atendimento.id) from Atendimento atendimento ");
			jpql.append("INNER JOIN atendimento.usuarios as usuarios ");
			jpql.append("WHERE usuarios.login = ?1 ");
			jpql.append("AND atendimento.status <> ?2 ");

			Query query = getEntityManager().createQuery(jpql.toString());
			query.setParameter(1, login );
			query.setParameter(2, Status.FINALIZADO);
			
			return (Long) query.getSingleResult();
		} catch (PersistenceException e) {
			throw new DAOException(e);
		}
		
	}
	
	/**
	 * Metodo responsavel por trazer os Atendimentos de acordo com o Filtro informado.
	 * @param filtro
	 * @return
	 * @throws DAOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Atendimento> listarFiltro(AtendimentoFiltro filtro) throws DAOException {
		try {
			
			boolean where = false;
			Map<Integer, Object> parametro = new HashMap<Integer, Object>();
					
			StringBuilder jpql = new StringBuilder();
			jpql.append(" SELECT atendimento FROM Atendimento atendimento ");
			
			if(!Util.isNullOrBlank(filtro.getUsuario()) || !Util.isNullOrBlank(filtro.getLogin())){
				jpql.append(" INNER JOIN atendimento.usuarios as usuarios ");
			}
			
			if(!Util.isNullOrBlank(filtro.getAssunto())){
				jpql.append(" INNER JOIN atendimento.assuntos as assuntos ");
				
			}
			
			/* filtrar por usuario  */
			if(!Util.isNullOrBlank(filtro.getUsuario())){
				jpql.append(" WHERE usuarios.id = ?1");
				where = true;
				parametro.put(1, filtro.getUsuario().getId());
			}
			
			/* filtrar por assunto  */
			if(!Util.isNullOrBlank(filtro.getAssunto())){
				if(where){
					jpql.append(" AND assuntos.id = ?2");
				}else{
					jpql.append(" WHERE assuntos.id = ?2");
					where = true;
				}
				parametro.put(2, filtro.getAssunto().getId());
			}
			
			/* filtrar por cliente  */
			if(!Util.isNullOrBlank(filtro.getCliente())){
				if(where){
					jpql.append(" AND atendimento.cliente = ?3");
				}else{
					jpql.append(" WHERE atendimento.cliente = ?3");
					where = true;
				}
				
				parametro.put(3, filtro.getCliente());
			}
			
			/* filtrar por categoria  */
			if(!Util.isNullOrBlank(filtro.getCategoria())){
				if(where){
					jpql.append(" AND atendimento.cliente.categoria = ?4");
				}else{
					jpql.append(" WHERE atendimento.cliente.categoria = ?4");
					where = true;
				}
				
				parametro.put(4, filtro.getCategoria());
			}
			
			/* filtrar por status  */
			if(!Util.isNullOrBlank(filtro.getStatus())){
				if(where){
					jpql.append(" AND atendimento.status = ?5");
				}else{
					jpql.append(" WHERE atendimento.status = ?5");
					where = true;
				}
				
				parametro.put(5, filtro.getStatus());
			}
			
			/* filtrar por favorito  */
			if(!Util.isNullOrBlank(filtro.getFavorito())){
				boolean isFavorito = filtro.getFavorito().equals("true") ? true : false;
				if(where){
					jpql.append(" AND atendimento.favorito= ?6");
				}else{
					jpql.append(" WHERE atendimento.favorito= ?6");
					where = true;
				}
				
				parametro.put(6,isFavorito);
			}
			
			
			/* filtrar por dataInicial  */
			if(!Util.isNullOrBlank(filtro.getDataInicial())){
				if(where){
					jpql.append(" AND atendimento.dataInicio > ?7");
				}else{
					jpql.append(" WHERE atendimento.dataInicio > ?7");
					where = true;
				}
				
				parametro.put(7, (LocalDateTime) filtro.getDataInicial());
			}
			
			/* filtrar por dataFinal  */
			if(!Util.isNullOrBlank(filtro.getDataFinal())){
				if(where){
					jpql.append(" AND atendimento.dataInicio < ?8");
				}else{
					jpql.append(" WHERE atendimento.dataInicio < ?8");
					where = true;
				}
				LocalDateTime data = filtro.getDataFinal();
				data = data.plus(1, ChronoUnit.DAYS);
				parametro.put(8, data);
			}
			
			/* filtrar por login */
			if(!Util.isNullOrBlank(filtro.getLogin())){
				if(where){
					jpql.append(" AND usuarios.login = ?9");
				}else{
					jpql.append(" WHERE usuarios.login = ?9");
					where = true;
				}
				
				parametro.put(9, filtro.getLogin());
			}
			
			jpql.append(" ORDER BY case ");
			jpql.append("when atendimento.status = 'EM_ANDAMENTO' then 0 ");
			jpql.append("when atendimento.status = 'PENDENTE' then 1 ");
			jpql.append("else 2 end asc , atendimento.dataInicio DESC");
			
			TypedQuery<Atendimento> query = getEntityManager().createQuery(jpql.toString(), Atendimento.class);
			
			Iterator it = parametro.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry<Integer, Object> map = (Map.Entry<Integer, Object>)it.next();
				query.setParameter(map.getKey(), map.getValue());
			}
			
			return query.getResultList();
							
		} catch (PersistenceException e) {
			throw new PersistenceException(e);
		}
	}
}
