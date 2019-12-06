/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ktu.ds.lab3.Staskevicius;

import edu.ktu.ds.lab3.gui.ValidationException;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;

/**
 *
 * @author Tomas
 */
public class DeviceGenerator {
    private static final String ID_CODE = "TA";      //  ***** Nauja
    private static int serNr = 10000;               //  ***** Nauja

    private Device[] devices;
    private String[] keys;

    private int currentDeviceIndex = 0, currentDeviceIdIndex = 0;

    public static  Device[] generateShuffleDevices(int size) {
         Device[] cars = IntStream.range(0, size)
                .mapToObj(i -> new  Device.Builder().buildRandom())
                .toArray(Device[]::new);
        Collections.shuffle(Arrays.asList(cars));
        return cars;
    }

    public static String[] generateShuffleIds(int size) {
        String[] keys = IntStream.range(0, size)
                .mapToObj(i -> ID_CODE + (serNr++))
                .toArray(String[]::new);
        Collections.shuffle(Arrays.asList(keys));
        return keys;
    }

    public  Device[] generateShuffleDevicesAndIds(int setSize,
            int setTakeSize) throws ValidationException {

        if (setTakeSize > setSize) {
            setTakeSize = setSize;
        }
        devices = generateShuffleDevices(setSize);
        keys = generateShuffleIds(setSize);
        this.currentDeviceIndex = setTakeSize;
        return Arrays.copyOf(devices, setTakeSize);
    }

    // Imamas po vienas elementas iš sugeneruoto masyvo. Kai elementai baigiasi sugeneruojama
    // nuosava situacija ir išmetamas pranešimas.
    public Device getDevice() {
        if (devices == null) {
            throw new ValidationException("carsNotGenerated");
        }
        if (currentDeviceIndex < devices.length) {
            return devices[currentDeviceIndex++];
        } else {
            throw new ValidationException("allSetStoredToMap");
        }
    }

    public String getDeviceId() {
        if (keys == null) {
            throw new ValidationException("carsIdsNotGenerated");
        }
        if (currentDeviceIdIndex < keys.length) {
            return keys[currentDeviceIdIndex++];
        } else {
            throw new ValidationException("allKeysStoredToMap");
        }
    }
}
