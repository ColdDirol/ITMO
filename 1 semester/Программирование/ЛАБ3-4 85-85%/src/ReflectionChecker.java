import annotations.ToChangeAnnotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionChecker {
    public void showClassFullName(Object object){
        Class clazz = object.getClass();
        System.out.println(clazz.getName());
    }

    public String getClassFullName(Object object){
        Class clazz = object.getClass();
        return clazz.getName();
    }

    public void showClassFields(Object object){
        Class clazz = object.getClass();
        Field fields[] = clazz.getDeclaredFields();
        System.out.println("Поля класса " + getClassFullName(object) + " :");
        for(Field field : fields){
            System.out.println(field.getName());
        }
    }

    public void showClassMethods(Object object){
        Class clazz = object.getClass();
        Method methods[] = clazz.getMethods();
        System.out.println("Методы класса " + getClassFullName(object) + " :");
        for(Method method : methods){
            System.out.println(method.getName());
        }
    }

    public void showClassFieldsAnnotations(Object object){
        Class clazz = object.getClass();
        Field fields[] = clazz.getDeclaredFields();
        System.out.println("Аннотации полей класса " + getClassFullName(object) + " :");
        for(Field field : fields){
            Annotation annotations[] = field.getAnnotations();
            for(Annotation annotation : annotations){
                System.out.println(field.getName() + " : " + annotation.toString());
            }
        }
    }

    public void showClassMethodsAnnotations(Object object){
        Class clazz = object.getClass();
        Method methods[] = clazz.getMethods();
        System.out.println("Аннотации методов класса " + getClassFullName(object) + " :");
        for(Method method : methods){
            Annotation annotations[] = method.getAnnotations();
            for(Annotation annotation : annotations){
                System.out.println(method.getName() + " : " + annotation.toString());
            }
        }
    }

    public void changePrivateFields(Object object) throws IllegalAccessException {
        Class clazz = object.getClass();
        Field fields[] = clazz.getDeclaredFields();
        System.out.println("Изменено: ");
        for(Field field : fields){
            Annotation annotation = field.getAnnotation(ToChangeAnnotation.class);
            if(annotation == null){
                continue;
            }
            // делаем поле доступным (если оно private)
            field.setAccessible(true);

            field.set(object, "Nikita");

            //делаем поле недоступным
            field.setAccessible(false);
            // Если поле не помечено аннотацией @ToChangeAnnotation - if, иначе - else
        }
    }

    public Object createNewObject (Object object) throws InstantiationException, IllegalAccessException {
        Class clazz = object.getClass();
        return clazz.newInstance();
    }
}
