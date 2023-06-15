package catering.businesslogic.kitchentask;

import java.security.Provider.Service;
import java.util.ArrayList;

import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.menu.Menu;
import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.shift.Shift;
import catering.businesslogic.user.User;
import javafx.event.Event;

public class KitchenTaskManager {
    private ArrayList<TaskEventReceiver> eventReceivers;
    private SummarySheet currentSummarySheet;

    public SummarySheet generateSummarySheet(EventInfo event, ServiceInfo service) throws UseCaseLogicException{
        //TODO : sono motlo confuso e non so come implementare sta roba 
        User user = UserManager.getCurrentUser();
        if(!user.isChef() || event.providesService(service) || (event.getChef() != user) || service.getMenu() == null)
            throw new UseCaseLogicException();

        Menu menu = service.getMenu();
        ArrayList<Recipe> recipes =  menu.getNeededRecipes();
        SummarySheet newSumSheet = new SummarySheet(user);

        for(Recipe rec : recipes){
            KitchenTask t = new KitchenTask(rec);
            newSumSheet.addTask(t);
        }
        
        this.setCurrentSumSheet(newSumSheet);
        this.notifySheetGenerated(newSumSheet);
        
        return newSumSheet;
    }

    private void setCurrentSumSheet(SummarySheet newSumSheet) {
        this.currentSummarySheet = newSumSheet;
    }

    public SummarySheet getSummarySheet(SummarySheet summarySheet) throws UseCaseLogicException{
        User user = UserManager.getCurrentUser();
        if(!summarySheet.isOwner(user))
            throw new UseCaseLogicException();
        
        return this.currentSummarySheet;
    }

    public void insertTask(Recipe rec){
        KitchenTask t = new KitchenTask(rec);
        
    }

    public void moveTask(KitchenTask t, int pos) throws UseCaseLogicException{
        ArrayList<KitchenTask> tasks = currentSummarySheet.getTasks();
        if(pos < 0 || pos > tasks.size())
            throw new UseCaseLogicException();

        // if(!tasks.contains(t))
        currentSummarySheet.moveTask(t, pos);
        this.notifyTaskRearrangered(currentSummarySheet);
    }

    private void notifyTaskRearrangered(SummarySheet summarySheet) {
        for(TaskEventReceiver er : eventReceivers){
            er.updateTasksRearranged(summarySheet);
        }
    }

    public ArrayList<Shift> getShiftBoard(){
        return null;
    }

    public void assignKitchenTask(KitchenTask t, Shift s, User c, int time, int qty){

    }

    private void notifySheetGenerated(SummarySheet newSumSheet){
        for(TaskEventReceiver er : eventReceivers)
            er.updateSheetGenerated(newSumSheet);
    }
//TODO : da finire 
    private void notifyKitchenTaskAdded(KitchenTask t){
        for(TaskEventReceiver er : eventReceivers)
            er.upd(t);    
    }
}
