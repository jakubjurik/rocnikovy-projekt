# Silné hranové farbenie špeciálnych tried kubických grafov.

Klasické hranové farbenie vyžaduje, aby susedné hrany - teda hrany vo vzdialenosti 1, mali rozlišnú farbu. 
Silné hranové farbenie túto podmienku zosilňuje a vyžaduje aby ľubovoľná dvojica hrán, ktorej vzájomná vzdialenosť je menšia alebo rovná ako 2 mala rozlišnú farbu. 
Tak ako pri každom farbení nás zaujíma, koľko najmenej farieb je potrebných aby sme náležite ofarbili daný graf. 
V našom prípade nás budú zaujímať claw-free kubické grafy. 
Pre tieto grafy je ukázané, že je vždy potrebné najmenej 6 farieb a taktiež, že 7 vždy postačuje. 
Jediné známe claw-free grafy, ktoré vyžadujú 7 farieb obsahujú špecifické podgrafy a sú nutne hranovo aj vrcholovo 2-súvislé. 
Preto vznikla hypotéza, že pre všetky 3-súvislé claw-free kubické grafy postačuje 6 farieb. 
Na triedu 3-súvislých claw-free grafov sa dá pozrieť aj inak a nie je zložité nahliadnuť, že ide o trunkácie kubických grafov - to sú kubické grafy, v ktorých každý vrchol nahradíme trojuholníkom.

Pre túto triedu grafov, sú vypozorované určité invarianty, ktoré musí každé náležité farbenie zachovávať. 
Pomocou týchto pozorovaní, vieme lokálnymi zmenami prerobiť jedno náležité farbenie na iné. 
Ide o prefarbovanie pomocou šírenia konfliktu ale nie je celkom zrejmé ako sa bude konflikt v grafe šíriť. 
Naším cieľom je ukázať, že každý konflikt po istom počte krokov zanikne a teda obdržíme nové náležité hranové farbenie.
