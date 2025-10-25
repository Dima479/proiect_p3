# proiect_p3
# Sistem de Management al Reparațiilor Auto  
## Car Service Manager

---

## 1. Descriere generală
Proiectul **„Sistem de management al reparațiilor auto”** are ca scop dezvoltarea unei aplicații care să faciliteze evidența și gestionarea activităților dintr-un service auto.  
Aplicația permite înregistrarea clienților, a autovehiculelor, a programărilor pentru reparații, a pieselor utilizate și a facturilor generate.  
Prin implementarea acestui sistem se urmărește optimizarea fluxului de lucru, reducerea erorilor administrative și automatizarea proceselor repetitive.

---

## 2. Obiective
- Crearea unei aplicații modulare, ușor de extins.  
- Implementarea unei structuri de date coerente care să reflecte relațiile dintre entitățile domeniului.  
- Asigurarea persistenței datelor (prin fișiere text sau bază de date).  
- Oferirea unei interfețe simple și eficiente pentru utilizatori.  
- Respectarea principiilor de programare orientată pe obiecte (încapsulare, moștenire, polimorfism).

---

## 3. Funcționalități principale
- **Gestionare clienți:** adăugare, modificare, ștergere, căutare după nume sau CNP.  
- **Gestionare autovehicule:** asocierea unei mașini la un client, stocarea informațiilor tehnice (marca, model, an, număr de înmatriculare).  
- **Programări service:** înregistrarea lucrărilor programate, actualizarea stării („planificată”, „în lucru”, „finalizată”).  
- **Gestionare piese de schimb:** adăugare piese, actualizare stoc, calcul costuri utilizare.  
- **Facturare automată:** generare factură pe baza pieselor utilizate și a manoperei.  
- **Gestionare roluri:** diferențiere între utilizatorii de tip administrator, mecanic și client.

---

## 4. Modelul conceptual al datelor
Principalele entități ale sistemului:
- `Client`
- `Masina`
- `Programare`
- `Mecanic`
- `Piesa`
- `Factura`

### Relații:
- Un **client** poate deține mai multe **mașini**.  
- O **mașină** poate avea mai multe **programări**.  
- O **programare** este efectuată de un **mecanic** și poate utiliza mai multe **piese**.  
- Pentru fiecare programare finalizată se emite o **factură**.

---

## 5. Structura proiectului
src/
├── model/
│ ├── Client.java
│ ├── Masina.java
│ ├── Programare.java
│ ├── Piesa.java
│ ├── Mecanic.java
│ └── Factura.java
│
├── service/
│ ├── ClientService.java
│ ├── ProgramareService.java
│ ├── FacturaService.java
│
├── repository/
│ ├── FileRepository.java
│ ├── DatabaseConnection.java (opțional)
│
├── ui/
│ └── MainMenu.java
│
└── Main.java

---



---

## 6. Persistența datelor
Aplicația asigură stocarea persistentă a informațiilor prin:
- **Fișiere text (`.txt`, `.csv`)** – pentru structuri simple, citite și rescrise complet la fiecare actualizare; sau  
- **Bază de date relațională (MySQL / SQLite)** – pentru gestionare avansată a datelor, utilizând JDBC.

---

## 7. Scenariu de utilizare
1. Administratorul adaugă un client nou și autovehiculul asociat acestuia.  
2. Se creează o programare, specificând data, descrierea lucrării și mecanicul responsabil.  
3. Mecanicul actualizează starea lucrării și introduce piesele folosite.  
4. La finalizarea lucrării, aplicația generează o factură cu detaliile costurilor.  
5. Clientul poate vizualiza istoricul lucrărilor efectuate asupra autovehiculului.

---

## 8. Tehnologii și concepte utilizate
- **Limbaj:** Java SE  
- **Paradigmă:** Programare orientată pe obiecte (OOP)  
- **Persistență:** Fișiere text / JDBC + MySQL  
- **Gestionare colecții:** List, Map, Stream API  
- **Interfață:** consolă (CLI) sau interfață grafică (JavaFX / Swing)  
- **Arhitectură:** Model – Service – Repository – UI

---

## 9. Extensii posibile
- Export automat al facturilor în format PDF.  
- Generarea de rapoarte lunare privind numărul de lucrări și veniturile totale.  
- Notificări automate prin e-mail pentru programările viitoare.  
- Implementarea unei versiuni web cu Spring Boot și MySQL.

---

## 10. Concluzii
Proiectul oferă o bază solidă pentru gestionarea operațiunilor unui service auto.  
Designul modular, împărțirea pe straturi logice și utilizarea principiilor OOP facilitează întreținerea, testarea și extinderea ulterioară a sistemului.  
Aplicația poate fi dezvoltată progresiv până la o soluție completă de gestiune pentru ateliere auto.

---



## 11. Model UML al claselor

Diagrama de mai jos reprezintă principalele clase ale sistemului și relațiile dintre acestea.

classDiagram
    class User {
        -int idUser
        -String username
        -String parola
        -String nume
        -String email
    }

    class Client {
        -String telefon
        +getMasini(): List<Masina>
    }

    class Mecanic {
        -String specializare
        -int experientaAni
    }

    class Administrator {
        +creeazaCont(User u)
        +stergeCont(User u)
    }

    class Masina {
        -String marca
        -String model
        -String nrInmatriculare
    }

    class Programare {
        -Date dataProgramare
        -String descriereLucrare
        -String status
    }

    class Piesa {
        -String denumire
        -double pretUnitate
    }

    class Factura {
        -double valoareTotala
        -Date dataEmitere
    }

    User <|-- Client
    User <|-- Mecanic
    User <|-- Administrator

    Client "1" --> "many" Masina
    Masina "1" --> "many" Programare
    Programare "many" --> "1" Mecanic
    Programare "1" --> "1" Factura
    Programare "many" --> "many" Piesa
    Administrator "1" --> "many" User : gestionează

    Programare "1" --> "1" Mecanic
    Programare "many" --> "many" Piesa
    Programare "1" --> "1" Factura

