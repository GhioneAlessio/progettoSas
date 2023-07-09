package catering;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.kitchentask.KitchenTask;
import catering.businesslogic.kitchentask.SummarySheet;
import catering.persistence.PersistenceManager;
import javafx.collections.ObservableList;

import java.util.Optional;

public class TestKitchenTask5a {

    public static void main(String[] args) {
        try {
            PersistenceManager.resetDb();

            CatERing.getInstance().getUserManager().fakeLogin("Tony");
            System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());

            System.out.println("\nTEST GET SUMMARY SHEET");
            ObservableList<SummarySheet> smList = SummarySheet.loadAllSheets();
            SummarySheet sm = CatERing.getInstance().getKitchenTaskManager().getSummarySheet(smList.get(0));
            System.out.println(sm.testString());

            KitchenTask task = CatERing.getInstance().getKitchenTaskManager().getSummarySheet(sm).getTasks().get(0);

            System.out.println("\nTEST EDIT TASK");
            CatERing.getInstance().getKitchenTaskManager().editTask(task, Optional.of(1), Optional.of("500g"),
                    Optional.ofNullable(null));
            System.out.println(sm.testString());
        } catch (UseCaseLogicException e) {
            System.out.println(e.getMessage());
        }
    }
}
