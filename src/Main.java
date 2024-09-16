import Entities.Materiaux;
import Enums.TypeComposant;
import Services.MateriauxService;
import repositories.Materiaux.MateriauxRepository;
import repositories.Materiaux.MateriauxRepositoryImpl;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        MateriauxRepository materiauxRepository = new MateriauxRepositoryImpl();
        MateriauxService materiauxService = new MateriauxService(materiauxRepository);

        Materiaux m = materiauxService.createMateriaux(new Materiaux(
                "khlaid",
                2.2,
                null,
                TypeComposant.MATERIEL,
                33.3,
                53.32,
                76.3,
                87.
        ));

        System.out.println(m);

    }
}