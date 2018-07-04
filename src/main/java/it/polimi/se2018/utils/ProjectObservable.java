package it.polimi.se2018.utils;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import it.polimi.se2018.model.events.messages.Message;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class ProjectObservable {

    private ArrayList<ProjectObserver> observers;
    private Message memorizedMessage;

    private boolean changed;
    public ProjectObservable(){
        observers = new ArrayList<>();
        changed = false;
    }

    public void addObserver(ProjectObserver newObserver){
        synchronized (observers){
            observers.add(newObserver);
        }
    }

    public void removeObserver(ProjectObserver oldObserver){
        synchronized (observers){
            observers.remove(oldObserver);
        }
    }

    public void memorizeMessage(Message message){
        this.memorizedMessage = message;
    }

    public void setChanged(){
        synchronized (observers){
            changed = true;
        }
    }

    public void notifyObservers(Message message){
        System.out.println("notifying " + observers.size() + " observers w/: " + message.toString() +
                            "\n Recipient: " + message.getRecipient());
        synchronized (observers){
            if(changed){
                observers.stream().forEach(
                        o -> {
                            try{
                                Method update = o.getClass().getMethod("update", message.getClass());
                                update.invoke(o, message);
                            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                System.out.println("Error on invoking a method on observer");
                            }
                        }
                );
                changed = false;
            }
        }
    }

    public void notifyObservers(){
        System.out.println("notifying " + observers.size() + " observers w/: " + memorizedMessage.toString() +
                "\n Recipient: " + memorizedMessage.getRecipient());
        synchronized (observers){
            System.out.println("STO MANDANDO AL GIOCATORE UN MESSAGGIO");
            if(changed){
                System.out.println("STO NOTIFICANDO AGLI OSSERVATORI");
                for(ProjectObserver observer: observers){
                    System.out.println("NOTIFICO");
                    try{
                        Method update = observer.getClass().getMethod("update", memorizedMessage.getClass());
                        update.invoke(observer, memorizedMessage);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
                changed = false;
            }
        }
    }
}
