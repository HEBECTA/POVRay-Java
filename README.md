# POVRay-Java
VU MIF

Bakalaurinis darbas

POV-Ray kodo generavimas naudojant Java

Programa buvo išbandyta su JDK 1.8 Java platforma ir POV-Ray 3.6.1 ir 3.7 versija, taip pat kurta ant Linux platformos. Sukompiliuotas programos Java kodas „bakalaurinis-jar-with-dependencies.jar“ yra „Bakalaurinis/target“ aplanke. Programa paleidžiama nurodytame aplanke per komandinę eilutę įvykdžius komandą:


„java -Xss26m -jar bakalaurinis-jar-with-dependencies.jar“.

Taip pat programa gali neveikti, nes POV-Ray konfigūracijos faile (http://www.povray.org/documentation/view/3.7.1/800/) nėra nurodyti specialūs skaitymo ir rašymo failinėje sistemoje leidimai. Windows platformoje galima koreguoti minėtus leidimus atidarius POV-Ray IDE programą ir išjungus Options $\rightarrow$ Script I/O Restrictions $\rightarrow$ No Restrictions. Taip pat Windows platformoje priklausomai nuo POV-Ray versijos reikia pervadinti POV-Ray instaliacijos aplanke, kuris gali būti panašus į „C:/Program Files/POV-Ray/v3.7/bin“, esanti „pvengine“ failą į „povray“ bei „Path“ sistemos kintamajam priskirti kelią iki pervadinto „povray“ failo. Windows platformoje paleidus šiame darbe aprašytą įrankį ir su juo generuojant objektus gali būti, kad bus papildomai atidaryta POV-Ray programą, kuri su savo variklių sugeneruos išpūsto objekto paveikslėlį. Tokiu atveju jei baigus generavimo procesą reikia ją išjungti, nes kitaip šiame darbe sukurtas įrankis negalės pakartotinai iškviesti POV-Ray variklį naujam paveikslėlio generavimui.


Papildomi failai

Nupiešti įvesties paveiksliukai saugojami „Bakalaurinis/data/input-images“ aplanke. Kiekvienam pūtimo metodui reikia naudoti paveikslėlį, kuris yra nupieštas pagal atitinkamus metodo reikalavimus. Sugeneruoti išpūsto objekto paveikslėliai yra saugojami „Bakalaurinis/data/output-images“ aplanke. Jei užkrauta programa nerodo sugeneruotų paveikslėlių, tai reiškia, kad jie ištrinti. Tokiu atveju reikia juos sugeneruoti, bet programa jų vis vien nerodys. Per nauja užkrauta programa turėtų rodyti šiame aplankale saugomus paveikslėlius. Sugeneruotas POV-Ray paveikslėlių kodas saugojamas „Bakalaurinis/data/generated-pov-ray“.

Naudojimosi instrukcija

Paveiksliukai buvo piešti naudojant Gimp redaktorių. Pirmiausia reikia užkrauti įvesties paveikslėlius, kuriuose yra nupiešta norima išpūsti figūra. Reikia paspausti ant „Load Images“ skilties, kurioje bus tris pasirinkimai:

FirstMethod1 image - skirtas užkrauti paveikslėlį skirtą pirmajam pūtimo metodui, kuris jį apdoros su horizontaliomis linijomis. 
FirstMethod1 image - skirtas užkrauti paveikslėlį skirtą pirmajam pūtimo metodui, kuris jį apdoros nupiešęs jo vidų ir su kvadratų sutrianguliuojant nupieštus pikselius. 
SecondMethod image - skirtas užkrauti paveikslėlį skirtą antrajam pūtimo metodui.


Mygtukai „first Method 1“, „first Method 1“ ir „second Method“ yra skirti paleisti norimą metodą. Pov-Ray scene settings sekcijoje galima koreguoti generuojama POV-Ray sceną, pavyzdžiui keisti kameros $z$ lokacijos koordinates, šitaip priartinant arba patolinant vaizdą. Atliekant rotate transformacijas galima sukioti objektą. Keisti algoritmų parmetrus galima specialiuose laukuose:

    
Inflation Dept - objekto išpūtimo dydis. 
Relief iteration interval - intervalas matuojamas pikseliais, pagal kuri matuojamas atstumas tarp generuojamų karkaso linijų, „data/output-images/flatAreaPixels.png“ pav. atstumas tarp violetinių linijų arba geltonų taškų (skirtas tik antrajam pūtimo metodui).
Line generating two points precision distance - atstumas tarp dviejų reljefo taškų iš kurių sugeneruojama laikina linija, o jai sukuriamos dvi į skirtingas puses nukreiptos stačios linijos - karkaso linijos, „data/output-images/flatAreaPixels.png“ pav. violetinės linijos (skirtas tik antrajam pūtimo metodui).
Triangulation precision - vienai karkaso linijai generuojamų trikampiams sukurti taškų skaičius, „data/output-images/inflatedAreaPixels.png“ pav. išpūsti pikseliai, viena karkaso linija yra „data/output-images/flatAreaPixels.png“ pav. nuo geltono iki mėlyno taško violetine spalva nupiešta linija (skirtas tik antrajam pūtimo metodui).



Sugeneruotus paveikslėlius galima peržiūrėti specialiose skiltyse:

    
Preview painted image - vaizduojama nupiešta figūra (skirtas tik antrajam pūtimo metodui).
Preview flat area pixels - vaizduojamas sugeneruotas neišpūstas figūros karkasas (skirtas tik antrajam pūtimo metodui).
Preview inflated area pixels - vaizduojami iš karkaso sugeneruoti išpūsti pikseliai, kurių viršūnės bus trianguliuojamos (skirtas tik antrajam pūtimo metodui).
Preview inflated triangulated object - vaizduojamas galutinis išpūstas objektas (skirtas tik antrajam pūtimo metodui).
First method - vaizduojamas galutinis išpūstas objektas (skirtas tik pirmojo pūtimo metodui).

