/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pojos.Nivelusuario;

/**
 *
 * @author Centro de Trabajo
 */
@Stateless
public class NivelusuarioFacade extends AbstractFacade<Nivelusuario> implements NivelusuarioFacadeLocal {

    @PersistenceContext(unitName = "FukusukeSushi-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NivelusuarioFacade() {
        super(Nivelusuario.class);
    }
    
}
