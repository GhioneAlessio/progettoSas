package catering.businesslogic.kitchentask;

import java.sql.Time;
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
        //TODO : (event.getChef() != user) || service.getMenu() == null, questo case messo nell'if da problemi non essendo il nostro caso d'uso e' scomodo
        if(event == null || service == null || !user.isChef() || !event.providesService(service) || (event.getChef() != user) || service.getMenu() == null)
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
        for(KitchenTaskReceiver er : eventReceivers){
            er.updateTasksRearranged(summarySheet);
        }
    }

    public ArrayList<Shift> getShiftBoard(){
        return CatERing.getInstance().getShiftManager().getShiftBoard();
    }

    //TODO : da completare
    public void assignKitchenTask(KitchenTask t, Optional<Shift> s, Optional<User> c, Optional<Integer> time, Optional<String> qty) throws UseCaseLogicException{
        //TODO : !this.currentSummarySheet.getTasks().contains(t) si potrebbe spostare dentro summarySheet, cosi' non si deve 
        //recuperare l'elenco delle task, forse ha piu' senso che sia summarySheet ha controllare direttamente se contiene la task t 
        if(this.currentSummarySheet == null || !this.currentSummarySheet.getTasks().contains(t))
            throw new UseCaseLogicException();

        this.currentSummarySheet.assignKitchenTask(t, s, c, time, qty);

        this.notifyKitchenTaskAssigned(currentSummarySheet, t);
    }

    //TODO: cambiare modifyTask in editTask nell ssd
    public void editTask(KitchenTask t, Optional<Integer> time, Optional<String> qty, Optional<Boolean> completed) throws UseCaseLogicException{
        if(this.currentSummarySheet == null || !this.currentSummarySheet.getTasks().contains(t))
            throw new UseCaseLogicException();

        this.currentSummarySheet.editTask(t, time, qty, completed);
        //TODO: notify non consistenti tra codice e ssd
        this.notifyKitchenTaskEdited(t);
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
        for(KitchenTaskReceiver er : eventReceivers)
            er.updateSheetGenerated(newSumSheet);
    }

    private void notifyKitchenTaskAdded(KitchenTask newTask){
        for(KitchenTaskReceiver er : eventReceivers)
            er.updateKitchenTaskAdded(this.currentSummarySheet, newTask);    
    }

    private void notifyKitchenTaskAssigned(SummarySheet currentSummarySheet2, KitchenTask t) {
        for(KitchenTaskReceiver er : eventReceivers)
            er.updateKitchenTaskAssigned(this.currentSummarySheet, t); 
    }

    private void notifyKitchenTaskEdited(KitchenTask t) {
        for(KitchenTaskReceiver er : eventReceivers)
            er.updateKitchenTaskEdited(this.currentSummarySheet, t); 
    }

    public void addEventReceiver(KitchenTaskReceiver rec) {
        this.eventReceivers.add(rec);
    }

    public void removeEventReceiver(KitchenTaskReceiver rec) {
        this.eventReceivers.remove(rec);
    }
}
