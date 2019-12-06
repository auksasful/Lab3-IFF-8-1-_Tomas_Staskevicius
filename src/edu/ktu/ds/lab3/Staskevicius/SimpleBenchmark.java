/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ktu.ds.lab3.Staskevicius;


import edu.ktu.ds.lab3.gui.ValidationException;
import edu.ktu.ds.lab3.utils.HashMap;
import edu.ktu.ds.lab3.utils.HashType;
import edu.ktu.ds.lab3.utils.Ks;
import edu.ktu.ds.lab3.utils.ParsableHashMap;
import edu.ktu.ds.lab3.utils.ParsableMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tomas
 */
public class SimpleBenchmark {
     public static final String FINISH_COMMAND = "                               ";
    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("edu.ktu.ds.lab3.Manogui.messages");

    private final Timekeeper timekeeper;

    private final String[] BENCHMARK_NAMES = {"putHash", "putHashOa", "containsJavaHash", "containsHash", "hashRemove", "genericHashRemove"};
    private final int[] COUNTS = {10000, 20000, 40000, 80000};

    private final Queue<String> chainsSizes = new LinkedList<>();
    
    private final HashMapOa<String, String> doubleHash = new HashMapOa<>();
    private final HashMap<String, String> singleHash = new HashMap<>();
    private final java.util.HashMap<String, String> genericHash = new java.util.HashMap<>();

    /**
     * For console benchmark
     */
    public SimpleBenchmark() {
        timekeeper = new Timekeeper(COUNTS);
    }

    /**
     * For Gui benchmark
     *
     * @param resultsLogger
     * @param semaphore
     */
    public SimpleBenchmark(BlockingQueue<String> resultsLogger, Semaphore semaphore) {
        semaphore.release();
        timekeeper = new Timekeeper(COUNTS, resultsLogger, semaphore);
    }

    public static void main(String[] args) {
        executeTest();
    }

    public static void executeTest() {
        // suvienodiname skaičių formatus pagal LT lokalę (10-ainis kablelis)
        Locale.setDefault(new Locale("LT"));
        Ks.out("Greitaveikos tyrimas:\n");
        new edu.ktu.ds.lab3.Staskevicius.SimpleBenchmark().startBenchmark();
    }

    public void startBenchmark() {
        try {
            benchmark();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    public void benchmark() throws InterruptedException {
        
          Runtime runtime = Runtime.getRuntime();
        try {
            chainsSizes.add(MESSAGES.getString("maxChainLength"));
            chainsSizes.add("   kiekis      " + BENCHMARK_NAMES[0] + "   " + BENCHMARK_NAMES[1]);
            for (int k : COUNTS) {
                
                

                doubleHash.clear();
                singleHash.clear();
                genericHash.clear();
                timekeeper.startAfterPause();
                
                read(singleHash, null, null, 0, k);
                read(null,doubleHash,null,1,k);
                read(null,null,genericHash,2,k);
                
                Object[] set = singleHash.keySet().toArray();
               
                
                timekeeper.start();
                
                 long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
                 for (int i = 0; i < k; i++) {
                  singleHash.put((String)set[i],(String)set[i]);
                }
                 long usedMemory = (runtime.totalMemory() - runtime.freeMemory()) - usedMemoryBefore;
                 timekeeper.finish(BENCHMARK_NAMES[0]);
                
                 
                 set = doubleHash.keySet().toArray();
                
               // timekeeper.start();
                usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
                for (int i = 0; i < k; i++) {
                doubleHash.put((String)set[i],(String)set[i]);
                }
                 long usedMemory2 = (runtime.totalMemory() - runtime.freeMemory()) - usedMemoryBefore;
                 timekeeper.finish(BENCHMARK_NAMES[1]);
                
                 //set = singleHash.keySet().toArray();
                 
                 String key = Integer.toString(k/2);
                  
               // timekeeper.start();
               usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
                genericHash.containsKey(key);
                genericHash.containsValue(key);
                 long usedMemory3 = (runtime.totalMemory() - runtime.freeMemory()) - usedMemoryBefore;
                timekeeper.finish(BENCHMARK_NAMES[2]);
                 
                  
                 usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
               // timekeeper.start();
                
                singleHash.contains(key); 
                 long usedMemory4 = (runtime.totalMemory() - runtime.freeMemory()) - usedMemoryBefore;
                timekeeper.finish(BENCHMARK_NAMES[3]);
                
                
               Object[] setRemove = singleHash.keySet().toArray();
                
                
               usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory(); 
               for(int i = 0; i < setRemove.length; i++){
                    singleHash.remove((String)setRemove[i]);
               }
               
               long usedMemory5 = (runtime.totalMemory() - runtime.freeMemory()) - usedMemoryBefore;
               timekeeper.finish(BENCHMARK_NAMES[4]);
                
               setRemove = genericHash.keySet().toArray();
               usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
                 for(int i = 0; i < setRemove.length; i++){
                    genericHash.remove((String)setRemove[i]);
               }
               long usedMemory6 = (runtime.totalMemory() - runtime.freeMemory()) - usedMemoryBefore;
               timekeeper.finish(BENCHMARK_NAMES[5]);
               
                 System.out.println("Memory consumption: 1) " + usedMemory + " 2) " +
                     usedMemory2 + " 3) " + usedMemory3 + " 4) " + usedMemory4 + " 5) " + usedMemory5 + " 6) " + usedMemory6);
                 
                 timekeeper.seriesFinish();
            }

            StringBuilder sb = new StringBuilder();
            chainsSizes.forEach(p -> sb.append(p).append(System.lineSeparator()));
            timekeeper.logResult(sb.toString());
            timekeeper.logResult(FINISH_COMMAND);
        } catch (ValidationException e) {
            timekeeper.logResult(e.getMessage());
        }
    }
    
     public void read(HashMap structure, HashMapOa structure2, java.util.HashMap structure3, int turn, int counter)
    {
        int k = 0;
        try {
            //
            Scanner in = new Scanner(new File("C:\\Users\\Tomas\\Desktop\\Java laboratoriniai\\Lab3\\Lab3_MaisosLenteles\\data\\zodynas.txt"));
            while(in.hasNext())
            {
                String value = in.nextLine();
                if (counter != k) {
                    if(turn == 0)
                    structure.put(value, value);
                    else if(turn == 1)
                        structure2.put(value, value);
                    else
                        structure3.put(value, value);
                    k++;
                }
            }
            in.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SimpleBenchmark.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
