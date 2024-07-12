package com.revature.AKBanking.util.interfaces;

import java.util.Scanner;

@FunctionalInterface
public interface ScannerLooper<type> {
    type getNext(Scanner scanner, String repeatMessage);
}
