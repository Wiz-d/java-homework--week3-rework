package robot;

import java.util.Random;

public class Robot {
    private MoodEnum mood;
    private String dayStartPhrase = "";

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

    public void generateMood() {

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

    }

    private void setStartupPhrase() {

        switch (this.mood) {
            case PRODUCTIVITY -> {
                dayStartPhrase = "Работа, работа, работа";
            }
            case TRAGEDY -> {
                dayStartPhrase = "Сега стана страшно";
            }
            case FRIENDLINESS -> {
                dayStartPhrase = "Прекрасен ден птичките пеят";
            }
            case CHAOS -> {
                dayStartPhrase = "Време е да унищожим всички човеци";
            }
            case DEPRESSION -> {
                dayStartPhrase = "Трудно е да си робот :(";
            }
        }

    }

    public void printStartupPhrase(){
        System.out.println(dayStartPhrase);
    }

}
