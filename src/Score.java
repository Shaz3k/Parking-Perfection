import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

//The score class will be used to read and write final scores from a text file and is currently being implemented into the game
public class Score {
	
	//This method is used to add scores after the game and will be presented in the end game screen
	public static void addScore(int scores) {
		String score;
		score=Integer.toString(scores);
		
		try {
				File file = new File("FinalScores.txt");
			    if (file.exists()) {
		
			        PrintWriter addScore = new PrintWriter(new FileOutputStream(file,true));
			        addScore.append("\n" + score);
			        addScore.close();
			}
			}
		
			catch (IOException e){
				e.printStackTrace();
			}
		
	}
	
	//This method is used to read the top score after the game and will be presented in the end game screen
	public static void highScore() throws Exception{
		File file = new File("FinalScores.txt");
	
		String number;
		int val=0;
		int num=0;
	
		Scanner sc = new Scanner(file);
		while(sc.hasNextLine()) {
			number =sc.nextLine();
			num = Integer.parseInt(number);
			if (num>val) {
				val = num;
			}
			System.out.println(num);
		}
		sc.close();
	}

}
