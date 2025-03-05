CREATE OR REPLACE FUNCTION get_table_columns_info_table(table_name TEXT)
RETURNS TABLE (
    column_number INTEGER,
    column_name TEXT,
    column_type TEXT,
    column_not_null BOOLEAN,
    column_description TEXT,
    constraint_name TEXT,
    constraint_definition TEXT
)
LANGUAGE plpgsql
AS $$
DECLARE
    rec RECORD;
    col_num INTEGER := 1;
BEGIN
    FOR rec IN
        SELECT
            a.attname,
            pg_catalog.format_type(a.atttypid, a.atttypmod),
            a.attnotnull,
            (SELECT pg_catalog.col_description(a.attrelid, a.attnum)),
            (
                SELECT con.conname
                FROM pg_catalog.pg_constraint con
                WHERE con.conrelid = a.attrelid AND a.attnum = ANY(con.conkey) AND con.contype = 'c'
            )
        FROM pg_catalog.pg_attribute a
        WHERE a.attrelid = ('public.' || table_name)::regclass AND a.attnum > 0 AND NOT a.attisdropped
        ORDER BY a.attnum
    LOOP
        column_number := col_num;
        column_name := rec.attname;
        column_type := rec.format_type;
        column_not_null := rec.attnotnull;
        column_description := rec.col_description;
        constraint_name := rec.conname;
        constraint_definition := (SELECT pg_get_constraintdef(oid) FROM pg_catalog.pg_constraint WHERE conname=rec.conname);
        RETURN NEXT;
        col_num := col_num + 1;
    END LOOP;
END $$;