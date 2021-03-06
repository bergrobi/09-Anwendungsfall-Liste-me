package control;

import model.File;
import model.List;

/**
 * Created by Jean-Pierre on 05.11.2016.
 */
public class MainController {

    private List<File>[] allShelves;    //Ein Array, das Objekte der Klasse Liste verwaltet, die wiederum Objekte der Klasse File verwalten.

    public MainController(){
        allShelves = new List[2];
        allShelves[0] = new List<File>(); //Beachtet die unterschiedliche Instanziierung! Was bedeutet das?
        allShelves[1] = new List<>();
        createFiles();
    }

    /**
     * Die Akten eines Regals werden vollständig ausgelesen.
     * @param index Regalnummer
     * @return String-Array mit den Familiennamen
     */
    public String[] showShelfContent(int index){
        List<File> list = allShelves[index];
        //TODO 03: Ausgabe der Inhalte
        int size = 0;
        list.toFirst();
        while (list.hasAccess()){
            size++;
            list.next();
        }
        String[] output = new  String[size];
        list.toFirst();
        for (int i = 0; i < output.length; i++){
            output[i] = list.getContent().getName();
            list.next();
        }
        return output;
    }

    /**
     * Ein Regal wird nach Familiennamen aufsteigend sortiert.
     * @param index Regalnummer des Regals, das sortiert werden soll.
     * @return true, falls die Sortierung geklappt hat, sonst false.
     */
    public boolean sort(int index){
        //TODO 07: Sortieren einer Liste.
        List<File> list = allShelves[index];
        list.toFirst();
        if (list.hasAccess()){
            List<File> helpList=new List<>();
            helpList.append(list.getContent());
            list.remove();
            helpList.toFirst();
            while (list.hasAccess()){
                if (list.getContent().getName().compareToIgnoreCase(helpList.getContent().getName())<0){
                    helpList.insert(list.getContent());
                    list.remove();
                    helpList.toFirst();
                }else {
                    helpList.next();
                }
                if (helpList.hasAccess()){
                    helpList.append(list.getContent());
                    list.remove();
                    helpList.toFirst();
                }
            }
            /*helpList.toFirst();
            while (helpList.hasAccess()){
                list.append(helpList.getContent());
                helpList.remove();
            }*/
            allShelves[index]= concat (helpList);
            return true;
        }

        return false;
    }

    /**
     * Die gesammte Aktensammlung eines Regals wird zur Aktensammlung eines anderen Regals gestellt.
     * @param from Regalnummer, aus dem die Akten genommen werden. Danach sind in diesem Regal keine Akten mehr.
     * @param to Regalnummer, in das die Akten gestellt werden.
     * @return true, falls alles funktionierte, sonst false.
     */
    public boolean appendFromTo(int from, int to){
        //TODO 04: Die Objekte einer Liste an eine andere anhängen und dabei die erste Liste leeren.
        if (0 <= from && from < allShelves.length && 0 <= to && to < allShelves.length && !allShelves[from].i) {
            List<File> list1 = allShelves[from];
            List<File> list2 = allShelves[to];

            list2.concat(list1);
            return true;
        }
        return false;
    }

    /**
     * Es wird eine neue Akte erstellt und einem bestimmten Regal hinzugefügt.
     * @param index Regalnummer
     * @param name Name der Familie
     * @param phoneNumber Telefonnummer der Familie
     * @return true, falls das Hinzufügen geklappt hat, sonst false.
     */
    public boolean appendANewFile(int index, String name, String phoneNumber){
        //TODO 02: Hinzufügen einer neuen Akte am Ende der Liste.

        return false;
    }

    /**
     * Es wird eine neue Akte in ein Regal eingefügt. Funktioniert nur dann sinnvoll, wenn das Regal vorher bereits nach Namen sortiert wurde.
     * @param index Regalnummer, in das die neue Akte einsortiert werden soll.
     * @param name Name der Familie
     * @param phoneNumber Telefonnummer der Familie
     * @return true, falls das Einfügen geklappt hat, sonst false.
     */
    public boolean insertANewFile(int index, String name, String phoneNumber){
        //TODO 08: Einfügen einer neuen Akte an die richtige Stelle innerhalb der Liste.
        //meine drei Ansätze: 1. Einfügen und neu sortieren 2. Erst Sortieren und dann an die richtige Stelle einfügen 3.überprüfen, ob sortiert wurde und dann richtg einfügen -> entschieden für Nummer 2...
        //Quick and Dirty...(nicht mehr die untere...)

        if (0<=index && index < allShelves.length && name != null && !name.equals("")&& phoneNumber != null) {
            File file = new File(name, phoneNumber);
            allShelves[index].toFirst();
            while (allShelves[index].hasAccess()) {
                //falls die Akte vor dem aktuellen Objekt eingefügt werden muss...
                if (file.getName().compareToIgnoreCase(allShelves[index].getContent().getName()) < 0) {
                    //dann...
                    allShelves[index].insert(file);
                    return true;
                }
                //...sonst geh in der Liste weiter!
                allShelves[index].next();
            }
            allShelves[index].append(file);
            return true;
        }
        return false;
    }

    /**
     * Es wird nach einer Akte gesucht.
     * @param name Familienname, nach dem gesucht werden soll.
     * @return Zahlen-Array der Länge 2. Bei Index 0 wird das Regal, bei Index 1 die Position der Akte angegeben. Sollte das Element - also die Akte zum Namen - nicht gefunden werden, wird {-1,-1} zurückgegeben.
     */
    public int[] search(String name){
        //TODO 05: Suchen in einer Liste.
        int[] output = new int[]{-1,-1};
        for (int i = 0; i < allShelves.length; i++){
            allShelves[i].toFirst();
            for (int j = 0; allShelves[i].hasAccess(); j++){
                if (allShelves[i].getContent().getName().equals(name)){
                    output[0] = i;
                    output[1] = j;
                }
                allShelves[i].next();
            }
        }
        return output;
    }

    /**
     * Eine Akte wird entfernt. Dabei werden die enthaltenen Informationen ausgelesen und zurückgegeben.
     * @param shelfIndex Regalnummer, aus dem die Akte entfernt wird.
     * @param fileIndex Aktennummer, die entfernt werden soll.
     * @return String-Array der Länge 2. Index 0 = Name, Indedx 1 = Telefonnummer.
     */
    public String[] remove(int shelfIndex, int fileIndex){
        //TODO 06: Entfernen aus einer Liste.
        String[] out = new String[2];
        if (0 <=shelfIndex && shelfIndex < allShelves.length){
            int counter = 0;
            allShelves[shelfIndex].toFirst();
            while(allShelves[shelfIndex].hasAccess()){
                if (counter == fileIndex){
                    File file = allShelves[shelfIndex].getContent();
                    out[0] = file.getName();
                    out[1] = file.getPhoneNumber();
                    allShelves[shelfIndex].remove();
                    return out;
                }
                counter++;
                allShelves[shelfIndex].next();
            }
            out[0] = "Regalindex:" + shelfIndex + " vohanden";
            out[1] = "Aktenindex:" + fileIndex + " nicht vorhanden";
            return out;
        }
        return new String[]{"Nicht vorhanden","Nicht vorhanden"};
    }

    /**
     * Es werden 14 zufällige Akten angelegt und zufällig den Regalen hinzugefügt.
     */
    private void createFiles(){
        for(int i = 0; i < 14; i++){
            int shelfIndex = (int)(Math.random()*allShelves.length);

            int nameLength = (int)(Math.random()*5)+3;
            String name = "";
            for(int j = 0; j < nameLength; j++){
                name = name + (char) ('A' + (int)(Math.random()*26));
            }

            int phoneLength = (int)(Math.random()*2)+8;
            String phone = "0";
            for (int k = 1; k < phoneLength; k++){
                phone = phone + (int)(Math.random()*10);
            }

            appendANewFile(shelfIndex,name,phone);
        }
    }
}
