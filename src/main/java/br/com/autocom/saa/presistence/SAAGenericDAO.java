package br.com.autocom.saa.presistence;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.sw3.persistence.GenericDAO;
import br.com.sw3.persistence.entity.Entidade;

/**
 * Classe mãe de todas as classes DAO, herda da classe {@link GenericDAO}.
 * 
 * @author Paulo Leonardo de O. Miranda
 *
 * @param <T>
 * @param <ID>
 */
public class SAAGenericDAO <T extends Entidade<?>, ID extends Serializable> extends GenericDAO<T, ID> {
	
	@Inject
	private EntityManager entityManager;

	/**
	 * @see br.com.sw3.persistence.GenericDAO#getEntityManager()
	 */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}
}