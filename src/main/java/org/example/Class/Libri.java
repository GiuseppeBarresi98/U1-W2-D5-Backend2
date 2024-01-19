package org.example.Class;

public class Libri extends Catalogo {
    private String autore;
    private String genere;

    public Libri(Long ISBN, String titolo, int annoDiPubblicazione, int numeroPagine,String autore,String genere) {
        super(ISBN, titolo, annoDiPubblicazione, numeroPagine);
        this.autore= autore;
        this.genere = genere;
    }


    public String getAutore() {
        return autore;
    }

    public String getGenere() {
        return genere;
    }
    public void setAutore(String autore) {
        this.autore = autore;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    @Override
    public String toString() {
        return "Libri{" +
                "autore='" + autore + '\'' +
                ", titolo='" + getTitolo() + '\'' +
                ", genere='" + genere + '\'' +
                ", Anno di Pubblicazione ='" + getAnnoDiPubblicazione() + '\'' +
                ", ID ='" + getISBN() + '\'' +
                ", Numero di pagine ='" + getNumeroPagine() + '\'' +


                '}';
    }
}
