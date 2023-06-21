package catering.businesslogic.shift;

import java.sql.Date;
import java.util.ArrayList;

import catering.businesslogic.kitchentask.KitchenTask;
import catering.businesslogic.user.User;

public class Shift {
    private int timeSlot;
    private Date date;
    private String place;

    //TODO : var che distingue tra turno preparatorio e di servizio (?) costruttore da rivedere + compito da svolgere nel turno
    private String type;
    private KitchenTask kitchenTask;
    //TODO : arrayList di user assegnati a questo turno 
    ArrayList<User> assignedUsers;

    public Shift(Date date, int timeSlot, String place) {
        this.date = date;
        this.timeSlot = timeSlot;
        this.place = place;
        this.assignedUsers = new ArrayList<>();        
    }

    //TODO : solita storia, non mi fido di entrambi
    public void assignUser(User u){
        this.assignedUsers.add(u);
    }

    public void setKitchenTask(KitchenTask kitchenTask){
        this.kitchenTask = kitchenTask;
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

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Shift tmpShift = (Shift) obj;

        return this.timeSlot ==  tmpShift.getTimeSlot() && (this.date.compareTo(tmpShift.getDate()) == 0) && this.place == tmpShift.getPlace(); 
    }
}
