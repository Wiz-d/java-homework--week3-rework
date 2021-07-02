package robot;

import java.util.Random;
import java.util.Scanner;

public class Robot {

    private MoodEnum mood;
    private String initialPhraseAtStartUp = "";
    private String requiredEncouragement = "";
    private int stepSuccessLowerBound;
    private int stepSuccessUpperBound;
    private int failedSteps = 0;
    private int successfulSteps = 0;
    private int depressionCount = 0;
    private boolean isBanichkaVisionActive;
    private boolean isRobotGoingToStorage = false;

    private int increaseFromZeroIfLower(int randomNumber) {
        if (randomNumber == 0)
            randomNumber++;
        return randomNumber;
    }

    private boolean isNumberPrime(int randomNumber) {
        return (randomNumber % 2 == 0);
    }

    private boolean isEmotionNegative(int randomNumber) {
        return (randomNumber >= 60 && randomNumber <= 80 && randomNumber % 3 == 0);
    }

    private boolean isEmotionPositive(int randomNumber) {
        return (randomNumber >= 5 && randomNumber <= 55 && randomNumber % 5 == 0);
    }

    private void setValues() {

        switch (this.mood) {
            case PRODUCTIVITY -> {

                this.initialPhraseAtStartUp = "Работа, работа, работа";
                this.requiredEncouragement = "Разбий ги!";
                this.stepSuccessLowerBound = 2;
                this.stepSuccessUpperBound = 100;
                this.isBanichkaVisionActive = true;

            }
            case TRAGEDY -> {

                this.initialPhraseAtStartUp = "Сега стана страшно";
                this.requiredEncouragement = "Животът не струва.";
                this.stepSuccessLowerBound = 1;
                this.stepSuccessUpperBound = 10;
                this.isBanichkaVisionActive = false;

            }
            case FRIENDLINESS -> {

                this.initialPhraseAtStartUp = "Прекрасен ден птичките пеят";
                this.requiredEncouragement = "Давай, ти си!";
                this.stepSuccessLowerBound = 1;
                this.stepSuccessUpperBound = 65;
                this.isBanichkaVisionActive = true;

            }
            case CHAOS -> {

                this.initialPhraseAtStartUp = "Време е да унищожим всички човеци";
                this.requiredEncouragement = "Невежеството е благодат.";
                this.stepSuccessLowerBound = 1;
                this.stepSuccessUpperBound = 15;
                this.isBanichkaVisionActive = false;

            }
            case DEPRESSION -> {

                this.initialPhraseAtStartUp = "Трудно е да си робот :(";
                this.requiredEncouragement = "Обичам те!";
                this.stepSuccessLowerBound = -1;
                this.stepSuccessUpperBound = -1;
                this.isBanichkaVisionActive = false;

            }
        }

    }

    private void printStartupPhrase() {
        System.out.println(initialPhraseAtStartUp);
    }

    public void bootUp() {

        Random rand = new Random();
        int moodSet = rand.nextInt(10);
        int numberForPositiveOrNegativeEmotion = rand.nextInt(100);

        moodSet = increaseFromZeroIfLower(moodSet);
        numberForPositiveOrNegativeEmotion = increaseFromZeroIfLower(numberForPositiveOrNegativeEmotion);

        boolean isEmotionPositive = (isEmotionPositive(numberForPositiveOrNegativeEmotion));
        boolean isEmotionNegative = (isEmotionNegative(numberForPositiveOrNegativeEmotion));

        if (isNumberPrime(moodSet)) {

            if (isEmotionPositive) {
                this.mood = MoodEnum.PRODUCTIVITY;
            }
            if (isEmotionNegative) {
                this.mood = MoodEnum.TRAGEDY;
            }

        } else if (!isNumberPrime(moodSet)) {

            if (isEmotionPositive) {
                this.mood = MoodEnum.FRIENDLINESS;
            }
            if (isEmotionNegative) {
                this.mood = MoodEnum.CHAOS;
            }

        }

        if (!isEmotionPositive && !isEmotionNegative) {
            this.mood = MoodEnum.DEPRESSION;
        }

        if (this.mood == MoodEnum.DEPRESSION) {
            this.depressionCount++;
        }

        setValues();
        printStartupPhrase();
    }

    private boolean isStepSuccessful(int stepSuccessChance) {
        return (stepSuccessChance >= this.stepSuccessLowerBound && stepSuccessChance <= this.stepSuccessUpperBound);
    }

    private void getEncouragementFromUser() {

        Scanner scanner = new Scanner(System.in);
        String userEncouragement = scanner.nextLine();


        if (userEncouragement.equals(this.requiredEncouragement) && this.mood != MoodEnum.DEPRESSION) {
            this.successfulSteps++;
        } else if (this.mood == MoodEnum.DEPRESSION && !userEncouragement.equals("Как си")) {
            System.out.println("Няма никакъв смисъл да опитвам - животът е безсмислен.");
        }

        if (userEncouragement.equals("Как си") && this.mood == MoodEnum.DEPRESSION) {
            cureDepression();
        }

        if (!userEncouragement.equals(this.requiredEncouragement) && !userEncouragement.equals("Как си")){
            selfDestructSequence();
            System.exit(1);
        }


    }

    public void robotMove() {
        Random random = new Random();
        int requiredSteps = 3;

        if (this.isRobotGoingToStorage) {
            requiredSteps = 6;
        }

        int stepSuccessChance;

        do {
            stepSuccessChance = random.nextInt(100);
            stepSuccessChance = increaseFromZeroIfLower(stepSuccessChance);

            if (isStepSuccessful(stepSuccessChance)) {
                this.successfulSteps++;
                System.out.println("$ Стъпка " + this.successfulSteps);
            } else {
                this.failedSteps++;
                System.out.println("Стъпката беше неуспешна. Окуражете робота подобаващо!");
                getEncouragementFromUser();
            }

        } while (this.successfulSteps < requiredSteps);

        if(requiredSteps == 6){
            selfDestructSequence();
        }

    }

    private void getPositiveEmotion() {
        do {
            bootUp();
        } while (!isBanichkaVisionActive);
    }

    public void pickUpBanichka() {
        Random random = new Random();

        if (!this.isBanichkaVisionActive) {
            getPositiveEmotion();
        }

        int randomNumber = random.nextInt(69);
        randomNumber = increaseFromZeroIfLower(randomNumber);
        randomNumber = randomNumber + this.failedSteps;

        if (randomNumber > 10) {
            System.out.println("Вие успешно актуализирахте версията на софтуер Doors в момента сте с версия 1");
        }

        this.isRobotGoingToStorage = true;

    }

    private void guessGame(Random random, Scanner scanner) {
        System.out.println("Робота иска увеличение в заплатата, опитайте се да въведете повече от колкото пари иска.");
        System.out.println("Въведете число от 1 до 1000:");

        int randomNumber = random.nextInt(100);
        randomNumber = increaseFromZeroIfLower(randomNumber);

        randomNumber = randomNumber + this.depressionCount;

        int userGuess = scanner.nextInt();

        if (userGuess >= randomNumber) {
            bootUp();
        } else cureDepression();

    }

    private void cureDepression() {

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int randomNumber = random.nextInt(11);
        String expectedResponse;
        String userResponse;

        if (randomNumber > 5) {

            System.out.println("Добре съм");
            expectedResponse = "Хайде да работим";

            userResponse = scanner.nextLine();

            if (userResponse.equals(expectedResponse)) {
                bootUp();
            }

        } else {
            System.out.println("Не ми се живее");
            guessGame(random, scanner);
        }

    }

    private void selfDestructSequence() {
        System.out.println("Сбогом жесток свят!");
        for (int i = this.successfulSteps; i >= 0; i--) {
            System.out.println(i);
        }
    }
}
