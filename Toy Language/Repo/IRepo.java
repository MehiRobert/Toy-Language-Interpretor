package Repo;

import Model.PrgState;

import java.io.IOException;
import java.util.List;

public interface IRepo {
    void addPrg(PrgState newState);

    void logPrgStateExec(PrgState prgState) throws IOException;

    List<PrgState> getPrgList();

    void setPrgList(List<PrgState> prgList);
}
