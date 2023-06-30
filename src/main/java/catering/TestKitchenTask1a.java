package catering;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.kitchentask.SummarySheet;
import catering.businesslogic.menu.Menu;
import catering.businesslogic.menu.Section;
import catering.businesslogic.recipe.Recipe;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.Map;

public class TestKitchenTask1a {
 
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

            // SummarySheet sm2 = recupero dal db


        } catch (UseCaseLogicException e) {
            System.out.println(e.getMessage());
        }
    }
}
