package catering;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.kitchentask.KitchenTask;
import catering.businesslogic.kitchentask.SummarySheet;
import catering.persistence.PersistenceManager;
import javafx.collections.ObservableList;

import java.util.Optional;

public class TestKitchenTask5a {
    
    public static void main(String[] args) {
        try {
            PersistenceManager.resetDb();


            // System.out.println("TEST FAKE LOGIN");
            // CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            // System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());

            // System.out.println("\nTEST GENERATE SUMMARY SHEET");
            // CatERing.getInstance().getMenuManager().getAllMenus();
            // ObservableList<EventInfo> events = CatERing.getInstance().getEventManager().getEventInfo();      
            
            // EventInfo event = events.get(0);
            // ServiceInfo serviceInfo = event.getServices().get(0);

            // SummarySheet sm = CatERing.getInstance().getKitchenTaskManager().generateSummarySheet(event, serviceInfo);
            // System.out.println(sm.testString());
            
            // KitchenTask task = CatERing.getInstance().getKitchenTaskManager().getSummarySheet(sm).getTasks().get(0);

            // System.out.println("\nTEST ASSIGN KITCHEN TASK");
            // CatERing.getInstance().getKitchenTaskManager().assignKitchenTask(task, Optional.ofNullable(null), Optional.ofNullable(null), Optional.ofNullable(2), Optional.of("1kg"));
            // System.out.println(sm.testString());

            CatERing.getInstance().getUserManager().fakeLogin("Tony");
            System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());

            System.out.println("\nTEST GET SUMMARY SHEET");
            ObservableList<SummarySheet> smList = SummarySheet.loadAllSheets();
            SummarySheet sm = CatERing.getInstance().getKitchenTaskManager().getSummarySheet(smList.get(0));
            System.out.println(sm.testString());

            KitchenTask task = CatERing.getInstance().getKitchenTaskManager().getSummarySheet(sm).getTasks().get(0);

            System.out.println("\nTEST EDIT TASK");
            CatERing.getInstance().getKitchenTaskManager().editTask(task, Optional.of(1), Optional.of("500g"), Optional.of(true));
            System.out.println(sm.testString());
        } catch (UseCaseLogicException e) {
            System.out.println(e.getMessage());
        }
    }
}
