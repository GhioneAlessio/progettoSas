package catering.persistence;

import catering.businesslogic.kitchentask.KitchenTask;
import catering.businesslogic.kitchentask.KitchenTaskReceiver;
import catering.businesslogic.kitchentask.SummarySheet;

public class KitchenTaskPersistence implements KitchenTaskReceiver {

    @Override
    public void updateSheetGenerated(SummarySheet sheet) {
        SummarySheet.saveNewSummarySheet(sheet);
    }

    @Override
    public void updateKitchenTaskAdded(SummarySheet sheet, KitchenTask task) {
        KitchenTask.saveNewTask(sheet.getId(), task);
    }

    @Override
    public void updateTasksRearranged(SummarySheet sheet) {
        //TODO
        SummarySheet.saveTaskOrder(sheet);
    }

    @Override
    public void updateKitchenTaskAssigned(SummarySheet currentSummarySheet, KitchenTask t) {
        //TODO 
        KitchenTask.saveKitchenTaskAssigned(t);
    }

    public void updateKitchenTaskEdited(SummarySheet currentSummarySheet, KitchenTask task) {
        //TODO check
        KitchenTask.saveKitchenTaskEdited(task);
    }

    @Override
    public void updateTaskDeleted(KitchenTask task) {
        //TODO
        KitchenTask.updateDeleteKitchenTask(task);
    }

    @Override
    public void updateTaskCanceled(KitchenTask task) {
        //TODO
        KitchenTask.updateCancelKitchenTask(task);
    }

}
