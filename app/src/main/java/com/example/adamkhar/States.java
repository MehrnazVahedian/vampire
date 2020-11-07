package com.example.adamkhar;

import java.util.ArrayList;

public class States {


    public ArrayList<Person> persons;
    public ArrayList<Person> inBoat;
    private boolean boatDown;


    public States(){
        persons = new ArrayList<>();
        inBoat = new ArrayList<>();
        Person personV1 = new Person(R.id.vampire1,Positions.Down,true);
        this.persons.add(personV1);
        Person personV2 = new Person(R.id.vampire2,Positions.Down,true);
        this.persons.add(personV2);
        Person personV3 = new Person(R.id.vampire3,Positions.Down,true);
        this.persons.add(personV3);
        Person personG1 = new Person(R.id.girl1,Positions.Down,false);
        this.persons.add(personG1);
        Person personG2 = new Person(R.id.girl2,Positions.Down,false);
        this.persons.add(personG2);
        Person personG3 = new Person(R.id.girl3,Positions.Down,false);
        this.persons.add(personG3);
        this.boatDown = true;
    }

    public void addPerson(Person persons) {
        this.persons.add(persons);
    }

    public boolean isBoatDown() {
        return boatDown;
    }

    public void setBoatDown(boolean boatDown) {
        this.boatDown = boatDown;
    }


    public int getArrayPosition(int view)
    {
        for (int i = 0; i < this.persons.size(); i++) {
            if (view == persons.get(i).getView())
                 return i;
        }
        return -1;
    }


    public boolean go(){
        inBoat.clear();
        for (Person person:persons) {
            if (person.getPosition() == Positions.Boat)
                inBoat.add(person);
        }

        return inBoat.size() == 1 || inBoat.size() == 2;
    }

    public GameStates gameState(){
        int vampireDown = 0, vampireUp = 0, girlDown = 0, girlUp = 0 ;
        for (Person person: persons)
        {
            if (person.isVampire()){
                if (person.getPosition() == Positions.Up)
                    vampireUp++;
                else if (person.getPosition() == Positions.Boat)
                    if (boatDown)
                        vampireDown++;
                    else
                        vampireUp++;
                else
                    vampireDown++;
            }
            else{
                if (person.getPosition() == Positions.Up)
                    girlUp++;
                else if (person.getPosition() == Positions.Boat)
                    if (boatDown)
                        girlDown++;
                    else
                        girlUp++;
                else
                    girlDown++;
            }
        }

        if (vampireUp == 3 && girlUp == 3)
            return GameStates.Win;
        else if ((vampireUp > girlUp && girlUp > 0) || (vampireDown > girlDown && girlDown > 0))
            return GameStates.GameOver;
        else
            return GameStates.Continue;
    }


}
