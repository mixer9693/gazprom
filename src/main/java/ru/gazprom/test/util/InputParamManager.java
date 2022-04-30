package ru.gazprom.test.util;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class InputParamManager {

    public InputParams parse(String[] args) throws IllegalArgumentException {
        if (args.length != 2){
            String mes = String.format("Wrong arguments number: required 2, given %s", args.length);
            throw new IllegalArgumentException(mes);
        }
        int amount;
        try {
            amount = Integer.parseInt(args[0]);
        } catch (NumberFormatException e){
            String mes = String.format("Amount should be integer \"%s\" given", args[0]);
            throw new IllegalArgumentException(mes);
        }
        String fileName = args[1];

        return new InputParams(amount, fileName);
    }

    public void validate(InputParams inputParams) throws IllegalArgumentException {
        validateAmount(inputParams.getAmount());
        validateFileName(inputParams.getFilename());
    }

    public void validateAmount(int amount) throws IllegalArgumentException {
        if (amount <= 0){
            String mes = "Amount must be more then 0";
            throw new IllegalArgumentException(mes);
        }
    }

    public void validateFileName(String filename){
        try {
            File file = new File(filename);
            boolean created = false;
            try {
                created = file.createNewFile();
            } finally {
                if (created) {
                    file.delete();
                }
            }
        } catch (IOException e){
            String mes = String.format("Filename %s is incorrect", filename);
            throw new IllegalArgumentException(mes);
        }
    }
}
