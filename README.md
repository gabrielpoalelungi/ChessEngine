# ChessEngine
Chess engine made with Java and used with XBoard for a college project.

 
----------------------------------------------------Etapa 1---------------------------------------------------

 -----------------------------
| Instructiuni de compilare:  |
 -----------------------------
Pentru a rula bot-ul cu interfata Xboard-ului, este suficient sa rulam urmatoarea comanda in terminal in folderul 
sursa al proiectului: xboard -fcp "make run" -debug (debug este optional), deoarece "make run" il compileaza intai cu
"make build", iar apoi il ruleaza.

Doar pentru compilare, comanda este "make build" sau doar "make".
Pentru stergerea fisierelor compilate (cele de tip .class), comanda este "make clean".
Pentru rulare (chiar daca nu a fost compilat inainte), comanda este "make run".

 ---------------------------------------
| Detalii despre Structura proiectului: |
 ---------------------------------------
Proiectul este alcatuit din mai multe clase. Au fost create cate o clasa pentru fiecare piesa
in parte, avand culoarea piesei respective, pozitia ei pe tabla si metode de miscare a piesei
(deocamdata implementat doar pentru pion).
De asemenea a fost creata o clasa de tip singleton pentru tabla de joc care contine o matrice
de obiecte de tip "piesa" (clasa parinte pentru fiecare piesa a noastra), vectori de piese
pentru accesarea lor mai rapida (deocamdata, vectorii de piese contin numai pioni).


 ----------------------------------------
| Detalii despre abordarea algoritmica:  |
 ----------------------------------------
Deocamdata la aceasta etapa nu am folosit algoritmi speciali.
Am folosit:
- indexare in matrice -> O(1);
- indexare in vector -> O(1);
- cautare in matrice -> O(n^2); unde n = 8 (dimensiunea tablei de joc).

-------------------------------------------------Etapa 2-----------------------------------------------------

 -----------------------------
| Instructiuni de rulare:  |
 -----------------------------
Pentru a rula bot-ul cu interfata Xboard-ului, este suficient sa rulam urmatoarea comanda in terminal in folderul 
sursa al proiectului: xboard -fcp "make run" -debug (debug este optional), deoarece "make run" il compileaza intai cu
"make build", iar apoi il ruleaza.

Doar pentru compilare, comanda este "make build" sau doar "make".
Pentru stergerea fisierelor compilate (cele de tip .class), comanda este "make clean".
Pentru rulare (chiar daca nu a fost compilat inainte), comanda este "make run".

 ---------------------------------------
| Detalii despre Structura proiectului: |
 ---------------------------------------

- Au fost completate clasele pieselor cu metode pentru mutarea acestora:
	- Fiecare clasa are o metoda numita getMoves() care adauga intr-un vector al clasei casutele in care acesta ar putea sa mute si o metoda move() care returneaza true daca face o mutare valida si false daca nu are nicio mutare valida;

- In clasa King au fost implementate metodele:
	- Detectare de sah;
	- Cele care fac rocada mica/mare (atat pentru engine, cat si pentru player - updatarea tablei interne dupa rocada);
	- Apararea de sah (daca nu are cum sa se apere de sah, returneaza false);

- In clasa Piece au fost implementate in plus metodele de promovare a pionilor. Promovarile pe care le face engine-ul sunt alese la intamplare;

- Au fost updatate metodele din Pawn astfel incat sa poata muta astfel incat sa nu bage propriul rege in sah si sa aiba posibilitatea de a apara regele de sah in caz de nevoie;

- A fost updatat main-ul astfel incat sa mute la intamplare o piesa conform specificatiilor din cerinta;


 ----------------------------------------
| Detalii despre abordarea algoritmica:  |
 ----------------------------------------
Deocamdata la aceasta etapa nu am folosit algoritmi speciali.
Am folosit:
- indexare in matrice -> O(1);
- indexare in vector -> O(1);
- cautare in matrice -> O(n^2); unde n = 8 (dimensiunea tablei de joc)
