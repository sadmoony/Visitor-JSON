package Main;

import Race.ElfFactory;
import Race.HalflingFactory;
import Race.RaceFactory;
import Visitor.DataElement;
import Visitor.ElementVisitor;
import classes.ClassFactory;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Demo {

    public static void main(String[] args) throws IOException {
        ElementVisitor visitor = new ElementVisitor();
        FileWriter file = new FileWriter("Characters.json");
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("Characteristics", "Values");

        file.write("[");

        Character character1 = new Character("Moonlight", ClassFactory.getClass("classes.Paladin"), RaceFactory.getRace(new ElfFactory()));
        putCharacterToJSON(character1, jsonObject, file, visitor);

        Character character2 = new Character("Faaron", ClassFactory.getClass("classes.Barbarian"), RaceFactory.getRace(new HalflingFactory()));
        file.write(",");
        putCharacterToJSON(character2, jsonObject, file, visitor);

        file.write("]");
        file.flush();

        file.close();
    }

    public static void putCharacterToJSON(Character character, JSONObject jsonObject, FileWriter file, ElementVisitor visitor) throws IOException {
        List<DataElement> list = new ArrayList<>();

        character.setAttributes(Stats.generate());
        character.addRaceBonuses();

        list.add(character);
        list.add(character.getDndClass());
        list.add(character.getRace());
        list.add(character.getAttributes());

        for (DataElement val : list){
            jsonObject.putAll(val.accept(visitor));
        }

        file.write(jsonObject.toJSONString());
        list.clear();
        file.flush();
    }
}
