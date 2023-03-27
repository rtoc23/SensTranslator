public class SensTransTest
{
	public static void main(String[] args)
	{
		Game first = new Game("tf2", "Source");
		Game second = new Game("l4d2", "so urce");
		Game third = new Game("gmod", "SoURCE");
		Game fourth = new Game("hunt", "cryengine");
		Game fifth = new Game("overwatch2", "Overwatch");
		
		first.setFigures(800, 2, 1);
		first.translateSens(fourth);
		first.translateSens(fifth);
	}
}