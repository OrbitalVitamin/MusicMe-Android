package com.example.musicme.utilities;

import com.example.musicme.objects.QuestionAttributes;
import com.example.musicme.objects.QuestionNameHolder;
import com.example.musicme.objects.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SongFilterUtilities {
    public static List<QuestionAttributes> populateHolder(List<Song> songs, int limit) {


        List<QuestionAttributes> attributesList = new ArrayList<>();
        Random random = new Random();
        List<Song> correctSongs = selectRandomSongs(songs, limit);
        for(int i = 0; i< songs.size(); i++) {
            String[] songArr = new String[4];
            String[] artistArr = new String[4];
            List<Song> newRound = new ArrayList<>();
            for(int j = 0; j < songs.size(); j++){
                newRound.add(songs.get(j));
            }

            List<Integer> indexs = new ArrayList<>();
            indexs.add(0);
            indexs.add(1);
            indexs.add(2);
            indexs.add(3);

            Song correctSong = correctSongs.get(i);
            int correctIndex = random.nextInt(indexs.size());
            indexs.remove(Integer.valueOf(correctIndex));
            newRound.remove(correctSong);
            songArr[correctIndex] = correctSong.getName();
            artistArr[correctIndex] = correctSong.getArtist();
            for (int e = 0; e < 3; e++) {

                int someIndex = random.nextInt(newRound.size());
                songArr[indexs.get(e)] = newRound.get(someIndex).getName();
                artistArr[indexs.get(e)] = newRound.get(someIndex).getArtist();
                newRound.remove(someIndex);
            }


            attributesList.add(new QuestionAttributes(correctIndex+1, new QuestionNameHolder(songArr[0], songArr[1], songArr[2], songArr[3]), songArr[correctIndex]));

        }





        return attributesList;
    }

    public static void avoidingPointers(List<Song> songs, List<Song> newRound){

        for(int i = 0; i< songs.size(); i++) {
            newRound.add(songs.get(i));
        }

    }

    public static List<Song> selectRandomSongs(List<Song> songs, int select){
        List<Song> newSongs = new ArrayList<>();
        Random ran = new Random();

        for(int i = 0; i< select; i++){
            int index = ran.nextInt(songs.size());
            newSongs.add(songs.get(i));
            songs.remove(i);

        }
        return newSongs;
    }
}
