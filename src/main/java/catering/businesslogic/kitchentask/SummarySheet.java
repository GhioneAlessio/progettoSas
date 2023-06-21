package catering.businesslogic.kitchentask;

import java.util.ArrayList;
import java.util.Optional;

import catering.businesslogic.shift.Shift;
import catering.businesslogic.user.User;

public class SummarySheet {
    //TODO : prima owner era di tipo String ma non so da dove fosse comparso quindi indagare
    private User owner;
    //TODO : observablelist maybe
    private ArrayList<KitchenTask> tasks;

    public SummarySheet(User user){
        this.owner = user;
        this.tasks = new ArrayList<>(tasks);
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

    // TODO : tutta la parte dei turni fa schifo, non mi fido di quello che ho scritto, miriam salvami
    public void assignKitchenTask(KitchenTask t, Optional<Shift> s, Optional<User> c, Optional<Integer> time, Optional<Integer> qty){
        t.setToPrepare(true);
        t.setFinished(false);
        if (time.isPresent()) 
            t.setEstimatedTime(time.get());
        
        if(qty.isPresent())
            t.setQuantity(qty.get());

        if(c.isPresent() && s.isPresent()){
            Shift shift = s.get();
            User cook = c.get();
            shift.assignUser(cook); 
            shift.setKitchenTask(t);
            cook.assignShift(shift);
            t.setCook(cook);
        }else if(c.isPresent() && !s.isPresent()){
            t.setCook(c.get());
        }else if(!c.isPresent() && s.isPresent()){
            s.get().setKitchenTask(t);
        }
    }

    public ArrayList<KitchenTask> getTasks(){
        return this.tasks;
    }

    public User getOwner() {
        return owner;
    }
}
