package _04_Maze_Maker;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;


public class MazeMaker{
	
	private static int width;
	private static int height;
	
	private static Maze maze;
	
	private static Random randGen = new Random();
	private static Stack<Cell> uncheckedCells = new Stack<Cell>();
	
	
	public static Maze generateMaze(int w, int h){
		width = w;
		height = h;
		maze = new Maze(width, height);
		
		//4. select a random cell to start
		
		int x = randGen.nextInt(w);
		int y = randGen.nextInt(h);
		Cell randCell = new Cell(x,h);
		
		//5. call selectNextPath method with the randomly selected cell
		selectNextPath(randCell);
		return maze;
	}

	//6. Complete the selectNextPathMethod
	private static void selectNextPath(Cell currentCell) {
		//A. mark cell as visited
		currentCell.setBeenVisited(true);
		//B. Get an ArrayList of unvisited neighbors using the current cell and the method below
		ArrayList<Cell> unvisitedCell = getUnvisitedNeighbors(currentCell);
		//C. if has unvisited neighbors,
		if(getUnvisitedNeighbors(currentCell).size()>0) {
			//C1. select one at random.
			Random rand = new Random();
			int n = rand.nextInt(getUnvisitedNeighbors(currentCell).size());
			
			//C2. push it to the stack
			uncheckedCells.push(unvisitedCell.get(n));
			//C3. remove the wall between the two cells
			removeWalls(currentCell, unvisitedCell.get(n));
			//C4. make the new cell the current cell and mark it as visited
			currentCell=unvisitedCell.get(n);
			unvisitedCell.get(n).setBeenVisited(true);
			//C5. call the selectNextPath method with the current cell
			selectNextPath(currentCell);
		}
		//D. if all neighbors are visited
		if(getUnvisitedNeighbors(currentCell).size()==0) {
			//D1. if the stack is not empty
			if(uncheckedCells.isEmpty()==false) {
				// D1a. pop a cell from the stack
				Cell popped = uncheckedCells.pop();
				// D1b. make that the current cell
				currentCell= popped;
				// D1c. call the selectNextPath method with the current cell
				selectNextPath(currentCell);
			}
		}
	}

	//7. Complete the remove walls method.
	//   This method will check if c1 and c2 are adjacent.
	//   If they are, the walls between them are removed.
	private static void removeWalls(Cell c1, Cell c2) {
		int x1 = c1.getX();
		int x2 = c2.getX();
		int y1 = c1.getY();
		int y2 = c2.getY();
		if (x1-x2 == 1 && y1 == y2) {
			c2.setEastWall(false);
			c1.setWestWall(false);
		}
		else if (x2-x1 == 1 && y1 == y2) {
			c1.setEastWall(false);
			c2.setWestWall(false);
		}
		else if (x1 == x2 && y1-y2 == 1) {
			c1.setNorthWall(false);
			c2.setSouthWall(false);
		}
		else if (x1 == x2 && y2-y1 == 1) {
			c2.setNorthWall(false);
			c1.setSouthWall(false);
		}
	}
	
	//8. Complete the getUnvisitedNeighbors method
	//   Any unvisited neighbor of the passed in cell gets added
	//   to the ArrayList
	private static ArrayList<Cell> getUnvisitedNeighbors(Cell c) {
		ArrayList<Cell> unvisited = new ArrayList<Cell>();
		int x = c.getX();
		int y = c.getY();
		if(x!=0 && maze.getCell(x-1, y).hasBeenVisited()==false){
			unvisited.add(maze.getCell(x-1, y));
		}
		if(y!=0 && maze.getCell(x, y-1).hasBeenVisited()==false) {
			unvisited.add(maze.getCell(x, y-1));
		}
		if(x<maze.cell.length-1 && maze.getCell(x+1, y).hasBeenVisited()==false) {
			unvisited.add(maze.getCell(x+1, y));
		}
		if(y<maze.cell[0].length-1 && maze.getCell(x, y+1).hasBeenVisited()==false) {
			unvisited.add(maze.getCell(x, y+1));
		}
		
		return unvisited;
	}
	
}
