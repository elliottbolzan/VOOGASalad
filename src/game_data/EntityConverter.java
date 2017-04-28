package game_data;

import java.lang.reflect.Field;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import engine.GameObject;
import engine.entities.Entity;
import engine.game.EngineController;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author nikita This class is used by xStream to convert Entities to XML and
 *         load Entities from XML.
 */
public class EntityConverter implements Converter {
	/**
	 * Check if this converter can convert an instance of the class provided.
	 * 
	 * @param arg0
	 *            the class to check
	 * @return if the converter can convert this type of class
	 */
	@Override
	public boolean canConvert(Class arg0) {
		try {
			return arg0.equals(Entity.class) || arg0.getSuperclass().equals(Entity.class);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * write an entity out to XML
	 * 
	 * @param arg0
	 *            the entity to be written out
	 * @param writer
	 *            the writer that writes to XML
	 * @param context
	 *            the context which converts and writes other objects out
	 *            automatically
	 */
	@Override
	public void marshal(Object arg0, HierarchicalStreamWriter writer, MarshallingContext context) {
		Entity entity = (Entity) arg0;
		entity.setGameInfo(null);
		writer.startNode("EntityType");
		writer.setValue(entity.getDisplayName());
		writer.endNode();

		Class<?> objClass = entity.getClass();
		Field[] fields = objClass.getDeclaredFields();

		writeFields(entity, fields, writer, context);

		objClass = objClass.getSuperclass();
		fields = objClass.getDeclaredFields();
		writeFields(entity, fields, writer, context);

		writer.close();
	}

	/**
	 * write the values of the entities into their respective
	 * double/string/boolean values
	 * 
	 * @param entity
	 *            entity to be written
	 * @param fields
	 *            fields within entity to be written
	 * @param writer
	 *            writer object
	 * @param context
	 *            context for writing
	 */
	private void writeFields(Object entity, Field[] fields, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		for (Field field : fields) {
			if (java.lang.reflect.Modifier.isStatic(field.getModifiers()))
				continue;
			field.setAccessible(true);
			String name = field.getName();
			Object value = null;
			try {
				value = field.get(entity);
			} catch (Exception e) {
				// TODO remove print stack trace
				// e.printStackTrace();
			}
			if (value != null) {
				writer.startNode(name);
				if (value instanceof Property){
					((Property)value).unbind();
				}
				if (value instanceof SimpleDoubleProperty)
					writer.setValue(((SimpleDoubleProperty) value).get() + "");
				else if (value instanceof SimpleStringProperty)
					writer.setValue(((SimpleStringProperty) value).get());
				else if (value instanceof SimpleBooleanProperty)
					writer.setValue(((SimpleBooleanProperty) value).get() + "");
				else if (value instanceof GameObject){
					((GameObject)value).setGameInfo(null);
					context.convertAnother(value);
				}
				else
					context.convertAnother(value);
				writer.endNode();
			}
		}
	}

	/**
	 * create an Entity from XML
	 * 
	 * @param reader
	 *            the reader that reads the XML file
	 * @param context
	 *            the context which reads and converts other objects
	 *            automatically
	 * @return the entity converted from the XML
	 */
	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		EngineController controller = new EngineController();
		reader.moveDown();
		Entity entity = controller.createEntity(reader.getValue());
		reader.moveUp();

		while (reader.hasMoreChildren()) {
			reader.moveDown();
			Field field = null;
			try {

				field = entity.getClass().getDeclaredField(reader.getNodeName());
			} catch (NoSuchFieldException | SecurityException e) {
				try {
					field = entity.getClass().getSuperclass().getDeclaredField(reader.getNodeName());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			if (field == null)
				break;
			field.setAccessible(true);
			Object value;
			if (field.getType().equals(SimpleDoubleProperty.class))
				value = new SimpleDoubleProperty(Double.parseDouble(reader.getValue()));
			else if (field.getType().equals(SimpleStringProperty.class))
				value = new SimpleStringProperty(reader.getValue());
			else if (field.getType().equals(SimpleBooleanProperty.class))
				value = new SimpleBooleanProperty(Boolean.parseBoolean(reader.getValue()));
			else
				value = context.convertAnother(entity, field.getType());
			try {
				field.set(entity, value);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			reader.moveUp();
		}
		return entity;
	}
}