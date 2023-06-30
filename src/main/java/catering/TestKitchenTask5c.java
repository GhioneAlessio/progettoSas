package catering;

import java.util.Optional;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.kitchentask.KitchenTask;
import catering.businesslogic.kitchentask.SummarySheet;
import javafx.collections.ObservableList;

public class TestKitchenTask5c {
    public static void main(String[] args) {
        try {
            System.out.println("TEST FAKE LOGIN");
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());

            System.out.println("\nTEST GENERATE SUMMARY SHEET");
            CatERing.getInstance().getMenuManager().getAllMenus();
            ObservableList<EventInfo> events = CatERing.getInstance().getEventManager().getEventInfo();      
            
            EventInfo event = events.get(0);
            ServiceInfo serviceInfo = event.getServices().get(0);

            SummarySheet sm = CatERing.getInstance().getKitchenTaskManager().generateSummarySheet(event, serviceInfo);
            System.out.println(sm.testString());
            
            KitchenTask task = CatERing.getInstance().getKitchenTaskManager().getSummarySheet(sm).getTasks().get(0);

            System.out.println("\nTEST ASSIGN KITCHEN TASK");
            CatERing.getInstance().getKitchenTaskManager().assignKitchenTask(task, Optional.ofNullable(null), Optional.ofNullable(null), Optional.ofNullable(2), Optional.of("1kg"));
            System.out.println(sm.testString());

            System.out.println("\nTEST CANCEL TASK");
            CatERing.getInstance().getKitchenTaskManager().cancelKitchenTask(task);
            System.out.println(sm.testString());
        } catch (UseCaseLogicException e) {
            System.out.println(e.getMessage());
        }
    }
}
