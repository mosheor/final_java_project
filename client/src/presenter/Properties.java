package presenter;

import java.io.Serializable;

/**
 * <h1>  Properties Interface <h1>
 * This class defines the game's properties
 * 
 * @author  Ben Mazliach & Or Moshe
 * @version 1.0
 * @since   18/01/16
 */
@SuppressWarnings("serial")
public class Properties implements Serializable {

	//all the properties
	private int xSize;
	private int ySize;
	private int zSize;
	private String algorithmSearchName;
	private int numOfThreads;
	private String algorithmGenerateName;
	private String userInterface;
	private String mazeName;
	private int serverPort;
	
	/**
	 * Default C'tor
	 */
	public Properties() {
		this.xSize=0;
		this.ySize=0;
		this.zSize=0;
		this.serverPort = 0;
		this.algorithmSearchName=null;
		this.numOfThreads=0;
		this.algorithmGenerateName=null;
		this.userInterface=null;
		this.mazeName = null;
	}
	
	/**
	 * Get the maze x size property
	 * @return int
	 */
	public int getXSize() {
		return xSize;
	}

	/**
	 * Set the maze x size property
	 * @param int
	 */
	public void setXSize(int xSize) {
		this.xSize = xSize;
	}

	/**
	 * Set the maze x size property
	 * @param int
	 */
	public void setYSize(int ySize) {
		this.ySize = ySize;
	}
	
	/**
	 * Set the maze amount of threads in the threadpool property
	 * @param numOfThreads
	 */
	public void setNumOfThreads(int numOfThreads) {
		this.numOfThreads = numOfThreads;
	}

	/**
	 * Get the maze y size property
	 * @return int
	 */
	public int getYSize() {
		return ySize;
	}

	/**
	 * Get the maze z size property
	 * @return int
	 */
	public int getZSize() {
		return zSize;
	}

	/**
	 * Get the maze amount of threads in the threadpool property
	 * @return int
	 */
	public int getNumOfThreads() {
		return numOfThreads;
	}

	/**
	 * Set the maze z size property
	 * @param int
	 */
	public void setZSize(int zSize) {
		this.zSize = zSize;
	}

	/**
	 * Get the search algorithm type property
	 * @return String
	 */
	public String getAlgorithmSearchName() {
		return algorithmSearchName;
	}

	/**
	 * Set the search algorithm type property
	 * @param String algorithm name
	 */
	public void setAlgorithmSearchName(String algorithmSearchName) {
		this.algorithmSearchName = algorithmSearchName;
	}

	/**
	 * Get the generation algorithm type property
	 * @return String
	 */
	public String getAlgorithmGenerateName() {
		return algorithmGenerateName;
	}

	/**
	 * Set the generation algorithm type property
	 * @param String
	 */
	public void setAlgorithmGenerateName(String algorithmGenerateName) {
		this.algorithmGenerateName = algorithmGenerateName;
	}

	/**
	 * Get the user interface (GUI/CLI) property
	 * @return String
	 */
	public String getUserInterface() {
		return userInterface;
	}

	/**
	 * Set the user interface (GUI/CLI) property
	 * @param String
	 */
	public void setUserInterface(String userInterface) {
		this.userInterface = userInterface;
	}

	/**
	 * Get the maze name property
	 * @return String
	 */
	public String getMazeName() {
		return mazeName;
	}

	/**
	 * Set the maze name property
	 * @param String
	 */
	public void setMazeName(String mazeName) {
		this.mazeName = mazeName;
	}

	/**
	 * Get the x size property
	 * @param String
	 */
	public int getxSize() {
		return xSize;
	}
	
	/**
	 * Set the x size property
	 * @param String
	 */
	public void setxSize(int xSize) {
		this.xSize = xSize;
	}

	/**
	 * Get the y size property
	 * @param String
	 */
	public int getySize() {
		return ySize;
	}

	/**
	 * Set the y size property
	 * @param String
	 */
	public void setySize(int ySize) {
		this.ySize = ySize;
	}

	/**
	 * Get the z size property
	 * @param String
	 */
	public int getzSize() {
		return zSize;
	}

	/**
	 * Set the z size property
	 * @param String
	 */
	public void setzSize(int zSize) {
		this.zSize = zSize;
	}

	/**
	 * Get the server port property
	 * @param String
	 */
	public int getServerPort() {
		return serverPort;
	}

	/**
	 * Set the server port property
	 * @param String
	 */
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	
}
