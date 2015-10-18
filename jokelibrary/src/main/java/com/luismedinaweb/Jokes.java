package com.luismedinaweb;

public class Jokes {

    private static final String[] jokes = new String[]{
            "Why did the duck go to rehab? Because he was a quack addict!",
            "How do you make holy water? You boil the hell out of it.",
            "What do you call a bear with no teeth? -- A gummy bear!",
            "I wondered why the frisbee was getting bigger, and then it hit me.",
            "I used to like my neighbors, until they put a password on their Wi-Fi."
    };

    private static int nextJoke = 0;

    public static String generateJoke(){
        if(nextJoke == jokes.length){
            nextJoke = 0;
        }
        return jokes[nextJoke++];
    }
}
