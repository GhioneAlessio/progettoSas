package catering.businesslogic.kitchentask;

import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.shift.Shift;
import catering.businesslogic.user.User;

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

    public void deleteShift(){
        this.shift = null;
    }

    public void deleteCook(){
        this.cook = null;
    }
    
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void setCompleted(boolean completed){
        this.completed = completed;
    }

    public void setToPrepare(boolean toPrepare){
        this.toPrepare = toPrepare;
    }

    public void setEstimatedTime(int estimatedTime){
        this.estimatedTime = estimatedTime;
    }

    public boolean getFinished(){
        return this.completed;
    }

    public boolean getToPrepare(){
        return this.toPrepare;
    }

    public int getEstimatedTime(){
        return this.estimatedTime;
    }

    @Override
    public String toString() {
        return "KitchenTask [toPrepare=" + toPrepare + ", completed=" + completed + ", estimatedTime=" + estimatedTime
                + ", cook=" + cook + ", quantity=" + quantity + ", shift=" + shift + ", recipe=" + recipe + "]";
    }

    // STATIC METHODS FOR PERSISTENCE
    public static void saveNewTask(SummarySheet sheet, KitchenTask task) {
        // TODO : implementare si deve
    }

    public static void deleteItem(KitchenTask task) {
        // TODO : implementare si deve
    }
}
