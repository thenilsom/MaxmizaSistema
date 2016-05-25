package br.com.maximiza.presistence;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.sw3.persistence.AbstractEntityManagerProducer;

/**
 * Classe que determina o ponto de injeção unico para a criação da instância do {@link EntityManager}, 
 * definido pela anotação @Produces do CDI.
 * 
 * @author Denilson Godinho
 */
@ApplicationScoped
public class SAAEntityManagerProducer extends AbstractEntityManagerProducer {

	private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("maximiza");

	/**
	 * @see br.com.sw3.persistence.AbstractEntityManagerProducer#createEntityManager()
	 */
	@Produces
	@RequestScoped
	@Override
	protected EntityManager createEntityManager() {
		return createEntityManager(entityManagerFactory);
	}
}