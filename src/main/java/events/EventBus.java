package events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EventBus {

    private static final List<Class<?>> staticListeners = new ArrayList<>();
    private static final List<Object> instanceListeners = new ArrayList<>();

    public static void addListener(Class<?> event){
        staticListeners.add(event);
    }

    public static void addListener(Object event){
        instanceListeners.add(event);
    }

    public static void invoke(Event event){
        for(Class<?> clazz : staticListeners){
            for(Method method : clazz.getMethods()){
                if(method.isAnnotationPresent(Event.EventHandler.class)){
                    if(method.getParameterCount() == 1){
                        if(method.getParameterTypes()[0].isAssignableFrom(event.getClass())){
                            try{
                                method.invoke(clazz, event);
                            } catch (InvocationTargetException | IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            }
        }

        for(Object instance : instanceListeners){
            for(Method method : instance.getClass().getMethods()){
                if(method.isAnnotationPresent(Event.EventHandler.class)){
                    if(method.getParameterCount() == 1){
                        if(method.getParameterTypes()[0].isAssignableFrom(event.getClass())){
                            try{
                                method.invoke(instance, event);
                            } catch (InvocationTargetException | IllegalAccessException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }


}
