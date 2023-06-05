--удаление триггеров с нижеперечисленными названиями, если они уже существуют
DROP TRIGGER IF EXISTS peticantrop_insert_trigger ON PETICANTROP;
DROP TRIGGER IF EXISTS peticantrop_update_trigger ON PETICANTROP;
DROP TRIGGER IF EXISTS peticantrop_delete_trigger ON PETICANTROP;


--создание функции, вызываемой при добавлении нового объекта в таблицу peticantrop
CREATE OR REPLACE FUNCTION peticantrop_after_insert_function() RETURNS TRIGGER AS $$
BEGIN
    RAISE NOTICE 'INSERT: New object: id=''%'', name=''%'', sex=''%'', hunger=''%'', thirst=''%''', NEW.id, NEW.name, NEW.sex, NEW.hunger, NEW.thirst;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


--создание функции, вызываемой при обновлении объекта в таблице peticantrop
CREATE OR REPLACE FUNCTION peticantrop_after_update_function() RETURNS TRIGGER AS $$
BEGIN
    RAISE NOTICE 'UPDATE: Changed object: id=''%'', name=''%'', sex=''%'', hunger=''%'', thirst=''%''', NEW.id, NEW.name, NEW.sex, NEW.hunger, NEW.thirst;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


--создание функции, вызываемой при удалении объекта из таблицы peticantrop
CREATE OR REPLACE FUNCTION peticantrop_before_delete_function() RETURNS TRIGGER AS $$
BEGIN
    RAISE NOTICE 'DELETE: Deleted object: id=''%'', name=''%'', sex=''%'', hunger=''%'', thirst=''%''', OLD.id, OLD.name, OLD.sex, OLD.hunger, OLD.thirst;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER peticantrop_insert_trigger
    AFTER INSERT ON peticantrop
    FOR EACH ROW
    EXECUTE FUNCTION peticantrop_after_insert_function();


CREATE TRIGGER peticantrop_update_trigger
    AFTER UPDATE ON peticantrop
    FOR EACH ROW
    EXECUTE FUNCTION peticantrop_after_update_function();


CREATE TRIGGER peticantrop_delete_trigger
    BEFORE DELETE ON peticantrop
    FOR EACH ROW
    EXECUTE FUNCTION peticantrop_before_delete_function();