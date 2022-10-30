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

    public DobbeltLenketListe() {
    }

    public DobbeltLenketListe(T[] a) {
        Objects.requireNonNull(a, "Tabellen a er null!");
        Node<T> forrige = null;
        for (T t : a) {
            if (t != null) {
                if (hode == null) {
                    hode = new Node<>(t);
                    forrige = hode;
                } else {
                    Node<T> ny = new Node<>(t, forrige, null);
                    forrige.neste = ny;
                    forrige = ny;
                }
                antall++;
            }
        }
        hale = forrige;
    }

    public Liste<T> subliste(int fra, int til) {
        fratilKontroll(antall, fra, til);
        DobbeltLenketListe<T> ny = new DobbeltLenketListe<>();
        if (fra == til) {
            return ny;
        }

        for (int i = fra; i < til; ++i) {
            ny.leggInn(this.hent(i));
        }

        return ny;
    }

    private void fratilKontroll(int antall, int fra, int til) {
        if (fra < 0) {                                  // fra er negativ
            throw new IndexOutOfBoundsException("fra(" + fra + ") er negativ!");
        }

        if (til > antall) {                           // til er utenfor tabellen
            throw new IndexOutOfBoundsException("til(" + til + ") > antall(" + antall + ")");
        }

        if (fra > til) {                             // fra er større enn til
            throw new IllegalArgumentException("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
        }
    }



    //  Oppgave: 1, 2, 3, 4, 5, 6, 8

    @Override
    public int antall() {
        return antall;
    }

    @Override
    public boolean tom() {
        return hode == null;
    }

    @Override
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Null-verdier er ikke tillatt!");

        if (antall == 0) {
            hode = hale = new Node<T>(verdi);
        } else {
            hale.neste = new Node<T>(verdi);
            hale.neste.forrige = hale;
            hale = hale.neste;
        }
        antall++;
        endringer++;

        return true;
    }

    public Node<T> finnNode(int indeks) {
        int midtIndeks = antall/2;
        Node<T> resultat = null;

        if (indeks < midtIndeks) {
            //søke fra hode til høyre
            Node<T> current = hode;
            int teller = 0;

            while (current != null) {
                if (teller == indeks) {
                    resultat = current;
                    break;
                }

                current = current.neste;
                teller++;
            }
        } else {
            //søke fra hale og gå mot venstre
            Node<T> current = hale;
            int teller = antall-1;

            while (current != null) {
                if (teller == indeks) {
                    resultat = current;
                    break;
                }

                current = current.forrige;
                teller--;
            }
        }

        return resultat;
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        Objects.requireNonNull(verdi, "Null-verdier er ikke tillatt!");
        indeksKontroll(indeks, true);

        if (indeks == 0) {
            if (antall == 0) {
                hale = hode = new Node<>(verdi);
            } else {
                hode = hode.forrige = new Node<>(verdi, null, hode);
            }
        } else if (indeks == antall) {
            hale = hale.neste = new Node<>(verdi, hale, null);
        } else {
            Node<T> ref = hode;
            for (int i = 1; i < indeks; ++i) {
                ref = ref.neste;
            }
            ref.neste = ref.neste.forrige = new Node<>(verdi, ref, ref.neste);
        }
        antall++;
        endringer++;
    }


    @Override
    public boolean inneholder(T verdi) {
        return indeksTil(verdi) != -1;
    }

    @Override
    public T hent(int indeks) {
        this.indeksKontroll(indeks, false);

        return finnNode(indeks).verdi;
    }

    @Override
    public int indeksTil(T verdi) {
        if (verdi == null) {
            return -1;
        }

        Node<T> current = hode;
        int teller = 0;

        while (current != null) {
            if (verdi.equals(current.verdi)) {
                return teller;
            }

            current = current.neste;
            teller++;
        }

        return -1;
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {

        //Find node with given index
        //Update values
        //Dont add null values
        //Increase endringer

        Objects.requireNonNull(nyverdi, "Null-verdier er ikke tillatt!");
        this.indeksKontroll(indeks, false);

        Node<T> node = finnNode(indeks);
        T gammelVerdi = node.verdi;
        node.verdi = nyverdi;
        endringer++;

        return gammelVerdi;

    }

    @Override
    public boolean fjern(T verdi) {
        if (verdi == null) {
            return false;
        }

        Node<T> current = hode;

        while (current != null) {
            if (verdi.equals(current.verdi)) {
                Node<T> previous = current.forrige;

                if (antall == 1) {
                    hale = hode = null;
                } else if (current == hale) {
                    hale = previous;
                    previous.neste = current.neste;
                } else {
                    current.neste.forrige = previous;

                    if (current == hode) {
                        hode = current.neste;
                    } else {
                        previous.neste = current.neste;
                    }
                }

                antall--;
                endringer++;

                return true;
            }
            current = current.neste;

        }

        return false;
    }

    @Override
    public T fjern(int indeks) {
        indeksKontroll(indeks,false);

        T temp;

        if (indeks == 0) {
            temp = hode.verdi;

            if (antall == 1) {
                hale = hode = null;
            } else {
                hode = hode.neste;
                hode.forrige = null;
            }
        } else {
            Node<T> p = finnNode(indeks -1);
            Node<T> q = p.neste;
            temp = q.verdi;

            if (q == hale) {
                hale = p;
            } else {
                q.neste.forrige = p;
            }

            p.neste = q.neste;
        }
        antall--;
        endringer++;

        return temp;
    }

    @Override
    public void nullstill() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append('[');
        if (!tom())
        {
            Node<T> p = hode;
            s.append(p.verdi);
            p = p.neste;
            while (p != null)  // tar med resten hvis det er noe mer
            {
                s.append(',').append(' ').append(p.verdi);
                p = p.neste;
            }
        }
        s.append(']');
        return s.toString();
    }

    public String omvendtString() {
        StringBuilder s = new StringBuilder();
        s.append('[');
        if (!tom())
        {
            Node<T> p = hale;
            s.append(p.verdi);
            p = p.forrige;
            while (p != null)  // tar med resten hvis det er noe mer
            {
                s.append(',').append(' ').append(p.verdi);
                p = p.forrige;
            }
        }
        s.append(']');
        return s.toString();
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

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        String[] navn = {"Lars","Anders","Bodil","Kari","Per","Berit"}; Liste<String> liste = new DobbeltLenketListe<>(navn);
        liste.forEach(s -> System.out.print(s + " ")); System.out.println();
        for (String s : liste) System.out.print(s + " ");
    }

} // class DobbeltLenketListe
