package catering.businesslogic.kitchentask;

import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.shift.Shift;
import catering.businesslogic.user.User;

public class KitchenTask {
    private boolean toPrepare;
    private boolean finished;
    private int estimatedTime;
    int quantity;
    User cook;
    Shift Shift; 
    Recipe recipe;

    public KitchenTask(Recipe r){
        this.recipe = r;
    }
}
