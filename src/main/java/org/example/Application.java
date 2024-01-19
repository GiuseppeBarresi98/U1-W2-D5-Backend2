package org.example;

import com.github.javafaker.Faker;
import org.apache.commons.io.FileUtils;
import org.example.Class.Catalogo;
import org.example.Class.Libri;
import org.example.Class.Riviste;
import org.example.Utility.Periodicita;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.example.Utils.Utils.getRandomEnum;

public class Application {


    public static void main(String[] args) throws IOException {
        Faker faker = new Faker(Locale.ITALY);




        Supplier<Long> numeroRandom = () -> {
            Random random = new Random();
            return random.nextLong(1000, 5000);
        };

        Supplier<Libri> generaLibro = () -> {
            return new Libri(
                    numeroRandom.get(),
                    faker.book().title(),
                    faker.number().numberBetween(1000, 2024),
                    faker.number().numberBetween(50, 500),
                    faker.book().author(),
                    faker.book().genre());

        };

        Supplier<Riviste> generaRivista = () -> new Riviste(
                numeroRandom.get(),
                faker.book().title(),
                faker.number().numberBetween(1000, 2024),
                faker.number().numberBetween(50, 500),
                getRandomEnum(Periodicita.class)
        );

        List<Catalogo> catalogo = new ArrayList<>();



        aggiungiLibroAlCatalogo(catalogo, generaLibro);
        aggiungiLibroAlCatalogo(catalogo, generaLibro);
        aggiungiRivistaAlCatalogo(catalogo, generaRivista);

        salvaSuDisco(catalogo);

        Scanner scanner = new Scanner(System.in);
        catalogo.forEach(System.out::println);

        System.out.println("Iserisci l'ID dell'elemento che vuoi cancellare: ");
        Long input = Long.parseLong(scanner.nextLine());
        eliminaElementoPerId(catalogo,input);
        catalogo.forEach(System.out::println);


        System.out.println("Inserisci l'ID dell'elemento da cercarlo: " );
        Long inputChiave = Long.parseLong(scanner.nextLine());
        System.out.println(ricercaPerId(catalogo,inputChiave));

        System.out.println("Riecco la lista completa: ");
        catalogo.forEach(System.out::println);

        System.out.println("Inserisci l'anno di pubblicazione per cercare l'elemento: ");
        Integer inputAnno = Integer.parseInt(scanner.nextLine());
        System.out.println(ricarcaPerAnno(catalogo,inputAnno));

        System.out.println("Riecco la lista completa: ");
        catalogo.forEach(System.out::println);

        System.out.println("Inserisci l'autore per cercare l'elemento: ");
        String inputAutore = scanner.nextLine();
        System.out.println(ricercaPerAutore(catalogo,inputAutore));














    }


    public static void aggiungiLibroAlCatalogo(List<Catalogo> catalogo, Supplier<Libri> generaLibro) {
        Libri nuovoLibro = generaLibro.get();
        catalogo.add(nuovoLibro);
    }

    public static void aggiungiRivistaAlCatalogo(List<Catalogo> catalogo, Supplier<Riviste> generaRivista) {
        Riviste nuovaRivista = generaRivista.get();
        catalogo.add(nuovaRivista);
    }

    public static void eliminaElementoPerId(List<Catalogo> catalogo, Long ISBN) {
        catalogo.removeIf(elemento -> {
            {
                if (elemento instanceof Libri) {
                    return ((Libri) elemento).getISBN().equals(ISBN);
                } else if (elemento instanceof Riviste) {
                    return ((Riviste) elemento).getISBN().equals(ISBN);
                }
                return false;
            }
        });
    }

    public static List<Catalogo> ricercaPerId(List<Catalogo> catalogo, Long ISBN) {
         List<Catalogo> risultato = catalogo.stream().filter(elemento -> elemento.getISBN().equals(ISBN)).collect(Collectors.toList());
            return risultato;
    }

    public static List<Catalogo> ricarcaPerAnno(List<Catalogo> catalogo,Integer annoDiPubblicazione){
        return catalogo.stream()
                .filter(elemento -> elemento.getAnnoDiPubblicazione().equals(annoDiPubblicazione))
                .collect(Collectors.toList());
    }

    public static List<Catalogo> ricercaPerAutore(List<Catalogo> catalogo,String autore){
        return catalogo.stream().filter(element -> element instanceof Libri && ((Libri) element).getAutore().equals(autore)).collect(Collectors.toList());

    }
   static Logger logger = LoggerFactory.getLogger(Application.class);
    public static void salvaSuDisco(List<Catalogo> catalogo) throws IOException {
        StringBuilder toWrite = new StringBuilder();

        for (Catalogo elemento : catalogo) {
            StringBuilder str = new StringBuilder();
            if (elemento instanceof Libri) {
                str.append(((Libri) elemento).getAutore()).append(((Libri) elemento).getGenere());
            }
            ;
            if (elemento instanceof Riviste) {
                str.append(((Riviste) elemento).getPeriodicita());
            }

            toWrite.append(elemento.getISBN())
                    .append("@").append(elemento.getTitolo()).
                    append("@").append(elemento.getAnnoDiPubblicazione()).append("@")
                    .append(elemento.getNumeroPagine()).append("@").append(str).append("#");
        }

        try {
            File file = new File("prova.txt");
            FileUtils.writeStringToFile(file, toWrite.toString(), "UTF-8");

        } catch (IOException e) {
            logger.error("Errore nella scrittura del file", e);

        }



        }
    }





