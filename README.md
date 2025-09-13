# Splender (Java)

Un projet Java inspiré du jeu de plateau Splendor.

---

## 📦 Organisation du projet

Splender/
├─ src/ # code source .java
├─ bin/ # fichiers compilés .class
├─ Splender.jar # jar exécutable
├─ stats.csv # fichier de données (nécessaire à l'exécution)
├─ unifont.otf # police utilisée par l'interface
├─ .gitignore
└─ README.md


⚠️ `stats.csv` et `unifont.otf` doivent rester **à côté du JAR** pour que le programme les trouve.

---

## ⚙️ Compilation et exécution

Dans VS Code ou PowerShell, à la racine du projet :

```powershell
javac -d bin src/*.java


usage: java Game [n] [p]
n : nombre de joueurs
p : nombre de joueurs humains
