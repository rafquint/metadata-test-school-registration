package br.com.rqgr.infrastructure;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Rafael
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(value = {AbstractEntityListener.class})
public abstract class AbstractEntity extends AbstractObject implements IHaveUUID {

    @Id
    @Basic(optional = false)
    @Size(min = 1, max = 32)
    @Column(name = "id", nullable = false, length = 32)
    private String id;

}
