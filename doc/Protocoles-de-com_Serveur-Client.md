### Protocole de communication Serveur <-> Client:


Modèle : M <br>
Cleint : C <br>
Controlleur : Ctr <br>

#### Placement des unités :

M -> C : Q? <br>
M -> C : U-[Unit size] <br>
M -> C : [Request] <br>

C -> Ctr : U-[Unit size] <br>
C -> Ctr : [Answer]  <br>

if anwserCheck ok : <br>

Ctr -> M : [data] <br>

else: <br>

Ctr -> C : asking for valid input <br>

#### Tir :

M -> C : Q? <br>
M -> C : S-[SHot type] <br>
M -> C : [Request] <br>




#### Communication sans commande spécifique mais demande de confirmation: 

M ou Ctr -> C : Q? <br>
M ou Ctr -> C : C- <br>
M ou Ctr -> C : [Request] <br>

C -> M ou Ctr : [Answer] <br>