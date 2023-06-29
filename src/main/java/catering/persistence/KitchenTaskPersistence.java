package catering.persistence;

import catering.businesslogic.kitchentask.KitchenTask;
import catering.businesslogic.kitchentask.KitchenTaskReceiver;
import catering.businesslogic.kitchentask.SummarySheet;

public class KitchenTaskPersistence implements KitchenTaskReceiver{

    @Override
    public void updateSheetGenerated(SummarySheet sheet) {
        // TODO Auto-generated method stub
        System.out.println("ok");
    }

    @Override
    public void updateKitchenTaskAdded(SummarySheet sheet, KitchenTask task) {
        // TODO Auto-generated method stub
        System.out.println("ok");

    }

    @Override
    public void updateTasksRearranged(SummarySheet sheet) {
        // TODO Auto-generated method stub
    }

    @Override
    public void updateTasksAssigned(SummarySheet sheet) {
        // TODO Auto-generated method stub
    }

    @Override
    public void updateKitchenTaskEdited(SummarySheet currentSummarySheet, KitchenTask t) {
        // TODO Auto-generated method stub
    }

    @Override
    public void updateKitchenTaskAssigned(SummarySheet currentSummarySheet, KitchenTask t) {
        // TODO Auto-generated method stub
    }
    
}
