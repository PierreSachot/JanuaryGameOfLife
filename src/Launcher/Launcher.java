package Launcher;

import java.awt.Dimension;

import org.eclipse.january.dataset.Dataset;

import Controller.GameController;

public class Launcher {

	public static void main(String[] args) {
		GameController myGame = new GameController();
		int nbRound = 10;
		for(int round = 1; round<nbRound+1;round++)
		{
			Dataset use = myGame.getDatas();
			int[] shape = use.getShape();
			System.out.println(round + " Round\n");
			for(int i = 0; i<shape[0];i++)
			{
				for(int j = 0; j<shape[1];j++)
				{
					if(use.getBoolean(i, j))
						System.out.print("X");
					else
						System.out.print(" ");	
				}
				System.out.print("\n");
			}
			myGame.playNextRound();
		}
	}

}
