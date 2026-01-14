# Sistem de Management al Service-ului Auto

Acest proiect este o aplicație desktop pentru managementul unui service auto, dezvoltată în Java Swing. Aplicația permite gestionarea clienților, mașinilor, mecanicilor, reparațiilor și a stocului de piese.

## Tehnologii Utilizate

- **Java 11**: Limbajul de programare principal.
- **Swing**: Pentru interfața grafică (GUI).
- **Hibernate**: Pentru persistența datelor (ORM).
- **MySQL**: Baza de date relațională.
- **Maven**: Pentru managementul dependențelor și build-ul proiectului.

## Funcționalități

Aplicația oferă funcționalități diferite în funcție de rolul utilizatorului autentificat:

### Administrator
- **Management Clienți**: Vizualizarea și gestionarea clienților.
- **Management Mașini**: Vizualizarea și gestionarea mașinilor.
- **Management Mecanici**: Adăugarea, vizualizarea și asignarea mecanicilor la reparații.
- **Management Stoc**: Gestionarea stocului de piese.

### Mecanic
- **Reparații Asignate**: Vizualizarea reparațiilor care i-au fost asignate.
- **Management Clienți**: Vizualizarea clienților.
- **Management Mașini**: Vizualizarea mașinilor.

### Client
- **Mașinile Mele**: Vizualizarea și gestionarea propriilor mașini.
- **Reparațiile Mele**: Urmărirea statusului reparațiilor pentru mașinile proprii.
- **Facturile Mele**: Vizualizarea facturilor pentru reparațiile efectuate.

## Cum se rulează

1. **Configurarea Bazei de Date**:
   - Asigură-te că ai un server MySQL pornit.
   - Creează o bază de date.
   - Configurează detaliile conexiunii în `src/main/resources/META-INF/persistence.xml`.

2. **Build și Rulare**:
   - Proiectul folosește Maven. Poți rula aplicația dintr-un IDE (precum IntelliJ sau Eclipse) care are suport pentru Maven.
   - Clasa principală (entry point) este `App.java`.

## Autentificare

Aplicația pornește cu un ecran de login. Utilizează un cont de utilizator valid (email și parolă) pentru a te autentifica. Rolul utilizatorului va determina ce funcționalități sunt disponibile.
