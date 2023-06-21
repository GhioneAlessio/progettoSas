package catering.businesslogic.kitchentask;

public interface TaskEventReceiver {
    
    public void updateSheetGenerated(SummarySheet sheet);
    public void updateKitchenTaskAdded(SummarySheet sheet, KitchenTask task);
    public void updateTasksRearranged(SummarySheet sheet);
    void updateTasksAssigned(SummarySheet sheet);
}