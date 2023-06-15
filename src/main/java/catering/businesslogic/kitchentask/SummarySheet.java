package catering.businesslogic.kitchentask;

import java.rmi.Remote;
import java.util.ArrayList;

import catering.businesslogic.shift.Shift;
import catering.businesslogic.user.User;

public class SummarySheet {
    private String owner;
    private ArrayList<KitchenTask> tasks;

    public SummarySheet(User user){
        tasks = new ArrayList<>(tasks);
    }

    public void addTask(KitchenTask t){
        tasks.add(t);
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

}
