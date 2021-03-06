/*
 * This file is generated by jOOQ.
 */
package eu.codeloop.thehub.jooq.tables;


import eu.codeloop.thehub.jooq.Indexes;
import eu.codeloop.thehub.jooq.Keys;
import eu.codeloop.thehub.jooq.Public;
import eu.codeloop.thehub.jooq.tables.records.LocationsRecord;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


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
public class Locations extends TableImpl<LocationsRecord> {

    private static final long serialVersionUID = 145572935;

    /**
     * The reference instance of <code>public.locations</code>
     */
    public static final Locations LOCATIONS = new Locations();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<LocationsRecord> getRecordType() {
        return LocationsRecord.class;
    }

    /**
     * The column <code>public.locations.location</code>.
     */
    public final TableField<LocationsRecord, String> LOCATION = createField("location", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.locations.date_created</code>.
     */
    public final TableField<LocationsRecord, OffsetDateTime> DATE_CREATED = createField("date_created", org.jooq.impl.SQLDataType.TIMESTAMPWITHTIMEZONE.nullable(false).defaultValue(org.jooq.impl.DSL.field("now()", org.jooq.impl.SQLDataType.TIMESTAMPWITHTIMEZONE)), this, "");

    /**
     * The column <code>public.locations.date_modified</code>.
     */
    public final TableField<LocationsRecord, OffsetDateTime> DATE_MODIFIED = createField("date_modified", org.jooq.impl.SQLDataType.TIMESTAMPWITHTIMEZONE.nullable(false).defaultValue(org.jooq.impl.DSL.field("now()", org.jooq.impl.SQLDataType.TIMESTAMPWITHTIMEZONE)), this, "");

    /**
     * Create a <code>public.locations</code> table reference
     */
    public Locations() {
        this(DSL.name("locations"), null);
    }

    /**
     * Create an aliased <code>public.locations</code> table reference
     */
    public Locations(String alias) {
        this(DSL.name(alias), LOCATIONS);
    }

    /**
     * Create an aliased <code>public.locations</code> table reference
     */
    public Locations(Name alias) {
        this(alias, LOCATIONS);
    }

    private Locations(Name alias, Table<LocationsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Locations(Name alias, Table<LocationsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Locations(Table<O> child, ForeignKey<O, LocationsRecord> key) {
        super(child, key, LOCATIONS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.LOCATIONS_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<LocationsRecord> getPrimaryKey() {
        return Keys.LOCATIONS_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<LocationsRecord>> getKeys() {
        return Arrays.<UniqueKey<LocationsRecord>>asList(Keys.LOCATIONS_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Locations as(String alias) {
        return new Locations(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Locations as(Name alias) {
        return new Locations(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Locations rename(String name) {
        return new Locations(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Locations rename(Name name) {
        return new Locations(name, null);
    }
}
