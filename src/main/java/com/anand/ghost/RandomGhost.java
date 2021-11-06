package com.anand.ghost;

import com.anand.Directions;
import com.anand.Map;
import com.anand.Player;

import javax.swing.*;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomGhost extends Ghost{

    public RandomGhost(int x, int y, ImageIcon image, Map map, String name, Player player) {
        super(x, y, image,  map, name, player);
    }



    //from the list of allowed directions belonging to the super class, return a random direction
    public Directions getMove(){
        List<Directions> possible = this.getAllowedMoves();
        Random rand = new Random();
        int maxIndex = possible.size();
        int index = rand.nextInt(maxIndex);
        //System.out.println(possible);
        //System.out.println(index);
        return possible.get(index);
    }
}
