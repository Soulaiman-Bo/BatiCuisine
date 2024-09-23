import Config.DIBootstrap;
import Presentation.MainView;
import Utils.DatabaseSeeder;
import Utils.InputValidator;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {
        DIBootstrap bootstrap = DIBootstrap.start();

        MainView mainView = bootstrap.resolve(MainView.class);
        InputValidator inputValidator = bootstrap.resolve(InputValidator.class);

        String isChoice = inputValidator.validateYesNo(" ==> do you want a Fresh Database? ");
        if (isChoice.equals("y")) {
            DatabaseSeeder databaseSeeder = new DatabaseSeeder();
            databaseSeeder.dropAndCreateTables();
        }

        mainView.main();
    }

}