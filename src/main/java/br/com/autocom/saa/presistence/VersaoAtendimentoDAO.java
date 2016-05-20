package br.com.autocom.saa.presistence;

import javax.enterprise.context.Dependent;
import javax.inject.Named;

import br.com.autocom.saa.dom.VersaoAtendimento;

/**
 * Implementaçao dos métodos de persistência relacionado a {@link VersaoAtendimento}
 * 
 * @author Denilson Godinho
 *
 */

@Named
@Dependent
public class VersaoAtendimentoDAO extends SAAGenericDAO<VersaoAtendimento, Long> {

}
