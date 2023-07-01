package catering;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.kitchentask.SummarySheet;
import javafx.collections.ObservableList;

public class TestKitchenTask1a {
 
    public static void main(String[] args) {
        try {
            System.out.println("TEST FAKE LOGIN");
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());
            ObservableList<SummarySheet> smList = SummarySheet.loadAllSheets();
            SummarySheet sm = CatERing.getInstance().getKitchenTaskManager().getSummarySheet(smList.get(0));

            System.out.println(sm.testString());

        } catch (UseCaseLogicException e) {
            System.out.println(e.getMessage());
        }
    }
}
