/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.telefonos.resources;

import co.edu.uniandes.csw.telefonos.dtos.CompradorDTO;
import co.edu.uniandes.csw.telefonos.dtos.FacturaDTO;
import co.edu.uniandes.csw.telefonos.ejb.CompradorFacturasLogic;
import co.edu.uniandes.csw.telefonos.ejb.CompradorLogic;
import co.edu.uniandes.csw.telefonos.ejb.FacturaLogic;
import co.edu.uniandes.csw.telefonos.entities.FacturaEntity;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Laura Valentina Prieto Jimenez
 */
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class CompradorFacturaResource {

    private static final Logger LOGGER = Logger.getLogger(CompradorFacturaResource.class.getName());

    @Inject
    private CompradorLogic compradorLogic;
    
    @Inject
    private FacturaLogic facturaLogic;
    
    @Inject 
    private CompradorFacturasLogic compradorFacturasLogic;
    
    /**
     * Relaciona una factura a un comprador
     *
     * @param compradorId Identificador del comprador con el que se quiere
     * relacionar la factura
     * @param facturaId Identificador de la factura que se quiere asociar a un
     * comprador
     * @return Factura relacionada con el comprador
     */
    @POST
    @Path("{facturaId: \\d+}")
    public FacturaDTO agregarFactura(@PathParam("compradorId") Long compradorId, @PathParam("facturaId") Long facturaId) {
        if (compradorLogic.getComprador(compradorId) == null) {
            throw new WebApplicationException("El recurso /compradores/" + compradorId + " no existe.", 404);
        }
        FacturaDTO facturaDTO = new FacturaDTO(compradorFacturasLogic.addFactura(compradorId, facturaId));
        return facturaDTO;
    }

    /**
     * Obtiene todas las facturas asociadas comprador 
     * @param compradorId
     * @return 
     */
    @GET
    public List<FacturaDTO> obtenerFacturas(@PathParam("compradorId") Long compradorId) {
        List<FacturaDTO> listaDetailDTOs = facturasListEntity2DTO(compradorFacturasLogic.getFacturas(compradorId));
        return listaDetailDTOs;
    }
    
    /**
     * Obtiene una factura con identificador dado de un comprador con identificador dado
     * @param compradorId Identificador del comprador
     * @param facturaId Identificador de la factura a la que se quiere acceder
     * @return 
     */
    @GET
    @Path("{facturaId: \\d+}")
    public FacturaDTO obtenerFactura(@PathParam("compradorId") Long compradorId, @PathParam("facturaId") Long facturaId) {
        if (facturaLogic.getFactura(facturaId) == null) {
            throw new WebApplicationException("El recurso /compradores/" + compradorId + "/facturas/" + facturaId + " no existe.", 404);
        }
        FacturaDTO facturaDTO = new FacturaDTO(compradorFacturasLogic.getFactura(compradorId, facturaId));
        return facturaDTO;
    }
    
    /**
     * Elimina una factura de un ocomprador con identificador dado
     * @param compradorId Identificador del comprador
     * @param facturaId Identificador de la factura que se quiere eliminar
     * @return Esto debe quitarse!!! Solo es para probar en Postman
     */
    @DELETE
    @Path("{facturaId: \\d+}")
    public String eliminarFactura(@PathParam("compradorId") Long compradorId, @PathParam("facturaId") Long facturaId) {
        return ("Comp: "+compradorId+" -- Fact: "+facturaId);
    }
    
    /**
     * Convierte una lista de FacturaEntity a una lista de FacturaDTO.
     *
     * @param entityList Lista de FacturaEntity a convertir.
     * @return Lista de FacturaDTO convertida.
     */
    private List<FacturaDTO> facturasListEntity2DTO(List<FacturaEntity> entityList) {
        List<FacturaDTO> list = new ArrayList();
        for (FacturaEntity entity : entityList) {
            list.add(new FacturaDTO(entity));
        }
        return list;
    }
}
