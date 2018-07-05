package it.polimi.se2018.utils;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import it.polimi.se2018.model.events.messages.Message;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class ProjectObservable {

    private final ArrayList<ProjectObserver> observers;
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
                            "\n Recipient: " + message.getRecipient() +
                            "\nSender: " + message.getSender());
        synchronized (observers){
            if(changed){
                observers.stream().forEach(
                        observer -> {
                            try{
                                Method update = observer.getClass().getMethod("update", message.getClass());
                                update.invoke(observer, message);
                            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                System.out.println("Error on invoking a method on observable 1" +
                                        "\nwith message: " + message.toString() +
                                        "\nwith recipient: " + message.getRecipient() +
                                        "\nwith sender: " + message.getSender() +
                                        "\nto observer: " + observer.toString());
                            }
                        }
                );
                changed = false;
            }
        }
    }

    public void notifyObservers(){
        System.out.println("notifying " + observers.size() + " observers w/: " + memorizedMessage.toString() +
                "\nRecipient: " + memorizedMessage.getRecipient() +
                "\nSender: " + memorizedMessage.getSender());
        synchronized (observers){
            if(changed){
                observers.stream().forEach(
                        observer -> {
                            try{
                                Method update = observer.getClass().getMethod("update", memorizedMessage.getClass());
                                update.invoke(observer, memorizedMessage);
                                System.out.println("notified observer: " + observer.toString());
                            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                System.out.println("error on invoking a method on observable 2" +
                                        "\nwith message: " + memorizedMessage +
                                        "\nwith recipient: " + memorizedMessage.getRecipient() +
                                        "\non observer: " + observer.toString());
                            }
                        }
                );
                changed = false;
            }
        }
    }
}
