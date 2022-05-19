package br.com.rqgr.infrastructure;

import javax.persistence.PrePersist;

/**
 *
 * @author Rafael
 */
public class AbstractEntityListener {
    
    @PrePersist
    protected void genereteOrderedUUID(IHaveUUID entity) {
        if(entity != null && entity.getId() == null){
            entity.setId(OrderedUUIDHelper.randomOrderedUUID());
        }
    }
    
}
