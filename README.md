# Sistem de Management al Reparațiilor Auto

## 1. Descriere generală

Proiectul „Sistem de management al reparațiilor auto” este o aplicație orientată pe obiecte care facilitează gestionarea activităților unui service auto. Sistemul permite administrarea clienților, vehiculelor, programărilor, pieselor de schimb, mecanicilor și facturilor, cu scopul de a automatiza procesele, de a reduce erorile și de a urmări stocurile și activitățile în timp real.

### Obiective
- Dezvoltarea unei aplicații modulare și extensibile.  
- Implementarea unei structuri de date coerente, bazată pe principiile OOP: încapsulare, moștenire și polimorfism.  
- Asigurarea persistentei datelor prin fișiere text sau baze de date relaționale.  
- Crearea unei interfețe CLI intuitive, cu posibilitate de extindere către GUI.  
- Automatizarea verificării stocurilor de piese și a procesului de facturare.

---

## 2. Funcționalități

**Gestionare utilizatori:**  
- Roluri: Client, Mecanic, Administrator  
- Autentificare și autorizare diferențiată  

**Gestionare clienți:**  
- Adăugare, modificare, ștergere și căutare după nume sau CNP  
- Vizualizare istoric reparații  

**Gestionare vehicule:**  
- Asociere a mai multor vehicule unui client  
- Stocare informații: marcă, an, număr de înmatriculare  

**Gestionare programări (Reservation):**  
- Creare, actualizare și anulare rezervări  
- Status: `PLANNED`, `IN_PROGRESS`, `COMPLETED`, `PENDING_PARTS`  
- Verificare automată a pieselor necesare; alertare administrator dacă stocul este insuficient  

**Gestionare piese de schimb:**  
- Adăugare, actualizare, ștergere piese  
- Evidența stocurilor și prețurilor  
- Integrare cu Used_Parts pentru urmărirea pieselor utilizate  

**Facturare automată (Receipt):**  
- Generare automată la finalizarea reparației  
- Include valoarea totală și data emiterii  

**Gestionare administrativă:**  
- Comandă piese lipsă  
- Verificare stocuri  
- Creare și ștergere conturi utilizatori  
- Generare rapoarte lunare privind activitatea și veniturile  

---

## 3. Modelul conceptual al datelor (UML)

**Clase principale:**  
- `User` (ID, nume, email, parolă, rol)  
- `Client` (moștenește User; deține vehicule și rezervări)  
- `Mecanic` (moștenește User; experiență, rating, listă reparații)  
- `Administrator` (moștenește User; gestionează piese și conturi)  
- `Car` (vehicul asociat unui client)  
- `Reservation` (programare reparație, detalii, piese folosite, status)  
- `Used_Parts` (relație N–N între Reservation și Part)  
- `Part` (nume, preț, stoc)  
- `Receipt` (factură emisă la finalizarea reparației)

**Relații:**  
- 1 Client → N Car  
- 1 Car → N Reservation  
- 1 Mecanic → N Reservation  
- 1 Reservation → 1 Receipt  
- N Reservation → N Part (prin Used_Parts)  

---

## 4. Persistența datelor

- Fișiere text / CSV pentru prototipuri și teste.  
- Bază de date relațională (MySQL / SQLite) pentru versiunea finală.  

**Structura tabelelor:**  
```text
User(User_ID, Name, Email, Password, Role)
Client( User_ID,  CNP, Ph_Number)
Mecanic(User_ID,  Experience, Rating)
Car(Car_ID, Licence_Plate, Brand, Year, Client_ID)
Reservation( Rez_ID, Date,  Status, Details, Car_ID, Mecanic_ID)
Used_Parts(Rez_ID, Part_ID, Quantity)
Part(Part_ID, Name, Price,  Stock)
Receipt(Receipt_ID, Value, Date, Rez_ID)
```
## 5. Scenariu de utilizare

1. Administratorul adaugă un client și vehiculul asociat acestuia.  
2. Clientul creează o rezervare pentru reparație.  
3. Mecanicul pornește reparația:  
   - Sistemul verifică automat piesele necesare.  
   - Dacă piesele lipsesc → status `PENDING_PARTS` și alertă către administrator.  
4. După finalizarea reparației, se generează automat factura (`Receipt`).  
5. Administratorul poate consulta rapoarte privind piesele utilizate și veniturile generate.

---

## 6. Tehnologii și concepte utilizate

- **Limbaj:** Java SE  
- **Paradigmă:** Programare Orientată pe Obiecte (OOP)  
- **Persistență:** Fișiere text / JDBC + MySQL  
- **Colecții:** `List`, `Map`, `Stream API`  
- **Enum:** pentru roluri și statusuri  
- **Interfață:** Consolă (CLI), extensibilă la GUI (JavaFX / Swing)  

---

## 7. Extensii viitoare

- Export automat al facturilor în PDF  
- Generarea de rapoarte statistice și analize de performanță (piese utilizate, venituri, top mecanici)  
- Notificări automate pentru programările viitoare sau stocurile lipsă  
- Implementarea unei versiuni web (Spring Boot + MySQL)  

