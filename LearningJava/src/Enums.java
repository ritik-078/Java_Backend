public class Enums {
	// Example usage of an enum
	public static void main(String[] args) {
		Day today = Day.MONDAY;
		System.out.println("Today is: " + today);

		// Using enum in switch
		switch (today) {
			case MONDAY:
				System.out.println("Start of the week!");
				break;
			case FRIDAY:
				System.out.println("Almost weekend!");
				break;
			default:
				System.out.println("Regular day.");
		}

//    1. Enums are special classes that represent a group of constants (unchangeable variables).
//    2. Enums improve type safety, readability, and maintainability.
//    3. Enums can have fields, constructors, and methods.
//    4. Commonly used for representing fixed sets like days, directions, states, etc.
 
        Status taskStatus = Status.IN_PROGRESS;
        Status.values(); // returns an array of all enum constants
        System.out.println("All Statuses: " + Status.values());
        System.out.println("Task Status: " + taskStatus);

        Level currentLevel = Level.MEDIUM;
        System.out.println("Current Level: " + currentLevel + ", Code: " + currentLevel.getCode());
	}
}


// Enum with parameters
enum Level {
	LOW(1),
	MEDIUM(2),
	HIGH(3);

	private int code;

	Level(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

    public void setCode(int code) {
        this.code = code;
    }
    
}

// ...existing code...

enum Status{
    NEW, IN_PROGRESS, COMPLETED, CLOSED
}
