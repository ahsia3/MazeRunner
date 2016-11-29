import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.*;




/**
 * Authors: Harsh Bhatt, Anthony Hsia, Anthony Lie, Abdullah Khan
 * Credit to Princeton University's maze.java and Hobbart and William
 * Smith Colleges maze.java for helping us on hoe to create a maze.
 * The purpose of this program is to create a maze by a user specified
 * dimension, and then solve the maze using a DFS algorithm. The program will
 * end once it has finished locating a path, and a completion time value 
 * will be stored.
 */


public class Maze extends JPanel implements Runnable{
	static Scanner scan = new Scanner(System.in);
    static JFrame window = new JFrame("DFS Random Maze Solve");
    
    	//To reference dimension (user inputed) from StyleOptionsPanel class to Maze class.
     static int b;
     static int c;  
     static int temp1;
     static int temp2;
     static int counter;
    // Main method to run the program, 
	//PRECONDITION: user must enter integer dimensions
	//POST CONDITION: creates a maze of specified dimension and solves using DFS.
    public static void main(String[] args){
    	System.out.println("Enter numbers for rows and columns: ");
    	b=scan.nextInt();
    	c=scan.nextInt(); 
    	if(b % 2 == 0){
    		temp1=b+3;
    	}
        else{
        	temp1=b+2;
        }
    	if(c % 2 == 0){
    		temp2=c+3;
    	}
    	else{
    		temp2 = c+2;
    	}
    	File.main(args);
    	
    	
        window.setContentPane(new Maze());
        window.pack();
        window.setLocation(120, 80);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
       
    }

    int[][] maze;   // This will create a state of a maze. A maze[i][j] can be
    				// used to represent a wall, path, empty, or visited
    				// section of a maze.
                    //    A maze is supposed to contain a set number of walls
    				// and corridors. The value for maze[i][j] is either going 
                    // to be a wall or a corridor based on this definition.
    				// The current path of the maze will contain a cell, a cell
                    // will be labeled as visited if it has already been 
    				// explore, or empty if it is undiscovered.
    
    //These are the necessary valued needed to create a state of a maze.
    final static int bgcode = 0;
    final static int wall = 1;
    final static int path = 2;
    final static int empty = 3;
    final static int visited = 4;


    Color[] color;          // an array of colors to be used for our maze values defined earlier.
    

    int border = 0;         // number of pixels separating maze and non-maze parts  
    int solvespeed = 10;    // we can adjust this value to make is easier to see how the maze is solving.
    int createspeed = 2;	// the same speed adjustment, but for the creation of the maze
    int cellsize = 12;     // the cell size
    int width = -1;   // width of the panel holding the maze, created with identifysize()
    int height = -1;  // height of the panel holding the maze, created with identifysize()
    int rows = temp1;          // the row of cells in a maze, including the walls
    int columns = temp2;       // number of column cells in a maze, including the walls
    int widthtotal;   // same width as before, but without the border area.
    int heighttotal;  // same height as before, but without border area
    int left;         // border for the left side of the maze
    int top;          // border for top edge of the maze

    boolean mazeExists = false; // will allow us to know if a maze is successfully created; used in
    							// createmaze() and redrawmaze()
                                

    // We can retrieve user input for the size of the maze they wish to solve.

    // Maze constructor

 public static Thread t;
	public Maze() {
        color = new Color[] {
            new Color(0,0,0),
            new Color(0,51,153),
            new Color(200,0,0),
            new Color(255,255,255),
            new Color(100,100,100)
        };
        setBackground(color[bgcode]);
        setPreferredSize(new Dimension(cellsize*columns, cellsize*rows));
      t=new Thread(this);

    }

    // Will prevent paint from being called two times, this method paints the maze and helps with Drawing
    // PRECONDITION: need a size for the maze
    // POSTCONDITION: will paint a maze with the color based on the size.
    synchronized protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        identifysize();
        redrawMaze(g);
    }
    
    // We need to know the size in order to draw the maze.
    void identifysize() {
        // Again, this must be called before drawing so that the maze can be painted successfully.
    	if (getWidth() != width || getHeight() != height) {
    		width  = getWidth();
    		height = getHeight();
    		int w = (width - 2*border) / columns;
    		int h = (height - 2*border) / rows;
    		left = (width - w*columns) / 2;
    		top = (height - h*rows) / 2;
    		widthtotal = w*columns;
    		heighttotal = h*rows; 
    	}
    }

    void CreateMaze() {
            // The purpose of this method is to create a random maze.
    		// We do this by imagining a set of disconnected 
    		// rooms that are separated by walls. We look at each of 
    		// the separating walls in a random order. Tear down the walls
    		// as long as it does not create a LOOP in the maze.
        
        if (maze == null){
            maze = new int[rows][columns];
        }
        int i,j;
        int numrooms = 0; // number of rooms as described earlier
        int numwalls = 0;  // number of walls as described earlier
        int[] rowwalls = new int[(rows*columns)/2];  // this will position our walls between the rooms
        int[] colwalls = new int[(rows*columns)/2];
        for (i = 0; i<rows; i++){  // we must begin with everything being a wall
            for (j = 0; j < columns; j++){
                maze[i][j] = wall;
            }
        }
        for (i = 1; i<rows-1; i += 2){  // we must make sure to have a grid of empty rooms
            for (j = 1; j<columns-1; j += 2) {
                numrooms++;
                maze[i][j] = -numrooms;  // we can represent these rooms with a nonnegative number
                if (i < rows-2) {  // this gets information about a wall below a room
                    rowwalls[numwalls] = i+1;
                    colwalls[numwalls] = j;
                    numwalls++;
                }
                if (j < columns-2) {  // retrieves information about a wall to the right of this room
                    rowwalls[numwalls] = i;
                    colwalls[numwalls] = j+1;
                    numwalls++;
                }
            }
        }
        mazeExists = true;
        repaint();
        int r;
        for (i=numwalls-1; i>0; i--) {
            r = (int)(Math.random() * i);  // randomly chooses a wall and may or may not tear it down
            tearDown(rowwalls[r],colwalls[r]);
            rowwalls[r] = rowwalls[i];
            colwalls[r] = colwalls[i];
        }
        for (i=1; i<rows-1; i++){  // replace nonnegative values in a maze[][] with empty instead
            for (j=1; j<columns-1; j++){
                if (maze[i][j] < 0)
                    maze[i][j] = empty;
            }
        }
    }
    // used to make sure tearDown() is not being run twice at the same time
    synchronized void tearDown(int row, int column) {
    		// The purpose of this method is to tear down random walls unless doing so will
    		// for a LOOP. This action will merge two rooms, because there will be 
    		// no wall between them. As this method continues to perform on a maze, 
    		// rooms will begin to look like corridors. When two rooms combine, 
    		// their codes must match. The cells in a room must have the same code.
    		// The way we can know that tearing down a wall will create a loop is
    		// if the room codes before tearing down that wall are already the same.
    		// In this case, leave the wall alone.
           
        if (row % 2 == 1 && maze[row][column-1] != maze[row][column+1]) {
            // if the row is odd, the walls will separate the rooms horizontally.
            fill(row, column-1, maze[row][column-1], maze[row][column+1]);
            maze[row][column] = maze[row][column+1];
            repaint();
            try { wait(createspeed); }
            catch (InterruptedException e) { }
        }else if (row % 2 == 0 && maze[row-1][column] != maze[row+1][column]) {
            // if a row is even, the walls will separate the rooms vertically.
            fill(row-1, column, maze[row-1][column], maze[row+1][column]);
            maze[row][column] = maze[row+1][column];
            repaint();
            try { wait(createspeed); }
            catch (InterruptedException e) { }
        }
    }

    void fill(int row, int column, int replace, int replaceWith) {
            // this method is called by tearDown to change the room codes 
        if (maze[row][column] == replace) {
            maze[row][column] = replaceWith;
            fill(row+1,column,replace,replaceWith);
            fill(row-1,column,replace,replaceWith);
            fill(row,column+1,replace,replaceWith);
            fill(row,column-1,replace,replaceWith);
        }
    }
    boolean MazeSolver(int row, int col) {
    		// Solves the maze with a DFS implementation. Returns true if a 
    		// solution to this maze is found. We considered a maze to be 
    		// solved it it reaches the lower right cell.
    	if (maze[row][col] == empty) {
            maze[row][col] = path;      // adds a cell to a path
            repaint();
            if (row == rows-2 && col == columns-2){
                return true;  // the path has successfully reached its goal
            }
            try {Thread.sleep(solvespeed); }
            catch (InterruptedException e) { }
            if (MazeSolver(row-1,col)	||	MazeSolver(row,col-1) ||	MazeSolver(row+1,col) ||	MazeSolver(row,col+1)){   
            	// this will try to solve the maze by extending the path
                // in each direction it can
            	counter++;
            	return true;
            }
            // we have reached a dead end, backtrack
            maze[row][col] = visited;   // this will mark visited cells.
            repaint();
            
        }
        return false;
    }
    void redrawMaze(Graphics g) {
        // The purpose of this method is to draw the maze.
    	if (mazeExists) {
            int w = widthtotal / columns;  // width of the maze cell
            int h = heighttotal / rows;    //height of the maze cell
            for (int j=0; j<columns; j++){
                for (int i=0; i<rows; i++) {
                    if (maze[i][j] < 0)
                        g.setColor(color[empty]);
                    else
                        g.setColor(color[maze[i][j]]);
                    g.fillRect( (j * w) + left, (i * h) + top, w, h );
                }
            }
        }
    }

   public void Thread(){
	 t.start();
   }
  
    public void run() {
        //	the purpose of this method is to create a thread that creates a maze and solves it.
        CreateMaze();
       
        MazeSolver(1,1);
        System.out.println("Maze has been solved! The travel length of the solve is: "+counter);
    }
  
}