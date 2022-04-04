package hu.unideb.inf.homeworkproject.model;

public sealed interface IGameModel permits GameModel {
    default void alert(String message) {
        System.out.println("No message.");
    }
}
