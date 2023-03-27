// Author : Ryan O'Connell
// Date : 11/28/2022
// Purpose : Create a program which can take a sensitivity input from one game and convert it 
//			 to the same sensitivity setting in another game. This is a legitimate problem for 
//			 many shooter fans like myself, since different game studios have different methods
//			 of calculating their in-game sensitivity bars.

// References
// [1] https://funender.com/quake/mouse/index.html
// [2] https://www.mouse-sensitivity.com/

public class Game
{
	// important for the game's indentity.
    private String name = "";
    private String engine = "";
    private double mYaw = 0;
	
	// below:
	// necessary variables for calculating sensitivity in a source game
	// other games may use more or less variables.
	
	// indented variables are not used in any of the game conversions I
	// programmed in, but could be necessary for another type of translation.
	private double windowsSensMultiplier = 1;
	private double inGameSens = 1;
	private double mouseDPI = 800;
	   private double fov = 90;
	   private double cl_mouseAccel = 0;
	   private int resolutionWidth = 1080;
	   private int framesPerSec = 90;

	// constructor with game name and engine it uses.
    public Game(String name, String gameEngine)
    {
        this.name = name;

		// make the engine name lowercase and one
		// word so it can be handled later for mYaw assignment.
        engine = gameEngine.toLowerCase();
		engine = engine.trim();

		// figure out what mYaw the game needs to perform a translation.
		assign_mYaw(engine);
    }
   
    // translate from the current game to a destination or goal game.
    public void translateSens(Game goal)
	{
		// the equation for source game sensitivities are as follows:
		//
		// the number of centimeters in a full 360 degree turn =
		// (mouse yaw)(mouse DPI)(windows sensitivity)(ingame sensitivity)
		// 
		// this is only for source games however - I retrieved the formula from reference [1] at
		// the top of this document. Using this and reference [2], an existing tool for sensitivity 
		// calculation, i could work backwards to derive the "mouse yaw" value that other engines
		// used to calculate their sensitivities. 
		// 
		// it's worth noting this might not be the way cryengine actually calculates sensitivities,
		// but the reverse engineering results in a translation only a thousanth of a place off.
		
		// first, get the # of centimeters it takes to do a full 360 degree turn from the initially
		// chosen game - if the goal game and the start game's values don't line up, we need to adjust
		// the goal game.
		double startRealSens = getRealSensCM();
		double goalGameRealSens = goal.getRealSensCM();
		
		// if they dont align,
		if(startRealSens != goalGameRealSens)
		{
			double translatedSensitivity = 0;
			double realSensBody = 0;
			
			// calculate the 'body' of the centimeter equation. This is simply the interaction of the 
			// four variables inside the equation, and omits the '360 divides...' and 'multiply by 2.54.'
			
			// when reverse engineering the answer, i used to website to discover a source engine sensitivity of
			// 2 with a DPI of 800 translates to a Hunt Showdown sens of ~1.023 with a DPI of 800. The equation turned into:
		
			// ( 0.022 * 800 * 1 * 2.0 ) = 35.2 
			// then, since i knew the correct sensitivity from the website, you can isolate the Yaw value (Y)
			// and solve for it by dividing 35.2 by the result of 800 * 1.023.
			
			// ( Y * 800 * 1 * 1.023) = 818.3
			// 35.2 / 818.3 = 0.04301, and now we can translate any sensitivity from a source game to Hunt Showdown!
			
			realSensBody = ( get_mYaw() * getMouseDPI() * getWindowsSensMultiplier() * getInGameSens() );
			translatedSensitivity = ( realSensBody ) / ( goal.get_mYaw() * goal.getMouseDPI() * goal.getWindowsSensMultiplier() );
			
			goal.setInGameSens(translatedSensitivity);
		}
		
		System.out.println("Initial game...");
		System.out.println(this);
		
		System.out.println("Suggested sensitivity...");
		System.out.println(goal);
	}
   
    public void setFigures(double d, double igs, double ws)
	{
		mouseDPI = d;
		inGameSens = igs;
		windowsSensMultiplier = ws;
	}
   
    // have not done the calculations for other engines,
	// but could anytime I chose to now that I've figured out
	// how to derive mYaw from the website.
    public void assign_mYaw(String in)
    {
        switch(in)
        {
            case "source": mYaw = 0.022;
                           break;

            case "cryengine": mYaw = 0.04301;
                              break;
          
            case "unity": mYaw = 0;
                          break;

			case "unreal4": mYaw = 0;
                            break;
	
			case "unreal5": mYaw = 0;
                            break;
							
			case "overwatch": mYaw = 0.00659;
							  break;
							  
			case "rage": mYaw = 0.022;
						 break;
							  
			default: System.out.println("Unrecognized engine!");
					 break;
        }
    }

    public double get_mYaw()
    {   return mYaw; }

    public String getName()
    {   return name; }

    public String getEngine()
    {   return engine; }
	
	public double getRealSensCM()
	{
		return ( 360 / (mYaw * mouseDPI * windowsSensMultiplier * inGameSens) * 2.54 );
	}
	
	public double getRealSensIN()
	{
		return ( 360 / (mYaw * mouseDPI * windowsSensMultiplier * inGameSens) );
	}
	
	public double getWindowsSensMultiplier()
	{	return windowsSensMultiplier; }
	
	public void setWindowsSensMultipler(double newWSM)
	{ 	windowsSensMultiplier = newWSM; }
	
	public void setMouseYaw(double newMY)
	{ 	mYaw = newMY; }
	
	public double getFOV()
	{	return fov; }
	
	public void setFOV(double newFOV)
	{	 fov = newFOV; }
	
	public double getMouseAcceleration()
	{	return cl_mouseAccel; }
	
	public void setMouseAcceleration(double newMA)
	{ 	cl_mouseAccel = newMA; }
	
	public double getInGameSens()
	{	return inGameSens; }
	
	public void setInGameSens(double newSens)
	{	inGameSens = newSens; }
	
	public double getMouseDPI()
	{	return mouseDPI; }
	
	public void setMouseDPI(double newMDPI)
	{	mouseDPI = newMDPI; }
	
	public int getResolutionWidth()
	{	return resolutionWidth; }
	
	public void setResolutionWidth(int newRW)
	{ 	resolutionWidth = newRW; }
	
	public int getFPS()
	{	return framesPerSec; }
	
	public void setFPS(int newFPS)
	{	framesPerSec = newFPS; }
	
	public String toString()
	{
		String formatted = "";
		
		formatted += String.format("%-25s%s, %s\n", "Game:", getName(), getEngine());
		formatted += String.format("%-25s%4.5f\n", "cm/360:", getRealSensCM());
		formatted += String.format("%-25s%4.5f\n", "DPI:", getMouseDPI());
		formatted += String.format("%-25s%2.3f\n", "Game Sensitivity:", getInGameSens());
		
		return formatted;
	}
	
	
}