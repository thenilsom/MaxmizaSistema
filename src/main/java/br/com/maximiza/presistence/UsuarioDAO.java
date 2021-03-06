package br.com.maximiza.presistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.maximiza.dom.Usuario;
import br.com.sw3.persistence.exception.DAOException;

/**
 * Implementaçao dos métodos de persistência relacionado a {@link Usuario}.
 * 
 * @author Paulo Leonardo de O. Miranda
 */
@Named
@Dependent
public class UsuarioDAO extends SAAGenericDAO<Usuario, Long> {

	/**
	 * Recupera o {@link Usuario} segundo o Usuário e Senha informada.
	 * 
	 * @param login
	 * @param senha
	 * @return
	 * @throws DAOException
	 */
	public Usuario getUsuario(final String login, final String senha) throws DAOException {
		try {
			StringBuilder jpql = new StringBuilder();
			jpql.append(" SELECT usuario FROM Usuario usuario");
			jpql.append(" WHERE usuario.login = ?1 AND usuario.senha = ?2 ");

			TypedQuery<Usuario> query = getEntityManager().createQuery(jpql.toString(), Usuario.class);
			query.setParameter(1, login);
			query.setParameter(2, senha);

			Iterator<Usuario> iterator = query.getResultList().iterator();

			/*
			 * Caso seja encontrado mais de um Usuário com o 'login' e 'senha'
			 * informado, a primeira posição da Lista será retornada.
			 */
			return iterator.hasNext() ? iterator.next() : null;
		} catch (PersistenceException e) {
			throw new DAOException(e);
		}
	}
	
	/**
	 * Recupera o {@link Usuario} segundo o Login.
	 * 
	 * @param login

	 * @return
	 * @throws DAOException
	 */
	public Usuario getUsuario(final String login) throws DAOException{
		try{
			StringBuilder jpql = new StringBuilder();
			jpql.append(" SELECT usuario FROM Usuario usuario");
			jpql.append(" WHERE usuario.login = ?1");

			TypedQuery<Usuario>  query = getEntityManager().createQuery(jpql.toString(),Usuario.class);
			query.setParameter(1, login);
			
			Usuario usuario =  query.getSingleResult();
			
			return usuario;
			
		}catch(NoResultException e){
			return new Usuario();
		}catch(PersistenceException pe){
			throw new DAOException(pe);
		}
	}
	
	
	/**
	 * Recupera o {@link ID} segundo o Login.
	 * 
	 * @param login

	 * @return
	 * @throws DAOException
	 */
	public Long getID(final String login) throws DAOException{
		try{
			StringBuilder jpql = new StringBuilder();
			jpql.append(" SELECT usuario.id FROM Usuario usuario");
			jpql.append(" WHERE usuario.nome = ?1");

			Query  query = getEntityManager().createQuery(jpql.toString());
			query.setParameter(1, login);
			
			Long id = (Long) query.getSingleResult();
			
			return id;
			
		}catch(NoResultException e){
			return null;
		}catch(PersistenceException pe){
			throw new DAOException(pe);
		}
	}
	
	/**
	 * Retorna Lista de Usuarios ativos
	 * @return
	 * @throws DAOException 
	 */
	public List<Usuario> listarAtivos() throws DAOException{
		try {
			StringBuilder jpql = new StringBuilder();
			jpql.append(" SELECT usuario FROM Usuario usuario")
				  .append(" WHERE usuario.ativo = ?1");
			
			TypedQuery<Usuario> query = getEntityManager().createQuery(jpql.toString(), Usuario.class);
			query.setParameter(1, true);
			
			return query.getResultList();
		} catch (NoResultException e) {
			return new ArrayList<Usuario>();
		}catch(PersistenceException pe){
			throw new DAOException(pe);
		}
	}
	
}
