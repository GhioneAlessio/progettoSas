package catering.businesslogic.kitchentask;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import catering.businesslogic.shift.Shift;
import catering.businesslogic.user.User;
import catering.persistence.BatchUpdateHandler;
import catering.persistence.PersistenceManager;
import javafx.collections.ObservableList;

public class SummarySheet {
    private int id;
    private User owner;
    //TODO : observablelist maybe
    private ArrayList<KitchenTask> tasks;

    public SummarySheet(User user){
        this.id = 0;
        this.owner = user;
        this.tasks = new ArrayList<>();
    }

    public void addTask(KitchenTask t){
        this.tasks.add(t);
    }   
//TODO : da dove esce sta roba
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
        t.setCompleted(false);
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
            t.setCompleted(completed.get());        
    }

    public void deleteKitchenTask(KitchenTask task){
        //TODO : al livello del modello la differenza tra cancel e delete e' quasi inesistente, anche mettedno toPrepare = false credo che poi si perda
        // traccia della task visto che viene rimossa da (arrayList) tasks
        this.tasks.remove(task);
        Shift shift = task.getShift();
        User cook = task.getCook();
        if(shift!= null)
            shift.deleteTask();
        if(cook != null && shift != null)
            cook.removeShift(shift);
        //si potrebbe togliere credo
        // task.deleteShift();

        // if(type.equals("delete")){
        //     task = null;
        // }else
        
    }

    public void cancelKitchenTask(KitchenTask task){
        Shift shift = task.getShift();
        User cook = task.getCook();
        if(shift!= null)
            shift.deleteTask();
        if(cook != null && shift != null)
            cook.removeShift(shift);
        task.deleteShift();
        // task.

        // if(type.equals("delete")){
        //     task = null;
        // }else
        
        // task.setToPrepare(false);
        
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

    // STATIC METHODS FOR PERSISTENCE

    public static void saveNewSummarySheet(SummarySheet sheet) {
        // String menuInsert = "INSERT INTO catering.SummarySheets (owner_id) VALUES (?);";
        // int[] result = PersistenceManager.executeBatchUpdate(menuInsert, 1, new BatchUpdateHandler() {
        //     @Override
        //     public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
        //         ps.setInt(1, sheet.getOwner().getId());
        //         // ps.setInt(2, m.owner.getId());
        //         // ps.setBoolean(3, m.published);
        //     }

        //     //TODO : non so se necessaria, al momento la nostra summarySheet neanche lo ha un id
        //     @Override
        //     public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
        //         // should be only one
        //         if (count == 0) {
        //             // sheet.id = rs.getInt(1);
        //         }
        //     }
        // });

        // if (result[0] > 0) { // menu effettivamente inserito
        //     // salva le features
        //     featuresToDB(m);

        //     // salva le sezioni
        //     if (m.sections.size() > 0) {
        //         Section.saveAllNewSections(m.id, m.sections);
        //     }

        //     // salva le voci libere
        //     if (m.freeItems.size() > 0) {
        //         MenuItem.saveAllNewItems(m.id, 0, m.freeItems);
        //     }
        //     loadedMenus.put(m.id, m);
        // }
    }

    public static void saveTaskOrder(SummarySheet sheet) {
    }

}
