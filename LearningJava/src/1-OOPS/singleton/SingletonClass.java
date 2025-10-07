package singleton;

// A Singleton ensures only one instance of the class exists throughout the application. Typically, it includes:
// A private static instance variable.
// A private constructor.
// A public static method to get the instance.

public class SingletonClass {
    // Private static instance of the class
    private static SingletonClass instance;

    // Private constructor to prevent instantiation
    private SingletonClass() {}

    // Public static method to provide access to the instance
    public static SingletonClass getInstance() {
        if (instance == null) {
            instance = new SingletonClass();
        }
        return instance;
    }
}