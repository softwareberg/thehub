/*
 * This file is generated by jOOQ.
 */
package eu.codeloop.thehub.jooq.tables;


import eu.codeloop.thehub.jooq.Indexes;
import eu.codeloop.thehub.jooq.Keys;
import eu.codeloop.thehub.jooq.Public;
import eu.codeloop.thehub.jooq.tables.records.MonthlySalariesRecord;

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
public class MonthlySalaries extends TableImpl<MonthlySalariesRecord> {

    private static final long serialVersionUID = 205685586;

    /**
     * The reference instance of <code>public.monthly_salaries</code>
     */
    public static final MonthlySalaries MONTHLY_SALARIES = new MonthlySalaries();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<MonthlySalariesRecord> getRecordType() {
        return MonthlySalariesRecord.class;
    }

    /**
     * The column <code>public.monthly_salaries.monthly_salary</code>.
     */
    public final TableField<MonthlySalariesRecord, String> MONTHLY_SALARY = createField("monthly_salary", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.monthly_salaries.date_created</code>.
     */
    public final TableField<MonthlySalariesRecord, OffsetDateTime> DATE_CREATED = createField("date_created", org.jooq.impl.SQLDataType.TIMESTAMPWITHTIMEZONE.nullable(false).defaultValue(org.jooq.impl.DSL.field("now()", org.jooq.impl.SQLDataType.TIMESTAMPWITHTIMEZONE)), this, "");

    /**
     * The column <code>public.monthly_salaries.date_modified</code>.
     */
    public final TableField<MonthlySalariesRecord, OffsetDateTime> DATE_MODIFIED = createField("date_modified", org.jooq.impl.SQLDataType.TIMESTAMPWITHTIMEZONE.nullable(false).defaultValue(org.jooq.impl.DSL.field("now()", org.jooq.impl.SQLDataType.TIMESTAMPWITHTIMEZONE)), this, "");

    /**
     * Create a <code>public.monthly_salaries</code> table reference
     */
    public MonthlySalaries() {
        this(DSL.name("monthly_salaries"), null);
    }

    /**
     * Create an aliased <code>public.monthly_salaries</code> table reference
     */
    public MonthlySalaries(String alias) {
        this(DSL.name(alias), MONTHLY_SALARIES);
    }

    /**
     * Create an aliased <code>public.monthly_salaries</code> table reference
     */
    public MonthlySalaries(Name alias) {
        this(alias, MONTHLY_SALARIES);
    }

    private MonthlySalaries(Name alias, Table<MonthlySalariesRecord> aliased) {
        this(alias, aliased, null);
    }

    private MonthlySalaries(Name alias, Table<MonthlySalariesRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> MonthlySalaries(Table<O> child, ForeignKey<O, MonthlySalariesRecord> key) {
        super(child, key, MONTHLY_SALARIES);
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
        return Arrays.<Index>asList(Indexes.MONTHLY_SALARIES_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<MonthlySalariesRecord> getPrimaryKey() {
        return Keys.MONTHLY_SALARIES_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<MonthlySalariesRecord>> getKeys() {
        return Arrays.<UniqueKey<MonthlySalariesRecord>>asList(Keys.MONTHLY_SALARIES_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MonthlySalaries as(String alias) {
        return new MonthlySalaries(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MonthlySalaries as(Name alias) {
        return new MonthlySalaries(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public MonthlySalaries rename(String name) {
        return new MonthlySalaries(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public MonthlySalaries rename(Name name) {
        return new MonthlySalaries(name, null);
    }
}
