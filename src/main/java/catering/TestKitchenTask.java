package catering;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.kitchentask.SummarySheet;
import catering.businesslogic.menu.Menu;
import catering.businesslogic.shift.Shift;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class TestKitchenTask {
    public static void main(String[] args) {
        try {
            System.out.println("TEST FAKE LOGIN");
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());

            System.out.println("\nTEST GENERATE SUMMARY SHEET");
            
            ObservableList<EventInfo> events = CatERing.getInstance().getEventManager().getEventInfo();
      
            EventInfo event = events.get(0);
            ServiceInfo serviceInfo = event.getServices().get(0);

            SummarySheet sm = CatERing.getInstance().getKitchenTaskManager().generateSummarySheet(event, serviceInfo);
            System.out.println(sm.testString());

            System.out.println("\nTEST INSERT TASK");
            CatERing.getInstance().getKitchenTaskManager().insertTask(null);
          
            System.out.println("\nTEST MOVE TASK");
            CatERing.getInstance().getKitchenTaskManager().moveTask(null, 0);
            
            System.out.println("\nTEST GET SHIFT BOARD");
            ArrayList<Shift> shiftBoard = CatERing.getInstance().getKitchenTaskManager().getShiftBoard();

            System.out.println("\nTEST ASSIGN KITCHEN TASK");
            CatERing.getInstance().getKitchenTaskManager().assignKitchenTask(null, null, null, null, null);

        } catch (UseCaseLogicException e) {
            System.out.println("Errore di logica nello use case");
        }
    }
}
