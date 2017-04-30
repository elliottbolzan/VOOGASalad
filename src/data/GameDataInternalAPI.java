package data;


public interface GameDataInternalAPI {
	
	/*
	 * Saves all entities and workspace into single folder
	 */
	public void saveGamePackage();
	
	/*
	 * Saves game folder into zip file
	 */
	public void saveZip(String filepath);
	
	/*
	 * Decompresses zip file into game folder
	 */
	public void decompressZip(String filepath);
	
}
