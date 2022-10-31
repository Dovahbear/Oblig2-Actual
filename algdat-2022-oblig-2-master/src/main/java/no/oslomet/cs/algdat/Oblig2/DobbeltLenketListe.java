package no.oslomet.cs.algdat.Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.*;


public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     *
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    public DobbeltLenketListe() {}
        //Oppgave 1 b)
    public DobbeltLenketListe(T[] a) {
        Objects.requireNonNull(a, "Tabellen a er null!");
        Node<T> node = null;
        for (T t : a) {                                                 //Går over tabellen a, et steg av gangen.
            if (t != null) {
                if (hode == null) {                                     //hvis listen er tom, så legges verdien inn, og hode settes til
                    hode = new Node<>(t);                               //Lager en ny node med verdien t fra a, og setter den inn i listen.
                    node = hode;
                } else {
                    Node<T> neste = new Node<>(t, node, null);       //lager en ny node som er den neste i rekken, setter verdien til verdien t, den forrige til noden node (fra forrige steg) og neste til null
                    node.neste = neste;                                   //setter noden fra forrige stegs neste til å være verdien til den nåværende noden
                    node = neste;                                         //Oppdaterer node til å være neste (for neste steg)
                }
                antall++;                                               //øker antall med 1
            }
        }
        hale = node;                                                    //når vi så er helt ferdige, så setter vi at hale = node, i stedenfor å gjøre det på hvert eneste steg.
    }
        //oppgave 3b)
    public Liste<T> subliste(int fra, int til) {
        fratilKontroll(antall, fra, til);                               //kontrollerer at begge verdiene eksisterer innenfor antall noder i listen
        DobbeltLenketListe<T> liste = new DobbeltLenketListe<>();       //lager en ny liste
        if (fra == til) {                                               //Hvis fra == til, hvil det ikke legges inn verdier, da det er fra-og-med, men ikke til-og-med
            return liste;                                               //tom liste
        }

        for (int i = fra; i < til; i++) {                               //går deretter over, fra fra til til
            liste.leggInn(this.hent(i));                                //Og legger inn i vår nye liste ved hjelp av leggInn() metoden.
        }

        return liste;                                                   //Og returnerer listen.
    }

    private void fratilKontroll(int antall, int fra, int til) {
        if (fra < 0) {                                                  // fra er negativ
            throw new IndexOutOfBoundsException("fra(" + fra + ") er negativ!");
        }

        if (til > antall) {                                              //til er større enn antall, dermed utenfor listen
            throw new IndexOutOfBoundsException("til(" + til + ") > antall(" + antall + ")");
        }

        if (fra > til) {                                                 // fra er større enn til, som gjør for en interessant opplevelse, men vil kræsje koden.
            throw new IllegalArgumentException("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
        }
    }



    //  Oppgave: 1

    @Override
    public int antall() {
        return antall;
    }

    @Override
    public boolean tom() {
        return hode == null;                //Hvis hodet er null, er listen tom, hvis listen er tom, så er tom() true.

        //Kunne også brukt return antall == 0.
    }

    @Override
    //Oppgave 2b)
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Null-verdier er ikke tillatt!");

        if (antall == 0) {                                              //Hvis antall == 0, så er listen tom, og vi lager en node som er lik hode, og hale
            hode = hale = new Node<>(verdi, null,null);
        } else {
            hale.neste = new Node<>(verdi,hale,null);             //Hvis det allerede er i listen, så lager vi en ny node med verdi = verdi, node.forrige = hale, og node.neste = null
            hale = hale.neste;                                          //Vi oppdaterer hale til å være neste i rekka
        }
        antall++;                                                       //Antall oppdateres
        endringer++;                                                    //endringer oppdateres

        return true;                                                    //returnerer true hvis gjennomført.
    }
       //Oppgave 3a)
    public Node<T> finnNode(int indeks) {
        int mid = antall/2;
        Node<T> node = null;                                        //lager en node som skal holde resultatet

        if (indeks < mid) {                                         //søker fra hode mot hæyre
            Node<T> temp = hode;                                    //en midlertidig node som skal brukes til å søke gjennom listen
            int count = 0;                                          //Da vi søker fra venstre mot høyre, så starter vi på 0. indeks

            while (temp != null) {                                  //Vi går så over arrayet
                if (count == indeks) {                              //Når vi har nådd vår ønskete indeks, så;
                    node = temp;                                    //setter vi node til å være verdt temp
                    break;                                          //og bryter
                }

                temp = temp.neste;                                  //Ellers så setter vi den til neste, og oppdaterer count
                count++;
            }
        } else {                                                    //søker fra hale mot venstre, som er det samme som mot høyre, bare andre veien.
            Node<T> temp = hale;
            int count = antall-1;

            while (temp != null) {
                if (count == indeks) {
                    node = temp;
                    break;
                }

                temp = temp.forrige;
                count--;
            }
        }

        return node;                                                //Når alt er gjort, så returneres noden.
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        Objects.requireNonNull(verdi, "Null-verdier er ikke tillatt!");
        indeksKontroll(indeks, true);                                       //Gjør sjekken at indeksen er lovlig.

        if (indeks == 0) {                                                          //Skal oppdatere første node
            if (antall == 0) {                                                      //Hvis den er tom, så legger vi inn som første og siste, med hode og hale
                hale = hode = new Node<>(verdi);
            } else {                                                                //ellers, så legges den inn i første posisjon, og setter forrige til null, neste som hode, oppdaterer hode.forrige til å være lik seg selv, og setter så hode til å være lik seg selv.
                hode = hode.forrige = new Node<>(verdi, null, hode);
            }
        } else if (indeks == antall) {
            hale = hale.neste = new Node<>(verdi, hale, null);                  //Setter inn bakerst, samme greia
        } else {
            Node<T> node = hode;                                                      //Ellers, så går vi over lista til vi kommer til ønskede plass, og skviser oss inn
            for (int i = 1; i < indeks; ++i) {
                node = node.neste;
            }
            node.neste = node.neste.forrige = new Node<>(verdi, node, node.neste);     //Oppdaterer så alle neste og forrige verdier stemmer
        }
        antall++;                                                                       //øker antall og endringer.
        endringer++;
    }


    @Override
    public boolean inneholder(T verdi) {
        return indeksTil(verdi) != -1;              //Så sant indekstTil() ikke returnerer -1, så får vi true, og den kjører som ei kule.
    }

    @Override
    public T hent(int indeks) {
        this.indeksKontroll(indeks, false);
        return finnNode(indeks).verdi;
    }

    @Override
    public int indeksTil(T verdi) {
        if (verdi == null) {                        //Hvis verdien er null, så returneres -1 som oppgaveteksten beskriver.
            return -1;
        }

        Node<T> temp = hode;                        //node som skal gå over listen
        int indeks = 0;                            //counter som skal gi oss indeksen

        while (temp != null) {                      //Går mot høyre til temp er null, dermed ute av listen
            if (verdi.equals(temp.verdi)) {         //hvis verdien i temp == verdien vi leter etter
                return indeks;                      //returneres indeksen til den noden
            }

            temp = temp.neste;                      //ellers hopper vi et hakk mot høyre
            indeks++;                               //Og øker hvilken indeks vi er på
        }

        return -1;                                  //Hvis T verdi ikke er i listen, blir -1 returnert, per oppgavetekst.
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {

        Objects.requireNonNull(nyverdi, "Null-verdier er ikke tillatt!");
        this.indeksKontroll(indeks, false);

        Node<T> node = finnNode(indeks);                        //bruker finnNode() for å finne den riktige noden som skal oppdateres
        T gamleVerdi = node.verdi;                             //lagrer gamle verdien
        node.verdi = nyverdi;                                   //setter nye verdien til å være ny verdi
        endringer++;                                            //oppdaterer endringer

        return gamleVerdi;                                      //returnerer verdien som er byttet ut.

    }

    @Override
    public boolean fjern(T verdi) {
        if (verdi == null) {
            return false;                               //Hvis verdien er null, så kan den ikke fjernes (da den ikke finnes) og vi får false
        }

        Node<T> node = hode;                            //hjelpenode

        while (node != null) {                          //går over hele listen, og ser om vi finner en node med verdi lik (T verdi)
            if (verdi.equals(node.verdi)) {
                Node<T> forrige = node.forrige;         //Vi settet enda en hjelpenode til å være lik hjelpenodens forrige verdi (i tilfelle første plass, null)

                if (antall == 1) {                      //Med bare 1 verdi i listen, er den nå tom, og vi setter hode og hale til null
                    hale = hode = null;
                } else if (node == hale) {              //Hvis noden vi kom til er halen, så setter vi da forrige til å være halen, og forrige.neste til å være null (da den er halen)
                    hale = forrige;
                    forrige.neste = null;
                } else {
                    node.neste.forrige = forrige;       //Ellers så oppdateres den neste som kommer etter node til å referere til forrige istedenfor.

                    if (node == hode) {                 //hvis noden vi skal fjerne ligger i hodet, så oppdateres det
                        hode = node.neste;
                    } else {
                        forrige.neste = node.neste;
                    }
                }

                antall--;                               //antall går ned, endringer går opp, og vi returnerer true
                endringer++;

                return true;
            }
            node = node.neste;                          //vi går stadig over listen, et steg av gangen.

        }

        return false;                                   //Hvis vi ikke returnerer true (fordi vi fjernet en verdi) så returneres false.
    }

    @Override
    public T fjern(int indeks) {
        indeksKontroll(indeks,false);           //Sjekker at indeks ikke er utenfor

        T temp;                                         //lager em midlertidig verdi

        if (indeks == 0) {                              //Hvis det er indeks 0 som skal slettes, så er det hodet som skal bort
            temp = hode.verdi;                          //verdien som fjernes er hodet

            if (antall == 1) {                          //hvis listen bare er 1 lang, så er både hode og hale == null (tom)
                hale = hode = null;
            } else {                                    //ellers, er nå hode=hode.neste, og dermed (nye)hode.forrige = null.
                hode = hode.neste;
                hode.forrige = null;
            }
        } else {
            Node<T> p = finnNode(indeks -1);     //Hvis ikke, så finner vi hvor noden er i rekka. Og dette er eneste gangen jeg vil bruke p og q som noder, da jeg hater det, men det var greiest nå.
            Node<T> q = p.neste;
            temp = q.verdi;

            if (q == hale) {                            //q = verdien som skal fjernes, hvis det er halen, så setter vi p (noden før q) til å være halen, og oppdaterer
                hale = p;
            } else {                                    //ellers, så endrer vi at q.neste.forrige (som er plassen til q) = p.
                q.neste.forrige = p;
            }

            p.neste = q.neste;                          //Og at p.neste, som før var q, er nå q.neste, så vi hopper effektivt over q.
        }
        antall--;                                       //antall går ned, da vi fjernet et ledd, endringer går opp, og verdien som er fjernet returneres.
        endringer++;

        return temp;
    }

    @Override
    public void nullstill() {
        throw new UnsupportedOperationException();
    }

    //Oppgave 2a)
    @Override
    public String toString() {
        StringBuilder streng = new StringBuilder("[");              //Lager en stringbuilder, med første verdi [

        if (!tom())                                                 //Så sant treet in question ikke er tomt
        {
            Node<T> node = hode;                                    //Så lager vi en node for å iterere over listen
            streng.append(node.verdi);                              //legger inn verdien på den nåværende plassen i listen.
            node = node.neste;                                      //Gjør om til neste
            while (node != null)                                    //Hvis node.neste ikke er null, så er listen lenger enn 1, og vi legger til de verdiene også
            {
                streng.append(',').append(' ').append(node.verdi);  //Legger til et komma, og et mellomrom før vi legger til en ny verdi. En annen mulighet er å legge til komma og mellomrom etter et input, for så å fjerne til slutt.
                node = node.neste;                                  //Oppdaterer til vi kommer til slutten av listen.
            }
        }
        streng.append(']');                                         //Avslutter listen med en ] for å få en pen utskrift.
        return streng.toString();                                   //Og returnerer Stringen vi har bygget.
    }

    public String omvendtString() {                                 //Skriver ikke en like omfattende kommentar her, den gjør det samme som toString(), bare fra hale, istedenfor fra hode.
        StringBuilder streng = new StringBuilder();
        streng.append('[');
        if (!tom())
        {
            Node<T> node = hale;
            streng.append(node.verdi);
            node = node.forrige;
            while (node != null)
            {
                streng.append(',').append(' ').append(node.verdi);
                node = node.forrige;
            }
        }
        streng.append(']');
        return streng.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks) {
        indeksKontroll(indeks, false);
        return new DobbeltLenketListeIterator(indeks);
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks) {
            denne = finnNode(indeks);
            fjernOK = false;
            iteratorendringer = endringer;
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {
            if (iteratorendringer != endringer) {
                throw new ConcurrentModificationException();
            }
            if (!hasNext()){
                throw new NoSuchElementException();
            }
            fjernOK = true;
            T nodeVerdi = denne.verdi;
            denne = denne.neste;
            return nodeVerdi;
        }


        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    } // class DobbeltLenketListeIterator

    /*public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }*/



} // class DobbeltLenketListe
