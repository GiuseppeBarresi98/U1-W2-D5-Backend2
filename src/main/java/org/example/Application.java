package org.example;

import com.github.javafaker.Faker;
import org.apache.commons.io.FileUtils;
import org.example.Class.Catalogo;
import org.example.Class.Libri;
import org.example.Class.Riviste;
import org.example.Utility.Periodicita;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
        boolean inputValido = false;
      while(!inputValido) {
          try {
              Long input = Long.parseLong(scanner.nextLine());
              eliminaElementoPerId(catalogo, input);
              catalogo.forEach(System.out::println);
              inputValido = true;
          } catch (NumberFormatException e) {
              logger.error("Hai inserito una stringa e non un numero");
              System.out.println("Iserisci l'ID dell'elemento che vuoi cancellare: ");

          }
      }




        System.out.println("Inserisci l'ID dell'elemento che vuoi cercare: " );
        boolean inputValidoRicerca = false;
      while (!inputValidoRicerca) {
          try {
              Long inputChiave = Long.parseLong(scanner.nextLine());
              System.out.println(ricercaPerId(catalogo, inputChiave));
              inputValidoRicerca = true;

          } catch (NumberFormatException e){
              logger.error("Hai inserito una stringa e non un numero");
              System.out.println("Inserisci l'ID dell'elemento che vuoi cercare: " );
          }
      }

        System.out.println("Riecco la lista completa: ");
        catalogo.forEach(System.out::println);

        System.out.println("Inserisci l'anno di pubblicazione per cercare l'elemento: ");
        boolean inputValidoAnno = false;
        while (!inputValidoAnno) {
            try {
                Integer inputAnno = Integer.parseInt(scanner.nextLine().trim());
                List<Catalogo> risultato = ricarcaPerAnno(catalogo, inputAnno);
                System.out.println(risultato);
                inputValidoAnno = true;

            } catch (NumberFormatException e) {
                logger.error("Hai inserito una stringa e non un numero");
                System.out.println("Inserisci l'anno di pubblicazione valido per cercare l'elemento: ");
            }
        }



        System.out.println("Riecco la lista completa: ");
        catalogo.forEach(System.out::println);

        System.out.println("Inserisci l'autore per cercare l'elemento: ");
        boolean inputValidoAutore = false;

        while (!inputValidoAutore) {
            try {
                String inputAutore = scanner.nextLine().trim();
                if (inputAutore.isEmpty()) {
                    System.out.println("Inserisci un nome valido per l'autore.");
                } else {
                    List<Catalogo> ricercaAutore = ricercaPerAutore(catalogo, inputAutore);

                    if (ricercaAutore.isEmpty()) {
                        System.out.println("Risultato non trovato. Inserisci un nuovo nome:");
                        String nuovaRisposta = scanner.nextLine().trim();
                        ricercaAutore = ricercaPerAutore(catalogo, nuovaRisposta);
                        System.out.println(ricercaAutore);
                    } else {
                        System.out.println(ricercaAutore);
                        inputValidoAutore = true;
                    }
                }
            } catch (Exception e) {
                logger.error("Errore nell'input dell'autore: " + e.getMessage());
                System.out.println("Inserisci l'autore per cercare l'elemento: ");
            }
        }

    }


    public static void aggiungiLibroAlCatalogo(List<Catalogo> catalogo, Supplier<Libri> generaLibro) {
        Libri nuovoLibro = generaLibro.get();
        catalogo.add(nuovoLibro);
    }

    public static void aggiungiRivistaAlCatalogo(List<Catalogo> catalogo, Supplier<Riviste> generaRivista) {
        Riviste nuovaRivista = generaRivista.get();
        catalogo.add(nuovaRivista);
    }

    public static void eliminaElementoPerId(List<Catalogo> catalogo, Long ISBN) throws NumberFormatException {
        catalogo.removeIf(elemento -> {
                if (elemento instanceof Libri) {
                    return ((Libri) elemento).getISBN().equals(ISBN);
                } else if (elemento instanceof Riviste) {
                    return ((Riviste) elemento).getISBN().equals(ISBN);
                }

                else {
                    System.out.println("Elemento non trovato");
                };
                return false;
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

        String file1 = "prova.txt";
        File file;
        try {
            file = new File(file1);
            FileUtils.writeStringToFile(file, toWrite.toString(), "UTF-8");

        } catch (IOException e) {
            logger.error("Errore nella scrittura del file", e);

        }





    }


    public static List<Catalogo> caricaDaDisco(String file) throws IOException {
        List<Catalogo> archivio = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split("#");
                for (String part : parts) {

                    String[] subParts = part.split("@");

                    if (subParts.length >= 4) {
                        Long isbn = Long.parseLong(subParts[0]);
                        String titolo = subParts[1];
                        Integer annoPubblicazione = Integer.parseInt(subParts[2]);
                        Integer numeroPagine = Integer.parseInt(subParts[3]);

                        if (part.contains("genere")) {

                            String autoreGenere = subParts[4];
                            String[] autoreGenereParts = autoreGenere.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
                            String autore = autoreGenereParts[0];
                            String genere = autoreGenereParts[1];

                            Libri libro = new Libri(isbn, titolo, annoPubblicazione, numeroPagine, autore, genere);
                            archivio.add(libro);
                        } else if (part.contains("periodicita")) {

                            Periodicita periodicita = Periodicita.valueOf(subParts[4]);

                            Riviste rivista = new Riviste(isbn, titolo, annoPubblicazione, numeroPagine, periodicita);
                            archivio.add(rivista);
                        }
                    }
                }
            }
        }

        return archivio;
    }
}





