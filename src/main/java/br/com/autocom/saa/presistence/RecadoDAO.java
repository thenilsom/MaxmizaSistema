package br.com.autocom.saa.presistence;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.autocom.saa.dom.Recado;
import br.com.sw3.persistence.exception.DAOException;

@Named
@Dependent
public class RecadoDAO  extends SAAGenericDAO<Recado, Long>{
	
	/**
	 * Retorna uma lista de recados enviados do usuario passado como parametro.
	 * @param login
	 * @return
	 * @throws DAOException
	 */
	public List<Recado> listarRecadosEnviados(String login) throws DAOException{
		try {
				StringBuilder jpql = new StringBuilder();
				jpql.append(" SELECT recados FROM Recado recados ")
					   .append(" INNER JOIN  recados.de as enviados ")
					   .append(" WHERE enviados.login = ?1")
					   .append(" ORDER BY recados.lido ");
				
				TypedQuery<Recado> query = getEntityManager().createQuery(jpql.toString(), Recado.class);
				query.setParameter(1, login);
				
				return query.getResultList();
						
		} catch (PersistenceException e) {
			throw new DAOException(e);
		}
	}
	
	/**
	 * Retorna uma lista de recados recebidos do usuario passado como parametro.
	 * @param login
	 * @return
	 * @throws DAOException
	 */
	public List<Recado> listarRecadosRecebidos(String login) throws DAOException{
		try {
				StringBuilder jpql = new StringBuilder();
				jpql.append(" SELECT recados FROM Recado recados ")
					   .append(" INNER JOIN  recados.para as recebidos ")
					   .append(" WHERE recebidos.login = ?1")
					   .append(" ORDER BY recados.lido ");
				
				TypedQuery<Recado> query = getEntityManager().createQuery(jpql.toString(), Recado.class);
				query.setParameter(1, login);
				
				return query.getResultList();
						
		} catch (PersistenceException e) {
			throw new DAOException(e);
		}
	}

	/**
	 * Retorna a Quantidade de Recados não Lidos. 
	 * @param login
	 * @return
	 * @throws DAOException
	 */
	public Long qtdRecados(String login) throws DAOException {
		try {
			StringBuilder jpql = new StringBuilder();
			jpql.append("SELECT count(recado.id) FROM Recado recado ")
			       .append(" INNER JOIN recado.para as recebidos ")
			       .append(" WHERE recebidos.login = ?1 ")
			       .append(" AND recado.lido <> ?2 ");
			
			Query query = getEntityManager().createQuery(jpql.toString());
			query.setParameter(1, login);
			query.setParameter(2, true);
			
			return (Long) query.getSingleResult();
					
		} catch (PersistenceException e) {
			throw new DAOException(e);
		}
	}

}
