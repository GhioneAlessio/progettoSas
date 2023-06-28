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

public class TestKitchenTask5a {
    
    public static void main(String[] args) {
        try {
            /* System.out.println("TEST DATABASE CONNECTION");
            PersistenceManager.testSQLConnection();*/
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());

            // SummarySheet sm = CatERing.getInstance().getKitchenTaskManager().getSummarySheet();
            // Section antipasti = CatERing.getInstance().getMenuManager().defineSection("Antipasti");
            // Section primi = CatERing.getInstance().getMenuManager().defineSection("Primi");
            // Section secondi = CatERing.getInstance().getMenuManager().defineSection("Secondi");

            // ObservableList<Recipe> recipes = CatERing.getInstance().getRecipeManager().getRecipes();
            // CatERing.getInstance().getMenuManager().insertItem(recipes.get(0), antipasti);
            // CatERing.getInstance().getMenuManager().insertItem(recipes.get(1), antipasti);
            // CatERing.getInstance().getMenuManager().insertItem(recipes.get(2), antipasti);
            // CatERing.getInstance().getMenuManager().insertItem(recipes.get(6), secondi);
            // CatERing.getInstance().getMenuManager().insertItem(recipes.get(7), secondi);
            // CatERing.getInstance().getMenuManager().insertItem(recipes.get(3));
            // CatERing.getInstance().getMenuManager().insertItem(recipes.get(4));

            System.out.println("\nTEST MODIFY TASK");
            CatERing.getInstance().getKitchenTaskManager().editTask(null, null, null, null);
            // System.out.println(m.testString());
            
        } catch (UseCaseLogicException e) {
            System.out.println("Errore di logica nello use case");
        }

    }
}
