package org.example.Class;

import org.example.Utility.Periodicita;

public class Riviste extends Catalogo {
  Periodicita periodicita;


    public Riviste(Long ISBN, String titolo, int annoDiPubblicazione, int numeroPagine, Periodicita periodicita) {
        super(ISBN, titolo, annoDiPubblicazione, numeroPagine);
        this.periodicita = periodicita;

    }

    public Periodicita getPeriodicita() {
        return periodicita;
    }

    public void setPeriodicita(Periodicita periodicita) {
        this.periodicita = periodicita;
    }

    @Override
    public String toString() {
        return "Riviste{" +
                "periodicita=" + periodicita +
                ", ID ='" + getISBN() + '\'' +
                ", Anno di pubblicazione ='" + getAnnoDiPubblicazione() + '\'' +
                ", Numero di pagine ='" + getNumeroPagine() + '\'' +
                ", Titolo ='" +getTitolo() + '\'' +
                '}';
    }
}
