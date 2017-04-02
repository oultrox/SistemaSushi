/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import java.util.List;
import javax.ejb.Local;
import pojos.Nivelusuario;

/**
 *
 * @author Yisus
 */
@Local
public interface NivelusuarioFacadeLocal {

    void create(Nivelusuario nivelusuario);

    void edit(Nivelusuario nivelusuario);

    void remove(Nivelusuario nivelusuario);

    Nivelusuario find(Object id);

    List<Nivelusuario> findAll();

    List<Nivelusuario> findRange(int[] range);

    int count();
    
}
