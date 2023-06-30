package catering.businesslogic.kitchentask;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.shift.Shift;
import catering.businesslogic.user.User;
import catering.persistence.BatchUpdateHandler;
import catering.persistence.PersistenceManager;
import javafx.collections.ObservableList;

public class SummarySheet {
    private int id;
    private User owner;
    private ServiceInfo service;
    // TODO : observablelist maybe
    private ArrayList<KitchenTask> tasks;

    public SummarySheet(User user, ServiceInfo service) {
        this.id = 0;
        this.owner = user;
        this.service = service;
        this.tasks = new ArrayList<>();
    }

    public void addTask(KitchenTask t) {
        this.tasks.add(t);
    }

    // TODO : da dove esce sta roba
    public boolean isOwner(User ch) {
        return true;
    }

    public void moveTask(KitchenTask t, int pos) {
        tasks.remove(t);
        tasks.add(pos, t);
    }

    // TODO : tutta la parte dei turni fa schifo, non mi fido di quello che ho
    // scritto, miriam salvami
    public void assignKitchenTask(KitchenTask t, Optional<Shift> s, Optional<User> c, Optional<Integer> time,
            Optional<String> qty) {
        t.setToPrepare(true);
        t.setCompleted(false);
        if (c.isPresent() && s.isPresent()) {
            Shift shift = s.get();
            User cook = c.get();
            shift.assignUser(cook);
            cook.assignShift(shift);
            shift.setKitchenTask(t);
            t.setCook(cook);
        } else if (c.isPresent() && !s.isPresent()) {
            t.setCook(c.get());
        } else if (!c.isPresent() && s.isPresent()) {
            s.get().setKitchenTask(t);
        }
        if (time.isPresent())
            t.setEstimatedTime(time.get());

        if (qty.isPresent())
            t.setQuantity(qty.get());
    }

    public void editTask(KitchenTask t, Optional<Integer> time, Optional<String> qty, Optional<Boolean> completed) {
        if (time.isPresent())
            t.setEstimatedTime(time.get());

        if (qty.isPresent())
            t.setQuantity(qty.get());

        if (completed.isPresent())
            t.setCompleted(completed.get());
    }

    public void deleteKitchenTask(KitchenTask task) {
        // TODO : al livello del modello la differenza tra cancel e delete e' quasi
        // inesistente, anche mettedno toPrepare = false credo che poi si perda
        // traccia della task visto che viene rimossa da (arrayList) tasks
        this.tasks.remove(task);
        Shift shift = task.getShift();
        User cook = task.getCook();
        if (shift != null)
            shift.deleteTask();
        if (cook != null && shift != null)
            cook.removeShift(shift);
        // si potrebbe togliere credo
        // task.deleteShift();

        // if(type.equals("delete")){
        // task = null;
        // }else

    }

    public void cancelKitchenTask(KitchenTask task) {
        Shift shift = task.getShift();
        User cook = task.getCook();
        if (shift != null)
            shift.deleteTask();
        if (cook != null && shift != null)
            cook.removeShift(shift);
        task.deleteShift();
        // task.

        // if(type.equals("delete")){
        // task = null;
        // }else

        // task.setToPrepare(false);

    }

    public ArrayList<KitchenTask> getTasks() {
        return this.tasks;
    }

    public int getSummarySheetSize() {
        return this.tasks.size();
    }

    public User getOwner() {
        return this.owner;
    }

    public int getId() {
        return this.id;
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

    public static void saveNewSummarySheet(SummarySheet sm) {
        String summarySheetInsert = "INSERT INTO catering.SummarySheets (owner_id) VALUES " + sm.getOwner().getId()
                + ";";
        PersistenceManager.executeUpdate(summarySheetInsert);
        sm.id = PersistenceManager.getLastId();
    }

    public static void saveTaskOrder(SummarySheet sheet) {
    }

}
