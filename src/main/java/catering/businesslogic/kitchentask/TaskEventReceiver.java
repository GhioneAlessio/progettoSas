package catering.businesslogic.kitchentask;

public interface TaskEventReceiver {
    
    public void updateSheetGenerated(SummarySheet sheet);
    public void updateKitchenTaskAdded(SummarySheet sheet, KitchenTask task);
    public void updateTasksRearranged(SummarySheet sheet);
    public void updateTasksAssigned(SummarySheet sheet);
    public void updateKitchenTaskEdited(SummarySheet currentSummarySheet, KitchenTask t);
    public void updateKitchenTaskAssigned(SummarySheet currentSummarySheet, KitchenTask t);
}