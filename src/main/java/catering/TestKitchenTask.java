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

public class TestKitchenTask {
    public static void main(String[] args) {
        try {
            System.out.println("TEST FAKE LOGIN");
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());

            System.out.println("\nTEST GENERATE SUMMARY SHEET");
            SummarySheet sm = CatERing.getInstance().getKitchenTaskManager().generateSummarySheet(null, null);

            System.out.println("\nTEST INSERT TASK");
            CatERing.getInstance().getKitchenTaskManager().insertTask(null);
          
            System.out.println("\nTEST MOVE TASK");
            
            System.out.println("\nTEST GET SHIFT BOARD");
            
            System.out.println("\nTEST ASSIGN KITCHEN TASK");

        } catch (UseCaseLogicException e) {
            System.out.println("Errore di logica nello use case");
        }
    }
}
