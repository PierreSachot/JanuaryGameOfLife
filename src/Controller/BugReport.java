package Controller;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;

public class BugReport {
	
	public static void main(String[] args)
	{

		Dataset possiblesNeighbors = DatasetFactory.createFromObject(new int[][]{{0, 1}, {4, 6, 9}});
		System.out.println(possiblesNeighbors.getInt(0,-1));
		//should return 1
		
	}

}
