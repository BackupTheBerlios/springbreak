package at.generic.eventmodel;

import java.io.Serializable;
import java.util.HashSet;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class ProductCollection implements Serializable {

    /** identifier field */
    private at.generic.eventmodel.ProductCollectionPK comp_id;

    /** nullable persistent field */
    private String amount;

    /** nullable persistent field */
    private at.generic.eventmodel.OrderReceivedEvent orderreceivedevent;

    /** full constructor */
    public ProductCollection(at.generic.eventmodel.ProductCollectionPK comp_id, String amount, at.generic.eventmodel.OrderReceivedEvent orderreceivedevent) {
        this.comp_id = comp_id;
        this.amount = amount;
        this.orderreceivedevent = orderreceivedevent;
    }
    
    /**
     * used for digester mapping!
     * 
     * @param productId
     */
    public void addProductCollectionKey(String productId) {
    	this.comp_id = new  ProductCollectionPK(null, productId);
    }
    
    /** default constructor */
    public ProductCollection() {
    }

    /** minimal constructor */
    public ProductCollection(at.generic.eventmodel.ProductCollectionPK comp_id) {
        this.comp_id = comp_id;
    }

    public at.generic.eventmodel.ProductCollectionPK getComp_id() {
        return this.comp_id;
    }

    public void setComp_id(at.generic.eventmodel.ProductCollectionPK comp_id) {
        this.comp_id = comp_id;
    }

    public String getAmount() {
        return this.amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public at.generic.eventmodel.OrderReceivedEvent getOrderreceivedevent() {
        return this.orderreceivedevent;
    }

    public void setOrderreceivedevent(at.generic.eventmodel.OrderReceivedEvent orderreceivedevent) {
        this.orderreceivedevent = orderreceivedevent;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof ProductCollection) ) return false;
        ProductCollection castOther = (ProductCollection) other;
        return new EqualsBuilder()
            .append(this.getComp_id(), castOther.getComp_id())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getComp_id())
            .toHashCode();
    }

}
