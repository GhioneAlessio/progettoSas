package catering.businesslogic.kitchentask;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.shift.Shift;
import catering.businesslogic.user.User;
import catering.persistence.PersistenceManager;

public class KitchenTask {
    private int id;
    private boolean toPrepare;
    private boolean completed;
    private int estimatedTime;
    private User cook;
    private String quantity;
    private Shift shift;
    private Recipe recipe;

    public KitchenTask(Recipe r) {
        this.id = 0;
        this.recipe = r;
    }

    public User getCook() {
        return this.cook;
    }

    public String getQuantity() {
        return this.quantity;
    }

    public Shift getShift() {
        return this.shift;
    }

    public Recipe getRecipe() {
        return this.recipe;
    }

    public void setCook(User cook) {
        this.cook = cook;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public void deleteShift() {
        this.shift = null;
    }

    public void deleteCook() {
        this.cook = null;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setToPrepare(boolean toPrepare) {
        this.toPrepare = toPrepare;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public boolean getCompleted() {
        return this.completed;
    }

    public boolean getToPrepare() {
        return this.toPrepare;
    }

    public int getEstimatedTime() {
        return this.estimatedTime;
    }

    public int getId(){
        return this.id;
    }
    @Override
    public String toString() {
        return "KitchenTask [toPrepare=" + toPrepare + ", completed=" + completed + ", estimatedTime=" + estimatedTime
                + ", cook=" + cook + ", quantity=" + quantity + ", shift=" + shift + ", recipe=" + recipe + "]";
    }

    // STATIC METHODS FOR PERSISTENCE
    public static void saveNewTask(int sheetId, KitchenTask task) {
        String taskInsert = "INSERT INTO catering.KitchenTasks (summarySheet_id, recipe_id) VALUES (" +
                sheetId + ", " +
                task.getRecipe().getId() + ");";
        PersistenceManager.executeUpdate(taskInsert);
        task.id = PersistenceManager.getLastId();
    }

    public static void saveKitchenTaskEdited(KitchenTask task){
        String upd = "UPDATE MenuItems SET position = ? WHERE id = ?";
    //TODO fare come sopra
        String taskEdit = "UPDATE catering.KitchenTasks (estimatedTime, quantity, completed) VALUES (" +
        task.getEstimatedTime() + ", " +
        task.getQuantity() + "," +
        task.getCompleted() + ")" +
        "WHERE id = " + task.getId() + ";";
        PersistenceManager.executeUpdate(taskEdit);
    }

    public static void saveKitchenTaskAssigned(KitchenTask task){
        String updateTaskAssigned = "UPDATE catering.KitchenTasks toPrepare = " + task.getToPrepare() + ", completed = " + task.getCompleted() +
        ", quantity = '" + task.getQuantity() + "'', estimatedTime = " + task.getEstimatedTime() + ", cook_id = " + task.getCook().getId() + 
        ", shift_id" + task.getShift().getId() + " WHERE id =" + task.getId();
        PersistenceManager.executeUpdate(updateTaskAssigned);
        //TODO : vedere in futuro se aggiungere l'id (getLastId)
        if(task.getCook() != null && task.getShift() != null){
            String newAssignment = "INSERT INTO UserAssignedShift (user_id, shift_id) VALUES (" + task.getCook().getId() + "," + task.getShift().getId()+ ")";
            PersistenceManager.executeUpdate(newAssignment);
        }
    }
    
    public static void updateDeleteKitchenTask(KitchenTask task) {
        //DELETE UserAssignedShift dove shiftId = task.shift.id AND cookId = task.cook.id
        String deleteAssignment = "DELETE FROM UserAssignedShift WHERE user_id = " + task.getCook().getId() + " AND shift_id = " + task.getShift().getId();
        PersistenceManager.executeUpdate(deleteAssignment);
        String deleteKitchenTask = "DELETE FROM KitchenTasks WHERE id = " + task.getId();
        //DELETE catering.KitchenTasks WHERE id =" + task.getId(); 
        PersistenceManager.executeUpdate(deleteKitchenTask);
    }

    public static void updateCancelKitchenTask(KitchenTask task){
        //UPDATE catering.KitchenTasks toPrepare = false WHERE id =" + task.getId();
        String updateTaskCanceled = "UPDATE catering.KitchenTasks toPrepare = " + task.getToPrepare();
        PersistenceManager.executeUpdate(updateTaskCanceled);
    }
}
