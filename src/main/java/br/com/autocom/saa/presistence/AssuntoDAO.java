package br.com.autocom.saa.presistence;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import br.com.autocom.saa.dom.Assunto;
import br.com.sw3.persistence.exception.DAOException;

/**
 * Implementaçao dos métodos de persistência relacionado a {@link Assunto}.
 * 
 * 
 * @author Samuel Borges de Oliveira
 */
@Named
@Dependent
public class AssuntoDAO extends SAAGenericDAO<Assunto, Long> {
	
	/* (non-Javadoc)
	 * @see br.com.sw3.persistence.GenericDAO#persistir(br.com.sw3.persistence.entity.Entidade)
	 */
	@Override
	public Assunto persistir(Assunto entidade) throws DAOException {
		
		return super.persistir(entidade);
	}
	
	/* (non-Javadoc)
	 * @see br.com.sw3.persistence.GenericDAO#excluir(br.com.sw3.persistence.entity.Entidade)
	 */
	@Override
	public void excluir(Assunto entidade) throws DAOException {

		super.excluir(entidade);
	}
	
	
	/**
	 * Retorna O ID de acordo com a descricao passada
	 * 
	 * @param descricao
	 * @return
	 * @throws DAOException
	 */
	public Long getID(final String descricao) throws DAOException{
		try{
			StringBuilder jpql = new StringBuilder();
			jpql.append(" SELECT assunto.id FROM Assunto assunto");
			jpql.append(" WHERE assunto.descricao = ?1");

			Query query = getEntityManager().createQuery(jpql.toString());
			query.setParameter(1, descricao);
			
			Long id = (Long) query.getSingleResult(); 
			
			return id;
			
		}catch(NoResultException e){
			return null;
		}catch(PersistenceException pe){
			throw new DAOException(pe);
		}
	}
	
	
	/**
	 * Verifica Se Assunto Existe antes de tentar excluir
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public boolean existeAssunto(final Long id) throws DAOException{
		try{
					
			StringBuilder jpql = new StringBuilder();
			jpql.append("select count(assuntos.id) from Atendimento atendimento ");
			jpql.append("inner join atendimento.assuntos assuntos ");
			jpql.append("where assuntos.id = ?1");

			Query query = getEntityManager().createQuery(jpql.toString());
			query.setParameter(1, id);
			
			Long a = (Long) query.getSingleResult();
			if(a < 1 ){
				return false;
			}
			return true;
			
		}catch(NoResultException e){
			throw new DAOException(e);
		}catch(PersistenceException pe){
			throw new DAOException(pe);
		}
	}
}
