/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ktu.ds.lab3.Staskevicius;

import edu.ktu.ds.lab3.utils.HashType;
import edu.ktu.ds.lab3.utils.Ks;
import edu.ktu.ds.lab3.utils.ParsableHashMap;
import edu.ktu.ds.lab3.utils.ParsableMap;
import java.util.Locale;
import java.util.Set;

/**
 *
 * @author Tomas
 */
public class ManualTest {
       public static void main(String[] args) {
        Locale.setDefault(Locale.US); // suvienodiname skaičių formatus
        executeTest();
    }

    public static void executeTest() {
        Device d1 = new Device("Samsung", "Galaxy S8", 2016, 5.3, 700);
        Device d2 = new Device("Samsung Galaxy S10 2017 7.0 1499.99");
        Device d3 = new Device("Google Watch 2 2019 1.5 399.89");
        Device d4 = new Device("Redmi Node5 2017 5.8 181.11");
        Device d5 = new Device.Builder().buildRandom();
        Device d6 = new Device("LG L70 2015 4.5 80.31");
        Device d7 = new Device("Apple Ipod 3 2012 4.5 299.20");
        
        
        // Raktų masyvas
        String[] deviceIds = {"TA156", "TA102", "TA178", "TA171", "TA105", "TA106", "TA107", "TA108"};
        int id = 0;
        ParsableMap<String, Device> devicesMap
                = new ParsableHashMap<>(String::new, Device::new, HashType.DIVISION);

        // Reikšmių masyvas
        Device[] devices = {d1, d2, d3, d4, d5, d6, d7};
        for (Device d : devices) {
            devicesMap.put(deviceIds[id++], d);
        }
        devicesMap.println("Porų išsidėstymas atvaizdyje pagal raktus");
        Ks.oun("Ar egzistuoja pora atvaizdyje?");
        Ks.oun(devicesMap.contains(deviceIds[6]));
        Ks.oun(devicesMap.contains(deviceIds[7]));
        Ks.oun("Pašalinamos poros iš atvaizdžio:");
        Ks.oun(devicesMap.remove(deviceIds[1]));
        Ks.oun(devicesMap.remove(deviceIds[6]));
        devicesMap.println("Porų išsidėstymas atvaizdyje pagal raktus");
        Ks.oun("Atliekame porų paiešką atvaizdyje:");
        Ks.oun(devicesMap.get(deviceIds[2]));
        Ks.oun(devicesMap.get(deviceIds[7]));
        Ks.oun("Išspausdiname atvaizdžio poras String eilute:");
        Ks.ounn(devicesMap);
        
        Ks.oun("Mano metodai---------------------------------------");
        Ks.oun("containsValue()");
        
        Ks.oun("d1 " + devicesMap.containsValue(d1));
        Ks.oun("d2 " + devicesMap.containsValue(d2));
        Ks.oun("d3 " + devicesMap.containsValue(d3));
        Ks.oun("d4 " + devicesMap.containsValue(d4));
        Ks.oun("d5 " + devicesMap.containsValue(d5));
        Ks.oun("d6 " + devicesMap.containsValue(d6));
        Ks.oun("d7 " + devicesMap.containsValue(d7));
        
        Ks.oun(" ");
        Ks.oun("putIfAbsent()");
        Ks.oun("Prieš: ");
        Ks.ounn(devicesMap);
        devicesMap.putIfAbsent("TA156", d7);
        devicesMap.putIfAbsent("TA107", d2);
        Ks.oun("Dedami TA156 ir TA107");
        Ks.oun("Po: ");
        Ks.ounn(devicesMap);
        Ks.oun(" ");
        Ks.oun("numberOfEmpties: " + devicesMap.numberOfEmpties());
        Ks.oun(" ");
        Ks.oun("keySet()");
        Set keys = devicesMap.keySet();
        Ks.oun(keys);
        
        Ks.oun(" ");
        Ks.oun("replace()");
        Ks.oun("Prieš: ");
        Ks.ounn(devicesMap);
        devicesMap.replace("TA156", d7);
        Ks.oun("Apkeičiamas TA156 su Apple Ipod");
        Ks.oun("Po: ");
        Ks.ounn(devicesMap);
    }
}
