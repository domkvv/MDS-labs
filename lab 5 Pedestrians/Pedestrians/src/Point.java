import java.util.ArrayList;

public class Point {

	public ArrayList<Point> neighbors;
	public static Integer []types ={0,1,2,3};
	public int type;
	public int staticField;
	public boolean isPedestrian;
	boolean blocked = false;

	public Point() {
		type=0;
		staticField = 100000;
		neighbors= new ArrayList<Point>();
	}
	
	public void clear() {
		staticField = 100000;
	}

	public boolean calcStaticField() {
		int minField = Integer.MAX_VALUE;
		for(Point i : neighbors){
			minField = Math.min(minField, i.staticField+1);
		}
		if(this.staticField > minField){
			this.staticField = minField;
			return true;
		}
		return false;
	}
	
	public void move(){
		if(this.isPedestrian && !this.blocked){
			int minField = Integer.MAX_VALUE;
			int j = 0, s = -1;
			for(Point i : neighbors){
				if(minField >= i.staticField && (i.type == 0 || i.type == 2) && !i.isPedestrian){
					minField = i.staticField;
					s = j;
				}
				j++;
			}

			if(s != -1){
				if(neighbors.get(s).type == 0){
					neighbors.get(s).isPedestrian = true;
					neighbors.get(s).blocked = true;
					neighbors.get(s).type = 3;
				}
				this.type = 0;
				this.isPedestrian = false;
			}
		}

	}

	public void addNeighbor(Point nei) {
		neighbors.add(nei);
	}
}