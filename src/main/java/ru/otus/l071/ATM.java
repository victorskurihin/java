package ru.otus.l071;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * The   Automatic  Teller  Machine   class   accepts   banknotes   of different
 * denominations (for each denomination there is a cell); hand out the requested
 * amount with a minimum number of banknotes or an error if  the amount  can not
 * be issued; gives  the amount of  the balance of cash.
 * The Automatic Teller Machine have the cash dispenser as particular place.
 */
public class ATM extends ATMOriginator implements ATMObservable {
    List<Observer> observers = new LinkedList<>();

    /**
     * This helper static method generate random state of the dispenser in ATM.
     * @return the dispenser as SortedList
     */
    public static SortedMap<Bond, Integer> generateRandomState() {
        final int LIMIT_BANKNOTES = 20;
        SortedMap<Bond, Integer> result = new TreeMap<>(
            (Bond b1, Bond b2) -> b2.value() - b1.value() // reverse sorting
        );

        for (Bond denomination : Bond.values()) {
            Random random = new Random();
            int randomInt = random.nextInt(LIMIT_BANKNOTES) + LIMIT_BANKNOTES;
            result.put(denomination, randomInt);
        }
        return result;
    }

    private Logger logger = LogManager.getLogger(getClass());

    /**
     * This helper method return the appropriate format String with  the amount.
     * @param amount value for the format String
     * @return the appropriate format String
     */
    private String depositString(int amount) {
        return String.format(
            "The machine deposited amount %d to dispenser %s",
            amount, dispensers
        );
    }

    /**
     * This method put a sum of money placed in a bank account.
     * The sum is equals notes*denomination.
     * @param notes a piece of paper money
     * @param denomination is a proper description of a currency amoun
     */
    public void deposit(int notes, Bond denomination) {
        int old = dispensers.getOrDefault(denomination, 0);
        dispensers.put(denomination, old + notes);
        logger.info(depositString(notes * denomination.value()));
    }

    /**
     * This method return a statement of the assets.
     * @return the balance
     */
    public int balance() {
        return  dispensers
                .entrySet()
                .stream()
                .mapToInt(entry -> entry.getValue() * entry.getKey().value())
                .sum();
    }

    /**
     * This helper method return the appropriate format String with  the amount.
     * @param amount value for the format String
     * @return the appropriate format String
     */
    private String dispenseString(int amount) {
        return String.format("The machine dispensed %d", amount);
    }

    /**
     * This helper method return the appropriate format String with  the amount.
     * @param amount value for the format String
     * @return the appropriate format String
     */
    private String notDispenseString(int amount) {
        return String.format(
            "Can not dispense amount %d by dispenser %s", amount, dispensers
        );
    }

    /**
     * This utility method inspect the dispensers for
     * @param amount
     * @return
     */
    Map<Bond, Integer> pickUp(int amount) {
        Map<Bond, Integer> tryResult = new HashMap<>();
        int remaining = amount;

        for (Bond denomination : dispensers.keySet()) {
            int thereIs = dispensers.get(denomination);
            int wantToDispense = remaining / denomination.value();
            int toDispense = thereIs > wantToDispense
                           ? wantToDispense
                           : thereIs;
            tryResult.put(denomination, toDispense);
            remaining -= denomination.value() * toDispense;
        }

        if (remaining > 0) {
            throw new NotDispenseAmountExecption(notDispenseString(amount));
        }

        return tryResult;
    }

    /**
     * This method hand out (something) from a particular place.
     * @param amount for hand out
     * @return collection of banknotes
     */
    public Map<Bond, Integer> withdraw(int amount) {
        try {
            Map<Bond, Integer> withdrawal = pickUp(amount);

            for (Map.Entry<Bond, Integer> entry : withdrawal.entrySet()) {
                dispensers.compute(
                    entry.getKey(),
                    (key, oldValue) -> oldValue - entry.getValue()
                );
            }
            logger.info(dispenseString(amount));
            notice();
            return withdrawal;

        } catch (Throwable e) {
            logger.warn(notDispenseString(amount));
            logger.error(e);
        }
        return new HashMap<>();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int getStatus() {
        return balance();
    }

    @Override
    public void attach(Observer o) {
        if ( ! observers.contains(o)) {
            observers.add(o);
        }
    }

    @Override
    public void detach(Observer o) {
        observers.remove(o);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void notice() {
        for (Observer o : observers) {
            o.update(this);
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF