package br.com.autocom.saa.presistence;

import java.time.LocalDateTime;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.autocom.saa.dom.Ponto;
import br.com.autocom.saa.dom.Usuario;
import br.com.autocom.saa.dom.filtro.PontoFiltro;
import br.com.sw3.persistence.exception.DAOException;

/**
 * Implementaçao dos métodos de persistência relacionado a {@link Ponto}
 * 
 * @author Samuel Oliveira
 *
 */

@Named
@Dependent
public class PontoDAO extends SAAGenericDAO<Ponto, Long> {
	
	/**
	 * Retorna O Ponto de acordo com a data passada
	 * 
	 * @param data
	 * @return
	 * @throws DAOException
	 */
	public Ponto getPonto(final LocalDateTime data, final Usuario usuario) throws DAOException{
		try{
			StringBuilder jpql = new StringBuilder();
			jpql.append(" SELECT ponto FROM Ponto ponto");
			jpql.append(" WHERE ponto.data = ?1 and ponto.usuario = ?2");

			Query query = getEntityManager().createQuery(jpql.toString());
			query.setParameter(1, data);
			query.setParameter(2, usuario);
			
			Ponto ponto = (Ponto) query.getSingleResult(); 
			
			return ponto;
			
		}catch(NoResultException e){
			return null;
		}catch(PersistenceException pe){
			throw new DAOException(pe);
		}
	}
	
	/**
	 * Metodo responsavel por trazer os Pontos do login passado
	 * @param login
	 * @return
	 * @throws DAOException
	 */
	public List<Ponto> listarTudo(PontoFiltro filtro) throws DAOException {

		try {

			StringBuilder jpql = new StringBuilder();
			jpql.append("SELECT ponto FROM Ponto ponto ");
			jpql.append("WHERE ponto.usuario.login = ?1 ");
			jpql.append("AND ponto.data >= ?2 ");
			jpql.append("AND ponto.data <= ?3 ");
			jpql.append("ORDER BY ponto.data");

			TypedQuery<Ponto> query = getEntityManager().createQuery(jpql.toString(), Ponto.class);
			query.setParameter(1, filtro.getUsuario().getLogin());
			query.setParameter(2, filtro.getDataInicial());
			query.setParameter(3, filtro.getDataFinal());

			return query.getResultList();
		} catch (PersistenceException e) {
			throw new DAOException(e);
		}

	}

}
