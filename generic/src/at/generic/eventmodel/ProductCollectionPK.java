package at.generic.eventmodel;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class ProductCollectionPK implements Serializable {

    /** identifier field */
    private Long id;

    /** identifier field */
    private String productId;

    /** full constructor */
    public ProductCollectionPK(Long id, String productId) {
        this.id = id;
        this.productId = productId;
    }

    /** default constructor */
    public ProductCollectionPK() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("productId", getProductId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof ProductCollectionPK) ) return false;
        ProductCollectionPK castOther = (ProductCollectionPK) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getProductId(), castOther.getProductId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .append(getProductId())
            .toHashCode();
    }

}
