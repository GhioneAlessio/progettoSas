package catering.businesslogic.kitchentask;

import java.util.ArrayList;

import catering.businesslogic.shift.Shift;
import catering.businesslogic.user.User;

public class SummarySheet {
    //TODO : prima owner era di tipo String ma non so da dove fosse comparso quindi indagare
    private User owner;
    private ArrayList<KitchenTask> tasks;

    public SummarySheet(User user){
        this.owner = user;
        tasks = new ArrayList<>(tasks);
    }

    public void addTask(KitchenTask t){
        this.tasks.add(t);
    }

    public boolean isOwner(User ch){
        return true;
    }

    public void moveTask(KitchenTask t, int pos){
        tasks.remove(t);
        tasks.add(pos, t);
    }

    public void assignKitchenTask(KitchenTask t, Shift s, User c, int time, int qty){}

    public ArrayList<KitchenTask> getTasks(){
        return this.tasks;
    }

    public User getOwner() {
        return owner;
    }
}
