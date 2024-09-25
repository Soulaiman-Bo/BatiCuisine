package Config;

import Presentation.*;
import Services.*;
import Utils.ConsolePrinter;
import Utils.InputValidator;
import repositories.Client.ClientRepository;
import repositories.Client.ClientRepositoryImpl;
import repositories.Devis.DevisRepository;
import repositories.Devis.DevisRepositoryImpl;
import repositories.MainDoeuvre.MainDoeuvreRepository;
import repositories.MainDoeuvre.MainDoeuvreRepositoryImpl;
import repositories.Materiaux.MateriauxRepository;
import repositories.Materiaux.MateriauxRepositoryImpl;
import repositories.Projet.ProjetRepository;
import repositories.Projet.ProjetRepositoryImpl;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DIBootstrap {
    private Map<Class<?>, Object> instances = new HashMap<>();

    public <T> void register(Class<T> type, T instance) {
        instances.put(type, instance);
    }

    @SuppressWarnings("unchecked")
    public <T> T resolve(Class<T> type) {
        Object instance = instances.get(type);
        if (instance != null) {
            return (T) instance;
        }

        Constructor<?>[] constructors = type.getConstructors();
        for (Constructor<?> constructor : constructors) {
            Class<?>[] paramTypes = constructor.getParameterTypes();
            Object[] params = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++) {
                params[i] = resolve(paramTypes[i]);
            }
            try {
                instance = constructor.newInstance(params);
                register(type, (T) instance);
                return (T) instance;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        throw new RuntimeException("Unable to resolve type: " + type.getName());
    }

    static public DIBootstrap start(){
        DIBootstrap bootstrap = new DIBootstrap();

        // register utils
        bootstrap.register(Scanner.class, new Scanner(System.in));
        bootstrap.register(InputValidator.class, new InputValidator());
        bootstrap.register(ConsolePrinter.class, new ConsolePrinter());

        // register repositories
        bootstrap.register(ClientRepository.class, new ClientRepositoryImpl());
        bootstrap.register(DevisRepository.class, new DevisRepositoryImpl());
        bootstrap.register(MainDoeuvreRepository.class, new MainDoeuvreRepositoryImpl());
        bootstrap.register(MateriauxRepository.class, new MateriauxRepositoryImpl());
        bootstrap.register(ProjetRepository.class, new ProjetRepositoryImpl());

        // register Services
        bootstrap.register(ProjetService.class, new ProjetService(
                bootstrap.resolve(ProjetRepository.class),
                bootstrap.resolve(MateriauxService.class),
                bootstrap.resolve(MainDœuvreService.class)
        ));

        bootstrap.register(ClientService.class, new ClientService(
                bootstrap.resolve(ClientRepository.class)
        ));

        bootstrap.register(MainDœuvreService.class, new MainDœuvreService(
                bootstrap.resolve(MainDoeuvreRepository.class)
        ));

        bootstrap.register(MateriauxService.class, new MateriauxService(
                bootstrap.resolve(MateriauxRepository.class)
        ));

        bootstrap.register(DevisService.class, new DevisService(
                bootstrap.resolve(DevisRepository.class)
        ));

        // register View
        bootstrap.register(ClientView.class, new ClientView(
                bootstrap.resolve(Scanner.class),
                bootstrap.resolve(InputValidator.class),
                bootstrap.resolve(ClientService.class),
                bootstrap.resolve(DevisService.class),
                bootstrap.resolve(ProjetService.class),
                bootstrap.resolve(ConsolePrinter.class)
        ));

        bootstrap.register(ComponentsView.class, new ComponentsView(
                bootstrap.resolve(Scanner.class),
                bootstrap.resolve(InputValidator.class)
        ));

        bootstrap.register(DevisView.class, new DevisView(
                bootstrap.resolve(Scanner.class),
                bootstrap.resolve(InputValidator.class),
                bootstrap.resolve(ConsolePrinter.class),
                bootstrap.resolve(DevisService.class)
        ));

        bootstrap.register(ProjectView.class, new ProjectView(
                bootstrap.resolve(Scanner.class),
                bootstrap.resolve(InputValidator.class),
                bootstrap.resolve(ConsolePrinter.class),
                bootstrap.resolve(ClientService.class),
                bootstrap.resolve(ProjetService.class),
                bootstrap.resolve(ComponentsView.class),
                bootstrap.resolve(DevisView.class)
        ));

        bootstrap.register(MainView.class, new MainView(
                bootstrap.resolve(ProjectView.class),
                bootstrap.resolve(ClientView.class),
                bootstrap.resolve(DevisView.class),
                bootstrap.resolve(ConsolePrinter.class),
                bootstrap.resolve(Scanner.class)
        ));

        return bootstrap;
    }

}
