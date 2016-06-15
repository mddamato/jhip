package org.microservice.countrycode.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A MasterCountryCode.
 */
@Entity
@Table(name = "master_country_code")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MasterCountryCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "master_country_code")
    private String masterCountryCode;

    @Column(name = "master_country_name")
    private String masterCountryName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMasterCountryCode() {
        return masterCountryCode;
    }

    public void setMasterCountryCode(String masterCountryCode) {
        this.masterCountryCode = masterCountryCode;
    }

    public String getMasterCountryName() {
        return masterCountryName;
    }

    public void setMasterCountryName(String masterCountryName) {
        this.masterCountryName = masterCountryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MasterCountryCode masterCountryCode = (MasterCountryCode) o;
        if(masterCountryCode.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, masterCountryCode.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MasterCountryCode{" +
            "id=" + id +
            ", masterCountryCode='" + masterCountryCode + "'" +
            ", masterCountryName='" + masterCountryName + "'" +
            '}';
    }
}
