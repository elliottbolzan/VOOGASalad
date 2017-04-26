package testers;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import engine.Entity;
import engine.Event;
import engine.entities.CharacterEntity;
import engine.events.regular_events.AlwaysEvent;
import game_data.EntityConverter;
import javafx.beans.property.SimpleDoubleProperty;

public class EntityConverterTest {
	public static void main(String[] args) {
		
		Entity entity = new CharacterEntity();//new CharacterEntity("Mario","src/resources/images/mario.png");
		entity.setName("Guilherme");
		SimpleDoubleProperty something = new SimpleDoubleProperty(5);
		entity.xProperty().bind(something);
		something.set(10);
		System.out.println("AHKGJDF:LS");
		System.out.println(entity.xProperty().get());
		//entity.setX(100.6);
		entity.setY(55);
		entity.setImagePath("test");
		Event event = new AlwaysEvent();
		entity.addEvent(event);
		XStream xStream = new XStream(new DomDriver());
		xStream.registerConverter(new EntityConverter());
		//xStream.alias("person", Person.class);
		//System.out.println(xStream.toXML(entity));

		entity = (Entity) xStream.fromXML(xStream.toXML(entity));
		System.out.println(entity.getEvents());
		System.out.println(entity.getClass());

		System.out.println(entity.getX());

		
		//System.out.println(entity.getX());
		
	}
}
