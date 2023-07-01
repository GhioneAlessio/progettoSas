package catering.businesslogic.shift;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

import catering.businesslogic.kitchentask.KitchenTask;
import catering.businesslogic.user.User;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Shift {
    private int id;
    private int timeSlot;
    private Date date;
    private String place;
    private int serviceId;
    //TODO : costruttore da rivedere + compito da svolgere nel turno
    
    private KitchenTask kitchenTask;
    private User assignedUser;

    public Shift(int serviceId, int timeSlot, Date date) {
        this.id = 0;
        this.serviceId = serviceId;
        this.date = date;
        this.timeSlot = timeSlot;
    }

    //TODO : solita storia, non mi fido di entrambi
    public void assignUser(User u){
        this.assignedUser = u;
    }
    
    public void setKitchenTask(KitchenTask kitchenTask){
        this.kitchenTask = kitchenTask;
    }
    
    public void deleteTask(){
        this.kitchenTask = null;
    }
    
    public void deleteAssignedUser() {
        this.assignedUser = null;
    }

    public int getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(int timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getId() {
        return this.id;
    }
    
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Shift tmpShift = (Shift) obj;

        return this.timeSlot ==  tmpShift.getTimeSlot() && (this.date.compareTo(tmpShift.getDate()) == 0); 
    }

    @Override
    public String toString() {
        return "\nShift [id=" + id + ", timeSlot=" + timeSlot + ", date=" + date + ", place=" + place + ", serviceId="
                + serviceId + "]";
    }

    public static ObservableList<Shift> loadAllShifts() {
        String query = "SELECT * FROM Shifts WHERE " + true;
        ObservableList<Shift> shiftsList = FXCollections.observableArrayList();

        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                int id = rs.getInt("id");
                Shift shift = new Shift(rs.getInt(2), rs.getInt(3), rs.getDate(4));
                shift.id = rs.getInt(1);
                shiftsList.add(shift);
            }
        });         
        return shiftsList; 
    }

    public static ObservableList<Shift> loadShiftByServiceId(int serviceId) {
        String query = "SELECT * FROM Shifts WHERE service_id =" + serviceId;
        ObservableList<Shift> shiftsList = FXCollections.observableArrayList();

        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                Shift shift = new Shift(rs.getInt(2), rs.getInt(3), rs.getDate(4));
                shift.id = rs.getInt(1);
                shiftsList.add(shift);
            }
        });         
        return shiftsList; 
    }

    public static Shift loadShiftById(int id) {
        String query = "SELECT * FROM Shifts WHERE id =" + id;

        AtomicReference<Shift> shiftRef = new AtomicReference<>();

        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                Shift shift = new Shift(rs.getInt(2), rs.getInt(3), rs.getDate(4));
                shift.id = rs.getInt(1);
                shiftRef.set(shift);
            }
        });    
        return shiftRef.get();     
    }
    
}
