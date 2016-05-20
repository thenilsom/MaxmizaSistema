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
import javax.persistence.TypedQuery;

import br.com.autocom.saa.dom.Auditoria;
import br.com.autocom.saa.dom.filtro.AuditoriaFiltro;
import br.com.autocom.saa.util.Util;
import br.com.sw3.persistence.exception.DAOException;

/**
 * Implementaçao dos métodos de persistência relacionado a {@link Auditoria}.
 * 
 * @author Denilson Godinho
 *
 */

@Named
@Dependent
public class AuditoriaDAO extends SAAGenericDAO<Auditoria, Long> {

	/**
	 * Metodo responsavel por trazer as Auditorias de acordo com o Filtro informado.
	 * 
	 * @param filtro
	 * @return
	 * @throws DAOException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Auditoria> listarFiltro(AuditoriaFiltro filtro) throws DAOException {
		
		try {
			
			boolean where = false;
			Map<Integer, Object> parametro = new HashMap<Integer, Object>();
			
			StringBuilder jpql = new StringBuilder();
			jpql.append(" SELECT auditoria FROM Auditoria auditoria");
			
			if(!Util.isNull(filtro.getAcao())){
				jpql.append(" WHERE auditoria.acao = ?1 ");
				where = true;
				parametro.put(1, filtro.getAcao());
			}
			
			if(!Util.isNull(filtro.getUsuario())){
				if(where){
					jpql.append(" AND auditoria.usuario = ?2 ");
				}else{
					jpql.append(" WHERE auditoria.usuario = ?2 ");
					where = true;
				}
				
				parametro.put(2, filtro.getUsuario());
			}
			
			if(!Util.isNull(filtro.getTabela())){
				if(where){
					jpql.append(" AND auditoria.tabela = ?3 ");
				}else{
					jpql.append(" WHERE auditoria.tabela = ?3 ");
					where = true;
				}
				
				parametro.put(3, filtro.getTabela());
			}
			
			if(!Util.isNull(filtro.getDataInicial())){
				if(where){
					jpql.append(" AND auditoria.data > ?4 ");
				}else{
					jpql.append(" WHERE auditoria.data > ?4 ");
					where = true;
				}
				
				parametro.put(4,(LocalDateTime) filtro.getDataInicial());
			}
			
			if(!Util.isNull(filtro.getDataFinal())){
				if(where){
					jpql.append(" AND auditoria.data < ?5 ");
				}else{
					jpql.append(" WHERE auditoria.data < ?5 ");
					where = true;
				}
				
				LocalDateTime data = filtro.getDataFinal();
				//Adiciona 1 Dia.
				data = data.plus(1, ChronoUnit.DAYS);
				
				parametro.put(5, data);
			}
			
			jpql.append(" ORDER BY auditoria.data DESC");
			
			TypedQuery<Auditoria> query = getEntityManager().createQuery(jpql.toString(), Auditoria.class);
			
			Iterator it = parametro.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry<Integer,Object> map = (Map.Entry<Integer,Object>)it.next();
		        query.setParameter(map.getKey(), map.getValue());
		        it.remove();
		    }
			
			if(!Util.isBlank(filtro.getRegistros())){
				query.setMaxResults(Integer.valueOf(filtro.getRegistros()));
			}

			return query.getResultList();
			
		} catch (PersistenceException e) {
			throw new DAOException(e);
		}
	}

}
