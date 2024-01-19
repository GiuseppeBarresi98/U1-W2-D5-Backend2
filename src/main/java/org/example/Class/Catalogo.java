package org.example.Class;

public class Catalogo {

   private Long ISBN;
   private String titolo;
   private int annoDiPubblicazione;
   private int numeroPagine;

   public Catalogo(Long ISBN,String titolo,int annoDiPubblicazione,int numeroPagine){
       this.ISBN = ISBN;
       this.titolo = titolo;
       this.annoDiPubblicazione = annoDiPubblicazione;
       this.numeroPagine = numeroPagine;
   }

    public String getTitolo() {
        return titolo;
    }

    public int getNumeroPagine() {
        return numeroPagine;
    }

    public Integer getAnnoDiPubblicazione() {
        return annoDiPubblicazione;
    }

    public Long getISBN() {
        return ISBN;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setAnnoDiPubblicazione(int annoDiPubblicazione) {
        this.annoDiPubblicazione = annoDiPubblicazione;
    }

    public void setISBN(Long ISBN) {
        this.ISBN = ISBN;
    }

    public void setNumeroPagine(int numeroPagine) {
        this.numeroPagine = numeroPagine;
    }

    @Override
    public String toString() {
        return "Catalogo{" +
                "ISBN=" + ISBN +
                ", titolo='" + titolo + '\'' +
                ", annoDiPubblicazione=" + annoDiPubblicazione +
                ", numeroPagine=" + numeroPagine +
                '}';
    }


}
