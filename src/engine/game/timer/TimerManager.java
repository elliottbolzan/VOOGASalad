package engine.game.timer;

import java.text.DecimalFormat;

import engine.game.gameloop.OldGameLoopBackup;
import engine.game.gameloop.Screen;

/**
 * Code relating to the Timer, for games that decide to have timed Levels. NOT to be confused with the GameLoop class,
 * which handles time on each animation step. This class handles the timer appearing on the gameplay screen.
 * 
 * DESIGN NOTE: Uses the Strategy/composition design pattern for tick() and timeIsUp() methods, depending on
 * whether timer is specified to tick up or down. See TickStrategy, TickUp, and TickDown classes.
 * 
 * Overrides toString() to produce a nicely formatted String of the current time to display.
 * 
 * @author Matthew Barbano
 *
 */
public class TimerManager {
	private static final String SECONDS_FORMAT = "00.00";
	private static final String MINUTES_FORMAT = "00";
	public static final int MILLISECONDS_PER_FRAME = Screen.FRAME_TIME_MILLISECONDS;
	private int initialMilliseconds;
	private int milliseconds;
	private TickStrategy tickStrategy;
	
	public TimerManager(double totalSeconds, boolean tickUp){
		initialMilliseconds = (int)(totalSeconds*1000);
		milliseconds = initialMilliseconds;
		if(tickUp){
			 tickStrategy = new TickUp();
		}
		else{
			tickStrategy = new TickDown();
		}
	}
	
	public void reset(){ 
		milliseconds = initialMilliseconds;
	}
	
	public int getMilliseconds(){
		return milliseconds;
	}
	
	public void tick(){
		milliseconds = tickStrategy.tick(milliseconds);
	}
	
	public boolean timeIsUp(){
		 return tickStrategy.timeIsUp(milliseconds);
	}
	
	public void incrementTime(int millisecondsIncrement){
		milliseconds += millisecondsIncrement;
	}
	
	public void decrementTime(int millisecondsDecrement){
		milliseconds -= millisecondsDecrement;
	}
	
	/**
	 * To be displayed on screen - note String.format DOES round! Returns the
	 * current time, in the format specified by MINUTES_FORMAT and SECONDS_FORMAT,
	 * such as "12:34.56" for 12 minutes, 34.56 seconds".
	 */
	@Override
	public String toString(){
		return new DecimalFormat(MINUTES_FORMAT).format(milliseconds / 60000) + ":" + new DecimalFormat(SECONDS_FORMAT).format((milliseconds % 60000) / 1000.0);
	}
}
