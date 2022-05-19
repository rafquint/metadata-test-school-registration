package br.com.rqgr.infrastructure;

import java.io.Serializable;

/**
 *
 * @author Rafael
 */
public interface IHaveUUID extends Serializable {

    public String getId();

    public void setId(String id);

}
