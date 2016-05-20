package br.com.autocom.saa.presistence;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.autocom.saa.dom.Cliente;
import br.com.sw3.persistence.exception.DAOException;

/**
 * Implementaçao dos métodos de persistência relacionado a {@link Cliente}.
 * 
 * @author Denilson Godinho
 *
 */

@Named
@Dependent
public class ClienteDAO extends SAAGenericDAO<Cliente, Long> {
	

	/**
	 * Retorna O ID de acordo com o CNPJ passado
	 * 
	 * @param cnpj
	 * @return
	 * @throws DAOException
	 */
	public Long getID(final String cnpj){
		try{
			StringBuilder jpql = new StringBuilder();
			jpql.append(" SELECT cliente.id FROM Cliente cliente");
			jpql.append(" WHERE cliente.cnpj = ?1");

			Query query = getEntityManager().createQuery(jpql.toString());
			query.setParameter(1, cnpj);
			
			Long id = (Long) query.getSingleResult(); 
			
			return id;
			
		}catch(Exception e){
			return null;
		}
	}

	/**
	 * Retorna O ID de acordo com o codigo passado
	 * 
	 * @param codigo
	 * @return
	 * @throws DAOException
	 */
	public Long getID(final Long codigo){
		try{
			StringBuilder jpql = new StringBuilder();
			jpql.append(" SELECT cliente.id FROM Cliente cliente");
			jpql.append(" WHERE cliente.codigo = ?1");

			Query query = getEntityManager().createQuery(jpql.toString());
			query.setParameter(1, codigo);
			
			Long id = (Long) query.getSingleResult(); 
			
			return id;
			
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * Retorna Lista de Clientes ativos
	 * @return
	 * @throws DAOException 
	 */
	public List<Cliente> listarAtivos() throws DAOException{
		try {
			StringBuilder jpql = new StringBuilder();
			jpql.append(" SELECT cliente FROM Cliente cliente")
				  .append(" WHERE cliente.ativo = ?1");
			
			TypedQuery<Cliente> query = getEntityManager().createQuery(jpql.toString(), Cliente.class);
			query.setParameter(1, true);
			
			return query.getResultList();
		} catch (NoResultException e) {
			return new ArrayList<Cliente>();
		}catch(PersistenceException pe){
			throw new DAOException(pe);
		}
	}
	
	/**
	 * Verifica Se Cliente Existe antes de tentar excluir
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public boolean existeCliente(final Long id) throws DAOException{
		try{
					
			StringBuilder jpql = new StringBuilder();
			jpql.append("select count(cliente.id) from Atendimento atendimento ");
			jpql.append("inner join atendimento.cliente cliente ");
			jpql.append("where cliente.id = ?1");

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
