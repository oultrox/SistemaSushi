/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import pojos.Nivelusuario;
import servicios.NivelusuarioFacadeLocal;

/**
 *
 * @author Centro de Trabajo
 */
@FacesConverter(value = "tipoUsuarioConverter")
public class tipoUsuarioConverter implements Converter {

    @EJB
    NivelusuarioFacadeLocal nivelusuarioFacade;

    public tipoUsuarioConverter() {
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            return this.nivelusuarioFacade.find(BigDecimal.valueOf(Integer.valueOf(value)));
        } catch (Exception e) {
            return e;
        }

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            return ((Nivelusuario) value).getIdnivelusuario().toString();
        } catch (Exception e) {
            return e.toString();
        }

    }

}
