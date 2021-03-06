/*
 * This file is generated by jOOQ.
 */
package eu.codeloop.thehub.jooq.tables.records;


import eu.codeloop.thehub.jooq.tables.Companies;

import java.time.OffsetDateTime;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.11"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CompaniesRecord extends UpdatableRecordImpl<CompaniesRecord> implements Record7<String, String, String, String, String, OffsetDateTime, OffsetDateTime> {

    private static final long serialVersionUID = 1282888976;

    /**
     * Setter for <code>public.companies.company_id</code>.
     */
    public CompaniesRecord setCompanyId(String value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.companies.company_id</code>.
     */
    @NotNull
    @Size(max = 255)
    public String getCompanyId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.companies.name</code>.
     */
    public CompaniesRecord setName(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.companies.name</code>.
     */
    @NotNull
    @Size(max = 255)
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.companies.logo</code>.
     */
    public CompaniesRecord setLogo(String value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>public.companies.logo</code>.
     */
    @NotNull
    @Size(max = 255)
    public String getLogo() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.companies.domain</code>.
     */
    public CompaniesRecord setDomain(String value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>public.companies.domain</code>.
     */
    @NotNull
    @Size(max = 255)
    public String getDomain() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.companies.location</code>.
     */
    public CompaniesRecord setLocation(String value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>public.companies.location</code>.
     */
    @NotNull
    @Size(max = 255)
    public String getLocation() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.companies.date_created</code>.
     */
    public CompaniesRecord setDateCreated(OffsetDateTime value) {
        set(5, value);
        return this;
    }

    /**
     * Getter for <code>public.companies.date_created</code>.
     */
    public OffsetDateTime getDateCreated() {
        return (OffsetDateTime) get(5);
    }

    /**
     * Setter for <code>public.companies.date_modified</code>.
     */
    public CompaniesRecord setDateModified(OffsetDateTime value) {
        set(6, value);
        return this;
    }

    /**
     * Getter for <code>public.companies.date_modified</code>.
     */
    public OffsetDateTime getDateModified() {
        return (OffsetDateTime) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<String, String, String, String, String, OffsetDateTime, OffsetDateTime> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<String, String, String, String, String, OffsetDateTime, OffsetDateTime> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return Companies.COMPANIES.COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Companies.COMPANIES.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Companies.COMPANIES.LOGO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Companies.COMPANIES.DOMAIN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return Companies.COMPANIES.LOCATION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<OffsetDateTime> field6() {
        return Companies.COMPANIES.DATE_CREATED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<OffsetDateTime> field7() {
        return Companies.COMPANIES.DATE_MODIFIED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getCompanyId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getLogo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getDomain();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getLocation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OffsetDateTime component6() {
        return getDateCreated();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OffsetDateTime component7() {
        return getDateModified();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getCompanyId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getLogo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getDomain();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getLocation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OffsetDateTime value6() {
        return getDateCreated();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OffsetDateTime value7() {
        return getDateModified();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompaniesRecord value1(String value) {
        setCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompaniesRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompaniesRecord value3(String value) {
        setLogo(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompaniesRecord value4(String value) {
        setDomain(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompaniesRecord value5(String value) {
        setLocation(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompaniesRecord value6(OffsetDateTime value) {
        setDateCreated(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompaniesRecord value7(OffsetDateTime value) {
        setDateModified(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompaniesRecord values(String value1, String value2, String value3, String value4, String value5, OffsetDateTime value6, OffsetDateTime value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached CompaniesRecord
     */
    public CompaniesRecord() {
        super(Companies.COMPANIES);
    }

    /**
     * Create a detached, initialised CompaniesRecord
     */
    public CompaniesRecord(String companyId, String name, String logo, String domain, String location, OffsetDateTime dateCreated, OffsetDateTime dateModified) {
        super(Companies.COMPANIES);

        set(0, companyId);
        set(1, name);
        set(2, logo);
        set(3, domain);
        set(4, location);
        set(5, dateCreated);
        set(6, dateModified);
    }
}
