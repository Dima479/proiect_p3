
## Sistem de Management al Reparațiilor Auto  

## Descriere generală
Proiectul **„Sistem de management al reparațiilor auto”** are ca scop dezvoltarea unei aplicații care să faciliteze evidența și gestionarea activităților dintr-un service auto.  
Aplicația permite înregistrarea clienților, a autovehiculelor, a programărilor pentru reparații, a pieselor utilizate și a facturilor generate.  
Prin implementarea acestui sistem se urmărește optimizarea fluxului de lucru, reducerea erorilor administrative și automatizarea proceselor repetitive.


## Obiective
- Crearea unei aplicații modulare, ușor de extins.  
- Implementarea unei structuri de date coerente care să reflecte relațiile dintre entitățile domeniului.  
- Asigurarea persistenței datelor (prin fișiere text sau bază de date).  
- Oferirea unei interfețe simple și eficiente pentru utilizatori.  
- Respectarea principiilor de programare orientată pe obiecte (încapsulare, moștenire, polimorfism).


## Funcționalități principale
- **Gestionare clienți:** adăugare, modificare, ștergere, căutare după nume sau CNP.  
- **Gestionare autovehicule:** asocierea unei mașini la un client, stocarea informațiilor tehnice (marca, model, an, număr de înmatriculare).  
- **Programări service:** înregistrarea lucrărilor programate, actualizarea stării („planificată”, „în lucru”, „finalizată”).  
- **Gestionare piese de schimb:** adăugare piese, actualizare stoc, calcul costuri utilizare.  
- **Facturare automată:** generare factură pe baza pieselor utilizate și a manoperei.  
- **Gestionare roluri:** diferențiere între utilizatorii de tip administrator, mecanic și client.



## Modelul conceptual al datelor
Principalele entități ale sistemului:
- `Client`
- `Masina`
- `Programare`
- `Mecanic`
- `Piesa`
- `Factura`

## Relații:
- Un **client** poate deține mai multe **mașini**.  
- O **mașină** poate avea mai multe **programări**.  
- O **programare** este efectuată de un **mecanic** și poate utiliza mai multe **piese**.  
- Pentru fiecare programare finalizată se emite o **factură**.


## Persistența datelor
Aplicația asigură stocarea persistentă a informațiilor prin:
- **Fișiere text (`.txt`, `.csv`)** – pentru structuri simple, citite și rescrise complet la fiecare actualizare; sau  
- **Bază de date relațională (MySQL / SQLite)** – pentru gestionare avansată a datelor, utilizând JDBC.


## Scenariu de utilizare
1. Administratorul adaugă un client nou și autovehiculul asociat acestuia.  
2. Se creează o programare, specificând data, descrierea lucrării și mecanicul responsabil.  
3. Mecanicul actualizează starea lucrării și introduce piesele folosite.  
4. La finalizarea lucrării, aplicația generează o factură cu detaliile costurilor.  
5. Clientul poate vizualiza istoricul lucrărilor efectuate asupra autovehiculului.


## Tehnologii și concepte utilizate
- **Limbaj:** Java SE  
- **Paradigmă:** Programare orientată pe obiecte (OOP)  
- **Persistență:** Fișiere text / JDBC + MySQL  
- **Gestionare colecții:** List, Map, Stream API  
- **Interfață:** consolă (CLI) sau  eventual interfață grafică (JavaFX / Swing)  


## Extensii posibile
- Export automat al facturilor în format PDF.  
- Generarea de rapoarte lunare privind numărul de lucrări și veniturile totale.  
- Notificări automate prin e-mail pentru programările viitoare.  
- Implementarea unei versiuni web cu Spring Boot și MySQL.
