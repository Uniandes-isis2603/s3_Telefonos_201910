/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.telefonos.ejb;

import co.edu.uniandes.csw.telefonos.entities.CelularEntity;
import co.edu.uniandes.csw.telefonos.entities.ListaDeDeseosEntity;
import co.edu.uniandes.csw.telefonos.entities.TabletEntity;
import co.edu.uniandes.csw.telefonos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.telefonos.persistence.CelularPersistence;
import co.edu.uniandes.csw.telefonos.persistence.ListaDeDeseosPersistence;
import co.edu.uniandes.csw.telefonos.persistence.TabletPersistence;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Andres Felipe Daza Diaz
 */
@Stateless
public class ListaDeDeseosCelularLogic {
     @Inject 
    private CelularPersistence celularPersistence;
    
    @Inject 
    private ListaDeDeseosPersistence listaPersistence;
    
    /**
     * Agregar un celular a la lista de deseos
     *
     * @param celularIMEI el IMEI del celular a guardar.
     * @param ListaId El id de la lista de deseos en la cual se va a guardar el celular.
     * @return La lista de deseos con el celular agregado.
     */
       public ListaDeDeseosEntity agregarCelular(Long celularIMEI, Long listaId)throws BusinessLogicException{
          ListaDeDeseosEntity listaEntity = listaPersistence.find(listaId);
          CelularEntity celularEntity = celularPersistence.findByImei(celularIMEI);
        ArrayList<TabletEntity> tablets = (ArrayList<TabletEntity>) listaEntity.getTablets();
        ArrayList<CelularEntity> celulares = (ArrayList<CelularEntity>) listaEntity.getCelulares();
        if(tablets.size()+celulares.size()>=10){
            throw new BusinessLogicException("No se pudo registrar la tableta en la lista de deseos. Solo se pueden tener 10 dispositivos como maximo");
        }
        celulares.add(celularEntity);
        listaEntity.setCelulares(celulares);
        return listaEntity;
    
    }
       
     /**
     * Borra un celular de la lista de deseos
     *
     * @param celularIMEI el IMEI del celular a remover.
     * @param ListaId El id de la lista de deseos en la cual se va a eliminar el celular.
     * @return la lista de deseos con el celular removido.
     */
       public ListaDeDeseosEntity removerCelular(Long celularIMEI, Long listaId) throws BusinessLogicException{
          ListaDeDeseosEntity listaEntity = listaPersistence.find(listaId);
      
        ArrayList<CelularEntity> celulares = (ArrayList<CelularEntity>) listaEntity.getCelulares();
        boolean encontro = false;
        for(CelularEntity celular: celulares){
            if(celular.getImei() == celularIMEI){
                encontro = true;
                celulares.remove(celular);
            }
        }
        if(!encontro){
            throw new BusinessLogicException("El celular que desea eliminar no se encuentra en la lista de deseos con id: "+listaId);
        }
        listaEntity.setCelulares(celulares);
        return listaEntity;
    
    }
}
