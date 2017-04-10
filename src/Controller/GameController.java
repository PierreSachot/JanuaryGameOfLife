package Controller;

import java.awt.Dimension;
import java.util.Arrays;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.PositionIterator;
import org.eclipse.january.dataset.Random;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.SliceIterator;

public class GameController {
	
	private Dataset cells;
	
	public GameController()
	{
		cells = DatasetFactory.createFromObject(new boolean[][] 	{{false, false, false, false, false, false, false, false, false, false},
																{false, false, false, false, false, false, false, false, false, false},
																{false, false, false, false, false, false, false, false, false, false},
																{false, false, false, false, true, false, false, false, false, false},
																{false, false, false, true, true, true, false, false, false, false},
																{false, false, false, true, false, true, false, false, false, false},
																{false, false, false, false, true, false, false, false, false, false},
																{false, false, false, false, false, false, false, false, false, false},
																{false, false, false, false, false, false, false, false, false, false},
																{false, false, false, false, false, false, false, false, false, false}});
	}
	
	public Dataset getDatas()
	{
		return this.cells;
	}
	
	private int neighborsCpt(int y, int x)
	{
		int retour = 0;
		Dataset possibleNeighbors;
		int [] shape = this.cells.getShape();
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
		
		
		
		
		Dataset slice = this.cells.getSlice(new Slice(possibleNeighbors.getInt(0,0), possibleNeighbors.getInt(0,-1)+1), 
											new Slice(possibleNeighbors.getInt(1,0), possibleNeighbors.getInt(1,-1)+1));

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
	
	
	
	public void playNextRound()
	{
		int[] shape = cells.getShape();
		Dataset nextStep = DatasetFactory.createFromObject( new boolean[shape[0]][shape[1]]);
		for(int i = 0; i<shape[0];i++)
		{
			for(int j = 0; j<shape[1];j++)
			{
				int nbVoisins = neighborsCpt(i, j);
				if(nbVoisins != 0)
					System.out.println("coords : ("+i+","+j+")"+ " nb de voisins : "+nbVoisins);
				if(cells.getBoolean(i, j))
				{
					if(nbVoisins > 3 && nbVoisins < 1)
						nextStep.set(false, i,j);
				}
				if(!cells.getBoolean(i,j) && nbVoisins == 3)
					nextStep.set(true, i, j);
				else
					nextStep.set(cells.getBoolean(i,j), i,j);
			}
		}
		this.cells = nextStep;
	}
}
