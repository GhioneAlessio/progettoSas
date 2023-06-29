package catering.businesslogic.kitchentask;

import java.util.ArrayList;
import java.util.Optional;

import catering.businesslogic.shift.Shift;
import catering.businesslogic.user.User;
import javafx.collections.ObservableList;

public class SummarySheet {
    private User owner;
    //TODO : observablelist maybe
    private ArrayList<KitchenTask> tasks;

    public SummarySheet(User user){
        this.owner = user;
        this.tasks = new ArrayList<>();
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
    public void assignKitchenTask(KitchenTask t, Optional<Shift> s, Optional<User> c, Optional<Integer> time, Optional<String> qty){
        t.setToPrepare(true);
        t.setFinished(false);
        if(c.isPresent() && s.isPresent()){
            Shift shift = s.get();
            User cook = c.get();
            shift.assignUser(cook); 
            cook.assignShift(shift);
            shift.setKitchenTask(t);
            t.setCook(cook);
        }else if(c.isPresent() && !s.isPresent()){
            t.setCook(c.get());
        }else if(!c.isPresent() && s.isPresent()){
            s.get().setKitchenTask(t);
        }
        if (time.isPresent()) 
            t.setEstimatedTime(time.get());
        
        if(qty.isPresent())
            t.setQuantity(qty.get());
    }


    public void editTask(KitchenTask t, Optional<Integer> time, Optional<String> qty, Optional<Boolean> completed) {
        if(time.isPresent())
            t.setEstimatedTime(time.get());

        if(qty.isPresent())
            t.setQuantity(qty.get());

        if(completed.isPresent())
            t.setFinished(completed.get());        
    }

    public void deleteKitchenTask(KitchenTask task, String type){
        //TODO : agguingere tutt i controlli, se prima annullo il compito e poi provo a cancellarlo alcune operazioni potrebbero fare boom
        this.tasks.remove(task);
        Shift shift = task.getShift();
        shift.deleteTask();
        User cook = task.getCook();
        cook.removeShift(shift);
        //si potrebbe togliere credo
        // task.deleteShift();

        // if(type.equals("delete")){
        //     task = null;
        // }else
         if(type.equals("cancel")){
            task.setToPrepare(false);
        }
    }

    public ArrayList<KitchenTask> getTasks(){
        return this.tasks;
    }

    public int getSummarySheetSize(){
        return this.tasks.size();
    }

    public User getOwner() {
        return owner;
    }

    public String testString() {
        String result = this.toString() + "\n";

        result += "Owner : " + this.owner.getUserName();

        result += "\n";

        for (KitchenTask t : tasks) {
            result += t.toString() + "\n";
        }

        return result;
    }
}
