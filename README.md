# Asset Return Evaluation System (ARES)
ARES is the result of an assignment from my university class KdP.

# Design and Objectives
The Asset Return Evaluation System (ARES) is a Java-based analytical application designed to evaluate and compare the profitability of different financial assets over a user-defined investment horizon. The system enables users to model investment outcomes by specifying an initial capital amount, an investment duration in years, and one or more predefined asset classes. Based on these inputs, ARES calculates and visually presents the comparative performance of the selected assets.

The primary objective of this project is to provide a structured and intuitive tool for analyzing investment returns, while demonstrating fundamental software engineering concepts such as user input handling, financial computation, data visualization, and modular program design.

# Roadmap
- Phase 1: Backend Architecture
  - Unit-Tests
  - Asset Classes
  - Object-Generation
- Phase 2: Frontend Implementation
  - Until API/Backend is needed
- Phase 3: Backend Implementation & RESTful API
  - Systemcontroller
  - Systemservice
  - Calculationservice: nextGaussian
- Phase 4: Data Visualization & Quality of Life
  - Page 2, Underneath the Graph next to the Table: Small Box with a "+" hitting turns the plus by 45° and lets the user add another table below for comparison
- Phase 5: UI Polish

# Requirements
(original formulation of the minimum requirement of the assignment)

Folgende Mindestanforderungen an die Anwendung müssen beachtet werden:
- Als Benutzer möchte ich einen Anlagebetrag, eine Laufzeit und eine oder mehrere Anlageklassen eingeben können, damit ich die Renditeentwicklung meiner Investition simulieren kann.
- Als Benutzer möchte ich eine übersichtliche tabellarische Ausgabe der jährlichen Entwicklung meiner Investition sehen, damit ich die Unterschiede zwischen den Anlageklassen nachvollziehen kann.
- Als Benutzer möchte ich sicherstellen, dass meine Eingaben (z. B. positive Beträge und gültige Laufzeiten) validiert werden, damit ich keine falschen Ergebnisse erhalte.
- Als Benutzer möchte ich mindestens die Anlageklassen Aktien, Anleihen und Immobilien wählen können.
- Als Benutzer möchte ich, dass die Anwendung auch mit fehlerhaften Eingaben (z. B. negative Werte oder unbekannte Anlageklassen) sinnvoll umgeht, damit ich keine Abstürze erlebe.
- Als Benutzer möchte ich, dass die Ergebnisse der Simulation verständlich und nachvollziehbar dargestellt werden, damit ich sie als Entscheidungsgrundlage nutzen kann.
- Als Entwickler möchte ich, dass die Anwendung die grundlegenden Konzepte der objektorientierten Programmierung (z. B. Vererbung, Polymorphie) verwendet, damit sie modular, wartbar und erweiterbar bleibt.
- Als Entwickler möchte ich, dass der Code der Anwendung gut dokumentiert und modular ist, damit er für andere verständlich und wartbar bleibt.
Eine (nicht verpflichtende) Erweiterung des Funktionsumfangs ist z.B. durch folgende Anforderungen möglich:
- Als Benutzer möchte ich die Simulation über eine grafische Benutzeroberfläche (GUI) durchführen können, damit die Eingabe und Visualisierung der Ergebnisse einfacher und ansprechender ist.
- Als Benutzer möchte ich, dass die Anwendung die Renditeentwicklung in einem Diagramm darstellt, damit ich die Ergebnisse visuell nachvollziehen kann.
- Als Benutzer möchte ich historische Daten über eine Webschnittstelle abfragen können, damit ich die Entwicklung von Anlageklassen retrospektiv analysieren und mit der Simulation vergleichen kann.

# Scope and Limitations
This project is intended for academic and instructional purposes. It does not account for real-world market volatility, transaction costs, inflation, or risk factors beyond the simplified asset models. Consequently, the results produced by ARES should not be interpreted as financial advice.