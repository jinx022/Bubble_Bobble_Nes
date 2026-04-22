# 🐉 Bubble Bobble Nes - Java Clone

![Java](https://img.shields.io/badge/Java_21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX_21-4791E8?style=for-the-badge&logo=java&logoColor=white)
![Design Patterns](https://img.shields.io/badge/Design_Patterns-OOP-59666C?style=for-the-badge)

Una fedele reinterpretazione in Java e JavaFX dell'iconico videogioco arcade a 8-bit *Bubble Bobble* (sistema NES). Il giocatore controlla il draghetto verde Bub, muovendosi nei livelli e intrappolando i nemici nelle bolle.

Questo progetto è stato sviluppato come prova d'esame per il corso universitario di Metodologie di Programmazione (Sapienza Università di Roma), con un focus fortissimo sulla corretta implementazione dei principi OOP e dei Design Pattern.

## Funzionalità

* **8 Livelli Completi:** Ricreati utilizzando il mapping delle collisioni e la classe `Rectangle` di JavaFX.
* **3 Tipologie di Nemici:** Zen, Mighta e Monsta, ognuno con il proprio algoritmo di movimento (incluso il volo e la gravità).
* **10 Power-Up Dinamici:** Dalle caramelle per potenziare le bolle, fino alla `Fire Bubble` e alla `Thunder Bubble` che cambiano radicalmente le meccaniche di attacco.
* **Profili Utente e Leaderboard:** Sistema di salvataggio per tener traccia del punteggio e delle statistiche di gioco.

## Comandi di Gioco

* **Freccia Sinistra:** Muovi Bub a sinistra.
* **Freccia Destra:** Muovi Bub a destra.
* **Freccia Su:** Salta.
* **Barra Spaziatrice:** Spara le bolle.

## Architettura e Design Pattern

1. **MVC (Model-View-Controller):** L'intero gioco è rigorosamente separato. I Model gestiscono le coordinate, le View l'aggiornamento dei frame e i Controller (come `GameManager`) lo stato di avanzamento.
2. **Strategy:** Utilizzato in due contesti fondamentali: per definire i diversi stili di movimento dei nemici (chi vola, chi segue la gravità) e per iniettare dinamicamente gli effetti dei power-up sulle entità, evitando la rigidità del Decorator.
3. **Observer:** Implementato per gestire le notifiche di aggiornamento della View in tempo reale, ad esempio quando un nemico entra in collisione con una bolla o il giocatore perde una vita.
4. **Builder:** Sostituisce l'Abstract Factory per permettere una costruzione più flessibile e modulare di nemici e oggetti da raccogliere.
5. **Singleton:** Impiegato per la classe `AudioManager`, garantendo un'unica istanza globale per la gestione concorrente della colonna sonora e degli effetti sonori senza memory leak.
6. **Streams & Lambdas:** Ampio utilizzo degli Stream Java per iterare le collisioni e pulire il tabellone dalle entità non più attive in modo elegante e performante.

## Come avviare il gioco

### Prerequisiti
* Java JDK 21 installato
* Maven installato

### Esecuzione
1. Clona il repository.
2. Posizionati nella directory root del progetto e lancia il comando Maven per avviare l'interfaccia JavaFX:
   ```bash
   mvn clean javafx:run
