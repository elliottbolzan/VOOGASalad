/**
 * 
 */
package authoring.networking;

import java.io.Serializable;

/**
 * @author Elliott Bolzan
 *
 */
public class Packet implements Serializable {

	private static final long serialVersionUID = 5279768333160170194L;
	private String identifier;
	
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

}
