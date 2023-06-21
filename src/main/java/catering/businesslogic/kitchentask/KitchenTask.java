package catering.businesslogic.kitchentask;

import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.shift.Shift;
import catering.businesslogic.user.User;

public class KitchenTask {
    private boolean toPrepare;
    private boolean finished;
    private int estimatedTime;
    private User cook;
    private int quantity;
    private Shift Shift;
    private Recipe recipe;

    public KitchenTask(Recipe r) {
        this.recipe = r;
    }
    
    public User getCook() {
        return cook;
    }

    public int getQuantity() {
        return quantity;
    }

    public Shift getShift() {
        return Shift;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setCook(User cook) {
        this.cook = cook;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setShift(Shift shift) {
        Shift = shift;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void setFinished(boolean finished){
        this.finished = finished;
    }

    public void setToPrepare(boolean toPrepare){
        this.toPrepare = toPrepare;
    }

    public void setEstimatedTime(int estimatedTime){
        this.estimatedTime = estimatedTime;
    }

    public boolean getFinished(){
        return this.finished;
    }

    public boolean getToPrepare(){
        return this.toPrepare;
    }

    public int getEstimatedTime(){
        return this.estimatedTime;
    }
}
