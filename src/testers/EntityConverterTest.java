package testers;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import data.EntityConverter;
import engine.entities.Entity;
import engine.entities.entities.CharacterEntity;
import engine.events.Event;
import engine.events.regular_events.AlwaysEvent;
import javafx.beans.property.SimpleDoubleProperty;

public class EntityConverterTest {
	
	public static void main(String[] args) {

		Entity entity = new CharacterEntity();
		entity.setName("Guilherme");
		SimpleDoubleProperty something = new SimpleDoubleProperty(5);
		entity.xProperty().bind(something);
		something.set(10);
		System.out.println(entity.xProperty().get());
		entity.setY(55);
		entity.setImagePath("test");
		Event event = new AlwaysEvent();
		entity.addEvent(event);
		XStream xStream = new XStream(new DomDriver());
		xStream.registerConverter(new EntityConverter());

		entity = (Entity) xStream.fromXML(xStream.toXML(entity));
		System.out.println(entity.getEvents());
		System.out.println(entity.getClass());

		System.out.println(entity.getX());

	}
	
}
