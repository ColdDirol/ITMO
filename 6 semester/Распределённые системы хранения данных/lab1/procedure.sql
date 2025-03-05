CREATE OR REPLACE PROCEDURE get_table_columns_info(table_name TEXT)
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
        WHERE a.attrelid = ('s373440.' || table_name)::regclass AND a.attnum > 0 AND NOT a.attisdropped
        ORDER BY a.attnum
    LOOP
        RAISE NOTICE '%  %  %',
            col_num,
            rec.attname,
            'Type: ' || rec.format_type ||
            CASE WHEN rec.attnotnull THEN ' NOT NULL' ELSE '' END ||
            CASE WHEN rec.col_description IS NOT NULL THEN E'\nComment: ' || rec.col_description ELSE '' END ||
            CASE WHEN rec.conname IS NOT NULL THEN E'\nConstr: ' || rec.conname || ' CHECK (' || (SELECT pg_get_constraintdef(oid) FROM pg_catalog.pg_constraint WHERE conname=rec.conname) || ')' ELSE '' END;
        col_num := col_num + 1;
    END LOOP;
END $$;