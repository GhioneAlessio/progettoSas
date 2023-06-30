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
    private ArrayList<KitchenTaskReceiver> eventReceivers;
    private SummarySheet currentSummarySheet;

    public KitchenTaskManager(){
        this.eventReceivers = new ArrayList<>();
    }

    public SummarySheet generateSummarySheet(EventInfo event, ServiceInfo service) throws UseCaseLogicException{
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if(event == null) 
            throw new UseCaseLogicException("Error event is null");
        if(service == null)
            throw new UseCaseLogicException("Error service is null");
        if(!user.isChef())
            throw new UseCaseLogicException("Error user is not chef");
        if(!event.providesService(service))
            throw new UseCaseLogicException("Error event does not provide service");
        if((event.getChef() != user)) 
            throw new UseCaseLogicException("Error event.chef != user");
        if(service.getMenu() == null)
            throw new UseCaseLogicException("Error service.menu is null");

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
            throw new UseCaseLogicException("CurrentSummarySheet is null");
        if(rec == null)
            throw new UseCaseLogicException("recipe is null");
        KitchenTask newTask = new KitchenTask(rec);
        this.currentSummarySheet.addTask(newTask);
        this.notifyKitchenTaskAdded(this.currentSummarySheet, newTask);
    }

    public void moveTask(KitchenTask t, int pos) throws UseCaseLogicException{
        if(this.currentSummarySheet == null)
            throw new UseCaseLogicException("CurrentSummarySheet is null");
        if(!this.currentSummarySheet.getTasks().contains(t))
            throw new UseCaseLogicException("CurrentSummarySheet does not contain task");
        if(pos < 0 || pos > this.currentSummarySheet.getSummarySheetSize())
            throw new IllegalArgumentException();
        currentSummarySheet.moveTask(t, pos);
        this.notifyTaskRearrangered(currentSummarySheet);
    }

    public ArrayList<Shift> getShiftBoard(){
        return CatERing.getInstance().getShiftManager().getShiftBoard();
    }

    //TODO : da completare
    public void assignKitchenTask(KitchenTask t, Optional<Shift> s, Optional<User> c, Optional<Integer> time, Optional<String> qty) throws UseCaseLogicException{
        if(this.currentSummarySheet == null)
            throw new UseCaseLogicException("CurrentSummarySheet is null");
        if(t == null)
            throw new UseCaseLogicException("task is null");
        if(!this.currentSummarySheet.getTasks().contains(t))
            throw new UseCaseLogicException("CurrentSummarySheet does not contain task");

        this.currentSummarySheet.assignKitchenTask(t, s, c, time, qty);
        this.notifyKitchenTaskAssigned(currentSummarySheet, t);
    }

    public void editTask(KitchenTask t, Optional<Integer> time, Optional<String> qty, Optional<Boolean> completed) throws UseCaseLogicException{
        if(this.currentSummarySheet == null)
            throw new UseCaseLogicException("CurrentSummarySheet is null");
        if(t == null)
            throw new UseCaseLogicException("task is null");
        if(!this.currentSummarySheet.getTasks().contains(t))
            throw new UseCaseLogicException("CurrentSummarySheet does not contain task");

        this.currentSummarySheet.editTask(t, time, qty, completed);
        this.notifyKitchenTaskEdited(this.currentSummarySheet, t);
    }

    //forse non e' corretto, chiedere ai saggi
    public void deleteKitchenTask(KitchenTask t) throws UseCaseLogicException{
        if(this.currentSummarySheet == null)
            throw new UseCaseLogicException("CurrentSummarySheet is null");
        if(t == null)
            throw new UseCaseLogicException("task is null");
        if(!this.currentSummarySheet.getTasks().contains(t))
            throw new UseCaseLogicException("CurrentSummarySheet does not contain task");

        this.currentSummarySheet.deleteKitchenTask(t);
        this.notifyTaskDeleted(t);
    }

    public void cancelKitchenTask(KitchenTask t)throws UseCaseLogicException{
        if(this.currentSummarySheet == null)
            throw new UseCaseLogicException("CurrentSummarySheet is null");
        if(t == null)
            throw new UseCaseLogicException("task is null");
        if(!this.currentSummarySheet.getTasks().contains(t))
            throw new UseCaseLogicException("CurrentSummarySheet does not contain task");
        //TODO : 
        
        this.currentSummarySheet.cancelKitchenTask(t);
        this.notifyTaskCanceled(t);
    }
    private void notifySheetGenerated(SummarySheet newSumSheet){
        for(KitchenTaskReceiver er : eventReceivers)
            er.updateSheetGenerated(newSumSheet);
    }

    private void notifyKitchenTaskAdded(SummarySheet currSummarySheet, KitchenTask newTask){
        for(KitchenTaskReceiver er : eventReceivers)
            er.updateKitchenTaskAdded(currSummarySheet, newTask);    
    }
    
    private void notifyTaskRearrangered(SummarySheet currSummarySheet) {
        for(KitchenTaskReceiver er : eventReceivers){
            er.updateTasksRearranged(currSummarySheet);
        }
    }

    private void notifyKitchenTaskAssigned(SummarySheet currSummarySheet, KitchenTask task) {
        for(KitchenTaskReceiver er : eventReceivers)
            er.updateKitchenTaskAssigned(currSummarySheet, task); 
    }

    private void notifyKitchenTaskEdited(SummarySheet currSummarySheet, KitchenTask task) {
        for(KitchenTaskReceiver er : eventReceivers)
            er.updateKitchenTaskEdited(currSummarySheet, task); 
    }

    private void notifyTaskDeleted(KitchenTask task) {
        for(KitchenTaskReceiver er : eventReceivers)
            er.updateTaskDeleted(task); 
    }

    private void notifyTaskCanceled(KitchenTask task) {
        for(KitchenTaskReceiver er : eventReceivers)
            er.updateTaskCanceled(task); 
    }

    public void addEventReceiver(KitchenTaskReceiver rec) {
        this.eventReceivers.add(rec);
    }

    public void removeEventReceiver(KitchenTaskReceiver rec) {
        this.eventReceivers.remove(rec);
    }
}
