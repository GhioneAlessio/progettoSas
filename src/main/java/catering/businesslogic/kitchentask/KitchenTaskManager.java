package catering.businesslogic.kitchentask;

import java.security.Provider.Service;
import java.util.ArrayList;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.menu.Menu;
import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.shift.Shift;
import catering.businesslogic.user.User;

public class KitchenTaskManager {
    private ArrayList<TaskEventReceiver> eventReceivers;
    private SummarySheet currentSummarySheet;

    public SummarySheet generateSummarySheet(EventInfo event, ServiceInfo service) throws UseCaseLogicException{
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
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
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if(!summarySheet.isOwner(user))
            throw new UseCaseLogicException();
        
        return this.currentSummarySheet;
    }

    //TODO : completare 
    public void insertTask(Recipe rec) throws UseCaseLogicException{
        if(this.currentSummarySheet == null)
            throw new UseCaseLogicException();
        KitchenTask newTask = new KitchenTask(rec);
        this.currentSummarySheet.addTask(newTask);
        this.notifyKitchenTaskAdded(newTask);
    }

    public void moveTask(KitchenTask t, int pos) throws UseCaseLogicException{
        ArrayList<KitchenTask> tasks = currentSummarySheet.getTasks();
        if(!tasks.contains(t) || pos < 0 || pos > tasks.size())
            throw new UseCaseLogicException();
        
        currentSummarySheet.moveTask(t, pos);
        this.notifyTaskRearrangered(currentSummarySheet);
    }

    private void notifyTaskRearrangered(SummarySheet summarySheet) {
        for(TaskEventReceiver er : eventReceivers){
            er.updateTasksRearranged(summarySheet);
        }
    }

    public ArrayList<Shift> getShiftBoard(){
        return CatERing.getInstance().getShiftManager().getShiftBoard();
    }

    //TODO : da completare
    public void assignKitchenTask(KitchenTask t, Shift s, User c, int time, int qty) throws UseCaseLogicException{
        if(this.currentSummarySheet == null || this.currentSummarySheet.getTasks().contains(t))
            throw new UseCaseLogicException();

        this.currentSummarySheet.assignKitchenTask(t, s, c, time, qty);
    }

    private void notifySheetGenerated(SummarySheet newSumSheet){
        for(TaskEventReceiver er : eventReceivers)
            er.updateSheetGenerated(newSumSheet);
    }

    private void notifyKitchenTaskAdded(KitchenTask newTask){
        for(TaskEventReceiver er : eventReceivers)
            er.updateKitchenTaskAdded(this.currentSummarySheet, newTask);    
    }
}
