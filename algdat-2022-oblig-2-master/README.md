# Obligatorisk oppgave 2 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende studenter:
* Einar D. Amundsen, s325864, s325864@oslomet.no
* ...

# Arbeidsfordeling
Med bare 1 person, såeh, jah....



# Oppgavebeskrivelse

For Oppgave 1 så gjorde jeg som oppgaveteksten sa, og lagde tom og antall metode.
For å lage konstruktør uten en leggInn() metode, så fulgte jeg instruksjonen i oppgaveteksten, og har kommentert koden godt nok til å gi klar forklaring der.

For Oppgave 2a brukte jeg Stringbuilder per oppgavetekst, sjekket om listen var tom, og ellers gikk over listen steg for steg, og la inn hver verdi, etterfulgt av ", ", og når listen var tom, så append'et jeg inn en ] så listen så pen og ordentlig ut
For den omvendte var det samme prosess, men speilvendt.
For Oppgave 2b så sjekket jeg først om den skulle legges inn først (antall == 0), hvis ikke, så ble den lagt inn bakerst, ved å lage en ny node med verdiene (verdi, hale, null), og satt hale til å ha den nye verdien.

For Oppgave 3 så lagde jeg først en midt verdi som skulle avgjøre hvillken ende av listen jeg skulle starte å søke fra, deretter brukte jeg en hjelpenode for å søke.
For den andre biten, brukte jeg indekskontroll, og returnerte finnNode() sin verdi. For oppdater() så er det 4 ting som gjøres, finn noden ved gitt indeks, oppdater verdien, ikke legg inn null-verdier, og oppdater endringer. For dette brukte jeg require nonNull, og indekskontroll.
For oppgave 3b så kontrollerer vi at fra og til er verdier som er innenfor antall med fratilKontroll, deretter så lager vi en liste og går over og legger inn med leggInn på hver indeks i nye listen.

For Oppgave 4 så starter vi med å lage indeksTil, hvis verdien som skal hentes ut == null, eller om verdien ikke ligger i listen, returneres -1, per oppgavetekst, ellers så metoden over listen, og returnerer indeksen der første gang verdien forekommer fra venstre side.
Deretter, for boolean inneholder(T verdi) så lager vi en metode som sjekker om indeksTil() returnerer -1 (altså at verdien ikke finnes), hvis return verdien er -1, så returnerer inneholder() false, ellers; true.
For Oppgave 5 dette var en veldig mye klønings oppgave, men også en meget morsom oppgave, da det var mye å holde styre på;
Så sant verdi ikke er null, og indeks'en ligger innenfor størelsen vi har, så kjører koden;
Vi sjekker en hel del, om indeks er 0, så legges den nye verdien inn forrest, er den lik antall så legges den inn bakerst (kunne kanskje kjørt et leggInn() kall her?), og ellers, så har vi en hjelpevariabel som går over listen til vi kommer fram til indeksen vi skal ha, og legger den inn.
Veldig viktig å holde tunga rett i munn med alle neste og forrige verdier, men testen kjører som den skal!

For Oppgave 6 her, så er det litt som å legge inn, bare vi skal fjerne isteden;
Hvis indeksen er som den skal, og så kjører vi;
Er indeks 0, så er det det hodet som skal fjernes (eller hode, som det heter i koden), så da sjekker vi om antall er 1, for da sitter vi igjen med en tom liste. Oppdaterer alle neste og hode verdier som de skal bli.
Ellers, så benytter vi oss av finnNode for indeks -1, for å finne den verdien som står før den vi skal fjerne, og ved hjelp av den, og en annen hjelpenode satt til å være lik den vi skal fjerne, kan vi oppdatere neste/forrige verdier så vi hopper over den verdien som skal fjernes i listen.
Når ingenting peker mot den, er den klar for å overskrives av pc'en.
metoden sjekker også om vi skal fjerne halen, og tar høyde for det.

For boolean utgaven, er ting veldig likt, vi opererer derimot uten noen verdi som skal returneres, utenom true/false, så koden oppfører seg veldig likt.
Om verdien blir fjernet, så returner den true, ellers, false.


For Oppgave 8 skulle vi lage next(), der satt vi if-tester på informasjonen fra oppgaveteksten, og satt fjernOK = true, nodeVerdi = denne.verdi, denne = denne.neste, og returnerte tilslutt nodeVerdi.
8b) skulle vi returnere en instans av iteratorklassen, så ved å returnere new DobbeltlenketListeIterator() løste den oppgaven seg.
8c) skulle private DobbeltLenketListet løses, og det ble gjort ved å sette denne = finnNode(indeks), så et kall på en annen metode for å finne noden ved den gitte indeksen.
8d) skulle vi sjekke om indeks var lovlig, ved indekskontroll, og kjørte så en new DobbeltLenketListeIterator(med den ønskede indeksen).

Som påpekt ved en tidligere commit, det var ting i testen for oppgave 8 som ikke ville kjøre, dette var fordi det var ting der som krevde resultat fra oppgave 7.
Det som er endret skal være kommentert på.

Jeg vil også si at grunnen til så kort mellom commit'ene er at jeg og github/intellij ikke har vært gode venner dette semesteret, så for å få ting til å virke, så måtte jeg resete pc'en til fabrikk instillinger, og for å ikke få gjentatt samme problemet fra oblig 1, valgte jeg å lage et helt nytt repo i steden.

Når det kommer til warnings i koden;
Alt som er igjen (ikke kommentert vekk fordi de ikke ble brukt) var der fra første stund med den utleverte malen.