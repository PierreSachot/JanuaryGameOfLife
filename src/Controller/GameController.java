package Controller;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.Slice;

public class GameController {
	
	public GameController()
	{
	}
	
	private int neighborsCpt(Dataset toUse, int y, int x)
	{
		int retour = 0;
		Dataset possibleNeighbors;
		int [] shape = toUse.getShape();
		//gestion des cas possibles (début, fin ou milieu de ligne)
		int value = y%10;
		switch(value)
		{
			case 0 :
				if(y == 0)
					possibleNeighbors = DatasetFactory.createFromObject(new int[][]{{y,y+1},{x-1, x}});
				if(y == shape[1])
					possibleNeighbors = DatasetFactory.createFromObject(new int[][]{{y-1,y},{x-1, x}});
				else
					possibleNeighbors = DatasetFactory.createFromObject(new int[][]{{y-1, y, y+1},{x-1, x}});
				break;
			case 1 :
				if(y == 0)
					possibleNeighbors = DatasetFactory.createFromObject(new int[][]{{y,y+1},{x, x+1}});
				if(y == shape[1])
					possibleNeighbors = DatasetFactory.createFromObject(new int[][]{{y-1,y},{x, x+1}});
				else
					possibleNeighbors = DatasetFactory.createFromObject(new int[][]{{y-1, y, y+1},{x, x+1}});
				break;
			default:
					possibleNeighbors = DatasetFactory.createFromObject(new int[][]{{y-1, y, y+1},{x-1, x, x+1}});
				break;
		}
		
		
		
		
		Dataset slice = toUse.getSlice(new Slice(possibleNeighbors.getInt(0,0), possibleNeighbors.getInt(0,-1)+1), 
											new Slice(possibleNeighbors.getInt(1,0), possibleNeighbors.getInt(1,-1)+1));
		
		if(x== 4 && y==4)
		{
			System.out.println(slice.toString(true));
		}

		int[] shapeFromSlice = slice.getShape();
		for(int i = 0; i<shapeFromSlice[0]; i++)
		{
			for(int j=0; j<shapeFromSlice[1];j++)
			{
				if(i!=y && j!=x)
				{
					if(slice.getBoolean(i, j))
					{
						retour+=1;
					}
				}
			}
		}		
		return retour;
	}
	
	
	
	public Dataset playNextRound(Dataset toUse)
	{
		int[] shape = toUse.getShape();
		Dataset nextStep = DatasetFactory.createFromObject( new boolean[shape[0]][shape[1]]);
		for(int i = 0; i<shape[0];i++)
		{
			for(int j = 0; j<shape[1];j++)
			{
				int nbVoisins = neighborsCpt(toUse, i, j);
				if(nbVoisins != 0)
					System.out.println("coords : ("+i+","+j+")"+ " nb de voisins : "+nbVoisins);
				if(toUse.getBoolean(i, j))
				{
					if(nbVoisins > 3 && nbVoisins < 1)
						nextStep.set(false, i,j);
				}
				if(!toUse.getBoolean(i,j) && nbVoisins == 3)
					nextStep.set(true, i, j);
				else
					nextStep.set(false, i,j);
			}
		}
		return nextStep;
	}
}
