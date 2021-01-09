package Repo;

import Model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Repo implements IRepo{
    private List<PrgState> myPrgStates;
    String filepath;
    public Repo(String filepath) { myPrgStates = new ArrayList<>();
                                    this.filepath = filepath;}


    @Override
    public void addPrg(PrgState newState) {
            myPrgStates.add(newState);
    }

    @Override
    public void logPrgStateExec(PrgState prgState) throws IOException {
        try{
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(filepath,true)));
            logFile.write(prgState.toString() + "\n");
            logFile.print("");
            logFile.close();

        }
        catch (IOException e){
            e.getStackTrace();
        }



    }

    @Override
    public List<PrgState> getPrgList() {
        return myPrgStates;
    }

    @Override
    public void setPrgList(List<PrgState> prgList) {
        this.myPrgStates = prgList;
    }

}
