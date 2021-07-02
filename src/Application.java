import robot.Robot;

public class Application {
    
    public static void main(String[] args) {
        Robot banichkaRobot = new Robot();
        banichkaRobot.bootUp();
        banichkaRobot.robotMove();
        banichkaRobot.pickUpBanichka();
        banichkaRobot.robotMove();
    }

}
