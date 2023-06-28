package catering.businesslogic.kitchentask;

import java.util.ArrayList;
import java.util.Optional;

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
        if(this.currentSummarySheet == null || !this.currentSummarySheet.getTasks().contains(t))
            throw new UseCaseLogicException();
        if(pos < 0 || pos > this.currentSummarySheet.getSummarySheetSize())
            throw new IllegalArgumentException();
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
    public void assignKitchenTask(KitchenTask t, Optional<Shift> s, Optional<User> c, Optional<Integer> time, Optional<Integer> qty) throws UseCaseLogicException{
        //TODO : !this.currentSummarySheet.getTasks().contains(t) si potrebbe spostare dentro summarySheet, cosi' non si deve 
        //recuperare l'elenco delle task, forse ha piu' senso che sia summarySheet ha controllare direttamente se contiene la task t 
        if(this.currentSummarySheet == null || !this.currentSummarySheet.getTasks().contains(t))
            throw new UseCaseLogicException();

        this.currentSummarySheet.assignKitchenTask(t, s, c, time, qty);
    }
//forse non e' corretto, chiedere ai saggi
    public void deleteKitchenTask(KitchenTask task) throws UseCaseLogicException{
        if(this.currentSummarySheet == null || !this.currentSummarySheet.getTasks().contains(task))
            throw new UseCaseLogicException();

        this.currentSummarySheet.deleteKitchenTask(task, "delete");
    }

    private void cancelKitchenTask(KitchenTask task)throws UseCaseLogicException{
        if(this.currentSummarySheet == null || !this.currentSummarySheet.getTasks().contains(task))
            throw new UseCaseLogicException();
        
        this.currentSummarySheet.deleteKitchenTask(task, "cancel");
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
