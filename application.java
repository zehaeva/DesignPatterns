import java.util.ArrayList;

public class application {
	public static void main(String args[]) {
		MazeFactory fac = new MazeFactory();
		EnchantedMazeFactory efac = new EnchantedMazeFactory();

		Maze maze = MazeGame.CreateMaze(fac);

		System.out.println("Regular Maze Room Count");
		System.out.println(maze.RoomCount());

		maze = MazeGame.CreateMaze(efac);

		System.out.println("Enchanted Maze Room Count");
		System.out.println(maze.RoomCount());
	}
}


enum Direction {
North, South, East, West
}

class MapSite {
	public void Enter() {}
}

class Room extends MapSite {
	private int _room_number;
	private MapSite[] _sides;

	public Room(int roomno) {
		this._room_number = roomno;
		this._sides = new MapSite[4];
	}

	public int RoomNo() {
		return this._room_number;
	}
	public void SetSide(Direction dir, MapSite side) {
		this._sides[dir.ordinal()] = side;
	}
	public MapSite GetSide(Direction dir) {
		return this._sides[dir.ordinal()];
	}
}

class EnchantedRoom extends Room {
	public boolean Enchanted;
	public EnchantedRoom(int roomno) {
		super(roomno);
	}
}

class Wall extends MapSite {
}

class Door extends MapSite {
	private MapSite _room_one;
	private MapSite _room_two;
	private boolean _is_open;

	public Door(Room one, Room two) {
		this._room_one = one;
		this._room_two = two;
	}
}	

class Maze {
	private ArrayList<Room> _rooms;

	public Maze() {
		this._rooms = new ArrayList<>();
	}

	public void AddRoom(Room room) {
		this._rooms.add(room);
	}
	
	public int RoomCount() {
		return this._rooms.size();
	}
}

class MazeFactory {
	public Maze MakeMaze() {
		return new Maze();
	}
	public Room MakeRoom(int roomNo) {
		return new Room(roomNo);
	}
	public Wall MakeWall() {
		return new Wall();
	}
	public Door MakeDoor(Room one, Room two) {
		return new Door(one, two);
	}	
}

class EnchantedMazeFactory extends MazeFactory {
	public EnchantedRoom MakeRoom(int roomNo) {
		return new EnchantedRoom(roomNo);
	}
}

class MazeGame {
	public static Maze CreateMaze(MazeFactory factory) {
		Maze maze = factory.MakeMaze();
		Room one = factory.MakeRoom(0);
		Room two = factory.MakeRoom(1);
		Door door = factory.MakeDoor(one, two);

		maze.AddRoom(one);
		maze.AddRoom(two);

		one.SetSide(Direction.North, factory.MakeWall());
		one.SetSide(Direction.East, door);
		one.SetSide(Direction.South, factory.MakeWall());
		one.SetSide(Direction.West, factory.MakeWall());

		two.SetSide(Direction.North, factory.MakeWall());
		two.SetSide(Direction.East, factory.MakeWall());
		two.SetSide(Direction.South, factory.MakeWall());
		two.SetSide(Direction.West, door);

		return maze;
	}
}
